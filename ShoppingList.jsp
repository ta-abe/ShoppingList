<%@ page contentType= "text/html;charset=UTF-8"%>

<html>
<head>
<title>買い物リスト</title>
</head>
<body onLoad="document.F.submit():">
<h1>買い物リスト</h1>
<form method = "POST"action= "ShoppingListServlet">
商品名<input type ="text" name ="txtGoods"required   style="position: absolute; left: 75px; "/><BR><BR>
個数<input type ="number" name ="txtNumber" required min = "1" max ="32767" style="position:absolute; left: 75px;"/><BR><BR>
メモ<input type ="text" name ="txtMemo"  maxlength ="200" style="position: absolute; left: 75px;"/><BR><BR>
<input type ="submit" value = "登録" name = "btnRegister">
<input type ="reset" value ="リセット">
<br><br>
<h2>未購入商品一覧</h2>
</form>

<form method ="POST" action="ShoppingListServlet">
<table border = "1" width ="800" >
<tr><th></th><th>商品名</th><th>個数</th><th>メモ</th></tr>
<% Object size = request.getAttribute("size");
String s;
if (size != null){
	s = size.toString();
	int j = Integer.parseInt(s);
for(int i = 0 ;i < j ; i++){ %>
<tr>
<th><input type="radio"  value = "<%=request.getAttribute("UUID"+i) %>" name= "rdoselect"></th>
<th><input type="text" value ="<%=request.getAttribute("name"+i) %>" name = "lblGoods<%=request.getAttribute("UUID"+i)%>" required></th>
<th><input type="number" value ="<%=request.getAttribute("num"+i) %>" name = "lblNumber<%=request.getAttribute("UUID"+i)%>" required min = "1" max ="32767"></th>
<th><input type="text" value ="<%=request.getAttribute("memo"+i) %>" name = "lblMemo<%=request.getAttribute("UUID"+i)%>"></th>
<th><button type ="submit" value ="<%=request.getAttribute("UUID"+i) %>" name ="upd">更新</button></th>
<th><button type ="submit" value ="<%=request.getAttribute("UUID"+i) %>" name ="pur">購入済み</button></th>
</tr>
<%} %>
</table>
<input type = "submit" value = "削除"name = "delete">
</form>
<%}else{%>
<form method="GET" action="ShoppingListServlet" name ="F" >
<meta http-equiv="refresh" content="0;URL=http://localhost:8080/shoppinglistservlet/ShoppingListServlet">
</form>
<%}%>
</body>
</html>