package kr.co.book.ecom.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.adressDTO;
import kr.co.book.ecom.dto.mapDTO;
import kr.co.book.ecom.dto.noticeDTO;

@Mapper
public interface BookDAO {

	//베스트 셀러 9개 리스트
	public List<BookDTO> bestbooktop9();

	//요청책 리스트 3개
	public List<BookDTO> reqbook();

	//요청북 전체 리스트
	@Select("SELECT * FROM book WHERE bocode='A3' ORDER BY bodate DESC")
	public List<BookDTO> allreqbook();

	//광고책 리스트
	public List<BookDTO> adbook();

	//bono 책 detail
	public BookDTO bookdetail(String bono);

	//랜덤책4개
	public List<BookDTO> randombook(String bono);

	//품절된 책 리스트
	public List<BookDTO> soldbook();

	//품절 책 요청하기
	@Update("UPDATE book SET bocode = 'A3' WHERE bono = #{bono}")
	public int bookReq(String bono);

	//신작 리스트
	public List<BookDTO> newbook();


	//비회원 구매
	public int buynotmem(BookDTO bookDTO);


	//회원 구매
	public int buymem(BookDTO bookDTO);


	//구매수 변동
	public int countchange(BookDTO bookDTO);


	//재고수
	public int stockcount(String buybooknum);


	//비회원 로그인 정보 조회
	@Select ("select * from buy where buyno = #{buyno} and buypassword =#{buypassword}")
	public BookDTO nonMemlogin (Map<String, String> map);

	//비회원 주문정보로 날짜 조회
	@Select ("select buydate from buy, book where buy.buybooknum=book.bono and buyno = #{buyno}")
	public String nonmemBuydate(int buyno);


	//비회원 날짜로 정보 조회	
	@Select ("select * from buy, book where buy.buybooknum=book.bono and buydate = #{buydate}")
	public List<BookDTO> nonMemBuylist (String buydate);

	//비회원 배송 조회
	@Select ("select * from nonmemaddress where nonno=#{buyno}")
	public adressDTO nonMemDelivery(int buyno);




	//회원 아이디로 1번째 주소 불러오기
	@Select ("select * from adress where aid=#{mid} and ano=1")
	public adressDTO useraddress(String mid);

	//회원 아이디로 1번째 말고 나머지 주소 불러오기
	@Select("select * from adress where aid=#{mid}")
	public List<adressDTO> addressbook(String mid);

	//ano로 주소 불러오기
	@Select ("select * from adress where aid=#{mid} and ano=#{ano}")
	public adressDTO useraddressAno(String mid, int ano);


	//최대 ano값
	@Select("SELECT COALESCE(MAX(ano), 0) + 1 FROM adress WHERE aid = #{mid}")
	int getMaxAno(String mid);

	//주소 추가 등록
	@Insert("INSERT INTO adress (ano, aid, apostal, aadress, adong, adetail) VALUES (#{ano}, #{aid}, #{apostal}, #{aadress}, #{adong}, #{adetail})")
	public int addressAdd(adressDTO aDTO);


	//비회원 주소 추가
	@Insert("INSERT INTO nonmemaddress (nonno, nonpostal, nonaddress, nondetail, nondong) VALUES (#{nonno}, #{nonpostal}, #{nonaddress}, #{nondetail}, #{nondong})")
	public int nonmemaddressAdd(adressDTO aDTO);


	//주문 번호
	@Select ("SELECT COALESCE(MAX(buyno)) FROM buy")
	public int getMaxBuyno();



	//장바구니 불러오기
	@Select("select * from cart,book,file where cart.cbookno=book.bono AND book.bono=file.filepostno AND cuserid=#{cuserid}")
	public List<BookDTO> cartlist(String cuserid);



	//장바구니 기록 확인하기
	@Select("SELECT COALESCE(MAX(cno))+1 FROM cart where cuserid=#{cuserid}")
	public Integer getMaxCno(String cuserid);


	//회원 장바구니에 담기
	@Insert("INSERT INTO cart (cno, cuserid, cbookno, cbuynum) VALUES (#{cno}, #{cuserid}, #{cbookno}, #{cbuynum})")
	public int addCart(BookDTO bookDTO);

	//비회원 장바구니 담기
	@Insert("INSERT INTO cart (cno, cuserid, cbookno, cbuynum) VALUES (#{cno}, 'none', #{cbookno}, #{cbuynum})")
	public int addNonCart(BookDTO bookDTO);


	//아이디별 장바구니 전체 삭제
	@Delete("DELETE FROM cart WHERE cuserid = #{cuserid}")
	public int delcart(String cuserid);

	//장바구니에서 특정 행 삭제
	@Delete("delete from cart where cuserid=#{cuserid} and cno=#{cno}")
	public int delcartone(BookDTO bookDTO);


	//검색 기능(boname으로 bono번호 찾기)
	@Select("SELECT bono FROM book WHERE boname LIKE CONCAT('%', #{boname}, '%') AND bodel != 1")
	public String booksearch(String boname);



	//기업 정보
	@Select("SELECT * FROM branch WHERE brno NOT LIKE 'V%' AND brno!='admin'")
	public List<mapDTO> bookstoremap();

	//서재 정보
	@Select("SELECT * FROM branch where brno='admin'")
	public mapDTO breathingbook();
	
	
	//은미
	//팝업 전체 리스트
	@Select("select * from notice where ndel = 0 and nclass= 0 and nenddate > now() and nstartdate < now() order by ndate asc;")
	public List<noticeDTO> poplist();



	//카테고리 별 책 리스트
	@Select("select * from book, codetable,file where book.bogenre = codetable.pid AND book.bono=file.filepostno AND bogenre=#{bogenre} AND bodel != 1")
	public List<BookDTO> categorylist(String bogenre);


	//책 사진(한개)
	@Select("select filepath from book, file where file.filepostno = book.bono and bono=#{bono}")
	public String bookfile(String bono);

	//책 사진 여러개
	@Select("select filepath from book, file where file.filepostno = book.bono and bono=#{bono}")
	public List<BookDTO> bookfiles(String bono);


	//비회원 주문 내역 변경
	@Update("UPDATE buy SET buycode = #{buycode} WHERE buyno = #{buyno}")
	public int nonmemreq(BookDTO bookDTO);
	
	
	

}

