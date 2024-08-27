package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("adressDTO")
public class adressDTO {
	
	private int ano;
	private String aid;
	private String apostal; //우편
	private String aadress; // 주소 
	private String adong; //동 
	private String adetail; // 상세
	
	
		//비회원
		private int nonno;
		private String nonpostal;
		private String nonaddress;
		private String nondetail;
		private String nondong;

		//codetable
		private String codeid;
		private String pid;
		private String pname;
		private String codename;
		
		
	
	

}
