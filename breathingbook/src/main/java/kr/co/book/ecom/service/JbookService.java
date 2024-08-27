package kr.co.book.ecom.service;

import java.awt.print.PrinterException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dao.JbookDAO;
import kr.co.book.ecom.dto.adressDTO;
import kr.co.book.ecom.dto.buyDTO;
import kr.co.book.ecom.dto.commentsDTO;
import kr.co.book.ecom.dto.inquiryDTO;
import kr.co.book.ecom.dto.loginsessionDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.ecom.dto.noticeDTO;
import kr.co.book.ecom.dto.pointgameDTO;
import kr.co.book.ecom.dto.reviewDTO;



@Service
public class JbookService {
	
	private ModelAndView mv;
	
	@Autowired
	private JbookDAO dao;
	private MailService mService;
	
	
	//회원가입 처리
	public ModelAndView enewmamber (memberDTO mdto, adressDTO adto) {
		
		mv = new ModelAndView();
		
		String view;
		String msg;
		
		try {
			
			int result = dao.enewmaber(mdto);
			adto.setAno(dao.newano(adto.getAid()));
			adto.setAid(mdto.getMid());
			result = result + dao.newadress(adto);
			
			if(result != 0) {
				view = "e_login";
		        msg = "회원가입이 완료되었습니다.";
			} else {
				view = "e_join";
		        msg = "회원가입에 실패하였습니다.";
			}
			
			
		} catch (Exception e) {
			
			view = "e_join";
	        msg = "오류가 발생했습니다.";
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		mv.setViewName(view);
		mv.addObject("msg", msg);
		
		return mv;
		
	}
	
	//로그인 처리
	public ModelAndView login(Map<String, String> map, HttpSession session) {
		
		mv = new ModelAndView();
		
		try {
			
			String div = map.get("div");
			
			if(div.equals("0")) {
				loginsessionDTO dto = dao.login(map);
				
				if(dto == null) {
					
					mv.setViewName("e_login");
					mv.addObject("msg", "로그인에 실패하셨습니다.");
					
				} else {
					
					session.setAttribute("login", dto);
					mv.setViewName("redirect:/eMain"); //redirect:/
				}
				
			} else {
				loginsessionDTO dto = dao.login2(map);
				
				if(dto == null) {
					
					mv.setViewName("e_login");
					mv.addObject("msg", "로그인에 실패하셨습니다.");
					
				} else {
					
					session.setAttribute("login", dto);
					mv.setViewName("redirect:/m"); //redirect:/
				}
			}
			
			
			
		} catch (Exception e) {
			
			mv.setViewName("e_login");
			mv.addObject("msg", "오류가 발생했습니다.");
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		return mv;
	}
	
	//현재 기준 누군가의 확정 정보 수정
	public void buyallok() {
		
		List<buyDTO> dtolist = dao.buyallok();
		
		for (int i = 0; i < dtolist.size(); i++) {
		    dtolist.get(i).setBuycode("B13");
		    dao.upcode(dtolist.get(i));
		    dao.confirm(dtolist.get(i));
		}
		
	}
	
	//이메일 발송 - 아이디 찾기
    public String sendMessageid(String email, String mname)throws MessagingException {
    	
    	Map<String, String> map = new HashMap<>();
    	map.put("mmail", email);
    	map.put("mname", mname);
    	
    	String re = null;
    	
    	try {
    		
    		String mail = dao.mailck(map);
    		
	    	if(mail != null) {
	    		mService = new MailService();
	    		String code = mService.emailSending(email);
	    		re = code;
	        	
	    	} else {
	    		re = "N";
	    	}
			
    	} catch (Exception e) {
			
    		re = "E";
		}	
		
    	return re;
        
    }
    
	//이메일 발송 - 비밀번호 찾기
    public String sendMessagepw(String email, String mid)throws MessagingException {
   
    	Map<String, String> map = new HashMap<>();
    	map.put("mmail", email);
    	map.put("mid", mid);
    	
    	String re = null;
    	
    	try {
    		
    		String mail = dao.findpw(map);
    		
	    	if(mail != null) {
	    		mService = new MailService();
	    		String code = mService.emailSending(email);
	    		re = code;
	        	
	    	} else {
	    		re = "N";
	    	}
			
    	} catch (Exception e) {
			
    		re = "E";
		}	
		
		
    	return re;
        
    }
    
    //이메일체크
    public String mailselct (String mail) {
    	
    	String response = dao.mailselct(mail);
    	
    	if(response != null) {
    		return response = "N";
    	} else {
    		return response = "Y";
    	}
    }
    
    //아이디체크
    public String idselct (String mid) {
    	
    	String response = dao.idselct(mid);
    	
    	if(response != null) {
    		return response = "N";
    	} else {
    		return response = "Y";
    	}
    }
    
    //아이디 찾기
    public String findid (String email, String mname) {
    	
    	Map<String, String> map = new HashMap<>();
    	map.put("mmail", email);
    	map.put("mname", mname);
    	
    	String response = dao.findid(map);
    	
    	return response;
    }
    
    
    //비밀번호 찾기
    public void findpw (String email, String mid)throws MessagingException {
    	
    	Map<String, String> map = new HashMap<>();
    	map.put("mmail", email);
    	map.put("mid", mid);
    	
    	String pw = dao.findpw(map);
    	
    	mService.emailpw(email,pw);
    }
    
    //사업자 비밀번호 찾기
    public String brpw (String brno, String brbusinessno) {
    	
    	Map<String, String> map = new HashMap<>();
    	String pw = null;
    	
    	
    	try {
    		
    		map.put("brbusinessno", brbusinessno);
        	map.put("brno", brno);
        	
        	pw = dao.brpw(map);
    		
	    	if(pw == null || pw.equals("")) {
	    		pw = "N";
	    	}
			
    	} catch (Exception e) {
			
    		pw = "E";
		}
    	

    	return pw;
    }  
    
    
	//마이페이지
	public ModelAndView emypage (String mid) {
		
		mv = new ModelAndView();
		
		try {
			
			int wcnt = dao.waycont(mid);
			int bcnt = dao.buycont(mid);
			int dcnt = dao.deliverycont(mid);
			
			mv.addObject("wcnt", wcnt);
			mv.addObject("bcnt", bcnt);
			mv.addObject("dcnt", dcnt);
			
		} catch (Exception e) {
			
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		mv.setViewName("e_mypage");
		
		return mv;
		
	}
	
	
	//회원 구매 정보
	public ModelAndView emybuy(String mid) {
		
		
		try {
			
			List<buyDTO> dtolist = dao.mybuylist(mid);
			
			for (int a = 0; a < dtolist.size(); a++) {
				
				String date = dtolist.get(a).getBuydate();
				int bno = dtolist.get(a).getBuyno();
				
				if(dtolist.get(a).getBuyno() != 0) {
					
					for (int i = 0; i < dtolist.size(); i++) {
						
						if(dtolist.get(i).getBuydate().equals(date) && dtolist.get(i).getBuyno() != bno) {
							
							dtolist.get(i).setBuyno(0);
							dtolist.get(a).setBooknum(dtolist.get(a).getBooknum() + 1);
							dtolist.get(a).setBuyprice(dtolist.get(a).getBuyprice() + dtolist.get(i).getBuyprice());
						}
						
					}
				}
			}
			
			mv.addObject("dto", dtolist);
			
		} catch (Exception e) {
			
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		mv.setViewName("e_Phistory");
		
		return mv;
		
	}
	
	//회원 구매 정보 상세
	public ModelAndView emybuyditail(String mid, String buydate) {
		
		
		try {
			buyDTO dto = new buyDTO();
			dto.setBuyid(mid);
			dto.setBuydate(buydate);
			List<buyDTO> dtolist = dao.mybuyditail(dto);
			
			int sum = 0;
			
			for(int a =0; a < dtolist.size(); a++) {
				sum += dtolist.get(a).getBuyprice();
			}
			
			System.out.println(dto);
			mv.addObject("dto", dtolist);
			mv.addObject("sum", sum);
			
		} catch (Exception e) {
			
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		mv.setViewName("e_Dhistory");
		
		return mv;
		
	}
	
	//회원 구매 정보 상세
	public ModelAndView emybuyditail(String mid, String buycode, String buydate) {
		
		
		try {
			buyDTO dto = new buyDTO();
			dto.setBuyid(mid);
			dto.setBuydate(buydate);
			List<buyDTO> dtolist = dao.mybuyditail(dto);
			
			int sum = 0;
			
			for(int a =0; a < dtolist.size(); a++) {
				sum += dtolist.get(a).getBuyprice();
				
				dtolist.get(a).setDelivery(dao.deliveryno(dtolist.get(a).getBuyno()));
			}
			
			mv.addObject("dto", dtolist);
			mv.addObject("sum", sum);
			
		} catch (Exception e) {
			
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		mv.setViewName("e_Dhistory");
		
		return mv;
		
	}
	
	//주문 상태 처리
	public String upcode (buyDTO dto) {
		
		String msg;
		
		try {
			
			int re = dao.upcode (dto);
			
			if(re == 1) {
				
  				msg = "Y";
  				
  			} else {
  				
  				msg = "N";
  			}
			
		} catch (Exception e) {
			
			msg = "E";
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		return msg;
		
	}
	
	//확정처리
	public String confirm (buyDTO dto) {
		
		String msg;
		
		try {
			
			int re = dao.upcode (dto);
			re += dao.confirm(dto);
			
			if(re == 2) {
				
  				msg = "Y";
  				
  			} else {
  				
  				msg = "N";
  			}
			
		} catch (Exception e) {
			
			msg = "E";
	        e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
		
		return msg;
		
	}
	
	//리뷰 리스트
    public ModelAndView myreviewList (String mid) {
    	
    	mv = new ModelAndView();
    	
    	try {
    		
    		List<reviewDTO> list = dao.myreviewList(mid);
    		
    		mv.addObject("dto", list);
    		
    	} catch (Exception e) {
    		
			e.printStackTrace();
		}
    	
    	
    	mv.setViewName("e_review");
    	
    	return mv;
    }
    
    
    //회원 정보
  	public ModelAndView memberone (String mid) {
  		
  		
  		
  		mv = new ModelAndView();
  		
  		try {
  			
  			
  			memberDTO dto = dao.memberone(mid);
  			List<adressDTO> adto = dao.adresslist(mid);
  			
	    	if(dto != null) {
	    		
	    		
	    		String strh = dto.getMphone();
	    		String ph1 = strh.substring(0,3);
	    		String ph2 = strh.substring(3,7);
	    		String ph3 = strh.substring(7);
	    		
	    		
	    		String str = dto.getMemail();
	    		String [] mailNo = str.split("@");
	    		
	    		List<String> maillist = new ArrayList<>();
	    		
	    		for(int i =0; i<mailNo.length; i++){
	    			maillist.add(i, mailNo[i]);
	    		}
	    		
	    		
	    		mv.addObject("ph1", ph1);
	    		mv.addObject("ph2", ph2);
	    		mv.addObject("ph3", ph3);
	    		mv.addObject("maillist", maillist);
	    		mv.addObject("adto", adto);
	    		mv.addObject("asize", adto.size());
	    		mv.addObject("dto", dto);
	    		
	        	mv.setViewName("e_setting");
	        	
	    	} else {
	    		
	    		mv.setViewName("e_login");
	    		mv.addObject("msg", "로그인이 필요합니다.");
	    	}
			
    	} catch (Exception e) {
			
    		mv.setViewName("e_setting");
    		mv.addObject("msg", "오류가 발생했습니다.");
		}	
  		
		
    	return mv;
  	}
  	
  	
  	//회원 정보 - 주소 리스트
  	public List<adressDTO> adresslist (String mid) {
  		
  		List<adressDTO> dtolist = null;
  		
  		try {
  			
  			dtolist = dao.adresslist(mid);
			
    	} catch (Exception e) {
    		
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return dtolist;
  	}
  	
  	//회원 정보 - 주소 1건 조회
  	public adressDTO adressone (String mid) {
  		
  		adressDTO dto = null;
  		
  		try {
  			
  			dto = dao.adressone(mid);
			
    	} catch (Exception e) {
    		
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return dto;
  	}
  	
  	//회원 비밀번호 확인
  	public String selectPW (String mpw, String mid) {
  		
  		Map<String, String> map = new HashMap<>();
		map.put("mpw",mpw);
		map.put("mid",mid);
  		
  		String msg = null;
  		
  		try {
  			
  			loginsessionDTO dto = dao.login(map);
  			  			
  			if(dto != null) {
  				msg = "Y";
  				
  			} else {
  				
  				msg = "N";
  			}
			
    	} catch (Exception e) {
    		
    		msg = "E";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return msg;
  	}
  	
  	//회원 주소 추가
  	public String newadress (adressDTO dto) {
  		
  		String msg = null;
  		
  		try {
  			
  			dto.setAno(dao.newano(dto.getAid()));
  			
  			
  	  		int re = dao.newadress(dto);
  			
  			if(re == 1) {
  				
  				msg = "Y";
  				
  			} else {
  				
  				msg = "N";
  			}
			
    	} catch (Exception e) {
    		
    		msg = "E";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return msg;
  	}
  	
  	
  	
  	//회원 정보 수정
  	public ModelAndView msetting (memberDTO dto) {
  		
  		mv = new ModelAndView();
  		
  		try {
  			
  			if (dto.getMpw().equals("0") || dto.getMpw() == null || dto.getMpw().equals("")) {
  				
  				dto.setMpw(dao.findpw2(dto.getMid()));
  			}
  			
  			
  			int re = dao.msetting(dto);
    		
	    	if(re == 1) {
	    		
	        	mv.setViewName("redirect:/esettingup?mid="+dto.getMid());
	        	mv.addObject("msg", "정보가 수정되었습니다.");
	        	
	    	} else {
	    		
	    		mv.setViewName("redirect:/esettingup?mid="+dto.getMid());
	        	mv.addObject("msg", "다시 시도해주세요.");
	    		
	    	}
			
    	} catch (Exception e) {
			
    		mv.setViewName("redirect:/esettingup?mid="+dto.getMid());
    		mv.addObject("msg", "오류가 발생했습니다.");
		}	
  		
		
    	return mv;
  		
  	}
  	
  //회원 정보 - 상세 주소 수정
  	public String asetting (adressDTO dto) {
  		
  		String msg = null;
  		
  		try {
  			
  			int re = dao.asetting(dto);
  			
  			if (re == 1) {
  				
  				msg = "Y";
  				
  			} else {
  				msg = "N";
  			}
			
    	} catch (Exception e) {
    		
    		msg = "E";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return msg;
  	}
  	

  	//회원 정보 - 기본 주소 변경 (ano=바꾸려는주소)
  	public String abasic (String ano, String aid) {
  		
  		Map<String, String> map = new HashMap<>();
  		int re = 0;
  		String msg = null;
  		
  		try {
  			
  			//1번 기본 주소 변경을 위한 맵
  			map.put("ano", "1");
  	  		map.put("aid", aid);
  	  		
  	  		//1번 기본 주소의 ano를 바꾼다.
  	  		int ano2 = dao.newano(aid);
  	  		map.put("ano2", "" + ano2);
  	  		re = dao.anoup(map);
  	  		
  	  		//기본 주소로 바꾸려는 ano를 1로 바꾼다.
  	  		map.put("ano", ano);
  	  		re = re +  dao.abasic(map);
  	  		
  	  		//큰 값으로 변경되었던 기존 기본 주소의 값을 바꾼다.
  	  		map.put("ano2", ano);
  	  		map.put("ano", "" + ano2);
  	  		re = re + dao.anoup(map);  		
  	  		
  			if (re == 3) {
  				
  				msg = "Y";
  				
  			} else {
  				msg = "N";
  			}
			
    	} catch (Exception e) {
    		
    		msg = "E";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return msg;
  	}
	
	
  	
  	//주소 삭제
  	public String adel (adressDTO dto) {
  		
  		String msg = null;
  		
  		try {
  			
  			int re = dao.adel(dto);
  			  			
  			if(dto.getAno() == 1) {
  				  				
  				//ano 중 가장 작은 값을 구한다.
  				int ano = dao.anomin(dto);
  				  				
  				dto.setAno(ano);
  				
  				re = dao.abasic2(dto);
  			}
  			
  			if (re == 1) {
  				
  				msg = "Y";
  				
  			} else {
  				msg = "N";
  			}
			
    	} catch (Exception e) {
    		
    		msg = "E";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
  		
  		return msg;
  		
  	}
  	
  	//회원 탈퇴
  	public ModelAndView mdel (String mid, HttpSession session) {
  		
  		mv = new ModelAndView();

  		try {
  			
  			int re = dao.mdel(mid);
    		
	    	if(re == 1) {
	    		
	    		session = null;
	    		
	        	mv.setViewName("redirect:/elogin");
	        	
	    	} else {
	    		
	    		mv.setViewName("redirect:/e_settingup?mid="+mid);
	    		mv.addObject("msg", "다시 시도해주세요.");
	    	}
			
    	} catch (Exception e) {
			
    		mv.setViewName("redirect:/e_settingup?mid="+mid);
    		mv.addObject("msg", "오류가 발생했습니다.");
		}	
  		
		
    	return mv;
  		
  	}
  	
    
    
    
    
    
    
    
    
    
    
	//리뷰 리스트
    public List<reviewDTO> reviewList (String bono) {
    	
    	List<reviewDTO> list = dao.reviewList(bono);
    	
    	return list;
    }
    
	//리뷰 등록
    public String newreview (reviewDTO dto) {
    	
    	String msg = null;
    	int re = 0;
    	
    	try {
	    	
	    	int rno = dao.rno(dto.getRbookno()) + 1;
	    	dto.setRbookno(dto.getRbookno());
	    	dto.setRno(dto.getRbookno() + "-" + rno);
	    	re = re + dao.newreview(dto);
	    	
	    	if (re == 0) {
	    		msg = "댓글 등록에 실패하셨습니다.";
	    	} else {
	    		msg = "댓글이 등록되었습니다.";
	    	}
	    	
    	} catch (Exception e) {
    		
    		
    		msg = "댓글 등록 중 오류가 발생했습니다.";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
    		
    	}
    	
    	return msg;
    }

    //리뷰 삭제
    public String rdel (String rno) {
    	
    	String msg = null;
    	int re = 0;
    	
    	try {
    		
    		re = dao.rdel(rno);
    		
    		if (re == 0) {
	    		msg = "댓글 삭제에 실패하셨습니다.";
	    	} else {
	    		msg = "댓글이 삭제되었습니다.";
	    	}
    		
    	}catch (Exception e) {
    		msg = "댓글 삭제 중 오류가 발생했습니다.";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return msg;
    }
    
    //리뷰 수정
    public String upreview (reviewDTO dto) {
    	
    	String msg = null;
    	int re = 0;
    	
    	try {
    		
    		re = dao.upreview(dto);
    		
    		if (re == 0) {
	    		msg = "댓글 수정에 실패하셨습니다.";
	    	} else {
	    		msg = "댓글이 수정되었습니다.";
	    	}
    		
    	}catch (Exception e) {
    		msg = "댓글 수정 중 오류가 발생했습니다.";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return msg;
    	
    }
    
	//대댓 리스트
    public List<commentsDTO> comList (String rno) {
    	
    	List<commentsDTO> list = dao.comList(rno);
    	
    	return list;
    }
    
	//리뷰 대댓 등록
    public String newcom (commentsDTO dto) {
    	
    	String msg = null;
    	int re = 0;
    	
    	try {
	    	
	    	re = re + dao.newcom(dto);
	    	
	    	if (re == 0) {
	    		msg = "대댓글 등록에 실패하셨습니다.";
	    	} else {
	    		msg = "대댓글이 등록되었습니다.";
	    	}
	    	
    	} catch (Exception e) {
    		
    		
    		msg = "대댓글 등록 중 오류가 발생했습니다.";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
    		
    	}
    	
    	return msg;
    }
    
    //대댓 삭제
    public String cdel (String cno) {
    	
    	
    	String msg = null;
    	int re = 0;
    	
    	try {
    		
    		re = dao.cdel(cno);
    		
    		if (re == 0) {
	    		msg = "대댓글 삭제에 실패하셨습니다.";
	    	} else {
	    		msg = "대댓글이 삭제되었습니다.";
	    	}
    		
    	}catch (Exception e) {
    		msg = "대댓글 삭제 중 오류가 발생했습니다.";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return msg;
    }
    
    //대댓 수정
    public String upcom (commentsDTO dto) {
    	
    	String msg = null;
    	int re = 0;
    	
    	
    	try {
    		
    		re = dao.upcom(dto);
    		
    		if (re == 0) {
	    		msg = "대댓글 수정에 실패하셨습니다.";
	    	} else {
	    		msg = "대댓글이 수정되었습니다.";
	    	}
    		
    	}catch (Exception e) {
    		msg = "대댓글 수정 중 오류가 발생했습니다.";
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return msg;
    	
    }
    
    //공감 추가 삭제 등
    public void sympathy (String sno, String sid) {
    	
    	Map<String, String> map = new HashMap<>();
    	
    	map.put("sno", sno);
    	map.put("sid", sid);

    	
    	try {
    	
	    	String snoone = dao.sympathy(map);
	    	
	    	
	    	if (snoone == null) {
	    		
	    		dao.newsympathy(map);
	    		
	    	} else {
	    		
	    		dao.delsympathy(map);
	    		
	    	}
    	
    	}catch (Exception e) {
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    		
    }
    
    
    
    
    
    
    //공지 리스트
    public ModelAndView noticelist () {
    	
    	mv = new ModelAndView();
    	
    	try {
    		
    		mv.setViewName("e_notice");
    		List<noticeDTO> dto = dao.noticelist();
    		mv.addObject("dto", dto);
    	
    	} catch (Exception e) {
    		
    		mv.setViewName("redirect:/eMain");
    		mv.addObject("msg", "오류가 발생했습니다.");
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return mv;
    }
    
    //팝업 리스트
    public List<noticeDTO> poplist () {
    	
    	List<noticeDTO> dto = null;
    	
    	try {
    		 dto = dao.poplist();
    	
    	} catch (Exception e) {
    		
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return dto;
    }
    
	
	//팝업 상세 리스트
	public ModelAndView popone (int nnum) {
		
    	mv = new ModelAndView();
    	
    	try {
    		
    		mv.setViewName("e_popup");
    		noticeDTO dto = dao.popone(nnum);
    		
    		System.out.println(dto);
    		
    		mv.addObject("dto", dto);
    	
    	} catch (Exception e) {
    		
    		mv.setViewName("redirect:/eMain");
    		mv.addObject("msg", "오류가 발생했습니다.");
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return mv;
		
	}
	
	
	
	//포인트 게임 이력 조회
	public ModelAndView eroulette (String mid) {
		
    	mv = new ModelAndView();
    	
    	try {
    		
    		
    		//회원의 전체 포인트 정보
    		List<pointgameDTO> dto = dao.mypoint(mid);
    		
    		mv.addObject("dto", dto);
    		//현재 날짜
    		LocalDateTime now = LocalDateTime.now();
    		mv.addObject("localDateTime", now);
    	    //현재 월
    	    YearMonth yearMonth = YearMonth.from(now);
    	    //현재 일
    	    int today = now.getDayOfMonth();
    	    
    	    //현재 월의 일수
    	    List<Integer> days = new ArrayList<>();
    	    
    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	    for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
    	        days.add(day);
    	    }
    	    mv.addObject("days", days);
    	    
    	    //이번달 꺼만 조회
    	    List<Integer> point = new ArrayList<>();

    	    //누적 포인트
    	    int pointAll = 0;
    	    int pointcks = 0;
    	    
    	    //오늘 참여 여부
    	    int pointckY = 0;
    	    int rouletteY = 0;
    	    
    		if(dto != null) {
    	        
	        	for (int pointck = 0; pointck < dto.size(); pointck++) {
    	        	
	        		String gdateString = dto.get(pointck).getGdate();
    	        	LocalDateTime dateTime = LocalDateTime.parse(gdateString, formatter);
    	        	YearMonth dateyearMonth = YearMonth.from(dateTime);
    	        	int dayOfMonth = dateTime.getDayOfMonth();
    	        	
    	        	if(today == dayOfMonth) {
    	        		
    	        		if(dto.get(pointck).getGno() == 1) {
    	        			pointckY = 1;
    	        		}else {
    	        			rouletteY = 1;
    	        		}
    	        		
    	        	}
    	        	
    	        	if(dateyearMonth.equals(yearMonth) && dto.get(pointck).getGno() == 1) {
    	        		point.add(dayOfMonth);
    	        		pointcks += dto.get(pointck).getGpoint();
    	        	}
    	        	
    	        	pointAll += dto.get(pointck).getGpoint();
    	        	
    	        }
	        	
	        }
    		
    		mv.addObject("pointAll", pointAll);
    		mv.addObject("pointcks", pointcks);
    		mv.addObject("pointckY", pointckY);
    		mv.addObject("rouletteY", rouletteY);
    		mv.addObject("point", point);
    	    
    	    List<Integer> weeks = IntStream.range(0, 6).boxed().collect(Collectors.toList());
    	    mv.addObject("weeks", weeks);
    	    
    	    List<Integer> dayIndexes = IntStream.range(0, 7).boxed().collect(Collectors.toList());
    	    mv.addObject("dayIndexes", dayIndexes);
    	    
    	    mv.addObject("firstDayOfWeek", yearMonth.atDay(1).getDayOfWeek().getValue());
    	    
    	    mv.setViewName("e_roulette");
    	
    	} catch (Exception e) {
    		
    		mv.setViewName("redirect:/eMain");
    		mv.addObject("msg", "오류가 발생했습니다.");
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return mv;
		
	}
	
	
	//포인트 등록
	public String point (pointgameDTO dto, String mid, HttpSession session) {
		
		String response = null;
    	
    	try {
    		
    		int re = dao.pointgame(dto);
    		re += dao.pointgame2(dto);
    		
    		
    		if (re == 0 || re == 1 ) {
  				
    			response = "N";
    			
  			} else {
  				response = "Y";
  				
  				String mpw = dao.findpw2(mid);
  				Map<String, String> map = new HashMap<>();
  				map.put("mid", mid);
  				map.put("mpw", mpw);
  				loginsessionDTO ldto = dao.login(map);
  				
  				session.setAttribute("login", ldto);
  			}
    	
    	} catch (Exception e) {
    		
    		response = "E";
    		
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return response;
		
	}
     
	
	//문의 등록
	public String newinquiry (inquiryDTO dto) {
		
		String response = null;
    	
    	try {
    		
    		if(!dto.getIid().equals("none")) {
    			Map<String, String> map = new HashMap<>();
    			map.put("mid", dto.getIid());
    			map.put("mname", dto.getIname());
    			
    			dto.setIemail(dao.memail(map));
    		}
    		
    		int re = dao.newinquiry(dto);
    		
    		if (re == 0) {
  				
    			response = "N";
    			
  			} else {
  				response = "Y";
  			}
    	
    	} catch (Exception e) {
    		
    		response = "E";
    		
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	return response;
	}
	
	//문의 취소
	public ModelAndView delinquiry (String iid, String ino) {
		
		mv = new ModelAndView();
		Map<String, String> map = new HashMap<>();
    	
    	try {
    		
    		map.put("iid", iid);
    		map.put("ino", ino);
    		
    		int re = dao.delinquiry(map);
    		
    		if (re == 1) {
  				
    			mv.addObject("msg", "문의 요청이 취소되었습니다.");
    			List<inquiryDTO> dto = dao.inquirylist(iid);
        		mv.addObject("dto", dto);
    			
  			} else {
  				mv.addObject("msg", "문의 요청 취소 중 오류가 발생했습니다. 다시 시도해주세요.");
  			}
    	
    	} catch (Exception e) {
    		
    		mv.addObject("msg", "문의 요청 취소 중 오류가 발생했습니다. 다시 시도해주세요.");
    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
		}
    	
    	mv.setViewName("e_myQNAlist");
    	return mv;
	}
	
	//회원 문의 내역 조회
	public ModelAndView myinquirylist(String iid){
		
			mv = new ModelAndView();

	    	try {
	    		
	    		List<inquiryDTO> dto = dao.inquirylist(iid);
	    			    		
	    		if (dto !=  null) {
	    			
	    			for (int a = 0; a < dto.size(); a++ ) {
	    				dto.get(a).setIdiv(dao.pid(dto.get(a).getIdiv()));
	    			}
	    			
	    			mv.addObject("dto", dto);
	    			mv.setViewName("e_myQNAlist");
	    			
	  			} else {
	  				mv.setViewName("redirect:/emypage");
	  			}
	    	
	    	} catch (Exception e) {
	    		
	    		mv.setViewName("redirect:/emypage");
	    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
			}
	    	
    	return mv;
		
	}
	
	//비회원 문의 내역 조회
	public ModelAndView inquirylist(){
		
			mv = new ModelAndView();

	    	try {
	    		
	    		List<inquiryDTO> dto = dao.inquirylist2();
	    			    		
	    		if (dto !=  null) {
	    			
	    			for (int a = 0; a < dto.size(); a++ ) {
	    				dto.get(a).setIdiv(dao.pid(dto.get(a).getIdiv()));
	    			}
	  				
	    			mv.addObject("dto", dto);
	    			mv.setViewName("e_QNAlist");
	    			
	  			} else {
	  				mv.setViewName("redirect:/emypage");
	  			}
	    	
	    	} catch (Exception e) {
	    		
	    		mv.setViewName("redirect:/emypage");
	    		e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
			}
	    	
    	return mv;
		
	}
	
	//비회원 비공개 비밀번호 체크
	public String inquirypw (int ino) {
		
		String pw = null;
		
		try {
			pw = dao.inquirypw(ino);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pw;
	}
	
	//비회원 내용 조회
	public inquiryDTO inquirycont (int ino) {
		inquiryDTO dto = null;
		
		try {
			dto =  dao.inquirycont(ino);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	
	
	

	
	
	
	
	
	
	
    
}
