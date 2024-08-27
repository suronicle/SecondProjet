package kr.co.book.erp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.book.ecom.dto.BookDTO;



@Mapper
public interface sBookDAO {
	//책리스트
	public List<BookDTO> hBookList();
	public List<BookDTO> bBookList(String brno);
	
	//책등록
	public int bookInsert(BookDTO bodto);
	
	//책 상세정보
	public BookDTO bookSelect(String bono);
	
	//책 업데이트
	public int bookUpdate(BookDTO dto);
	
	//요청 책 리스트
	public List<BookDTO> requestBook();

	
	//책 재고 리스트
	public List<BookDTO> hBookStock();
	public List<BookDTO> bBookStock(String brno);
	
	//책 상세정보
	public BookDTO rBookView(int pno);
	
	//책 재고추가
	public int stockProcessing(BookDTO dto);
	
	//책 요청처리 
	public int processing(BookDTO dto);
	
	public int stockRequest(BookDTO dto);
	
	//책 차감처리
	public int Deducted(BookDTO dto);
	
	//결제확인
	public int paycheck(BookDTO dto);
	//결제확인
	public int dalreadydelivery(BookDTO dto);
	//구매 -> 배송준비
	public int alreadydelivery(BookDTO dto);
	public int stockMinus(BookDTO dto);
	//배송 준비 중 -> 배송 중
	public int delivering(BookDTO dto);
	//배송 준비 중 -> 배송 중
	public int completeDelivery(BookDTO dto);
	
	
	
	
	//반품 신청 -> 반품 처리 중
	public int processingTakeBack(BookDTO dto);
	//반품 처리 중 -> 반품 완료
	public int completeTakeBack(BookDTO dto);
	public int stockPlus(BookDTO dto);
	//환불 신청 -> 환불 처리 중
	public int processingRefund(BookDTO dto);
	//환불 처리중 -> 환불 완료
	public int completeRefund(BookDTO dto);
	//교환 신청 -> 교환 처리 중
	public int processingExchange(BookDTO dto);
	//교환 처리 중 -> 교환 완료
	public int completeExchange(BookDTO dto);
	

	//주문 리스트
	public List<BookDTO> hOrderList();
	public List<BookDTO> bOrderList(String brno);
	//배송 리스트
	public List<BookDTO> deliveryList();
	//반품리스트
	public List<BookDTO> returnManage();
	//환불리스트
	public List<BookDTO> hRefundList();
	public List<BookDTO> bRefundList(String brno);
	//교환리스트
	public List<BookDTO> hExchangeList();
	public List<BookDTO> bExchangeList(String brno);
	
	
	
	//주문 상세
	public BookDTO orderSelect(String buyno);
	//배송 상세
	public BookDTO deliverySelect(String buyno);
	//반품 상세
	public BookDTO returnManageSelect(String buyno);
	//환불 상세
	public BookDTO refundSelect(String buyno);
	//교환 상세
	public BookDTO exchangeSelect(String buyno);
	
	
}
