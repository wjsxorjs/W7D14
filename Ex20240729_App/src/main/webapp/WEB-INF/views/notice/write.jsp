<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기(공지사항)</title>
<link rel="stylesheet" href="resources/css/summernote-lite.css">
<%-- 현재 브라우저는 자신의 위치를 root로 인식하므로 --%>
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
	
		
</style>
<script type="text/javascript">
	function sendData(){
		for(var i=0 ; i<document.forms[0].elements.length ; i++){
			if(i>1){
				break;	// 에디터에 있는 컴포넌트들에 대한
						// 유효성검사 피하기 위해 탈출
			}
			if(document.forms[0].elements[i].value == ""){
				alert(document.forms[0].elements[i].name+
						"를 입력하세요");
				document.forms[0].elements[i].focus();
				return;//수행 중단
			}
		}

//		document.forms[0].action = "test.jsp";
		document.forms[0].submit();
	}
</script>
</head>

<body>
	<div id="bbs">
	<form action="write" method="post" 
	encType="multipart/form-data"> <%-- form에 파일을 첨부하게 될 때
										encType을 multipart로 지정 --%>
		<input type="hidden" name="bname" value="${param.bname }"/>
		<table summary="게시판 글쓰기">
			<caption>공지사항 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="subject" size="45"/></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12"/></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><textarea name="content" cols="50" 
							id="content" rows="8"></textarea></td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td><input type="file" name="file"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="보내기"
						onclick="sendData()"/>
						<input type="button" value="목록"
						onclick="javascript:location.href='list?bname=${param.bname}&cPage=${param.cPage}'"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<script src="resources/js/summernote-lite.js"></script>
	<script>
	$(function(){
		// 아이디가 content인 요소를 에디터로 표현
		$("#content").summernote({
			
			callbacks:{ // 특정한 사건이 발생했을 때 자동으로 호출되는 함수
				onImageUpload: function(files, editor){ // 이미지가 에디터에 추가될 때 
					for(let i=0; i<files.length; i++){
						sendImage(files[i], editor);
					}
					
				}
			}
		});
	});
		
	function sendImage(file, editor){
		let ff = new FormData();
		
		// 전송하고자 하는 이미지 파일을 파라미터로 설정
		ff.append("file",file);
		
		// 비동기식 통신
		$.ajax({
			url:"saveImg",
			data: ff,
			type: "post",
			contentType: false,
			processData: false,
			cache: false,
			dataType: "json",
		}).done(function(data){
			$("#content").summernote("editor.insertImage",data.url+"/"+data.fname);
		});
	}
		
	</script>
</body>
</html>
