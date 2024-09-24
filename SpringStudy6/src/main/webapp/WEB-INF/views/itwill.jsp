<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>views/itwill.jsp</h1>
	
	<!-- jsp 내장객체를 사용 -->
	<h2> 파라메터 데이터 (paramater)</h2>
	<h2> msg (paramater) : <%=request.getParameter("msg") %></h2>
	<h2> msg (el) : ${param.msg }</h2>
	
	<hr>
	<h2> 영역객체 데이터(attribute)</h2>
	<h2> msg (el) : ${msg }</h2>
	<h2> msg (el) : ${requestScope.msg }</h2>
	
	@ModelAttribute("msg") String msg
	  => request.getParameter("msg") + request.setAttribute("msg",값)
	
	<hr>
	<h2>MemberVO 정보출력</h2>
	<!-- ${requestScope} -->
	<h2>${memberVO}</h2>
	<h2>${adminVO}</h2>
	
	<hr>
	userid : ${userid }
	userid : ${param.userid }
	userpw : ${userpw }
	userpw : ${param.userpw }
	
	
	
</body>
</html>