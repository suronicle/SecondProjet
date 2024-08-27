package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("buyDTO")
public class buyDTO {

	
	private int buyno; //구매번호
	private String buyid; //구매자
	private String buybooknum; //구매책
	private int buyprice; //구매가격
	private int buynum; //구매갯수
	private String buydate; //구매날짜
	private int buydelivery;  //배송지
	private String buyway; //결재유형
	private String buyaccount; //결재건수??
	private String buycode; //상태
	private String buypassword; //비회원패스워드
	
	private String codename; //전체 상태
	private String wayname; //결재유형
	private String boname; //구매 책 내역
	private int boprice; //구매 책 단가
	private int boshipping; //배송비
	
	private int booknum; //이종 도서 구매 건수
	
	private String delivery; //운송장
	
	private String filepath; //책 사진
	private String dtransport; //운송장
	@Override
	public String toString() {
		return "buyDTO [buyno=" + buyno + ", buyid=" + buyid + ", buybooknum=" + buybooknum + ", buyprice=" + buyprice
				+ ", buynum=" + buynum + ", buydate=" + buydate + ", buydelivery=" + buydelivery + ", buyway=" + buyway
				+ ", buyaccount=" + buyaccount + ", buycode=" + buycode + ", buypassword=" + buypassword + ", codename="
				+ codename + ", wayname=" + wayname + ", boname=" + boname + ", boprice=" + boprice + ", boshipping="
				+ boshipping + ", booknum=" + booknum + ", delivery=" + delivery + ", filepath=" + filepath
				+ ", dtransport=" + dtransport + "]";
	}

	
	
	
	
}
