<%@ page contentType= "text/html;charset=UTF-8"%>

<html>
  <head>
    <title>買い物リスト</title>
  </head>
  <body>
    <h1>買い物リスト</h1>
    <%if(null != request.getAttribute("hidUpdateUuid")){%>
        <form method = "POST" action = "ShoppingListServlet">
          <div style = "position : absolute ; left : 10px;">商品名</div>
          <div style = "position : absolute ; left : 190px;">個数</div>
          <div style = "position : absolute ; left : 265px;">メモ</div>
          <BR>
          <input type = "hidden" name = "hidUpdateUuid" value = "<%=request.getAttribute("hidupdateUuid")%>">
          <input type = "text" name = "txtGoods" value = "<%=request.getAttribute("updName")%>" required  maxlength = "30" style= "position : absolute ; left : 10px;"/>
          <input type = "number" name = "txtNumber" value = "<%=request.getAttribute("updNumber")%>" min = "0" max = "999" style = "position : absolute ; left : 190px ;"/>
          <input type = "text" name = "txtMemo" value = "<%=request.getAttribute("updMemo")%>" maxlength = "200" style= "position : absolute ; left : 265px ;"/><BR><BR>
          <button type = "submit" value = "upd" name = "btnRegister">登録</button><br><br>
        </form>
    <%}else{%>
      <form method = "POST" action = "ShoppingListServlet">
        <div style = "position : absolute ; left : 10px;">商品名</div>
        <div style = "position : absolute ; left : 190px;">個数</div>
        <div style = "position : absolute ; left : 265px;">メモ</div>
        <BR>
        <input type = "text" name = "txtGoods" required  maxlength = "30" style= "position : absolute ; left : 10px;"/>
        <input type = "number" name = "txtNumber"  min = "0" max = "999" style = "position : absolute ; left : 190px ;"/>
        <input type = "text" name = "txtMemo" maxlength = "200" style= "position : absolute ; left : 265px ;"/><BR><BR>
        <button type = "submit" value = "reg" name = "btnRegister">登録</button><br><br>
      </form>
    <%} %>
    <h2>未購入商品一覧</h2>
    <form method = "POST" action= "ShoppingListServlet">
      <table border = "1" width = "800">
        <tr>
          <th></th>
          <th>商品名</th>
          <th>個数</th>
          <th>メモ</th>
        </tr>
        <% Object size = request.getAttribute("size");
        String s;
        if(size != null){
          s = size.toString();
          int j = Integer.parseInt(s);
          for(int i = 0 ;i < j ; i++){ %>
        <tr>
          <th><input type = "radio" value = "<%=request.getAttribute("UUID"+i)%>" name = "rdoselect">
          <input type = "hidden" value = "<%=request.getAttribute("UUID"+i)%>" name = "hiduuid"></th>
          <th><input type = "text" value = "<%=request.getAttribute("name"+i)%>" name = "lblGoods<%=request.getAttribute("UUID"+i)%>" readonly></th>
          <th><input type = "number" value = "<%=request.getAttribute("num"+i)%>" name = "lblNumber<%=request.getAttribute("UUID"+i)%>" required min = "0" max = "999"></th>
          <th><input type = "text" value = "<%=request.getAttribute("memo"+i)%>" name = "lblMemo<%=request.getAttribute("UUID"+i)%>"></th>
          <th><button type = "submit" value = "<%=request.getAttribute("UUID"+i)%>" name = "btnPurchase">購入済</button></th>
        </tr>
      <%} %>
      </table>
      <button type = "submit" value = "upd" name = "btnUpdate">修正</button>
      <button type = "submit" value = "del"name = "btnDelete">削除</button>
    </form>
    <%}else if(null != request.getAttribute("hidUpdateUuid")){%>

    <%}else{%>
    <form method="GET" action="ShoppingListServlet" name ="F" >
       <meta http-equiv="refresh" content="0;URL=http://localhost:8080/shoppinglistservlet/ShoppingListServlet">
    </form>
    <%}%>
  </body>
</html>