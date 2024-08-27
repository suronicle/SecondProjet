package kr.co.book.erp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dto.inquiryDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.ecom.dto.noticeDTO;
import kr.co.book.erp.service.JerpService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JerpController {

	private ModelAndView mv;
	
	@Autowired
	private JerpService service;
	
	//공지사항 리스트
	@GetMapping("/m_notice")
   public ModelAndView mnotice(){
		
		mv = new ModelAndView();
		mv = service.mnoticelist();
	
      return mv;
   }
	
	
	//공지사항 상세
	@GetMapping("/m_noticeditail")
	@ResponseBody
   public noticeDTO noticeditail (@RequestParam("nnum") int nnum){
		
		noticeDTO nodto = service.noticeditail(nnum);
	
      return nodto;
   }
	
	//공지사항 등록 겟
	@GetMapping("/m_newnotice")
   public String newnotice(){
		
		
      return "redirect:/m_notice"; 
   }
	
	//공지등록
   @PostMapping("/m_newnotice")
   public ModelAndView newnotice(@RequestPart(value = "file", required = false) MultipartFile file,
		   @RequestPart(value = "img", required = false) MultipartFile img, noticeDTO dto,
		   HttpSession session) {
       
	   System.out.println("dto" + dto );
       mv = service.newnotice(dto, session, file, img);

       return mv;
   }
   
   //파일 다운로더
   @GetMapping("/download")
   public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename,@RequestParam("realname") String realname, HttpServletResponse response) throws IOException {
       
	   ResponseEntity<Resource> re = service.downloadFile(filename, realname, response);
	   
       return re;
   }
	
   
   //공지 수정
   @PostMapping("/m_upnotice")
   public ModelAndView upnotice(@RequestPart(value = "file", required = false) MultipartFile file,
		   @RequestPart(value = "img", required = false) MultipartFile img, noticeDTO dto,@RequestParam("nclass1") int nclass,
		   HttpSession session) {
	   
	   dto.setNclass(nclass);
	   
	   mv = service.upnotice(dto, session, file, img);
	   
	   return mv;
   }
   
   
	//공지 수정 겟
  @GetMapping("/m_upnotice")
  public String upnotice(){
		
		
     return "redirect:/m_notice"; 
  }
   
   
   //공지 취소 //팝업 취소
   @PostMapping("/m_delnotice")
   @ResponseBody
   public String delnotice(@RequestParam("nnum") int nnum) {
	   
	   String msg = service.delnotice(nnum);
	   
	   return msg;
   }
   
   //공지 삭제
   @PostMapping("/m_delno")
   @ResponseBody
   public String delno(@RequestParam("nnum") int nnum, HttpSession session) {
	   
	   String msg = service.delno(nnum, session);
	   
	   return msg;
   }
	
	
	//팝업 리스트
	@GetMapping("/m_popup")
	public ModelAndView mpopup(){
		
		mv = new ModelAndView();
		mv = service.mpopuplist();
	
     return mv;
  }
	
	
	
	
	
	
	
	//큐엔에이리스트
	@GetMapping("/m_QNA")
	public ModelAndView mmQNA(){
		
		mv = service.qlist();
	
     return mv;
  }
	
	//큐엔에이상세
	@GetMapping("/m_qone")
	@ResponseBody
	public inquiryDTO mone(@RequestParam("ino") int ino){
		
		inquiryDTO dto = service.qone(ino);
	
     return dto;
  }
	
	//큐엔에이 답변 등록
	@PostMapping("/m_QNA")
	public ModelAndView mmQNA(inquiryDTO dto){
		
		mv = service.answer(dto);
	
     return mv;
  }
	
	
	//큐엔에이숨김
	@PostMapping("/m_qdel")
	@ResponseBody
	public String qdel(@RequestParam("ino") int ino){
		
		String msg = service.qdel(ino);
		
		System.out.println(ino + ":" + msg);
	
     return msg;
  }
	
	//큐엔에이공개
	@PostMapping("/m_qopen")
	@ResponseBody
	public String qopen(@RequestParam("ino") int ino){
		
		String msg = service.qopen(ino);
		
	return msg;
  }
	
	//큐엔에이삭제
	@PostMapping("/m_qnadel")
	@ResponseBody
	public String qnadel(@RequestParam("ino") int ino){
		
		String msg = service.qnadel(ino);
		
	return msg;
  }
	
	//회원 상세정보
	@GetMapping("/m_popmem")
	public ModelAndView viewMem2(@RequestParam("mid") String mid){
			
			mv = service.viewMem2(mid);
			
		return mv;
	  }

	
	
}


