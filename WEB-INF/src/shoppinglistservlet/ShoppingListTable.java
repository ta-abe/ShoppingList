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
	String url = "jdbc:mysql://localhost:3306/sample";
	String user = "root";
	String pass = "8121";

	public List<Goods> getAll() throws SQLException{
		String sql = "SELECT * FROM SHOPPINGLIST ORDER BY REGISTERED_DATETIME DESC";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ArrayList<Goods> array = new ArrayList<Goods>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);;
			conn.setAutoCommit(false);//トランザクション開始
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){
				String uuid = rs.getString("UUID");
				String name = rs.getString("ITEM");
				Integer num = rs.getInt("NUMBER");
				String memo = rs.getString("MEMO");
				Date rdate = rs.getDate("REGISTERED_DATETIME");
				Date pdate = rs.getDate("PURCHASED_DATETIME");
				Date udate = rs.getDate("UPDATED_DATETIME");
				Goods goods;
				goods = new Goods(uuid , name , num , memo , rdate , pdate , udate);
				array.add(goods);
			}
			conn.commit();
		}catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			rs.close();
			stm.close();
			conn.close();
		}
		return array;
	}

	public List<Goods> getAllYetPurchased() throws SQLException{
		String sql = "select * from shoppinglist where PURCHASED_DATETIME  is null order by REGISTERED_DATETIME desc";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ArrayList<Goods>  array = new ArrayList<Goods>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){
				String uuid = rs.getString("UUID");
				String name = rs.getString("ITEM");
				Integer num = rs.getInt("NUMBER");
				String memo = rs.getString("MEMO");
				Date rdate = rs.getDate("REGISTERED_DATETIME");
				Date pdate = rs.getDate("PURCHASED_DATETIME");
				Date udate = rs.getDate("UPDATED_DATETIME");
				Goods goods;
				goods = new Goods(uuid , name , num , memo , rdate , pdate , udate);
				array.add(goods);
			}
		}catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			rs.close();
			stm.close();
			conn.close();
		}
		return array;
	}

	public Goods get(String uuid) throws SQLException{
		String sql = "SELECT * FROM SHOPPINGLIST WHERE UUID = '" + uuid + "'";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Goods goods = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			rs.next();
			String item = rs.getString("ITEM");
			Integer num = rs.getInt("NUMBER");
			String memo = rs.getString("MEMO");
			Date rdate = rs.getDate("REGISTERED_DATETIME");
			Date pdate = rs.getDate("PURCHASED_DATETIME");
			Date udate = rs.getDate("UPDATED_DATETIME");
			goods = new Goods(uuid , item , num , memo , rdate , pdate , udate);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			rs.close();
			stm.close();
			conn.close();
		}
		return goods;
	}

	public Goods add(Goods goods) throws SQLException{
		String uuid = goods.getUuid();
		String name = goods.getName();
		Integer number = goods.getNumber();
		String memo = goods.getMemo();
		String sql = "INSERT INTO SHOPPINGLIST(UUID , ITEM , NUMBER , MEMO , REGISTERED_DATETIME)VALUES(? , ? , ? , ? , CAST(NOW() AS DATETIME))";
		Connection conn = null;
		PreparedStatement pst = null;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			conn.setAutoCommit(false);//トランザクション開始
			pst = conn.prepareStatement(sql);
			pst.setString(1 , uuid);
			pst.setString(2 , name);
			pst.setInt(3 , number);
			pst.setString(4 , memo);
			pst.executeUpdate();
			conn.commit();//トランザクションをコミット
		}catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			pst.close();
			conn.close();
		}
		return goods;
	}

	public void update(Goods goods) throws SQLException{
		String name = goods.getName();
		Integer number = goods.getNumber();
		String memo = goods.getMemo();
		String uuid = goods.getUuid();
		Connection conn = null;
		PreparedStatement pst = null;
		/*if(){

		}else{*/
			try{
				String sql = "UPDATE SHOPPINGLIST SET ITEM = ? , NUMBER = ? , MEMO = ? , UPDATED_DATETIME = CAST(NOW()AS DATETIME) WHERE UUID = ?";
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url , user , pass);
				conn.setAutoCommit(false);
				pst = conn.prepareStatement(sql);
				pst.setString(1 , name) ;
				pst.setInt(2 , number);
				pst.setString(3 , memo);
				pst.setString(4 , uuid);
				pst.executeUpdate();
				conn.commit();//トランザクションをコミット
			}catch(SQLException e){
				e.printStackTrace();
				conn.rollback();//トランザクションのロールバック
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				pst.close();
				conn.close();
			}
		}
	//}

	public void delete(String uuid) throws SQLException{
		String sql = "DELETE FROM SHOPPINGLIST WHERE UUID =?";
		Connection conn = null;
		PreparedStatement pst = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			pst.setString(1 , uuid);
			pst.executeUpdate();
			conn.commit();//トランザクションをコミット
		}catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			pst.close();
			conn.close();
		}
	}

	public void purchase(String uuid) throws SQLException{
		String sql = "UPDATE SHOPPINGLIST SET PURCHASED_DATETIME = CAST(NOW()AS DATETIME) WHERE UUID =?";
		Connection conn = null;
		PreparedStatement pst = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			pst.setString(1 , uuid);
			pst.executeUpdate();
			conn.commit();//トランザクションをコミット
		}catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			pst.close();
			conn.close();
		}
	}
}
