<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1px" style="text-align: center;">
		<tr>
			<th>id</th>
			<th>头像</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>手机号码</th>
			<th>操作</th>
		</tr>
		<c:forEach var="user" items="${userList }">
			<tr>
				<td>${user.id}</td>
				<td><img src="${user.path}" style="width:50px;height:50px;"></td>
				<td>${user.name}</td>
				<td>${user.age}</td>
				<td>${user.mobile}</td>
				<td><button onclick="updateUser(${user.id})">修改</button>
					<button onclick="deleteUser(${user.id})">删除</button>
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="button" onclick="addUser()" value="新增">
</body>
<script type="text/javascript">
function addUser(){
	window.location.href="/gotoAdd";
}
function updateUser(obj){
	window.location.href="/gotoUpdate/"+obj;
}
function deleteUser(obj){
	window.location.href="/gotoDelete/"+obj;
}
</script>
</html>