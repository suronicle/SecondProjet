package kr.co.book.ecom.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("BookDTO")
public class BookDTO {


	//book table

	private String bono;
	private String boname;
	private String bocontent;
	private String bocompany;
	private String bowriter;
	private String bodate;
	private int bostock;
	private int bodel;
	private int bosold;
	private int boprice;
	private String bogenre;
	private String bocode;
	private int boshipping;



	//buy table
	private int buyno;
	private String buyid;
	private String buybooknum;
	private int buyprice;
	private int buynum;
	private String buydate;
	private int buydelivery;
	private String buyway;
	private String buyaccount;
	private String buypassword;
	private String buycode;


	//member
	String mid;

	//codetable
	private String codeid;
	private String pid;
	private String pname;
	private String codename;


	//cart
	private int cno;
	private String cuserid;
	private String cbookno;
	private int cbuynum;



	// 혜빈
	private int pno;    // 관리번호
	private String brno; // 업체관리번호
	private String brname; // 업체명
	private String pdate; // 날짜
	private int pclass; // 구분
	private String pbookno; // 책번호(purchase테이블)
	//private String bono; // 책번호(book테이블)
	private String mname; // 회원이름

	private int pstock; // 매입,매출 수량(pclass가 1이면 매입수량)
	private String preason; // 재고수량 변경이유
	//private String boname; // 책이름
	//private int bosold;   // 판매수
	//private int boprice;   // 판매가격
	private int booriginal;   // 책단가

	private int totalpurchase; // 건별 총 매입 금액(재고 변경 수량 * 책단가)
	private int totalsales; // 건별 총 매출 금액(판매수 * 판매가격)

	//main쪽
	private String month;
	private int total_pstock; // 회사별 총 거래량
	private String start_date; // 계산 기간
	private String end_date; // 계산 기간
	private String cutbrname; // 지점이름만
	private String cutboname; // 도서 앞 5글자만


	private String formatpdate;   // 판매가격
	private String fomatboprice;   // 판매가격
	private String fomatbooriginal;   // 책단가
	private String fomattotalpurchase;   // 건별 매입
	private String fomattotalsales;   // 건별 매출
	private String fomatsales;   // 건별 매출 만단위 출력



	public void formatFields() {
		this.fomattotalpurchase = String.format("%,d", totalpurchase);
		this.fomattotalsales = String.format("%,d", totalsales);
		this.fomatsales = String.format("%,d", totalsales/10_000);
		this.fomatboprice = String.format("%,d", boprice);
		this.fomatbooriginal = String.format("%,d", booriginal);
	}

	//file
	//파일번호
	private String fileno;
	//파일 업로드 할 게시물
	private String filepostno;
	//파일 이름
	private String filerealname;
	//파일 저장이름
	private String filesavename;
	//파일 사이즈
	private long filesize;
	//파일 삭제여부
	private String filedelete;
	//파일 업로드 날짜
	private String filesavedate;
	//파일 삭제 날짜
	private String filedeletedate;
	//파일 경로
	private String filepath;


	//file
	private List<MultipartFile> files = new ArrayList<>();

	//purchase
	//책 요청수량
	private int brreqnum;
	//반려이유
	private String preturn;
	//요청코드
	private String pcode;

	//codetable
	private String pyorn;

	//알림창 띄우기
	private String message;

	//delivery
	//주문번호
	private String dno;
	//주문자 아이디
	private String did;
	//배송상태
	private String dtransport;
	//책번호
	private String dbookno;

	private String userBrno;
	
	private int deducted;
	
	
}
