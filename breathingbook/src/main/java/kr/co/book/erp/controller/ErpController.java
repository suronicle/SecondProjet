package kr.co.book.erp.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.book.erp.dto.BranchDTO;
import kr.co.book.erp.dto.FileDTO;
import kr.co.book.erp.service.BranchService;
import kr.co.book.erp.service.FileService;
import kr.co.book.erp.service.HmainService;
import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.loginsessionDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.erp.service.PurchaseService;
import kr.co.book.erp.service.sBookService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@Slf4j
public class ErpController {
	
	private ModelAndView mv;
	
	@Autowired
    private BranchService bServ;
	
	@Autowired
    private PurchaseService pServ;
	
	
	@Autowired
	private FileUtil fileUtil;
	@Autowired
	private FileService fileServ;
	@Autowired
	private sBookService boServ;
	@Autowired
	private HmainService hServ;

	@GetMapping("/m")
	public String m_main(Model model, HttpSession session) {

	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");
	    if (loginDto != null) {
	        // 로그인된 사용자의 mid 가져오기
	        String brno = loginDto.getBrno();
	        // eMain 페이지에 mid 정보를 전달하기 위해 추가
	        model.addAttribute("brno", brno);
	        log.info("m_main() with brno: {}", brno);
	        BranchDTO bdto = bServ.comView(brno);
		    model.addAttribute("bdto", bdto);
		    
	        try {
	            int vencnt = hServ.countVen();
	            model.addAttribute("vencnt", vencnt);
	        } catch (Exception e) {
	            log.error("Failed to fetch vendor count", e);
	            model.addAttribute("vencnt", 0); // 기본 값 설정
	        }

	        try {
	            BookDTO venbest = hServ.bestVen();
	            model.addAttribute("venbest", venbest != null ? venbest : new BookDTO());
	        } catch (Exception e) {
	            log.error("Failed to fetch best vendor book", e);
	            model.addAttribute("venbest", new BookDTO());
	        }

	        try {
	            int bracnt = hServ.countBra();
	            model.addAttribute("bracnt", bracnt);
	        } catch (Exception e) {
	            log.error("Failed to fetch branch count", e);
	            model.addAttribute("bracnt", 0);
	        }

	        try {
	            BookDTO braBest = hServ.bestBra();
	            model.addAttribute("braBest", braBest != null ? braBest : new BookDTO());
	        } catch (Exception e) {
	            log.error("Failed to fetch best branch book", e);
	            model.addAttribute("braBest", new BookDTO());
	        }

	        try {
	            int bookcnt = hServ.countBook();
	            model.addAttribute("bookcnt", bookcnt);
	        } catch (Exception e) {
	            log.error("Failed to fetch book count", e);
	            model.addAttribute("bookcnt", 0);
	        }

	        try {
	            BookDTO bookBest = hServ.bestBook();
	            model.addAttribute("bookBest", bookBest != null ? bookBest : new BookDTO());
	        } catch (Exception e) {
	            log.error("Failed to fetch best book", e);
	            model.addAttribute("bookBest", new BookDTO());
	        }

	        try {
	            BookDTO lastsales = hServ.lastmonth();
	            model.addAttribute("lastsales", lastsales != null ? lastsales : new BookDTO());
	        } catch (Exception e) {
	            log.error("Failed to fetch last month sales", e);
	            model.addAttribute("lastsales", new BookDTO());
	        }

	        try {
	            BookDTO cursales = hServ.curmonth();
	            model.addAttribute("cursales", cursales != null ? cursales : new BookDTO());
	        } catch (Exception e) {
	            log.error("Failed to fetch current month sales", e);
	            model.addAttribute("cursales", new BookDTO());
	        }
	        
	        try {
	        	List<BookDTO> salesData = hServ.getMonthlySales();
	            model.addAttribute("salesData", salesData != null ? salesData : new BookDTO());
	            System.out.println("Sales Data: " + salesData);

	        } catch (Exception e) {
	            log.error("Failed to fetch current month sales", e);
	            model.addAttribute("salesData", new BookDTO());
	        }
	        
	        try {
	        	List<BookDTO> buyData = hServ.getMonthlyBuy();
	            model.addAttribute("buyData", buyData != null ? buyData : new BookDTO());
	            System.out.println("Buy Data: " + buyData);
	        } catch (Exception e) {
	            log.error("Failed to fetch current month buy", e);
	            model.addAttribute("buyData", new BookDTO());
	        }
	    } else {
	        log.warn("No login session found");
	    }

	    return "m_main";
	}


	@GetMapping("m_comList")
	public ModelAndView m_comList(@RequestParam("source") String source) {
		log.info("m_comList() with source: {}", source);
		mv = new ModelAndView("m_comList");
		mv.addObject("source", source);
		if("branch".equals(source)) {
	        mv.addAllObjects(bServ.branchList().getModel());
	    } else {
	        mv.addAllObjects(bServ.vendorList().getModel());
	    }
		
		mv.setViewName("m_comList");
		return mv;
	}

	@PostMapping("m_comInsert")
    public String m_comInsert(@ModelAttribute BranchDTO bdto, @RequestParam("source") String source,
    							RedirectAttributes rttr) {
        log.info("m_comInsert() with source: {}", source);
        int result = bServ.comInsert(bdto);
       
        if (result < 0) {
        	log.error("Failed to insert branch data.");
            return "m_comInsert::insertcom";
        } else {
        	rttr.addFlashAttribute("msg", "등록 완료 되었습니다.");
            return "redirect:/m_comList?source=" + source;
        }
    }
	
	@GetMapping("m_View")
	public String m_View(Model model, @RequestParam("brno") String brno,
	                     @RequestParam("source") String source) {
	    log.info("m_View() with brno: {}, source: {}", brno, source);

	    BranchDTO bdto = bServ.comView(brno);
	    model.addAttribute("bdto", bdto);
	    model.addAttribute("source", source);

	    // m_comList.html의 viewCom Fragment를 반환합니다
	    return "m_View::viewcom";
	}
	
	@GetMapping("m_myinfo")
	public String m_myinfo(Model model, HttpSession session) {
	    // 세션에서 로그인 정보 가져오기
	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");
	    if (loginDto == null) {
	        // 로그인 정보가 없으면 처리할 로직 추가
	        return "redirect:/elogin"; // 예시로 임의의 로그인 페이지로 리다이렉트
	    }

	    // 로그인된 사용자의 brno 가져오기
	    String brno = loginDto.getBrno();
	    log.info("m_myinfo() with brno: {}", brno);

	    // BranchDTO 조회 및 모델에 추가
	    BranchDTO bdto = bServ.comView(brno);
	    model.addAttribute("bdto", bdto);

	    // brno을 사용하여 추가적인 로직을 수행할 수 있습니다.

	    // 최종적으로 view 이름을 반환하여 해당하는 뷰로 이동합니다.
	    return "m_myinfo"; // 이는 실제 뷰의 이름에 따라 달라질 수 있습니다.
	}
	
	@GetMapping("m_comUpdate")
	public String m_comUpdate(Model model, @RequestParam("brno") String brno,
	                     @RequestParam("source") String source) {
	    log.info("m_comUpdate() with brno: {}, source: {}", brno, source);

	    BranchDTO bdto = bServ.comView(brno);
	    model.addAttribute("bdto", bdto);
	    model.addAttribute("source", source);

	    // m_comList.html의 editCom Fragment를 반환합니다
	    return "m_comUpdate::editcom";
	}
	
	@PostMapping("m_comUpdate")
	public String m_comUpdate(BranchDTO bdto, @RequestParam("source") String source,
								RedirectAttributes rttr) {
	    log.info("m_comUpdate1() with source: {}", source);

	    int result = bServ.comUpdate(bdto);
		if (result < 0) {
			log.error("Failed to update branch data.");
			return "m_comUpdate::editcom";
		} else {
			if(source == "") {
				rttr.addFlashAttribute("msg", "수정 완료 되었습니다.");
				return "redirect:/m_myinfo?brno=" + bdto.getBrno();
			}
			rttr.addFlashAttribute("msg", "수정 완료 되었습니다.");
			return "redirect:/m_comList?source=" + source;
		}
	}
	
	
	@PostMapping("m_changePw")
	public void m_changePw(@RequestParam("cpw1") String cpw1, @RequestParam("cpw2") String cpw2,
						   @RequestParam("cpw3") String cpw3, HttpSession session,
						   HttpServletResponse response, HttpServletRequest request) throws IOException , Exception {
	    log.info("m_changePw() with cpw1:{}, cpw2:{}, cpw3:{}", cpw1, cpw2, cpw3);
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");
		String brno = loginDto.getBrno();
	    log.info("m_changePw() with brno:{}",brno);
	    
	    BranchDTO bdto = bServ.comView(brno);
	    String pwCheck = bdto.getBrpw();
	    log.info("m_changePw() with pwCheck:{}",pwCheck);
	    
	    if (pwCheck != null && pwCheck.equals(cpw1)) {
	        bServ.changePw(brno, cpw2);
	        log.info("success cpw2:{}", cpw2);
	        session.invalidate();
	        response.getWriter().write("success");
	    } else {
	    	log.info("failure");
	    }
	}
	
	@GetMapping("m_comDelete")
	public String m_comDelete(Model model, @RequestParam("brno") String brno,
								@RequestParam("source") String source, RedirectAttributes rttr) {
	    log.info("m_comDelete() with brno: {}, source: {}", brno, source);
	    
	    model.addAttribute("brno", brno);
	    model.addAttribute("source", source);
	    bServ.comDelete(brno);
	    rttr.addFlashAttribute("msg", "삭제 완료 되었습니다.");
	    return "redirect:/m_comList?source=" + source;
	}

	@GetMapping("m_calculation")
	public List<BookDTO> m_calculation(Model model, HttpSession session) {
		log.info("purList()");
	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
	    int brclass = loginDto.getBrclass();
	    String brno = loginDto.getBrno();
	    log.info("purList() with brclass:{},brno: {}", brclass, brno);

	    if(brclass != 0) {
			List<BookDTO> purList = pServ.purList1(brno); //월별
			model.addAttribute("purList",purList);
			return purList;
	    } else {
			List<BookDTO> purList = pServ.purList(); //월별
			model.addAttribute("purList",purList);
			return purList;
	    }
	}
	
	@GetMapping("m_calcYear")
	public List<BookDTO> m_calcYear(Model model, @RequestParam("year") int year, HttpSession session) {
		log.info("m_calcYear() with year: {}",year);
	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
	    int brclass = loginDto.getBrclass();
	    String brno = loginDto.getBrno();
	    log.info("purList() with brclass:{},brno: {}", brclass, brno);

	    if(brclass != 0) {
			List<BookDTO> sumyeartotal = pServ.sumyeartotal1(year,brno); //월별
			model.addAttribute("sumyeartotal",sumyeartotal);
			return sumyeartotal;
	    } else {
		List<BookDTO> sumyeartotal = pServ.sumyeartotal(year); //년별
		model.addAttribute("sumyeartotal",sumyeartotal);
		
		return sumyeartotal;
	    }
	}
	
	@GetMapping("m_calcMonth")
	public List<BookDTO> m_calcMonth(Model model, @RequestParam("month") int month, HttpSession session) {
		log.info("m_calcMonth() with month: {}",month);
	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
	    int brclass = loginDto.getBrclass();
	    String brno = loginDto.getBrno();
	    log.info("purList() with brclass:{},brno: {}", brclass, brno);

	    if(brclass != 0) {
			List<BookDTO> summonthtotal = pServ.summonthtotal1(month,brno); //월별
			model.addAttribute("summonthtotal",summonthtotal);
			return summonthtotal;
	    } else {
		List<BookDTO> summonthtotal = pServ.summonthtotal(month); //월별
		model.addAttribute("summonthtotal",summonthtotal);
		
		return summonthtotal;
	    }
	}
	
	@GetMapping("m_calcDay")
	public List<BookDTO> m_calcDay(Model model, @RequestParam("day") int day, HttpSession session) {
		log.info("m_calcDay() with day: {}", day);
	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
	    int brclass = loginDto.getBrclass();
	    String brno = loginDto.getBrno();
	    log.info("purList() with brclass:{},brno: {}", brclass, brno);

	    if(brclass != 0) {
			List<BookDTO> sumdaytotal = pServ.sumdaytotal1(day,brno); //월별
			model.addAttribute("sumdaytotal",sumdaytotal);
			return sumdaytotal;
	    } else {
		List<BookDTO> sumdaytotal = pServ.sumdaytotal(day); //일별
		model.addAttribute("sumdaytotal",sumdaytotal);
		
		return sumdaytotal;
	    }
	}
	
	
	@PostMapping("m_calcAdd")
    public String purInsert(@ModelAttribute BookDTO book, RedirectAttributes rttr) {
        log.info("purAdd()");
        int result = pServ.purAdd(book);
       
        if (result < 0) {
        	log.error("Failed to insert purchase data.");
            return "m_calcAdd";
        } else {
        	rttr.addFlashAttribute("msg", "등록 완료 되었습니다.");
            return "redirect:/m_calculation";
        }
    }

	@GetMapping("m_calcEdit")
	public String purUpdate(Model model, @RequestParam("pno") int pno,
							@RequestParam("source") String source) {
		log.info("purUpdate() with pno: {}, source: {}", pno, source);
		BookDTO book = pServ.purView(pno);
		model.addAttribute("book", book);
		model.addAttribute("source", source);

		return "m_calcEdit";
	}
	
	@PostMapping("m_calcEdit")
		public String purUpdate(BookDTO book, @RequestParam("source") String source,
								RedirectAttributes rttr) {
			log.info("purUpdate() with source: {}", source);
			int result = pServ.purEdit(book);
			rttr.addFlashAttribute("msg", "수정 완료 되었습니다.");
			if (result < 0) {
				log.error("Failed to update purchase data.");
				return "m_calcEdit";
			} else {
				if("purchase".equals(source)) {
					return "redirect:/m_comPurchase";
			} else if("sales".equals(source)) {
				return "redirect:/m_comSales";
			} else {
				return "redirect:/m_calculation";
		    }
		}
	}
	@GetMapping("m_searchBook")
	public String m_searchBook(Model model) {
		log.info("m_searchBook()");
		List<BookDTO> bookList;
		bookList = pServ.searchBook();
		model.addAttribute("bookList",bookList);
		
		return "m_searchModal::searchBook";
	}

	@GetMapping("m_searchBook2")
	public String m_searchBook2(Model model) {
		log.info("m_searchBook2()");
		List<BookDTO> bookList;
		bookList = pServ.searchBook();
		model.addAttribute("bookList",bookList);
		
		return "m_searchModal::searchBook2";
	}
	
	@GetMapping("m_searchCom")
	public String m_searchCom(@RequestParam(value = "brclass", required = false) Integer brclass, Model model) {
		log.info("m_searchCom()");
		List<BranchDTO> comList;
		 if (brclass == 0) {
	            comList = bServ.searchCom();
	        } else if (brclass == 2) {
	            comList = bServ.searchVen();
	        } else {
	            // 예외 처리 등
	            comList = bServ.searchCom();
	        }
		model.addAttribute("comList",comList);
		
		return "m_searchModal::searchCom";
	}
	
	@GetMapping("m_searchCom2")
	public String m_searchCom2(@RequestParam(value = "brclass2", required = false) Integer brclass2, Model model) {
		log.info("m_searchCom2()");
		List<BranchDTO> comList;
		 if (brclass2 == 0) {
	            comList = bServ.searchCom();
	        } else if (brclass2 == 2) {
	            comList = bServ.searchVen();
	        } else {
	            // 예외 처리 등
	            comList = bServ.searchCom();
	        }
		model.addAttribute("comList",comList);
		
		return "m_searchModal::searchCom2";
	}
	@GetMapping("m_comPurchase")
	public List<BookDTO> m_comPurchase(Model model) {
		log.info("purList()");
		List<BookDTO> purList = pServ.purList();
		model.addAttribute("purList",purList);
		
		return purList;
	}
	@GetMapping("m_purYear")
	public List<BookDTO> m_purYear(Model model, @RequestParam("year") int year) {
		log.info("m_purYear() with year: {}",year);
		List<BookDTO> sumyeartotal = pServ.sumyeartotal(year); //년별
		model.addAttribute("sumyeartotal",sumyeartotal);
		
		return sumyeartotal;
	}
	
	@GetMapping("m_purMonth")
	public List<BookDTO> m_purMonth(Model model, @RequestParam("month") int month) {
		log.info("m_purMonth() with month: {}",month);
		List<BookDTO> summonthtotal = pServ.summonthtotal(month); //월별
		model.addAttribute("summonthtotal",summonthtotal);
		
		return summonthtotal;
	}
	
	@GetMapping("m_purDay")
	public List<BookDTO> m_purDay(Model model, @RequestParam("day") int day) {
		log.info("m_purDay() with day: {}", day);
		List<BookDTO> sumdaytotal = pServ.sumdaytotal(day); //일별
		model.addAttribute("sumdaytotal",sumdaytotal);
		
		return sumdaytotal;
	}
	@GetMapping("m_comSales")
	public List<BookDTO> m_comSales(Model model) {
		log.info("purList()");
		List<BookDTO> purList = pServ.purList();
		model.addAttribute("purList",purList);
		
		return purList;
	}
	@GetMapping("m_salesYear")
	public List<BookDTO> m_salesYear(Model model, @RequestParam("year") int year) {
		log.info("m_salesYear() with year: {}",year);
		List<BookDTO> sumyeartotal = pServ.sumyeartotal(year); //년별
		model.addAttribute("sumyeartotal",sumyeartotal);
		
		return sumyeartotal;
	}
	
	@GetMapping("m_salesMonth")
	public List<BookDTO> m_salesMonth(Model model, @RequestParam("month") int month) {
		log.info("m_salesMonth() with month: {}",month);
		List<BookDTO> summonthtotal = pServ.summonthtotal(month); //월별
		model.addAttribute("summonthtotal",summonthtotal);
		
		return summonthtotal;
	}
	
	@GetMapping("m_salesDay")
	public List<BookDTO> m_salesDay(Model model, @RequestParam("day") int day) {
		log.info("m_salesDay() with day: {}", day);
		List<BookDTO> sumdaytotal = pServ.sumdaytotal(day); //일별
		model.addAttribute("sumdaytotal",sumdaytotal);
		
		return sumdaytotal;
	}
	
	@GetMapping("m_purDelete")
	public String m_purDelete(Model model, @RequestParam("pno") int pno,
								@RequestParam("source") String source, RedirectAttributes rttr) {
	    log.info("m_comDelete() with pno: {}, source: {}", pno, source);
	    
	    model.addAttribute("pno", pno);
	    model.addAttribute("source", source);
	    pServ.purDelete(pno);
	    rttr.addFlashAttribute("msg", "삭제 완료 되었습니다.");
	    if("purchase".equals(source)) {
	    	return "redirect:/m_comPurchase";
	    } else if("sales".equals(source)) {
	    	return "redirect:/m_comSales";
	    } else {
	    	return "redirect:/m_calculation";
	    }
	}
	
	@GetMapping("/m_print")
	public String m_print() {
		return "m_print";
	}
	
	// 회원정보
	

	@GetMapping("m_customerList")
	public String m_customerList(Model model) {
		log.info("m_customerList()");
		List<memberDTO> memList = bServ.memList();
		model.addAttribute("memList",memList);
		
		return "m_customerList";
	}
	
	@GetMapping("m_memView")
	public String m_memView(Model model, @RequestParam("mid") String mid) {
	    log.info("m_memView() with mid: {}", mid);

	    memberDTO mem = bServ.viewMem(mid);
	    model.addAttribute("mem", mem);

	    // m_comList.html의 viewCom Fragment를 반환합니다
	    return "m_memView::viewmem";
	}

	@GetMapping("m_memDelete")
	public String m_memDelete(Model model, @RequestParam("mid") String mid, RedirectAttributes rttr) {
	    log.info("m_comDelete() with mid: {}", mid);
	    
	    model.addAttribute("mid", mid);
	    bServ.memDelete(mid);
	    rttr.addFlashAttribute("msg", "강제 탈퇴 완료 되었습니다.");
    	return "redirect:/m_customerList";
	}
	@GetMapping("m_memSales")
	public List<BookDTO> m_memSales(Model model, HttpSession session) {
		log.info("mSalesList()");
	    loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

	    // 로그인된 사용자의 brno 가져오기
	    int brclass = loginDto.getBrclass();
	    String brno = loginDto.getBrno();
	    log.info("mSalesList() with brclass:{},brno: {}", brclass, brno);

		List<BookDTO> mSalesList = pServ.mSalesList(); //월별
		model.addAttribute("mSalesList",mSalesList);
	    log.info("mSalesList() with mSalesList:{}", mSalesList);
		return mSalesList;
	}
	
	@GetMapping("m_memYear")
	public List<BookDTO> m_memYear(Model model, @RequestParam("year") int year) {
		log.info("m_memYear() with year: {}",year);
		List<BookDTO> myeartotal = pServ.myeartotal(year); //년별
		model.addAttribute("myeartotal",myeartotal);
		
		return myeartotal;
	}
	
	@GetMapping("m_memMonth")
	public List<BookDTO> m_memMonth(Model model, @RequestParam("month") int month) {
		log.info("m_memMonth() with month: {}",month);
		List<BookDTO> mmonthtotal = pServ.mmonthtotal(month); //월별
		model.addAttribute("mmonthtotal",mmonthtotal);
		
		return mmonthtotal;
	}
	
	@GetMapping("m_memDay")
	public List<BookDTO> m_memDay(Model model, @RequestParam("day") int day) {
		log.info("m_memDay() with day: {}", day);
		List<BookDTO> mdaytotal = pServ.mdaytotal(day); //일별
		model.addAttribute("mdaytotal",mdaytotal);
		
		return mdaytotal;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////이하 성진
	@GetMapping("m_bookList")
	public ModelAndView m_bookList(HttpSession session) {
		log.info("m_bookList");

		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		String brno = loginDto.getBrno();

		log.info("purList() with brclass:{},brno: {}", brclass, brno);

		mv = new ModelAndView("m_bookList");
		mv.addObject("bookList");
		mv.addAllObjects(boServ.bookList(brno, session).getModel());
		mv.setViewName("m_bookList");

		return mv;
	}

	@PostMapping("m_bookInsert")
	public String m_bookInsert(BookDTO bodto, @RequestParam("files") MultipartFile[] files,RedirectAttributes rttr) throws Exception {
		log.info("m_bookInsert");

		if (files.length == 0) {
			log.warn("No files uploaded");
		} else {
			for (MultipartFile file : files) {
				log.info("File name: " + file.getOriginalFilename());
			}
		}

		String bono = bodto.getBono();

		// 책 정보 삽입
		int result = boServ.bookInsert(bodto);
		pServ.purInsert(bodto);
		log.info("input brno:{}",bodto.getBrno());


		// 파일 업로드 및 저장
		List<FileDTO> uploadedFiles = fileUtil.uploadFiles(Arrays.asList(files));
		fileServ.saveFiles(bono, uploadedFiles);
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			return "m_bookInsert::insertcom";
		} else {
			rttr.addFlashAttribute("msg", "등록 완료 되었습니다.");
			return "redirect:/m_bookList";
		}
	}

	@GetMapping("m_bookSelect")
	public String m_bookSelect(@RequestParam("bono")String bono,HttpSession session, Model model) {
		log.info("m_bookSelect");
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		String brno = loginDto.getBrno();
		log.info("purList() with brclass:{},brno: {}", brclass, brno);
		model.addAttribute("brclass",brclass);
		BookDTO bookSelect = boServ.bookSelect(bono);
		model.addAttribute("book",bookSelect);

		return "m_bookSelect";
	}
	
	@PostMapping("m_bookUpdate")
	public String m_bookUpdate(BookDTO dto,RedirectAttributes rttr) {
		log.info("m_bookUpdate");
		
		int result = boServ.bookUpdate(dto);
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			return "redirect:/m_bookSelect";
		} else {
			rttr.addFlashAttribute("msg", "수정 완료 되었습니다.");
			return "redirect:/m_bookList";
		}

	}

	@GetMapping("m_requestBook")
	public ModelAndView m_requestBook(HttpSession session,Model model) {
		log.info("m_requestBook");
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		String brno = loginDto.getBrno();

		log.info("purList() with brclass:{},brno: {}", brclass, brno);
		model.addAttribute("brclass",brclass);

		mv = new ModelAndView("m_requestBook");
		mv.addObject("requestBook");
		mv.addObject("brno",brno);
		mv.addAllObjects(boServ.requestBook(brno,session).getModel());
		mv.setViewName("m_requestBook");

		return mv;
	}

	@GetMapping("m_rBookView")
	public String m_rBookView(@RequestParam("pno")int pno, Model model,HttpSession session) {
		log.info("m_rBookView");
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		String brno = loginDto.getBrno();

		log.info("purList() with brclass:{},brno: {}", brclass, brno);
		model.addAttribute("brclass",brclass);
		BookDTO rBookView = boServ.rBookView(pno);
		model.addAttribute("rBookView",rBookView);

		return "m_rBookView";
	}

	@GetMapping("m_bookmStock")
	public String m_bookmStock(@RequestParam("pno")int pno, Model model,HttpSession session) {
		log.info("m_bookmStock");
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		String brno = loginDto.getBrno();
		
		model.addAttribute("brno",brno);

		log.info("purList() with brclass:{},brno: {}", brclass, brno);
		model.addAttribute("brclass",brclass);
		BookDTO bookstock = boServ.bookmStock(pno);
		model.addAttribute("stock",bookstock);

		return "m_bookmStock";
	}


	@GetMapping("m_retrun")
	public String m_return(@RequestParam("pno")int pno,Model model) {
		log.info("m_return");
		System.out.println(pno);

		BookDTO rBookView = boServ.rBookView(pno);
		model.addAttribute("rBookView",rBookView);

		return "m_return";
	}

	@GetMapping("m_bookStock")
	public ModelAndView m_bookStock(HttpSession session,Model model) {
		log.info("m_bookStock");
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		int brclass = loginDto.getBrclass();
		String brno = loginDto.getBrno();

		model.addAttribute("brclass",brclass);

		mv = new ModelAndView("m_requestBook");
		mv.addObject("bookStock");
		mv.addAllObjects(boServ.bookStock(brno,session).getModel());
		mv.setViewName("m_bookStock");

		return mv;
	}

	@PostMapping("m_stockProcessing")
	public String stockProcessing(BookDTO dto,HttpSession session,RedirectAttributes rttr) {
		log.info("m_stockProcessing");
		System.out.println(dto.getBrno());
		
		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기
		String userBrno = loginDto.getBrno();
		dto.setUserBrno(userBrno);
		int result = 0;
		
		
		if(userBrno.equals("admin")) {
			result = boServ.stockProcessing(dto,userBrno);
			pServ.purUpdate(dto);
		}else {
			result = boServ.stockProcessing(dto,userBrno);
		}
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "재고 추가를 실패하였습니다.");
			return "redirect:m_bookStock";
		} else {
			rttr.addFlashAttribute("msg", "재고 추가를 완료 되었습니다.");
			return "redirect:m_bookStock";
		}
	}


	@GetMapping("m_bookDeducted")
	public String m_bookDeducted(@RequestParam("pno")int pno,Model model) {
		log.info("m_bookDeducted");
		BookDTO bookstock = boServ.bookmStock(pno);
		model.addAttribute("stock",bookstock);
		
		System.out.println(pno);

		return "m_bookDeducted";
	}

	@PostMapping("/m_bookDeducted")
	public String bookDeducted(BookDTO dto,RedirectAttributes rttr) {
		log.info("m_bookDeducted-----------");
		System.out.println(dto.getPreason());
		System.out.println(dto.getPno());
		System.out.println(dto.getDeducted());

		int result = boServ.Deducted(dto);
		pServ.purCancle(dto);

		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "재고 추가를 실패하였습니다.");
			return "redirect:m_bookStock";
		} else {
			rttr.addFlashAttribute("msg", "재고 추가를 완료 되었습니다.");
			return "redirect:m_bookStock";
		}
		
		
	}

	@PostMapping("m_processing")
	public String processing(BookDTO dto,RedirectAttributes rttr) {
		log.info("m_processing");
		System.out.println(dto.getBrreqnum());
		System.out.println(dto.getPstock());
		
		
		int result = boServ.processing(dto);
		pServ.purUpdate(dto);
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "요청 처리를 실패하였습니다.");
			return "redirect:m_requestBook";
		} else {
			rttr.addFlashAttribute("msg", "요청 처리를 완료 되었습니다.");
			return "redirect:m_requestBook";
		}
		


	}

	@GetMapping("m_orderList")
	public ModelAndView m_orderList(HttpSession session,Model model) {
		log.info("m_orderLisT");

		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기

		String brno = loginDto.getBrno();

		mv = new ModelAndView();
		mv.addObject("orderList");
		mv.addAllObjects(boServ.orderList(brno,session).getModel());
		mv.setViewName("m_orderList");

		return mv;
	}
	@GetMapping("m_delivery")
	public ModelAndView m_delivery(HttpSession session,Model model) {
		log.info("m_delivery");

		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기

		String brno = loginDto.getBrno();

		mv = new ModelAndView();
		mv.addObject("delivery");
		mv.addAllObjects(boServ.deliveryList(brno,session).getModel());
		mv.setViewName("m_delivery");

		return mv;
	}
	@GetMapping("m_returnManage")
	public ModelAndView m_returnManage(HttpSession session,Model model) {
		log.info("m_returnManage");

		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기

		String brno = loginDto.getBrno();

		mv = new ModelAndView();
		mv.addObject("return");
		mv.addAllObjects(boServ.returnManage().getModel());
		mv.setViewName("m_returnManage");

		return mv;
	}
	@GetMapping("m_refund")
	public ModelAndView m_refund(HttpSession session,Model model) {
		log.info("m_refund");

		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기

		String brno = loginDto.getBrno();

		mv = new ModelAndView();
		mv.addObject("refund");
		mv.addAllObjects(boServ.refundList(brno,session).getModel());
		mv.setViewName("m_refund");

		return mv;
	}
	@GetMapping("m_exchange")
	public ModelAndView m_exchange(HttpSession session,Model model) {
		log.info("m_exchange");

		loginsessionDTO loginDto = (loginsessionDTO) session.getAttribute("login");

		// 로그인된 사용자의 brno 가져오기

		String brno = loginDto.getBrno();

		mv = new ModelAndView();
		mv.addObject("exchange");
		mv.addAllObjects(boServ.exchangeList(brno,session).getModel());
		mv.setViewName("m_exchange");

		return mv;
	}


	@GetMapping("m_orderSelect")
	public String oderSelect(@RequestParam("buyno")String buyno,
			@RequestParam("variable")String variable,
			Model model) {

		System.out.println(buyno);
		System.out.println(variable);

		if(variable.equals("order")) {
			log.info("orderSelect");

			BookDTO order = boServ.orderSelect(buyno,variable);
			model.addAttribute("order",order);

			return "m_orderSelect::viewcom";

		}else if(variable.equals("delivery")) {
			log.info("delivery");

			BookDTO order = boServ.orderSelect(buyno,variable);
			model.addAttribute("order",order);

			return "m_orderSelect::delivery";

		}else if(variable.equals("returnManage")) {
			log.info("returnManage");

			BookDTO order = boServ.orderSelect(buyno,variable);
			model.addAttribute("order",order);

			return "m_orderSelect::returnManage";

		}else if(variable.equals("refund")) {
			log.info("refund");

			BookDTO order = boServ.orderSelect(buyno,variable);
			model.addAttribute("order",order);

			return "m_orderSelect::refund";

		}else if(variable.equals("exchange")) {
			log.info("exchange");

			BookDTO order = boServ.orderSelect(buyno,variable);
			model.addAttribute("order",order);

			return "m_orderSelect::exchange";
		}
		return "";
	}

	@PostMapping("m_paycheck")
	public String m_paycheck(BookDTO dto,RedirectAttributes rttr) {
		log.info("paycheck");

		int result = boServ.paycheck(dto);

		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "주문 확인을 실패하였습니다.");
			return "redirect:m_orderList";
		} else {
			rttr.addFlashAttribute("msg", "주문 확인을 완료 되었습니다.");
			return "redirect:m_orderList";
		}
	}
	@PostMapping("m_deliveryCheck")
	public String m_deliveryCheck(BookDTO dto,RedirectAttributes rttr) {
		log.info("deliveryCheck");

		int result = boServ.deliveryCheck(dto);

		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "배송 확인을 실패하였습니다.");
			return "redirect:m_delivery";
		} else {
			rttr.addFlashAttribute("msg", "배송 확인을 완료 되었습니다.");
			return "redirect:m_delivery";
		}
		
	}

	@PostMapping("m_takeBackCheck")
	public String takeBackCheck(BookDTO dto,RedirectAttributes rttr) {
		log.info("takeBackCheck");

		int result = boServ.takeBackCheck(dto);
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "취소 확인을 실패하였습니다.");
			return "redirect:m_returnManage";
		} else {
			rttr.addFlashAttribute("msg", "취소 확인을 완료 되었습니다.");
			return "redirect:m_returnManage";
		}


	}

	@PostMapping("m_refundCheck")
	public String refundCheck(BookDTO dto,RedirectAttributes rttr) {
		log.info("refundCheck");

		int result = boServ.refundCheck(dto);
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "환불 확인을 실패하였습니다.");
			return "redirect:m_refund";
		} else {
			rttr.addFlashAttribute("msg", "환불 확인을 완료 되었습니다.");
			return "redirect:m_refund";
		}

	}

	@PostMapping("m_exchangeCheck")
	public String exchangeCheck(BookDTO dto,RedirectAttributes rttr) {
		log.info("exchangeCheck");

		int result = boServ.exchangeCheck(dto);
		
		if (result < 0) {
			log.error("Failed to insert branch data.");
			rttr.addFlashAttribute("msg", "교환 확인을 실패하였습니다.");
			return "redirect:m_exchange";
		} else {
			rttr.addFlashAttribute("msg", "교환 확인을 완료 되었습니다.");
			return "redirect:m_exchange";
		}

		
	}


} 
