package kr.co.book.ecom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.book.ecom.dto.BookDTO;

@Mapper
public interface BookDAO {

	//베스트 셀러 9개 리스트
	public List<BookDTO> bestbooktop9();

	//요청책 리스트
	public List<BookDTO> reqbook();

	//광고책 리스트
	public List<BookDTO> adbook();

	//bono 책 detail
	public BookDTO bookdetail(String bono);

	//랜덤책4개
	public List<BookDTO> randombook(String bono);

	//품절된 책 리스트
	public List<BookDTO> soldbook();
	
	//신작 리스트
	public List<BookDTO> newbook();


	//비회원 구매
	public int buynotmem(BookDTO bookDTO);

	
	//구매수 변동
	public int countchange(BookDTO bookDTO);


	//재고수
	public int stockcount(String buybooknum);


}

