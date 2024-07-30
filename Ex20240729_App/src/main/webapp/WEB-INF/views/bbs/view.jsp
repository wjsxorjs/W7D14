<%@page import="java.util.List"%>
<%@page import="mybatis.vo.CommVO"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 보기</title>
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
</head>
<body>
	<c:if test="${bvo == null }">
		<c:redirect url="list"/>
	</c:if>
	<div id="bbs">
	<form method="post" >
		<table summary="게시판 글보기">
			<caption>게시판 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td>${bvo.subject}</td>
				</tr>

				<tr>
					<th>첨부파일:</th>
					<td>
					<c:if test="${bvo.file_name ne null }">
						<a href="javascript:down('${bvo.file_name}')"> ${bvo.ori_name} </a>
					</c:if>
					<c:if test="${bvo.file_name eq null }">
						-
					</c:if>
					</td>
				</tr>
				
				<tr>
					<th>이름:</th>
					<td>${bvo.writer}</td>
				</tr>
				<tr>
					<th>내용:</th>
					<td>${bvo.content}</td>
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
	<form method="post" action="comm">
		이름:<input type="text" name="writer"/><br/>
		내용:<textarea rows="4" cols="55" name="content"></textarea><br/>
		비밀번호:<input type="password" name="pwd"/><br/>
		
		
		<input type="hidden" name="b_idx" value="${param.b_idx}">
		<input type="hidden" name="bname" value="${param.bname}">
		<input type="hidden" name="cPage" value="${param.cPage}"/>
		<input type="submit" value="저장하기"/> 
	</form>
	
	댓글들<hr/>
		<div class="list_bg">
			<c:forEach var="cvo" items="${bvo.c_list }">
				<div class="list_item">
					이름:${cvo.writer } &nbsp;&nbsp;
					날짜:${cvo.write_date }<br/>
					내용:${cvo.content }
				</div>
			</c:forEach>
		</div>
	</div>
	
	<form name="frm" action="Controller" method="post">
		<input type="hidden" name="type" value="del" />
		<input type="hidden" name="file_name"/>
		<input type="hidden" name="source" value="view"/>
		<input type="hidden" name="dir" value="/resources/upload"/>
		<input type="hidden" name="bname" value="${param.bname}" />
		<input type="hidden" name="b_idx" value="${param.b_idx}" />
		<input type="hidden" name="cPage" value="${param.cPage}" />
	
	</form>
	
	
	
	<script>
		function doBbs(type){
			let res = true;
			document.frm.action = type;
			
			if(type == "del"){
				res = confirm("삭제하시겠습니까?");
			}
			if(res){
				document.frm.submit();
			}
		}
		
		function down(file_name){
			// 인자로 사용자가 클릭한 파일명을 받는다.
			// 이것을 현재 문서 안에 있는 frm이라는 폼 객체에 이름이
			// fname이라는 hidden요소의 값(value)으로 지정해둔다.
			/*
			document.frm.file_name.value = file_name;
			document.frm.action = "FileDownload";
			document.frm.submit();
			*/
			location.href="FileDownload?dir=upload&filename="+file_name;
		}
	</script>
</body>
</html>