package com.sist.app.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BbsVO {
    private int rnum;

    private String  b_idx, subject, writer, content, file_name,
                    ori_name, write_date, ip, hit, bname, status;

    private List<CommVO> c_list; // 댓글 리스트

    private MultipartFile file; // 첨부파일
}
