<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
姓名：<input type="text" name="name" value="${user.name }"><br>
年龄：<input type="text" name="age" value="${user.age }"><br>
省份：<input type="text" name="province" value="${user.province }"><br>

${requestScope.user.name}
${requestScope.user.age}
${requestScope.user.province}
</form>
</body>
</html>