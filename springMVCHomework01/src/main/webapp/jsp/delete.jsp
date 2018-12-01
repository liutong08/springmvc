<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${sessionScope.msg }<br>
<button onclick="backtoList()">返回用户列表</button>
</body>
<script type="text/javascript">
function backtoList(){
	window.location.href="/backtoList";
}
</script>
</html>