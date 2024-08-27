package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("inquiryDTO")
public class inquiryDTO {

	private int ino; //문의번호
	private String iid;  //문의자
	private String iname;  //문의이름
	private String ititle;  //문의제목
	private String icontent; //문의내용
	private String ipw; //문의패스워드
	private String iemail;//답변받을 이메일
	private String idate; //문의일
	private String ianswer; //답변
	private String iadate; //답변일
	private int idel; //삭제여부
	private String idiv; //문의유형
	@Override
	public String toString() {
		return "inquiryDTO [ino=" + ino + ", iid=" + iid + ", iname=" + iname + ", ititle=" + ititle + ", icontent="
				+ icontent + ", ipw=" + ipw + ", iemail=" + iemail + ", idate=" + idate + ", ianswer=" + ianswer
				+ ", iadate=" + iadate + ", idel=" + idel + ", idiv=" + idiv + "]";
	}
	
	
	
	
	
	
	
}
