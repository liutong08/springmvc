<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/add" method="post" enctype="multipart/form-data">
		<table border="1px" >
			<tr>
				<td style="text-align: right:">id:</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td style="text-align: right:">name:</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td style="text-align: right:">age:</td>
				<td><input type="text" name="age"></td>
			</tr>
			<tr>
				<td style="text-align: right:">mobile:</td>
				<td><input type="text" name="mobile"></td>
			</tr>
			<tr>
				<td style="text-align: right:">path:</td>
				<td><input type="file" name="file"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="添加">
					<input type="reset" value="重置">
					<input type="button" onclick="backtoList()" value="返回用户列表">
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
function backtoList(){
	window.location.href="/backtoList";
}
</script>
</html>