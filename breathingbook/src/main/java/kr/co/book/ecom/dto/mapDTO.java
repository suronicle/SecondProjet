package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("mapDTO")
public class mapDTO {

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
}
