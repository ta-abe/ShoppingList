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

/**
 *
 * @author t-abe
 */

public class ShoppingListServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req , HttpServletResponse res){
		try {
			req.setCharacterEncoding("UTF-8");
			ShoppingListTable tbl;
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
				req.setAttribute("size" , list.size());
				getServletConfig().getServletContext().getRequestDispatcher("/ShoppingList.jsp").forward(req , res);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
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

	@Override
	public void doPost(HttpServletRequest req , HttpServletResponse res){
		try {
			req.setCharacterEncoding("UTF-8");
			String uuid = null;
			if("reg".equals(req.getParameter("btnRegister"))){ //登録
				String name = req.getParameter("txtGoods");
				int number = 0;
				String num = req.getParameter("txtNumber");
				if("" == num){
					number = 0;
				}
				else
				{
					number = Integer.parseInt(num);
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
				if(null != req.getParameter("rdoselect")){
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
						req.setAttribute("size" , list.size());
					}
					catch (SQLException e)
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
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
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

	/**
	 * 入力された値を引数として受け取り新しいデータとして登録します
	 * @param name
	 * @param number
	 * @param memo
	 */
	private void registerGoods(String name , int number , String memo){
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

	/**
	 * 指定されたUUIDに対応するデータを入力された値に更新し、
	 * 更新日時に現在の時刻を付与します
	 * @param uuid
	 * @param name
	 * @param number
	 * @param memo
	 */
	private void updateGoods(String uuid , String name , int number , String memo) {
		try {
			ShoppingListTable tbl;
			tbl = new ShoppingListTable();
			Goods goods = tbl.get(uuid);
			String rdate = goods.getRegisteredDatetime();
			String pdate = goods.getPurchasedDatetime();
			String udate = goods.getUpdateDatetime();
			Goods newgoods = new Goods(uuid , name , number , memo , rdate , pdate , udate);
			tbl.update(newgoods);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 引数に指定されたUUIDのデータを削除します
	 * @param uuid
	 */
	private void deleteGoods(String uuid){
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

	/**
	 * 引数に指定されたUUIDのデータに現時刻を購入日時として付与します
	 * @param uuid
	 */
	private void purchaseGoods(String uuid) {
		try {
			ShoppingListTable tbl;
			tbl = new ShoppingListTable();
			Goods goods;
			goods = tbl.get(uuid);
			String a = goods.getName();
			int b = goods.getNumber();
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
			Goods newgoods = new Goods(uuid , a , b , c , d , e , f);
			tbl.update(newgoods);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
