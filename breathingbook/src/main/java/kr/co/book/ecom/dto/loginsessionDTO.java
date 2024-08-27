package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("loginsessionDTO")
public class loginsessionDTO {

	//일반회원
	private String mid; //아이디
	private String mname; //이름
	private int mreport; // 신고누적
	private String mstate; //상태
	private int mpoint; //포인트
	private String mclass; //등급
	private String mclassname; //등급
	private int mbuy; //구매금액
	
	//사업자
	private String brno; //관리번호
	private int brclass; //관리자,사업장구분
	private String brname; //사업장이름
	
	@Override
	public String toString() {
		return "loginsessionDTO [mid=" + mid + ", mname=" + mname + ", mreport=" + mreport + ", mstate=" + mstate
				+ ", mpoint=" + mpoint + ", mclass=" + mclass + ", mclassname=" + mclassname + ", mbuy=" + mbuy
				+ ", brno=" + brno + ", brclass=" + brclass + ", brname=" + brname + "]";
	}
	
	
}
