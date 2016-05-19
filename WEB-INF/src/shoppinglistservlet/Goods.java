package shoppinglistservlet;

public class Goods {
	private  String uuid = null;
	private String name = null;
	private Integer number = null;
	private String memo = null;
	private String registeredDatetime = null;
	private String purchasedDatetime = null;
	private String updateDatetime = null;

	public Goods(String name , Integer number , String memo){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.name = name;
		this.number = number;
		this.memo = memo;
	}

	public Goods(String uuid , String name , Integer number , String memo , String rdate, String pdate , String udate){
		this.uuid = uuid;
		this.name = name;
		this.number = number;
		this.memo = memo;
		this.registeredDatetime = rdate;
		this.purchasedDatetime = pdate;
		this.updateDatetime = udate;
	}

	public String getUuid(){
		return uuid;
	}

	public String getName(){
		return name;
	}

	public Integer getNumber(){
		return number;
	}

	public String getMemo(){
		return memo;
	}

	public String getRegisteredDatetime(){
		return registeredDatetime;
	}

	public String getPurchasedDatetime(){
		return purchasedDatetime;
	}

	public String getUpdateDatetime(){
		return updateDatetime;
	}
}
