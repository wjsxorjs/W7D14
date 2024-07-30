<%@page import="java.util.List"%>
<%@page import="mybatis.vo.CommVO"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 보기</title>
<style type="text/css">
	#bbs table { 
	    width:580px;
	    margin-left:10px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	#bbs table td {
	    text-align:left;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	
	.odd {background:silver}
	
	
	.list_bg{
		background: rgb(249,250,255);
		padding: 10px;
	}
	
	.list_item{
		background: #fff;
		border-radius: 10px;
		padding: 20px;
		margin-bottom: 10px;
		box-shadow: rgb(204,204,204) 0px 2px 4px 0px;
	}
	
		
</style>
<%
Object obj = request.getAttribute("bvo");
	BbsVO bvo = null;
	if(obj != null){
		bvo = (BbsVO) obj;
	} else{
		response.sendRedirect("Controller");
	}
%>
</head>
<body>
	<div id="bbs">
	<form method="post" >
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><%=bvo.getSubject()%></td>
				</tr>

				<tr>
					<th>첨부파일:</th>
					<td>
					<%
					if(bvo.getFile_name() != null){
					%>
					<a href="javascript:down('<%=bvo.getFile_name()%>')"> <%=bvo.getOri_name()%> </a>
					<%
					} else{
					%>
					 -
					<%
					}
					%>
					</td>
				</tr>
				
				<tr>
					<th>이름:</th>
					<td><%=bvo.getWriter()%></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><%=bvo.getContent()%></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="button" onclick="doBbs('edit')" value="수정"/>
						<input type="button" onclick="doBbs('del')" value="삭제"/>
						<input type="button" onclick="doBbs('list')" value="목록"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<form method="post" action="Controller">
		이름:<input type="text" name="writer"/><br/>
		내용:<textarea rows="4" cols="55" name="comm"></textarea><br/>
		비밀번호:<input type="password" name="pwd"/><br/>
		
		
		<input type="hidden" name="b_idx" value="${param.b_idx}">
		<input type="hidden" name="bname" value="${param.bname}">
		<input type="hidden" name="cPage" value="${param.cPage}"/>
		<input type="hidden" name="type" value="comm"/>
		<input type="submit" value="저장하기"/> 
	</form>
	
	댓글들<hr/>
		<div class="list_bg">
		<%
		List<CommVO> c_list = bvo.getC_list();
			for(CommVO cvo: c_list){
		%>
			
				<div class="list_item">
					이름:<%=cvo.getWriter() %> &nbsp;&nbsp;
					날짜:<%=cvo.getWrite_date() %><br/>
					내용:<%=cvo.getContent() %>
				</div>
			
		<%} %>
		</div>
	</div>
	
	<form name="frm" action="Controller" method="post">
		<input type="hidden" name="type" value="del" />
		<input type="hidden" name="fname"/>
		<input type="hidden" name="source" value="view"/>
		<input type="hidden" name="bname" value="${param.bname}" />
		<input type="hidden" name="b_idx" value="${param.b_idx}" />
		<input type="hidden" name="cPage" value="${param.cPage}" />
	
	</form>
	
	
	
	<script>
		function doBbs(type){
			let res = true;
			document.frm.type.value = type;
			
			if(type == "del"){
				res = confirm("삭제하시겠습니까?");
			}
			if(res){
				document.frm.submit();
			}
		}
		
		function down(fname){
			// 인자로 사용자가 클릭한 파일명을 받는다.
			// 이것을 현재 문서 안에 있는 frm이라는 폼 객체에 이름이
			// fname이라는 hidden요소의 값(value)으로 지정해둔다.
			document.frm.fname.value = fname;
			document.frm.type.value = "down";
			document.frm.submit();
		}
	</script>
</body>
</html>