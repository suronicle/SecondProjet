package kr.co.book.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import kr.co.book.ecom.dao.BookDAO;
import kr.co.book.ecom.dto.BookDTO;



@Service
public class BookService {

	private ModelAndView mv;

	@Autowired
	private BookDAO DAO;


	//요청 책 리스트
	public ModelAndView reqbook(){

		mv = new ModelAndView();

		List<BookDTO> reqbook = DAO.reqbook();

		mv.addObject("reqbook", reqbook);		
		mv.setViewName("eMain");

		return mv;
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


}
