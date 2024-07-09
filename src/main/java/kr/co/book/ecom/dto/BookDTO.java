package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("BookDTO")
public class BookDTO {

	
	//book table
	
	String bono;
	String boname;
	String bocontent;
	String bocompany;
	String bowriter;
	String bodate;
	int bostock;
	int bodel;
	int bosold;
	int boprice;
	String bogenre;
	String bocode;
	int boshipping;
	
	
	
	//buy table
	int buyno;
	String buyid;
	String buybooknum;
	int buyprice;
	int buynum;
	String buydate;
	String buydelivery;
	String buyway;
	String buyaccount;
	String buypassword;
	

	
}
