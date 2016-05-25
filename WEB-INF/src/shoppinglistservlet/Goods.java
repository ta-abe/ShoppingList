package shoppinglistservlet;

/**
 *
 * @author excite
 *
 */
public class Goods {
	private  String uuid = null;
	private String name = null;
	private Integer number = null;
	private String memo = null;
	private String registeredDatetime = null;
	private String purchasedDatetime = null;
	private String updateDatetime = null;

	/**
	 * 引数で入れよくされたデータを取得し、UUIDをランダムで決定、
	 * フィールドに各値を代入します
	 * @param name
	 * @param number
	 * @param memo
	 */
	public Goods(String name , Integer number , String memo){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.name = name;
		this.number = number;
		this.memo = memo;
	}

	/**
	 * 引数でパラメータを取得し、各値をフィールドに代入します
	 * @param uuid
	 * @param name
	 * @param number
	 * @param memo
	 * @param rdate
	 * @param pdate
	 * @param udate
	 */
	public Goods(String uuid , String name , Integer number , String memo , String rdate, String pdate , String udate){
		this.uuid = uuid;
		this.name = name;
		this.number = number;
		this.memo = memo;
		this.registeredDatetime = rdate;
		this.purchasedDatetime = pdate;
		this.updateDatetime = udate;
	}

	/**
	 * フィールドに設定されたUUIDの値を返します
	 * @return uuid
	 */
	public String getUuid(){
		return uuid;
	}

	/**
	 * フィールドに設定されたNAMEの値を返します
	 * @return name
	 */
	public String getName(){
		return name;
	}

	/**
	 * フィールドに設定されたnumberの値を返します
	 * @return number
	 */
	public Integer getNumber(){
		return number;
	}

	/**
	 * フィールドに設定されたmemoの値を返します
	 * @return memo
	 */
	public String getMemo(){
		return memo;
	}

	/**
	 * フィールドに設定されたregisteredDatetimeの値を返します
	 * @return registeredDatetime
	 */
	public String getRegisteredDatetime(){
		return registeredDatetime;
	}

	/**
	 * フィールドに設定されたpurchasedDatetimeの値を返します
	 * @return purchasedDatetime
	 */
	public String getPurchasedDatetime(){
		return purchasedDatetime;
	}

	/**
	 * フィールドに設定されたupdatedDatetimeの値を返します
	 * @return updatedDatetime
	 */
	public String getUpdateDatetime(){
		return updateDatetime;
	}
}
