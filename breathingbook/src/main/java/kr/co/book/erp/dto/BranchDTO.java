package kr.co.book.erp.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("BranchDTO")
public class BranchDTO {
	private String brno; // 업체관리번호
	private int brclass; // 구분
	private String brpw; // 비밀번호
	private String brbusinessno; // 사업자등록번호
	private int brdel; // 삭제여부
	private String brdelegate; // 대표자명
	private String brname; // 업체명
	private String brpostal; // 우편번호
	private String braddress; // 주소
	private String brphone; // 회사 전화번호
	private String brfax; // 팩스번호
	private String brbank; // 은행명
	private String braccountholder; // 예금주
	private String braccount; // 계좌
	private String bropen; // 개업일
	private String brend; // 종료일
	
	private String formatbno; // 포맷 사업자등록번호
	private String formatphone;  // 포맷 회사 전화번호
	private String formatfax;  // 포맷 팩스번호
	private String formataccount; // 계좌
	
	 public void formatFields() {
	        this.formatbno = brbusinessno.replaceAll("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
	        this.formatphone = brphone.replaceAll("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
	        this.formatfax = brfax.replaceAll("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
	        this.formataccount = braccount.replaceAll("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
	    }
	
}