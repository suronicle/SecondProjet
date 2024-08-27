package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("memberDTO")
public class memberDTO {

	private  String mid; 
	private String mpw;
	private String mname;
	private String memail;
	private String magency; //통신사
	private String mphone;
	private int mrequired; //동의
	private String moptional;  // 선택 D0
	private String mjoindate; //가입일
	private String msecessiondate; // 탈퇴일
	private int mreport; // 신고누적
	private String mstate; // 유저상태  H0
	private int mpoint; // 회원포인트
	private String mclass;  // 회원 등급 F0
	private int mbuy; // 구매금액
	
	
	@Override
	public String toString() {
		return "memberDTO [mid=" + mid + ", mpw=" + mpw + ", mname=" + mname + ", memail=" + memail + ", magency="
				+ magency + ", mphone=" + mphone + ", mrequired=" + mrequired + ", moptional=" + moptional
				+ ", mjoindate=" + mjoindate + ", msecessiondate=" + msecessiondate + ", mreport=" + mreport
				+ ", mstate=" + mstate + ", mpoint=" + mpoint + ", mclass=" + mclass + ", mbuy=" + mbuy + "]";
	}
	
	//혜빈추가
	private int pid; // 코드테이블
	private int codename;
	private String formatmphone; // 포맷 휴대폰번호
	private String coptional;
	private String cstate;
	private String cclass;
	
	 public void formatFields() {
	        this.formatmphone = mphone.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
	    }
	
}
