<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<fieldset>
		<legend> 회원정보 삭제</legend>
		<form action="/member/delete" method="post">
		<!-- 아이디 :  -->
		<input type="hidden" name="userid" value="${id }">
		비밀번호 : <input type="password" name="userpw"> <hr>
		<input type="submit" value="회원정보삭제">
		</form>
	</fieldset>
	
</body>
</html>