package kr.co.book.ecom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.adressDTO;
import kr.co.book.ecom.dto.loginsessionDTO;
import kr.co.book.ecom.dto.mapDTO;
import kr.co.book.ecom.dto.noticeDTO;
import kr.co.book.ecom.service.BookService;
import kr.co.book.ecom.service.JbookService;


@Controller
public class bookController {

	private ModelAndView mv;

	@Autowired
	public BookService service;

	@Autowired
	public JbookService jservice;


	@GetMapping("/eMain")
	public ModelAndView eMain(HttpSession session){


		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();

			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);

			//장바구니
			System.out.println("회원 장바구니");
			Integer count = service.getMaxCno(mid);
			mv.addObject("count", count);
			mv.addAllObjects(service.cartlist(mid).getModel());

		}

		//비회원
		else  {
			System.out.println("비회원 장바구니");
			Integer count = service.getMaxCno("none");
			mv.addObject("count", count);
			mv.addAllObjects(service.cartlist("none").getModel());
		}



		mv.addAllObjects(service.bestbooktop9().getModel());


		mv.addAllObjects(service.reqbook().getModel());

		mv.addAllObjects(service.adbook().getModel());





		//은미 팝업창
		List<noticeDTO> ndto = service.poplist();
		mv.addObject("ndto", ndto);
		//은미 팝업창

		mv.setViewName("eMain");

		return mv;
	}

	@GetMapping("/eLogout")
	public String eLogout(HttpSession session){

		session.invalidate(); 
		return "redirect:/eMain"; 

	}




	@GetMapping("/eInfo")
	public ModelAndView eInfo(mapDTO map){

		mv = new ModelAndView();

		map = service.breathingbook();

		mv.addObject("breathing", map);

		mv.setViewName("eInfo");

		return mv;
	}



	@GetMapping("/eCart")
	public ModelAndView eCart(@RequestParam("mid")String cuserid,HttpSession session){
		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();
			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);

			//장바구니
			Integer count = service.getMaxCno(mid);
			mv.addObject("count", count);
			mv.addAllObjects(service.cartlist(mid).getModel());
		}	



		mv.addAllObjects(service.cartlist(cuserid).getModel());

		mv.setViewName("eCart");

		return mv;
	}

	@GetMapping("/eNonMemCart")
	public ModelAndView eNonMemCart(){
		mv = new ModelAndView();

		mv.addAllObjects(service.cartlist("none").getModel());

		//장바구니
		Integer count = service.getMaxCno("none");
		mv.addObject("count", count);
		mv.addAllObjects(service.cartlist("none").getModel());

		mv.setViewName("eNonMemCart");

		return mv;
	}


	@GetMapping("/eCartDel")
	public ModelAndView eCartDel(BookDTO bookDTO,HttpSession session) {
		mv = new ModelAndView();



		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();
			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);
			bookDTO.setCuserid(mid);

			int result=service.delcartone(bookDTO);
			mv.addObject("msg", "삭제되었습니다.");
			mv.addObject("url", "/eCart?mid="+mid);
			mv.setViewName("eAlert");

		}
		else if(loginDto == null) {
			bookDTO.setCuserid("none");

			service.delcartone(bookDTO);
			mv.addObject("msg", "삭제되었습니다.");
			mv.addObject("url", "/eCart?mid=none");
			mv.setViewName("eAlert");


		}



		return mv;
	}






	@GetMapping("/eBuyDel")
	public ModelAndView eBuyDel(BookDTO bookDTO,HttpSession session) {

		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();
			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);
			bookDTO.setCuserid(mid);

			int result=service.delcartone(bookDTO);

			mv.addObject("msg", "삭제되었습니다.");
			mv.addObject("url", "/eBuy?mid="+mid);
			mv.setViewName("eAlert");


		}
		else if(loginDto == null){
			bookDTO.setCuserid("none");

			service.delcartone(bookDTO);

			mv.addObject("msg", "삭제되었습니다.");
			mv.addObject("url", "/eNotMemBuy?mid=none");
			mv.setViewName("eAlert");

		}


		return mv;
	}







	@GetMapping("/eBookstoreList")
	public String eBookstoreList(){

		return "eBookstoreList";
	}


	@GetMapping("/eNotMemBuy")
	public ModelAndView eNotMemBuy(@RequestParam("mid") String mid){

		mv = new ModelAndView();

		mv.addAllObjects(service.cartlist(mid).getModel());


		//장바구니
		Integer count = service.getMaxCno("none");
		mv.addObject("count", count);
		mv.addAllObjects(service.cartlist("none").getModel());


		List<BookDTO> list = service.cartList(mid);

		mv.addObject("list", list);

		mv.setViewName("eNotMemBuy");

		return  mv;
	}





	@GetMapping("/eBuy")
	public ModelAndView eBuy(BookDTO bookDTO, HttpSession session){

		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();
			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);
			mv.addAllObjects(service.useraddress(mid).getModel());

			//장바구니 품목 가져옴
			mv.addAllObjects(service.cartlist(mid).getModel());

			//장바구니
			Integer count = service.getMaxCno(mid);
			mv.addObject("count", count);
			mv.addAllObjects(service.cartlist(mid).getModel());

			//금액관련 정보를 위한 리스트
			List<BookDTO> list = service.cartList(mid);
			mv.addObject("list", list);


			// 장바구니 번호 설정
			Integer cartnum = service.getMaxCno(mid);
			if (cartnum == null) {
				cartnum = 1;
			}
			else {
				cartnum = cartnum-1;
			}





		}



		return  mv;
	}



	@PostMapping("/emembuy")
	public ModelAndView emembuy(@RequestParam("cbookno") String[] cbooknos, @RequestParam("cbuynum") int[] cbuynums, @RequestParam("buyprice") int[] buyprices, @RequestParam("buydelivery") int ano, @RequestParam("buyway") String buyway, @RequestParam("buyaccount") String buyaccount, Model model, HttpSession session, BookDTO bookDTO) {
		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");
		String mid = loginDto.getMid();
		model.addAttribute("mid", mid);

		// 각 cbookno와 cbuynum을 BookDTO에 설정하고 Service에 전달
		for (int i = 0; i < cbooknos.length; i++) {
			bookDTO = new BookDTO();
			bookDTO.setBuyid(mid);
			bookDTO.setCbookno(cbooknos[i]);
			bookDTO.setCbuynum(cbuynums[i]);
			bookDTO.setBuyprice(buyprices[i]);
			bookDTO.setBuydelivery(ano);
			bookDTO.setBuyway(buyway);
			bookDTO.setBuyaccount(buyaccount);

			// 수량 체크하기
			int bookcount = service.stockcount(bookDTO.getCbookno());

			// 재고 없을 때
			if (bookcount - bookDTO.getCbuynum() < 0) {
				System.out.println(bookDTO.getCbookno()+"번 책의 재고");
				mv.addObject("msg", "책의 재고가 없어서 구매할 수 없습니다. " + bookcount + "개 미만으로 구매해주세요.");
				mv.addObject("url", "/eCart?mid=" + mid);
				mv.setViewName("eAlert");
				return mv; // 재고가 부족할 때 메서드를 즉시 종료
			}
		}

		// 모든 책이 구매 가능할 때
		for (int i = 0; i < cbooknos.length; i++) {
			bookDTO = new BookDTO();
			bookDTO.setBuyid(mid);
			bookDTO.setCbookno(cbooknos[i]);
			bookDTO.setCbuynum(cbuynums[i]);
			bookDTO.setBuyprice(buyprices[i]);
			bookDTO.setBuydelivery(ano);
			bookDTO.setBuyway(buyway);
			bookDTO.setBuyaccount(buyaccount);

			// Service 계층으로 DTO 전달
			service.buymem(bookDTO);

			// 수량변동
			int change = service.countchange(bookDTO);    
		}

		// 장바구니에서 지우기
		int result = service.delcart(mid);
		mv.addObject("msg", "구매가 완료되었습니다.");
		mv.addObject("url", "eBuySuccess");
		mv.setViewName("eAlert");

		return mv;

	}



	@PostMapping("/enotmembuy")
	public ModelAndView enotmembuy(@RequestParam("cbookno") String[] cbooknos,@RequestParam("cbuynum") int[] cbuynums, @RequestParam("buyprice") int[] buyprices,@RequestParam("buyway") String buyway, @RequestParam("buyaccount") String buyaccount,@RequestParam("buypassword") String buypassword,Model model,BookDTO bookDTO,adressDTO aDTO) {
		mv = new ModelAndView();

		String mid="none";
		System.out.println("비회원 구매");


		// 각 cbookno와 cbuynum을 BookDTO에 설정하고 Service에 전달
		for (int i = 0; i < cbooknos.length; i++) {
			bookDTO = new BookDTO();
			bookDTO.setBuyid(mid);
			bookDTO.setCbookno(cbooknos[i]);
			bookDTO.setCbuynum(cbuynums[i]);
			bookDTO.setBuyprice(buyprices[i]);
			bookDTO.setBuyway(buyway);
			bookDTO.setBuyaccount(buyaccount);
			bookDTO.setBuypassword(buypassword);

			//수량 체크하기
			int bookcount = service.stockcount(bookDTO.getCbookno());

			// 재고 없을 때
			if (bookcount - bookDTO.getCbuynum() < 0) {

				mv.addObject("msg", "책의 재고가 없어서 구매할 수 없습니다. " + bookcount + "개 미만으로 구매해주세요.");
				mv.addObject("url", "/eCart?mid=" + mid);
				mv.setViewName("eAlert");
				return mv; // 재고가 부족할 때 메서드를 즉시 종료
			}
		}

		int num=0;
		// 모든 책이 구매 가능할 때
		for (int i = 0; i < cbooknos.length; i++) {
			bookDTO = new BookDTO();
			bookDTO.setBuyid(mid);
			bookDTO.setCbookno(cbooknos[i]);
			bookDTO.setCbuynum(cbuynums[i]);
			bookDTO.setBuyprice(buyprices[i]);
			bookDTO.setBuyway(buyway);
			bookDTO.setBuyaccount(buyaccount);
			bookDTO.setBuypassword(buypassword);

			// Service 계층으로 DTO 전달
			int result= service.buynotmem(bookDTO);

			//수량변동
			int change = service.countchange(bookDTO);
			int cartdel = service.delcart(mid);

			if(result==1) {
				num=service.getMaxBuyno();


				System.out.println("구매 번호 : "+num);
				aDTO.setNonno(num);				
				int addAddress=service.nonmemaddressAdd(aDTO);
				if(addAddress==1) {
					System.out.println("주소등록 완료");

				}
				else {
					System.out.println("실패");
				}



			}

		}
		mv.addObject("num", num);
		mv.addObject("msg", "구매가 완료되었습니다.");
		mv.addObject("url", "eBuySuccess?num="+num);
		mv.setViewName("eAlert");

		return mv;

	}



	@GetMapping("/eBookDetail")
	public ModelAndView eBookDetail(@RequestParam("bono") String bono, HttpServletRequest req, HttpSession session) {

		ModelAndView mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();
			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);

			//장바구니
			Integer count = service.getMaxCno(mid);
			mv.addObject("count", count);
			mv.addAllObjects(service.cartlist(mid).getModel());
		} else {

			//장바구니
			Integer count = service.getMaxCno("none");
			mv.addObject("count", count);
			mv.addAllObjects(service.cartlist("none").getModel());
		}

		mv.addAllObjects(service.bookdetail(bono).getModel());
		mv.addAllObjects(service.randombook(bono).getModel());


		//사진
		String path = service.bookfile(bono);

		mv.addObject("filepath", path);	  





		mv.setViewName("eBookDetail");

		return mv;
	}



	@GetMapping("/eBookBuy")
	public ModelAndView eBookBuy(BookDTO bookDTO, @RequestParam("product-quantity") int buynum, HttpServletRequest req, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String btn = req.getParameter("buybtn");
		String bono = bookDTO.getBono();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인 된 경우
			String mid = loginDto.getMid();
			mv.addObject("mid", mid);

			// 장바구니 번호 설정
			Integer num = service.getMaxCno(mid);
			if (num == null) {
				num = 1;
			}

			// bookDTO 설정
			bookDTO.setCno(num);
			bookDTO.setCuserid(mid);
			bookDTO.setCbuynum(buynum);
			bookDTO.setCbookno(bono);

			// 장바구니에 추가
			int result = service.addCart(bookDTO);

			// 버튼에 따른 페이지 설정
			if (btn.equals("장바구니에 담기")) {
				mv.addObject("msg", "장바구니에 담겼습니다. 장바구니로 이동하겠습니다.");
				mv.addObject("url", "/eCart?mid="+mid);
				mv.setViewName("eAlert");
			} else if (btn.equals("구매하기")) {
				// 구매시 장바구니 리셋
				int delcart = service.delcart(mid);
				if(delcart==1) {
					System.out.println("장바구니 리셋");          
				}
				service.addCart(bookDTO);
				mv.setViewName("redirect:/eBuy");
			}
		} else {
			// 로그인 안된 경우
			if (btn.equals("구매하기")) {
				mv.addObject("msg", "로그인이 필요합니다.");
				mv.addObject("url", "/elogin");
				mv.setViewName("eAlert");

			} else {
				// 비회원 장바구니 번호 설정
				Integer num = service.getMaxCno("none");
				if (num == null) {
					num = 1;
				}

				// bookDTO 설정
				bookDTO.setCno(num);
				bookDTO.setCbuynum(buynum);
				bookDTO.setCbookno(bono);

				// 장바구니에 추가
				int result = service.addNonCart(bookDTO);

				// 버튼에 따른 페이지 설정
				if (btn.equals("비회원 주문")) {
					// 구매시 장바구니 리셋
					int delcart = service.delcart("none");
					if(delcart==1) {
						System.out.println("장바구니 리셋");          
					}
					service.addNonCart(bookDTO);
					mv.addObject("msg", "비회원 주문을 진행하겠습니다.");
					mv.addObject("url", "/eNotMemBuy?mid=none");
					mv.setViewName("eAlert");

				} else if (btn.equals("장바구니에 담기")) {
					mv.addObject("msg", "장바구니에 담겼습니다. 장바구니로 이동하겠습니다.");
					mv.addObject("url", "/eNonMemCart");
					mv.setViewName("eAlert");

				}
			}
		}

		return mv;
	}





	@GetMapping("/eRequestN")
	public ModelAndView eRequestN(){


		mv = new ModelAndView();
		mv.addAllObjects(service.newbook().getModel());

		mv.setViewName("eRequestN");

		return mv;
	}


	@GetMapping("/eRequestS")
	public ModelAndView eRequestS(){


		mv = new ModelAndView();
		mv.addAllObjects(service.soldbook().getModel());

		mv.setViewName("eRequestS");
		return mv;
	}


	@GetMapping("/eRequestList")
	public ModelAndView eRequestList(){

		mv = new ModelAndView();
		mv.addAllObjects(service.allreqbook().getModel());



		return mv;
	}



	@GetMapping("/eBuySuccess")
	public ModelAndView eBuySuccess(HttpSession session){

		mv = new ModelAndView();
		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");


		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();
			System.out.println(mid);
			mv.addObject("mid", mid);

		}


		return mv;
	}


	@GetMapping("/eBuyList")
	public String eBuyList(){

		return "eBuyList";
	}



	@GetMapping("/eNotMemBuyList")
	public ModelAndView eNotMemBuyList(@RequestParam("buyno")int buyno){

		mv = new ModelAndView();
		String buydate=service.nonmemBuydate(buyno);

		//장바구니
		Integer count = service.getMaxCno("none");
		mv.addObject("count", count);
		mv.addAllObjects(service.cartlist("none").getModel());

		mv.addAllObjects(service.nonMemBuylist(buydate).getModel());

		mv.addAllObjects(service.nonMemDelivery(buyno).getModel());



		return mv;
	}

	@GetMapping("/eNotMemBuyLogin")
	public String eNotMemBuyLogin(){


		return "eNotMemBuyLogin";
	}

	@PostMapping("/eNotMemBuyLogin")
	public ModelAndView eNotMemBuyLogin(@RequestParam Map<String, String> map, HttpSession session) {
		
		
		mv = new ModelAndView();
		

		mv = service.nonMemlogin(map, session);


		return mv;
	}

	@GetMapping("/eCanList")
	public String eCanList(){

		return "eCanList";
	}


	@GetMapping("/eMapList")
	public ModelAndView eMapList(){


		mv = new ModelAndView();
		//금액관련 정보를 위한 리스트

		List<mapDTO> list = service.bookstoremap();
		mv.addObject("list", list);

		mv.setViewName("eMapList");
		return mv;
	}


	@GetMapping("/eAddressList")
	public ModelAndView eAddressList(@RequestParam("mid")String mid){

		mv = new ModelAndView();
		mv.addAllObjects(service.addressbook(mid).getModel());

		mv.setViewName("eAddressList");

		return mv;
	}



	@GetMapping("/eAddressAdd")
	public String eAddressAdd(@RequestParam("mid")String mid){

		return "eAddressAdd";
	}



	@PostMapping("/addressAdd")
	public String addressAdd(@RequestParam("mid")String mid, adressDTO aDTO) {


		int ano = service.getMaxAno(mid);
		aDTO.setAid(mid);
		aDTO.setAno(ano);
		int result=service.addressAdd(aDTO);

		return "redirect:/eAddressList?mid="+mid;
	}



	@GetMapping("/eCard")
	public String eCard(){

		return "eCard";
	}

	@GetMapping("/eBank")
	public String eBank(){

		return "eBank";
	}

	@GetMapping("/ePayment")
	public String ePayment(){

		return "ePayment";
	}

	@PostMapping("/ePaymentmethod")
	public String ePaymentmethod(BookDTO bookDTO,HttpServletRequest req){

		String btn = req.getParameter("paymentbtn");


		if(btn.equals("카드 결제")) {
			bookDTO.setBuyway("W0");
			System.out.println(bookDTO.getBuyway());
		}else if(btn.equals("무통장 입금")) {
			System.out.println(btn+"결제 방식 선택");
			bookDTO.setBuyway("W1");
		}else if(btn.equals("간편 결제")) {
			System.out.println(btn+"결제 방식 선택");
			bookDTO.setBuyway("W2");
		}


		return "ePaymentmethod";
	}



	//요청


	@GetMapping("/eRequestForm")
	public ModelAndView eRequestForm(@RequestParam("bono")String bono, HttpSession session){



		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();

			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);

			int resut=service.bookReq(bono);
			mv.addObject("msg", "요청이 완료되었습니다. 페이지에서 확인 가능합니다.");
			mv.addObject("url", "/eBookDetail?bono="+bono);
			mv.setViewName("eAlert");

		}


		return mv;
	}



	//검색
	@GetMapping("/eBookSearch")
	public ModelAndView eBookSearch(@RequestParam("boname")String boname,HttpSession session,Model model) {
		mv = new ModelAndView();

		// 세션에서 로그인 정보 가져오기
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		if (loginDto != null) {
			// 로그인된 사용자의 mid 가져오기
			String mid = loginDto.getMid();

			// eMain 페이지에 mid 정보를 전달하기 위해 추가
			mv.addObject("mid", mid);

		}

		String bono = service.booksearch(boname);

		if(bono==null) {

			mv.addObject("msg", "책 제목을 다시 확인해주세요.");
			mv.addObject("url", "/eMain");
			mv.setViewName("eAlert");
		}
		else {
			mv.setViewName("redirect:/eBookDetail?bono="+bono);
		}


		return mv;
	}


	@GetMapping("/eBookCList")
	public ModelAndView eBookCList(@RequestParam("bogenre")String bogenre, BookDTO bookDTO) {
		mv = new ModelAndView();

		List<BookDTO> list = service.categorylist(bogenre);

		mv.addObject("list", list);

		mv.setViewName("eBookCList");

		return mv;
	}



	@GetMapping("/eNonMemCancel")
	public ModelAndView eNonMemCancel(@RequestParam("buyno")String buyno, BookDTO bookDTO) {
		mv = new ModelAndView();	
		System.out.println("취소 신청");
		bookDTO.setBuycode("B19");

		int result = service.nonmemreq(bookDTO);

		if(result==1) {
			mv.addObject("msg", "취소 신청되었습니다.");
		}
		else {
			mv.addObject("msg", "잘못된 접근입니다.");
		}
		mv.setViewName("eAlert");
		mv.addObject("url", "/eNotMemBuyList?buyno="+buyno);
		return mv;
	}

	@GetMapping("/eNonMemRefund")
	public ModelAndView eNonMemRefund(@RequestParam("buyno")String buyno,BookDTO bookDTO) {
		mv = new ModelAndView();
		System.out.println("환불 신청");
		bookDTO.setBuycode("B15");

		int result = service.nonmemreq(bookDTO);

		if(result==1) {
			mv.addObject("msg", "환불 신청되었습니다.");
			mv.setViewName("eAlert");
		}
		else {
			mv.addObject("msg", "잘못된 접근입니다.");
		}
		mv.setViewName("eAlert");
		mv.addObject("url", "/eNotMemBuyList?buyno="+buyno);
		return mv;
	}

	@GetMapping("/eNonMemChange")
	public ModelAndView eNonMemChange(@RequestParam("buyno")String buyno, BookDTO bookDTO) {
		mv = new ModelAndView();
		System.out.println("교환 신청");
		bookDTO.setBuycode("B3");

		int result = service.nonmemreq(bookDTO);

		if(result==1) {
			mv.addObject("msg", "교환 신청되었습니다.");
		}
		else {
			mv.addObject("msg", "잘못된 접근입니다.");
		}

		mv.setViewName("eAlert");
		mv.addObject("url", "/eNotMemBuyList?buyno="+buyno);
		return mv;
	}










}
