package kr.co.book.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


import kr.co.book.ecom.dto.BookDTO;

@Mapper

public interface PurchaseDAO {
	
	public int purInsert(BookDTO book);
	public int purUpdate(BookDTO book);
	public int purCancle(BookDTO book);
	public int purAdd(BookDTO book);
	public List<BookDTO> searchBook(); // 도서 검색 전체 목록
	public List<BookDTO> purList();

	public List<BookDTO> sumyeartotal(int year); //년별 조회
	public List<BookDTO> summonthtotal(int month); //월별 조회
	public List<BookDTO> sumdaytotal(int day); //일별 조회

	public int purEdit(BookDTO book);
	public int purDelete(int pno);
	public BookDTO purView(int pno);
	
	//가맹점
	public List<BookDTO> purList1(String brno);
	public List<BookDTO> sumyeartotal1(Map<String, String> map); //년별 조회
	public List<BookDTO> summonthtotal1(Map<String, String> map); //월별 조회
	public List<BookDTO> sumdaytotal1(Map<String, String> map); //일별 조회
	
	//회원
	public List<BookDTO> mSalesList();
	public List<BookDTO> myeartotal(int year); // 년별 조회
	public List<BookDTO> mmonthtotal(int month); //월별 조회
	public List<BookDTO> mdaytotal(int day); //일별 조회
	
	
}
	