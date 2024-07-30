package com.sist.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.sist.app.service.BbsService;
import com.sist.app.service.CommService;
import com.sist.app.util.FileRenameUtil;
import com.sist.app.util.Paging2;
import com.sist.app.vo.BbsVO;
import com.sist.app.vo.CommVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class BbsController {
    @Autowired
    BbsService b_service;

    @Autowired
    CommService c_service;

    @Autowired
    private ServletContext application;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

	@Autowired
	private HttpSession session;

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${server.upload.path}")
    private String upload_path; // 첨부파일 추가할 때 저장할 위치
    
    @Value("${server.editor_img.path}")
	private String editor_img; // 썸머노트 이미지 추가할 때 저장할 위치


    @RequestMapping("list")
    public ModelAndView list(@RequestParam String bname, String searchType, String searchValue, String cPage) {
        ModelAndView mv = new ModelAndView();
        int nowPage = 1;

        if(cPage != null){
            nowPage = Integer.parseInt(cPage);
        }

        if(bname == null || bname.trim().length()==0){
            bname = "bbs";
        }

        int totalRecord = b_service.getCount(bname, searchType, searchValue);

        // 위에서 전체 게시물의 수를 얻었다면 이제 페이징 기법을
        // 사용하는 객체를 생성할 수 있다.
        Paging2 page = new Paging2(7, 5, totalRecord, nowPage, bname);

        nowPage = page.getNowPage();

        // 페이징기법의 HTML코드를 얻어낸다.
        String pageCode = page.getSb().toString();

        // 뷰 페이지에서 표현할 목록 가져오기
        int begin = page.getBegin();
        int end = page.getEnd();

        BbsVO[] b_ar = b_service.getList(bname, searchType, searchValue, begin, end);

        // 뷰 페이지에서 표현하고자 하는 값들 저장
        mv.addObject("b_ar", b_ar);
        mv.addObject("b_page", page);
        mv.addObject("pageCode", pageCode);
        mv.addObject("totalRecord", totalRecord);
        mv.addObject("numPerPage", page.getNumPerPage());
        mv.addObject("nowPage", nowPage);
        mv.addObject("bname", bname);
        mv.setViewName(bname+"/list");

        return mv;
    }


    @GetMapping("write")
    public String write(String bname, String cPage) {
        return bname+"/write";
    }

    @PostMapping("write")
    public ModelAndView write(BbsVO bvo) {
        ModelAndView mv = new ModelAndView();


        MultipartFile file = bvo.getFile();

        // 파일이 첨부되지 않았다고 해도 file은 null이 아니다.
        // 그러므로 null과 비교하는 것이 아닌 용량으로 확인해야한다.
        if(file.getSize()>0){
            String realPath = application.getRealPath(upload_path);

            String oname = file.getOriginalFilename();
            bvo.setOri_name(oname);
            
            String fname = FileRenameUtil.checkSameFileName(oname, realPath);
            
            try {
                file.transferTo(new File(realPath, fname));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // 여기까지 서버에 업로드를 수행한 것이다.
            // 이제 DB에 저장하는 일을 하자.
            bvo.setFile_name(fname);

        }

        bvo.setIp(request.getRemoteAddr());

        // 필요한 내용 추가 완료 후
        // DB에 저장
        b_service.add(bvo);

        mv.setViewName("redirect:/list?bname="+bvo.getBname());
        return mv;
    }

    @PostMapping("saveImg")
    @ResponseBody
    public Map<String, String> saveImg(MultipartFile s_file) {
        Map<String, String> i_map = new HashMap<>();

        if(s_file.getSize()>0){
            // 받은 파일을 저장할 editor_img의 절대경로를 구한다.
            String realPath = application.getRealPath(editor_img);
            String oname = s_file.getOriginalFilename();

            String fname = FileRenameUtil.checkSameFileName(oname, realPath);

            try {
                s_file.transferTo(new File(realPath, fname));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 업로드된 파일의 경로를 반환하기 위해
            // 현재 서버의 url을 알아내자
            String c_path = request.getContextPath();
            i_map.put("url",c_path + editor_img);
            i_map.put("fname",fname);
        }

        return i_map;
    }


    @RequestMapping("view")
    public ModelAndView view(String bname, int b_idx) {
        ModelAndView mv = new ModelAndView();

        List<Integer> b_list;

        Object obj = session.getAttribute("b_list");

        if(obj != null){
            b_list = (List<Integer>) obj;
        } else {
            b_list = new ArrayList<>();
            session.setAttribute("b_list",b_list);
        }

        if(!b_list.contains(b_idx)){
            b_service.udtHit(b_idx);
            b_list.add(b_idx);
        } 
        

        BbsVO bvo = b_service.getBbs(b_idx);


        mv.addObject("bvo", bvo);
        mv.setViewName(bname+"/view");
        return mv;
    }
    
    
    @PostMapping("edit")
    public ModelAndView edit(String bname, String content, int b_idx, String cPage) {
        ModelAndView mv = new ModelAndView();
        if(request.getContentType().startsWith("application")){ // 수정으로 이동
            BbsVO bvo = b_service.getBbs(b_idx);

            mv.addObject("bvo", bvo);
            mv.setViewName(bname+"/edit");
        } else { // 수정한 것 적용
            b_service.udtBbs(content, b_idx);
            mv.setViewName("redirect:/view?bname="+bname+"&cPage="+cPage+"&b_idx="+b_idx);
        }
        return mv;
    }
    
    @PostMapping("del")
    public String del(String bname, int b_idx) {
        
        return "redircet:/list?bname="+bname;
    }

    @PostMapping("comm")
    public String comm(CommVO cvo, String bname, String cPage) {
        
        cvo.setIp(request.getRemoteAddr());

        c_service.commAdd(cvo);

        return "redirect:/view?bname="+bname+"&cPage="+cPage+"&b_idx="+cvo.getB_idx();
    }

    @PostMapping("download")
    public ResponseEntity<Resource> download(String file_name) {
        // 파일들이 위치하는 곳을 절대경로화 시킨다.
        String realPath = application.getRealPath(upload_path+"/"+file_name);
        File f = new File(realPath);

        if(f.exists()){
            byte[] buf = new byte[4096];
            int size = -1;

            // 다운로드에 필요한 스트림
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            // 보내기 할 때 필요한 스트림
            BufferedOutputStream bos = null;
                // 응답을 하는 것이 응답자의 컴퓨터로 다운로드를
                // 시켜야 하기 때문에 response를 통해 Outputstream을
                // 얻어내야 한다. 이때 response가 주는 스트림이
                // ServletOutputStream을 주기 때문에 선언되었다.
            ServletOutputStream sos = null;

            // 접속자 화면에 다운로드 창
            try {
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition",
                "attachment;filename="+new String(file_name.getBytes(), "8859_1"));
            
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);

                // response를 통해 이미 out이라는 스트림이 존재하므로
                // 다운로드 시키기 위해 스트림 준비
                sos = response.getOutputStream();
                bos = new BufferedOutputStream(sos);

                while ((size = bis.read(buf)) != -1) {
                    // 읽은 자원을 buf에 적재된 상태
                    // buf라는 배열에 있는 자원들을 쓰기
                    bos.write(buf,0,size);
                    bos.flush();
                }
            
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                try {
                    if(fis != null){
                        fis.close();
                    }
                    if(bis != null){
                        bis.close();
                    }
                    if(sos != null){
                        sos.close();
                    }
                    if(bos != null){
                        bos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }






        }
        return null;
    }
    

    
}
