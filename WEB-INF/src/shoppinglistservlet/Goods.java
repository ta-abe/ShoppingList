package shoppinglistservlet;

import java.sql.Date;

public class Goods {
	private  String uuid = null;
	private String name = null;
	private Integer number = null;
	private String memo = null;
	private Date registeredDatetime = null;
	private Date purchasedDatetime = null;
	private Date updateDatetime = null;

	Goods(String name ,Integer number ,String memo){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.name = name;
		this.number = number;
		this.memo = memo;
	}

	Goods(String uuid , String name , Integer number , String memo){
		this.uuid = uuid;
		this.name = name;
		this.number = number;
		this.memo = memo;
	}

	Goods(String uuid , String name , Integer number , String memo , Date rdate, Date pdate , Date udate){
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

	public Date getRegisteredDatetime(){
		return registeredDatetime;
	}

	public Date getPurchasedDatetime(){
		return purchasedDatetime;
	}

	public Date getUpdateDatetime(){
		return updateDatetime;
	}
}
