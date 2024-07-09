package kr.co.book.ecom.Controller;

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
import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.service.BookService;


@Controller
public class bookController {

	private ModelAndView mv;

	@Autowired
	public BookService service;


	@GetMapping("/eMain")
	public ModelAndView eMain(){


		mv = new ModelAndView();

		mv.addAllObjects(service.bestbooktop9().getModel());
		mv.addAllObjects(service.reqbook().getModel());
		mv.addAllObjects(service.adbook().getModel());

		mv.setViewName("eMain");

		return mv;
	}



	@GetMapping("/eCart")
	public String eCart(){

		return "eCart";
	}


	@GetMapping("/eBuy")
	public String eBuy(){

		return "eBuy";
	}

	@GetMapping("/ePayment")
	public String ePayment(){

		return "ePayment";
	}


	@GetMapping("/eBookstoreList")
	public String eBookstoreList(){

		return "eBookstoreList";
	}


	@GetMapping("/eNotMemBuy")
	public ModelAndView eNotMemBuy(@RequestParam("bono") String bono, @RequestParam("buynum") int buynum){

		mv = new ModelAndView();

		mv.addAllObjects(service.onebookbuy(bono).getModel());
		mv.addObject("buynum", buynum);

		return  mv;
	}


	@PostMapping("/enotmembuy")
	public String enotmembuy(BookDTO bookDTO, Model model) {


		int bookstock = service.stockcount(bookDTO.getBuybooknum());
		System.out.println(bookDTO.getBuybooknum()+"번 책 재고 수  " +bookstock +" 권 남음");

		int buy = bookDTO.getBuynum();
		System.out.println(buy+"개 구매");

		if(bookstock<=0) {
			System.out.println("재고 부족으로 구매 불가능");
			return "redirect:/eBookDetail?bono="+bookDTO.getBuybooknum();
		}

		else if((bookstock-buy)<0) {
			System.out.println("재고보다 많은 구매수로 구매 불가능");
			return "redirect:/eBookDetail?bono="+bookDTO.getBuybooknum();
		}
		else {	
			//구매
			int result= service.buynotmem(bookDTO);
			int count=service.countchange(bookDTO);

		}


		model.addAttribute("url", "eMain");

		return "eBuySuccess";

	}


	@GetMapping("/eBookDetail")
	public ModelAndView eBookDetail(@RequestParam("bono") String bono){

		mv = new ModelAndView();
		mv.addAllObjects(service.bookdetail(bono).getModel());
		mv.addAllObjects(service.randombook(bono).getModel());

		mv.setViewName("eBookDetail");

		return mv;
	}


	@GetMapping("/notmembuy")
	public String notmembuy(BookDTO bookDTO,@RequestParam("product-quantity") int buynum, HttpServletRequest req){

		String bono = bookDTO.getBono();


		System.out.println(bono+"번 책");
		System.out.println(buynum+"개 구매");


		return "redirect:/eNotMemBuy?bono="+bono+"&buynum="+buynum;
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
		mv.addAllObjects(service.reqbook().getModel());

		mv.setViewName("eRequestList");

		return mv;
	}



	@GetMapping("/eBuyList")
	public String eBuyList(){

		return "eBuyList";
	}

	@GetMapping("/eCanList")
	public String eCanList(){

		return "eCanList";
	}


	@GetMapping("/eMapList")
	public String eMapList(){

		return "eMapList";
	}




}
