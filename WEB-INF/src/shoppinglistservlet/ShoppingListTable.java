package shoppinglistservlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excite
 *
 */
public class ShoppingListTable {
	String url = "jdbc:mysql://localhost:3306/sample";
	String user = "root";
	String pass = "8121";

	/**
	 * 登録されているすべてのデータを取得し、Listに入れて返します
	 * @return 全てのデータをList<Goods\>に入れた結果
	 * @throws SQLException
	 */
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
				String rdate = rs.getString("REGISTERED_DATETIME");
				String pdate = rs.getString("PURCHASED_DATETIME");
				String udate = rs.getString("UPDATED_DATETIME");
				Goods goods;
				goods = new Goods(uuid , name , num , memo , rdate , pdate , udate);
				array.add(goods);
			}
			conn.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null){
				rs.close();
			}
			if(stm != null){
				stm.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return array;
	}

	/**
	 * 登録されているデータの中で、購入済みではないものを取得し、
	 * List<Goods>に入れて返します
	 * @return 購入済みでないデータをList<Goods>に入れた結果
	 * @throws SQLException
	 */
	public List<Goods> getAllYetPurchased() throws SQLException{
		String sql = "SELECT * FROM SHOPPINGLIST WHERE PURCHASED_DATETIME  IS NULL ORDER BY REGISTERED_DATETIME DESC";
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
				String rdate = rs.getString("REGISTERED_DATETIME");
				String pdate = rs.getString("PURCHASED_DATETIME");
				String udate = rs.getString("UPDATED_DATETIME");
				Goods goods;
				goods = new Goods(uuid , name , num , memo , rdate , pdate , udate);
				array.add(goods);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null){
				rs.close();
			}
			if(stm != null){
				stm.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return array;
	}

	/**
	 * 引数に指定されたUUIDに一致するデータを取得し
	 * Goodsクラスのオブジェクトを返します
	 * @param uuid 指定されたUUID
	 * @return Goodsクラスのオブジェクト
	 * @throws SQLException
	 */
	public Goods get(String uuid) throws SQLException{
		String sql = "SELECT * FROM SHOPPINGLIST WHERE UUID = '" + uuid + "'";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Goods goods = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			rs.next();
			String item = rs.getString("ITEM");
			Integer num = rs.getInt("NUMBER");
			String memo = rs.getString("MEMO");
			String rdate = rs.getString("REGISTERED_DATETIME");
			String pdate = rs.getString("PURCHASED_DATETIME");
			String udate = rs.getString("UPDATED_DATETIME");
			goods = new Goods(uuid , item , num , memo , rdate , pdate , udate);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null){
				rs.close();
			}
			if(stm != null){
				stm.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return goods;
	}

	/**
	 * Goodsクラスのオブジェクトを引数で取得し、そこから値を取出し新たなデータとして登録する
	 * @param goods 新規登録したいデータ
	 * @return Goodsクラスのオブジェクト
	 * @throws SQLException
	 */
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
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return goods;
	}

	/**
	 * 更新で入力されたデータをGoodsオブジェクトで取得し、
	 * データを更新する
	 * @param goods 更新したいデータ
	 * @throws SQLException
	 */
	public void update(Goods goods) throws SQLException{
		String name = goods.getName();
		Integer number = goods.getNumber();
		String memo = goods.getMemo();
		String uuid = goods.getUuid();
		Connection conn = null;
		PreparedStatement pst = null;
		if("pdateupdate" == goods.getPurchasedDatetime()){
			String sql = "UPDATE SHOPPINGLIST SET PURCHASED_DATETIME = CAST(NOW()AS DATETIME) WHERE UUID =?";
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url , user , pass);
				conn.setAutoCommit(false);
				pst = conn.prepareStatement(sql);
				pst.setString(1 , uuid);
				pst.executeUpdate();
				conn.commit();//トランザクションをコミット
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				conn.rollback();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(pst != null){
					pst.close();
				}
				if(conn != null){
					conn.close();
				}
			}
		}
		else
		{
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
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				conn.rollback();//トランザクションのロールバック
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(pst != null){
					pst.close();
				}
				if(conn != null){
					conn.close();
				}
			}
		}
	}

	/**
	 * 引数で取得したUUIDに対応するデータを削除します
	 * @param uuid 指定したUUID
	 * @throws SQLException
	 */
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
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}
}
