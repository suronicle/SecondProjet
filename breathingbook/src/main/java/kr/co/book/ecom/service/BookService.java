package kr.co.book.ecom.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dao.BookDAO;
import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.adressDTO;
import kr.co.book.ecom.dto.mapDTO;
import kr.co.book.ecom.dto.noticeDTO;



@Service
public class BookService {

	private ModelAndView mv;

	@Autowired
	private BookDAO DAO;


	//요청 책 리스트 3개(main)
	public ModelAndView reqbook(){

		mv = new ModelAndView();

		List<BookDTO> reqbook = DAO.reqbook();

		mv.addObject("reqbook", reqbook);		
		mv.setViewName("eMain");

		return mv;
	}



	//요청 책 리스트(전체)
	public ModelAndView allreqbook() {

		mv = new ModelAndView();
		List<BookDTO> reqbook = DAO.allreqbook();

		mv.addObject("reqbook", reqbook);

		mv.setViewName("eRequestList");
		return mv;
	}



	//책 요청하기
	public int bookReq(String bono) {

		int result = DAO.bookReq(bono);

		return result;
	}


	//광고 책 리스트
	public ModelAndView adbook(){

		mv = new ModelAndView();

		List<BookDTO> adbook = DAO.adbook();

		mv.addObject("adbook", adbook);		
		mv.setViewName("eMain");

		return mv;
	}

	//베스트 9
	public ModelAndView bestbooktop9(){

		mv = new ModelAndView();

		List<BookDTO> top9 = DAO.bestbooktop9();

		mv.addObject("top9", top9);		
		mv.setViewName("eMain");

		return mv;
	}


	//품절된 책 리스트
	public ModelAndView soldbook(){

		mv = new ModelAndView();

		List<BookDTO> soldbook = DAO.soldbook();

		mv.addObject("soldbook", soldbook);		
		mv.setViewName("eRequestS");

		return mv;
	}


	//신작 리스트
	public ModelAndView newbook(){

		mv = new ModelAndView();

		List<BookDTO> newbook = DAO.newbook();

		mv.addObject("newbook", newbook);		
		mv.setViewName("eRequestN");

		return mv;
	}


	//책 detail
	public ModelAndView bookdetail(String bono){

		mv = new ModelAndView();

		BookDTO book = DAO.bookdetail(bono);

		mv.addObject("book", book);		
		mv.setViewName("eBookDetail");


		return mv;
	}




	//한 책 살때 detail
	public ModelAndView onebookbuy(String bono){

		mv = new ModelAndView();

		BookDTO book = DAO.bookdetail(bono);

		mv.addObject("book", book);		
		mv.setViewName("eNotMemBuy");


		return mv;
	}



	//책 4개 랜덤
	public ModelAndView randombook(String bono){

		mv = new ModelAndView();

		List<BookDTO> randbook = DAO.randombook(bono);

		mv.addObject("randbook", randbook);		
		mv.setViewName("eBookDetail");

		return mv;
	}


	//모달 detail
	public ModelAndView modaldetail(String bono){

		mv = new ModelAndView();

		BookDTO mbook = DAO.bookdetail(bono);

		mv.addObject("mbook", mbook);		
		mv.setViewName("eBookModal");

		return mv;
	}

	//비회원 주문
	@Transactional
	public int buynotmem(BookDTO bookDTO) {

		int result=DAO.buynotmem(bookDTO);

		if(result==0) {
			System.out.println("실패다");
		}
		return result;
	}

	//회원 주문
	@Transactional
	public int buymem(BookDTO bookDTO) {

		int result=DAO.buymem(bookDTO);

		if(result==0) {
			System.out.println("실패다");
		}
		return result;
	}



	@Transactional
	public int countchange(BookDTO bookDTO) {

		int result=DAO.countchange(bookDTO);

		return result;
	}


	@Transactional
	public int stockcount(String buybooknum) {

		int result = DAO.stockcount(buybooknum);


		return result;
	}


	//비회원 조회
	public ModelAndView nonMemlogin(Map<String, String> map, HttpSession session) {

		mv = new ModelAndView();

		BookDTO bookDTO = DAO.nonMemlogin(map);

		if(bookDTO == null) {

			session.setAttribute("nonmem", bookDTO);
			mv.addObject("url","/eNotMemBuyLogin");
			mv.setViewName("eAlert");
			mv.addObject("msg", "주문 번호와 비밀번호를 다시 확인해주세요.");
			System.out.println("비회원 조회 실패");

		} else {
			session.setAttribute("nonmem", bookDTO);
			mv.setViewName("redirect:/eNotMemBuyList?buyno="+bookDTO.getBuyno()); 
			System.out.println("비회원 조회 성공");
		}



		return mv;
	}


	public ModelAndView nonMemBuylist(String buydate) {

		mv = new ModelAndView();

		List<BookDTO> bookDTO = DAO.nonMemBuylist(buydate);

		mv.addObject("book", bookDTO);
		mv.setViewName("eNotMemBuyList");

		return mv;
	}



	//비회원 배송 정보
	public ModelAndView nonMemDelivery(int buyno) {

		mv = new ModelAndView();
		adressDTO aDTO = DAO.nonMemDelivery(buyno);

		mv.addObject("address", aDTO);
		mv.setViewName("eNotMemBuyList");

		return mv;
	}

	//비회원 날짜 조회
	public String nonmemBuydate(int buyno) {

		String buydate=DAO.nonmemBuydate(buyno);

		return buydate;
	}




	public ModelAndView useraddress(String mid) {

		mv = new ModelAndView();
		adressDTO aDTO = DAO.useraddress(mid);
		mv.addObject("address", aDTO);

		return mv;
	}



	public ModelAndView addressbook(String mid) {

		mv = new ModelAndView();

		List<adressDTO> aDTO=DAO.addressbook(mid);
		mv.addObject("addressbook", aDTO);

		return mv;
	}


	//주소록 추가
	public int addressAdd(adressDTO aDTO) {

		int result = DAO.addressAdd(aDTO);

		return result;
	}


	//MaxANO값
	public int getMaxAno(@RequestParam("mid")String mid) {

		int maxAno = DAO.getMaxAno(mid);

		return maxAno;
	}


	//비회원 주소 추가
	public int nonmemaddressAdd(adressDTO aDTO) {

		int result=DAO.nonmemaddressAdd(aDTO);


		return result;
	}

	//주문 번호 조회
	public int getMaxBuyno() {

		int result=DAO.getMaxBuyno();


		return result;
	}


	//장바구니 번호
	public Integer getMaxCno(String cuserid) {

		Integer result=DAO.getMaxCno(cuserid);


		return result;
	}


	//장바구니에 담기
	public int addCart(BookDTO bookDTO) {

		int result=DAO.addCart(bookDTO);

		return result;
	}

	//비회원 장바구니에 담기
	public int addNonCart(BookDTO bookDTO) {

		int result=DAO.addNonCart(bookDTO);

		return result;
	}


	public ModelAndView cartlist(String cuserid) {
		mv = new ModelAndView();

		List<BookDTO> bookDTO =DAO.cartlist(cuserid);
		mv.addObject("cartlist", bookDTO);

		return mv;
	}

	public List<BookDTO> cartList(String cuserid){

		List<BookDTO> list = DAO.cartlist(cuserid);


		return list;
	}



	public int delcart(String cuserid) {

		int result=DAO.delcart(cuserid);

		return result;
	}


	public int delcartone(BookDTO bookDTO) {

		int result=DAO.delcartone(bookDTO);

		return result;

	}

	//검색기능
	public String booksearch(String boname) {
		String bono = DAO.booksearch(boname);

		return bono;
	}


	//지도 위치
	public List<mapDTO> bookstoremap(){

		List<mapDTO> list = DAO.bookstoremap();

		return list;
	}


	//서재
	public mapDTO breathingbook() {

		mapDTO breathing=DAO.breathingbook();

		return breathing;
	}


	//은미
	//팝업 리스트
	public List<noticeDTO> poplist () {

		List<noticeDTO> dto = null;

		try {
			dto = DAO.poplist();

		} catch (Exception e) {

			e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}

		return dto;
	}


	//장르별
	public List<BookDTO> categorylist(String bogenre){

		List<BookDTO> list=DAO.categorylist(bogenre);

		return list;
	}



	//책 사진 경로
	public String bookfile(String bono) {

		String result = DAO.bookfile(bono);

		return result;
	}


	//책 사진 경로
	public List<BookDTO> bookfiles(String bono) {


		List<BookDTO> results = DAO.bookfiles(bono);

		return results;
	}


	//취소
	public int nonmemreq(BookDTO bookDTO) {

		int result=DAO.nonmemreq(bookDTO);

		return result;
	}



}
