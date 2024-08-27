package kr.co.book.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.loginsessionDTO;
import kr.co.book.erp.dao.sBookDAO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class sBookService {
	
	private ModelAndView mv;
    
	@Autowired
	private sBookDAO boDao;
	
	public ModelAndView bookList(String brno,HttpSession session) {
		log.info("bookList()");
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");
		int brclass = loginDto.getBrclass();
		log.info("purList() with brclass:{},brno: {}", brclass, brno);
		
		
		
		mv = new ModelAndView();
		if(brclass != 0) {
			List<BookDTO> bookList = boDao.bBookList(brno);
			mv.addObject("bookList",bookList);
			mv.setViewName("m_bookList");
		}else {
			List<BookDTO> bookList = boDao.hBookList();
			mv.addObject("bookList",bookList);
			mv.setViewName("m_bookList");
		}
		
		return mv;
	}
	
	public int bookInsert(BookDTO bodto) {
		log.info("bookInsert()");
		

		boDao.bookInsert(bodto);
		
		return 1;
	}
	
	public BookDTO bookSelect(String bono) {
		log.info("rBookView() with bono:{}",bono);
		
		
		BookDTO bookSelect = boDao.bookSelect(bono);
		
		
		return bookSelect;
	}
	
	public int bookUpdate(BookDTO dto) {
		log.info("bookUpdate()");
		
		boDao.bookUpdate(dto);
		
		return 1;
	}
	
	public ModelAndView requestBook(String brno,HttpSession session) {
		log.info("requestBook");
		mv = new ModelAndView();
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");
		int brclass = loginDto.getBrclass();
		log.info("purList() with brclass:{},brno: {}", brclass, brno);
		

			List<BookDTO> requestBook = boDao.requestBook();
			mv.addObject("requestBook",requestBook);
			mv.setViewName("m_requestBook");
		
		return mv;
		
	}
	
	public ModelAndView bookStock(String brno,HttpSession session) {
		log.info("bookStock()");
		mv = new ModelAndView();
		
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		
		if(brclass != 0) {
			List<BookDTO> bookStock = boDao.bBookStock(brno);
			mv.addObject("bookStock",bookStock);
			mv.setViewName("m_bookStock");
		}else {
			System.out.println(brno); 
			List<BookDTO> bookStock = boDao.hBookStock();
			mv.addObject("bookStock",bookStock);
			mv.setViewName("m_bookStock");
		}
		
		
		return mv;
	}
	
	public BookDTO rBookView(int pno) {
		log.info("rBookView() with pno:{}",pno);
		BookDTO rBookView= boDao.rBookView(pno);
		
		
		return rBookView;
	}
	
	public BookDTO bookmStock(int pno) {
		log.info("rBookView() with pno:{}",pno);
		BookDTO bookmStock= boDao.rBookView(pno);
		
		
		return bookmStock;
	}
	
	public int stockProcessing(BookDTO dto,String userBrno) {
		log.info("stockprocessing() with brno:{}",dto.getBrno());
		log.info("stockprocessing() with pno:{}",dto.getPno());
		log.info("stockprocessing() with brreqnum:{}",dto.getBrreqnum());
		
		if(userBrno.equals("admin")) {
			boDao.stockProcessing(dto);
		}else {
			boDao.stockRequest(dto);
		}
		
		return 1;
	}
	
	public int processing(BookDTO dto) {
		
		log.info("processing() with pno:{}",dto.getPno());
		
		boDao.processing(dto);
		
		return 1;
	}
	
	public int Deducted(BookDTO dto) {
		log.info("Deducted() with pno:{}",dto.getPno());
		System.out.println(dto.getDeducted());
		
		boDao.Deducted(dto);
		
		return 1;
		
	}
	
	
	
	public ModelAndView orderList(String brno,HttpSession session) {
		log.info("orderList()");
		mv = new ModelAndView();
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		
		if(brclass != 0) {
			
			List<BookDTO> orderList = boDao.bOrderList(brno);
			mv.addObject("orderList",orderList);
			mv.setViewName("m_orderList");
		} else {
			List<BookDTO> orderList = boDao.hOrderList();
			mv.addObject("orderList",orderList);
			mv.setViewName("m_orderList");
		}
		return mv;
	}
	public ModelAndView deliveryList(String brno,HttpSession session) {
		log.info("deliveryList()");
		System.out.println(brno);
		
		mv = new ModelAndView();
		List<BookDTO> deliveryList = boDao.deliveryList();
		mv.addObject("delivery",deliveryList);
		mv.setViewName("m_delivery");
		
		return mv;
	}
	public ModelAndView returnManage() {
		log.info("returnmanage()");
		mv = new ModelAndView();
		List<BookDTO> returnmanage = boDao.returnManage();
		mv.addObject("return",returnmanage);
		mv.setViewName("m_returnManage");
		
		return mv;
	}
	public ModelAndView refundList(String brno,HttpSession session) {
		log.info("refundList()");
		mv = new ModelAndView();
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		if(brclass != 0) {
			List<BookDTO> refundList = boDao.bRefundList(brno);
			mv.addObject("refund",refundList);
			mv.setViewName("m_refund");
		} else {
			List<BookDTO> refundList = boDao.hRefundList();
			mv.addObject("refund",refundList);
			mv.setViewName("m_refund");
		}
		
		return mv;
	}
	public ModelAndView exchangeList(String brno,HttpSession session) {
		log.info("exchangeList()");
		mv = new ModelAndView();
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		if(brclass != 0) {
			List<BookDTO> exchangeList = boDao.bExchangeList(brno);
			mv.addObject("exchange",exchangeList);
			mv.setViewName("m_exchange");
		} else {
			List<BookDTO> exchangeList = boDao.hExchangeList();
			mv.addObject("exchange",exchangeList);
			mv.setViewName("m_exchange");
		}
		
		return mv;
	}

	
	
	
	public BookDTO orderSelect(String buyno,String variable) {
		
		BookDTO dto = new BookDTO();
		
		if(variable.equals("order")) {
			log.info("orderSelect()");
			
			BookDTO order = boDao.orderSelect(buyno);
			
			return order;
		}else if (variable.equals("delivery")) {
			log.info("deliverySelect()");
			
			BookDTO order = boDao.deliverySelect(buyno);
			
			return order;
		}else if (variable.equals("returnManage")) {
			log.info("returnManage()");
			
			BookDTO order = boDao.returnManageSelect(buyno);
			
			return order;
		}else if (variable.equals("refund")) {
			log.info("refund()");
			
			BookDTO order = boDao.refundSelect(buyno);
			
			return order;
		}else if (variable.equals("exchange")) {
			log.info("exchange()");
			
			BookDTO order = boDao.exchangeSelect(buyno);
			
			return order;
		}
		return dto;
	}
	
	
	
	public int paycheck(BookDTO dto) {
		System.out.println(dto.getBuycode());
		
		if(dto.getBuycode().equals("E1")) {
			log.info("paycheck()");
			boDao.paycheck(dto);
		}
		
		if(dto.getBuycode().equals("B13")) {
			log.info("deliverycheck()");
			boDao.alreadydelivery(dto);
			boDao.dalreadydelivery(dto);
			
		}
		return 1;
	}
	
	public int deliveryCheck(BookDTO dto) {
		System.out.println(dto.getBuycode());
		
		if(dto.getBuycode().equals("B5")) {
			log.info("delivering()");
			boDao.delivering(dto);
		}
		
		if(dto.getBuycode().equals("B6")) {
			log.info("completeDelivery()");
			boDao.completeDelivery(dto);

		}
		return 1;
	}
	
	public int takeBackCheck(BookDTO dto) {
		System.out.println(dto.getBuycode());
		
		if(dto.getBuycode().equals("B18")) {
			log.info("processingTakeBack()");
			boDao.processingTakeBack(dto);
		}
		
		if(dto.getBuycode().equals("B19")) {
			log.info("completeTakeBack()");
			boDao.completeTakeBack(dto);
			boDao.stockPlus(dto);
			System.out.println(dto.getBono());
			System.out.println(dto.getBuynum());
		}
		return 1;
	}
	
	public int refundCheck(BookDTO dto) {
		System.out.println(dto.getBuycode());
		
		if(dto.getBuycode().equals("B9")) {
			log.info("processingRefund()");
			boDao.processingRefund(dto);
		}
		
		if(dto.getBuycode().equals("B15")) {
			log.info("completeRefund()");
			boDao.completeRefund(dto);
			boDao.stockPlus(dto);
			System.out.println(dto.getBono());
			System.out.println(dto.getBuynum());
		}
		return 1;
	}
	
	public int exchangeCheck(BookDTO dto) {
		System.out.println(dto.getBuycode());
		
		if(dto.getBuycode().equals("B2")) {
			log.info("processingExchange()");
			boDao.processingExchange(dto);
		}
		
		if(dto.getBuycode().equals("B3")) {
			log.info("completeExchange()");
			boDao.completeExchange(dto);
		}
		return 1;
	}
}
