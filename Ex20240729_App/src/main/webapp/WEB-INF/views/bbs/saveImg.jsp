<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Object obj = request.getAttribute("f_name");
	String f_name = null;
	if(obj != null){
		f_name = (String) obj;
%>
{
 "img_url": "<%=request.getContextPath() %>/editor_img/<%=f_name %>"
}
<%
}
%>