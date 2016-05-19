package shoppinglistservlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShoppingListServlet extends HttpServlet{

	public void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException{
		try {
			req.setCharacterEncoding("UTF-8");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		ShoppingListTable tbl;
		tbl = new ShoppingListTable();
		List<Goods> list = new ArrayList<Goods>();
		try {
			list = tbl.getAllYetPurchased();
			for(int i = 0 ; i < list.size() ; i++){
				req.setAttribute("UUID"+i , list.get(i).getUuid());
				req.setAttribute("name"+i , list.get(i).getName());
				req.setAttribute("num"+i , list.get(i).getNumber());
				req.setAttribute("memo"+i , list.get(i).getMemo());
			}
			list.size();
			req.setAttribute("size", list.size());
			getServletConfig().getServletContext().getRequestDispatcher("/ShoppingList.jsp").forward(req , res);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		getServletConfig().getServletContext().getRequestDispatcher("/ShoppingList.jsp").forward(req , res);
	}

	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException{
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String uuid = null;
		if("reg".equals(req.getParameter("btnRegister"))){ //登録
			String name = req.getParameter("txtGoods");
			Integer number = 0;
			if("" == req.getParameter("txtNumber")){
				number = 0;
			}
			else
			{
				number = Integer.parseInt(req.getParameter("txtNumber"));
			}
			String memo = req.getParameter("txtMemo");
			registerGoods(name , number , memo);
		}
		else if("del".equals(req.getParameter("btnDelete")))//削除
		{
			uuid = req.getParameter("rdoselect");
			deleteGoods(uuid);
		}
		else if(null != (uuid = req.getParameter("btnPurchase")))//購入済みにする
		{
			purchaseGoods(uuid);
		}
		else if("upd".equals(req.getParameter("btnUpdate")))//更新
		{
			if(null == req.getParameter("rdoselect")){
				getServletConfig().getServletContext().getRequestDispatcher("/ShoppingList.jsp").forward(req , res);
			}
			else
			{
				uuid =req.getParameter("rdoselect");
				try {
					ShoppingListTable tbl = new ShoppingListTable();
					Goods goods = tbl.get(uuid);
					req.setAttribute("hidUpdateUuid" , uuid);
					req.setAttribute("updName" , goods.getName());
					req.setAttribute("updNumber" , goods.getNumber());
					req.setAttribute("updMemo" , goods.getMemo());
					tbl = new ShoppingListTable();
					List<Goods> list = new ArrayList<Goods>();
					list = tbl.getAllYetPurchased();
					for(int i = 0 ; i < list.size() ; i++){
						req.setAttribute("UUID"+i , list.get(i).getUuid());
						req.setAttribute("name"+i , list.get(i).getName());
						req.setAttribute("num"+i , list.get(i).getNumber());
						req.setAttribute("memo"+i , list.get(i).getMemo());
					}
					list.size();
					req.setAttribute("size", list.size());
					getServletConfig().getServletContext().getRequestDispatcher("/ShoppingList.jsp").forward(req , res);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				catch (ServletException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		else if("upd".equals(req.getParameter("btnRegister")))
		{
			uuid = req.getParameter("hidUpdateUuid");
			String name = req.getParameter("txtGoods");
			Integer num = Integer.parseInt(req.getParameter("txtNumber"));
			String memo = req.getParameter("txtMemo");
			updateGoods(uuid , name , num , memo);
		}
		getServletConfig().getServletContext().getRequestDispatcher("/ShoppingList.jsp").forward(req , res);
	}

	private void registerGoods(String name , int number , String memo){

		/*入力された値を新しいデータとして登録する */

		Goods goods;
		goods = new Goods(name , number , memo);
		ShoppingListTable tbl = new ShoppingListTable();
		try {
			tbl.add(goods);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void updateGoods(String uuid, String name, int number, String memo) {

		/*  指定されたUUIDのデータを入力された値に更新し、更新日時を付与する*/

		Goods goods;
		goods = new Goods(uuid , name , number , memo);
		ShoppingListTable tbl;
		tbl = new ShoppingListTable();
		try {
			tbl.update(goods);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void deleteGoods(String uuid){

		/* 指定されたUUIDのデータを削除する */

		try {
			ShoppingListTable tbl;
			tbl= new ShoppingListTable();
			tbl.delete(uuid);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void purchaseGoods(String uuid) {

		/* 指定されたUUIDのデータを購入済みにする*/

		try {
			ShoppingListTable tbl;
			tbl = new ShoppingListTable();
			Goods goods;
			goods = tbl.get(uuid);
			String a =goods.getName();
			int b =goods.getNumber();
			String c = goods.getMemo();
			String d = goods.getRegisteredDatetime().toString();
			String e = "pdateupdate";
			String f;
			if(null == goods.getUpdateDatetime()){
				f = null;
			}
			else
			{
				f = goods.getUpdateDatetime().toString();
			}
			Goods newgoods = new Goods(uuid ,a,b,c,d,e,f);
			tbl.update(newgoods);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
