package shoppinglistservlet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ShoppingListTable {
	public Statement stm;
	public ResultSet rs;
	Connection con = null;
	String url = "jdbc:mysql://localhost:3306/sample";
	String user = "root";
	String pass = "8121";

	ShoppingListTable(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,user,pass);
			con.setAutoCommit(false);//トランザクション開始
			this.stm = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Goods> getAll() throws SQLException{
		String sql = "select * from shoppinglist order by REGISTERED_DATETIME desc";
		rs = stm.executeQuery(sql);
		ArrayList<Goods>  array = new ArrayList<Goods>();
		while(rs.next()){
			String uuid = rs.getString("UUID");
			String name = rs.getString("ITEM");
			Integer num = rs.getInt("num");
			String memo = rs.getString("MEMO");
			Date rdate = rs.getDate("REGISTERED_DATETIME");
			Date pdate = rs.getDate("PURCHASED_DATETIME");
			Date udate = rs.getDate("UPDATED_DATETIME");
			Goods goods;
			goods = new Goods(uuid,name,num,memo,rdate,pdate,udate);
			array.add(goods);
		}
		rs.close();
		stm.close();
		con.close();
		return array;
	}

	public List<Goods> getAllYetPurchased() throws SQLException{
		String sql = "select * from shoppinglist where PURCHASED_DATETIME  is null order by REGISTERED_DATETIME desc";
		rs = stm.executeQuery(sql);
		ArrayList<Goods>  array = new ArrayList<Goods>();
		while(rs.next()){
			String uuid = rs.getString("UUID");
			String name = rs.getString("ITEM");
			Integer num = rs.getInt("num");
			String memo = rs.getString("MEMO");
			Date rdate = rs.getDate("REGISTERED_DATETIME");
			Date pdate = rs.getDate("PURCHASED_DATETIME");
			Date udate = rs.getDate("UPDATED_DATETIME");
			Goods goods;
			goods = new Goods(uuid,name,num,memo,rdate,pdate,udate);
			array.add(goods);
		}
		rs.close();
		stm.close();
		con.close();
		return array;
	}


	public Goods get(String uuid) throws SQLException{
		String sql = "select * from shoppinglist where UUID =  '"+ uuid  + "'";
		rs = stm.executeQuery(sql);
		rs.next();
		String item = rs.getString("ITEM");
		Integer num = rs.getInt("NUM");
		String memo = rs.getString("memo");
		Date rdate = rs.getDate("REGISTERED_DATETIME");
		Date pdate = rs.getDate("PURCHASED_DATETIME");
		Date udate = rs.getDate("UPDATED_DATETIME");
		Goods goods;
		goods = new Goods(uuid,item,num,memo,rdate,pdate,udate);
		rs.close();
		stm.close();
		con.close();
		return goods;

	}

	public void add(Goods goods) throws SQLException{
		String uuid = goods.getUuid();
		String name = goods.getName();
		Integer number = goods.getNumber();
		String memo = goods.getMemo();
		String sql = "insert into shoppinglist(uuid,item,num,memo,registered_datetime)values(?,?,?,?,cast(now() as datetime))";
		Connection conn = null;
		PreparedStatement pst = null;
		try{
			conn = DriverManager.getConnection(url,user,pass);
			pst = con.prepareStatement(sql);
			pst.setString(1, uuid);
			pst.setString(2, name);
			pst.setInt(3, number);
			pst.setString(4, memo);
			pst.executeUpdate();
			conn.commit();//トランザクションをコミット
		}catch(SQLException e){
			conn.rollback();
		}finally{
			pst.close();
			con.close();
		}
	}

	public void update(Goods goods) throws SQLException{
		String name = goods.getName();
		Integer number = goods.getNumber();
		String memo = goods.getMemo();
		String uuid = goods.getUuid();
		String sql ="update shoppinglist set item =? , num =? , memo =? , updated_datetime = cast(now()as datetime) where uuid =?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, name) ;
		pst.setInt(2, number);
		pst.setString(3,memo);
		pst.setString(4, uuid);
		pst.executeUpdate();
		con.commit();//トランザクションをコミット
		pst.close();
		con.close();
		stm.close();
	}


	public void delete(String uuid) throws SQLException{
		String sql = "delete from shoppinglist where uuid =?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, uuid);
		pst.executeUpdate();
		con.commit();//トランザクションをコミット
		pst.close();
		con.close();
		stm.close();
	}

	public void purchase(String uuid) throws SQLException{
		String sql = "update shoppinglist set purchased_datetime = cast(now()as datetime) where uuid =?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, uuid);
		pst.executeUpdate();
		con.commit();//トランザクションをコミット
		pst.close();
		con.close();
		stm.close();
	}

}
