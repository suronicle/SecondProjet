package kr.co.book.ecom.controller;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dto.adressDTO;
import kr.co.book.ecom.dto.buyDTO;
import kr.co.book.ecom.dto.commentsDTO;
import kr.co.book.ecom.dto.inquiryDTO;
import kr.co.book.ecom.dto.loginsessionDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.ecom.dto.pointgameDTO;
import kr.co.book.ecom.dto.reviewDTO;
import kr.co.book.ecom.service.BookService;
import kr.co.book.ecom.service.JbookService;

@Controller
public class Jbookcontroller {
   
   private ModelAndView mv;
   
   @Autowired
   private JbookService service;
   
   @Autowired
   private BookService bservice;


   //메인
   @GetMapping("/")
      public String newmain(){
         return "redirect:/eMain";
      }

   //로그인
   @GetMapping("/elogin")
      public String elogin(){
         return "e_login";
      }
   
   //로그인 처리
   @PostMapping("/elogin")
      public ModelAndView elogin(@RequestParam Map<String, String> map, HttpSession session){
      
         mv = service.login(map, session);
      
         
         // 숨 : 로그인 시에 비회원 장바구니 리셋
         int delcart = bservice.delcart("none");
         if(delcart==1) {
            System.out.println("비회원 장바구니 리셋");          
         }

         
         return mv;
      }
   
   //아이디 찾기 열기
   @GetMapping("/eidfind")
      public String eidfind(){
      
         return "e_idfind";
      }
   
   // 아이디 찾기 코드 메일 전송   
    @PostMapping("/emailsid")
    @ResponseBody
    public String sendMessageid(@RequestParam("email") String email, @RequestParam("mname") String mname)throws MessagingException {
       
       String response = service.sendMessageid(email, mname);

       return response;
        
    }
    
   //아이디 찾기 완료
   @PostMapping("/eidfind")
   @ResponseBody
      public String eidfind(@RequestParam("email") String email, @RequestParam("mname") String mname){
      
      String response = service.findid(email, mname);
      
         return response;
      }
   
   //비밀번호 찾기 열기
   @GetMapping("/epwfind")
      public String epwfind(){
      
         return "e_pwfind";
      }
   
   //비밀번호 찾기 코드 메일 전송   
    @PostMapping("/emailspw")
    @ResponseBody
    public String sendMessagepw(@RequestParam("email") String email, @RequestParam("mid") String mid)throws MessagingException {
       
       String response = service.sendMessagepw(email, mid);

       return response;
        
    }
    
   //비밀번호 찾기 완료
   @PostMapping("/epwfind")
   @ResponseBody
      public void epwfind(@RequestParam("email") String email, @RequestParam("mid") String mid)throws MessagingException {
      
      service.findpw(email, mid);
      
      }
   
   
   //사업자 비밀번호 찾기 열기
   @GetMapping("/mpwfind")
      public String mpwfind(){
      
         return "e_mpwfind";
      }
   
   //사업자 비밀번호 찾기
   @PostMapping("/mpwfind")
   @ResponseBody
      public String mpwfind(@RequestParam("brno") String brno, 
            @RequestParam("brcode") String brcode){
      
       String pw = service.brpw(brno, brcode);
      
         return pw;
      }
   
   //회원가입 페이지 열기
   @GetMapping("/ejoin")
      public String ejoin(){
      
         return "e_join";
      }
   
   //회원가입 처리
   @PostMapping("/ejoin")
      public ModelAndView ejoin(
            memberDTO mdto,
            adressDTO adto,
            @RequestParam("memail1") String memail1,
            @RequestParam("memail2") String memail2,
            @RequestParam("mphone1") String mphone1,
            @RequestParam("mphone2") String mphone2,
            @RequestParam("mphone3") String mphone3,
            @RequestParam("sterms1") int sterms1,
            @RequestParam("sterms2") int sterms2,
            @RequestParam("sterms3") int sterms3){
         
         int optional = sterms1+sterms2+sterms3;
         if(optional == 3 && sterms3 == 0) {   
            
            optional = 7;
            
            }
         
         String phone = mphone1+mphone2+mphone3;
         
         mdto.setMemail(memail1+"@"+memail2);
         mdto.setMphone(phone);
         mdto.setMoptional("D"+optional);
      
         mv = service.enewmamber(mdto,adto);
         
         return mv;
      }
   
   //회원가입 시 이메일 중복 체크
   @PostMapping("/mailselct")
   @ResponseBody
      public String mailselct(@RequestParam("mail") String mail){
      
      String response = service.mailselct(mail);
      
         return response;
      }
   
   //회원가입 시 아이디 중복 체크
   @PostMapping("/idselct")
   @ResponseBody
      public String idselct(@RequestParam("mid") String mid){
      
      String response = service.idselct(mid);
      
         return response;
      }
   
   //마이페이지
   @GetMapping("/emypage")
      public ModelAndView emypage(HttpSession session){
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.emypage(ldto.getMid());
         
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
      
         return mv;
      }
   
   //배송정보
   @GetMapping("/ePhistory")
      public ModelAndView ePhistory(HttpSession session){
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.emybuy(ldto.getMid());
         
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
      
         return mv;
      }
   
   //배송 상세
   @GetMapping("/eDhistory")
      public ModelAndView eDhistory(HttpSession session, @RequestParam("date") String buydate){
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.emybuyditail(ldto.getMid(), buydate);
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
       return mv;
      }
   
   //배송 상세 - 교환 환불 처리
   @PostMapping("/eDhistory")
      public ModelAndView eDhistory(HttpSession session, @RequestParam("buycode") String buycode, @RequestParam("date") String buydate){
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.emybuyditail(ldto.getMid(), buycode, buydate);
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
       return mv;
      }
   
   
   //주문 상태 처리
   @PostMapping("/ebuycode")
   @ResponseBody
   public String ebuycode(@RequestParam("buycode") String buycode,@RequestParam("buyno") int buyno, HttpSession session) {
      
      String msg = null;
      buyDTO dto = new buyDTO();
      dto.setBuycode(buycode);
      dto.setBuyno(buyno);
      
      try {
         
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         dto.setBuyid(ldto.getMid()); 
         msg = service.upcode(dto);
         
      } catch (Exception e) {
         msg = "E";
      }
      
      
      return msg;
   }
   
   //리뷰 리스트 마이페이지
   @GetMapping("/emyRlist")
      public ModelAndView myreviewList(HttpSession session){
      
         
         try {
            loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
            mv = service.myreviewList(ldto.getMid());
            
         } catch (Exception e) {
            mv = new ModelAndView();
            mv.setViewName("redirect:/elogin");
         }
      
         
      
         return mv;
      }
   
   //주문 확정 처리
   @PostMapping("/econfirm")
   @ResponseBody
   public String econfirm(@RequestParam("buycode") String buycode,@RequestParam("buyno") int buyno,
         @RequestParam("buybooknum") String buybooknum,@RequestParam("buynum") int buynum, HttpSession session) {
      
      System.out.println("ddddddddddddddd" + buybooknum);
      
      String msg = null;
      buyDTO dto = new buyDTO();
      dto.setBuycode(buycode);
      dto.setBuyno(buyno);
      dto.setBuybooknum(buybooknum);
      dto.setBuynum(buynum);
      
      System.out.println(dto);
      
      try {
         
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         
         if(ldto == null) {
            dto.setBuyid("none"); 
         } else {
            dto.setBuyid(ldto.getMid()); 
         }
         
         msg  = service.confirm(dto);
         
      } catch (Exception e) {
         msg = "redirect:/eMain";
      }
      
      return msg;
   }
      
   
   
   @GetMapping("/eicon")
      public String icon(Model model){
      
         return "e_icon";
      }
   
   //정보수정 페이지
   @GetMapping("/esetting")
      public ModelAndView esetting(HttpSession session){
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.memberone(ldto.getMid());
         
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
         return mv;
      }
   
   //정보수정 페이지
   @GetMapping("/esettingup")
      public ModelAndView esettingup(@RequestParam("mid") String mid, @RequestParam("msg") String msg){
      
      mv = service.memberone(mid);
      mv.addObject("msg", msg);
      
         return mv;
      }
   
   //비밀번호 확인
   @PostMapping("/epwck")
   @ResponseBody
      public String epwck(@RequestParam("mpw") String mpw, @RequestParam("mid") String mid){
      
      String msg = service.selectPW(mpw, mid);
      
      return msg;
      }
   
   //정보수정 
   @PostMapping("/msetting")
      public ModelAndView msetting(memberDTO dto,
            @RequestParam("memail1") String memail1,
            @RequestParam("memail2") String memail2,
            @RequestParam("mphone1") String mphone1,
            @RequestParam("mphone2") String mphone2,
            @RequestParam("mphone3") String mphone3,
            @RequestParam("sterms1") int sterms1,
            @RequestParam("sterms2") int sterms2,
            @RequestParam("sterms3") int sterms3){
      

         
         int optional = sterms1+sterms2+sterms3;
         if(optional == 3 && sterms3 == 0) {   
            
            optional = 7;
            
            }
         
         String phone = mphone1+mphone2+mphone3;
         
         dto.setMemail(memail1+"@"+memail2);
         dto.setMphone(phone);
         dto.setMoptional("D"+optional);
      
         mv = service.msetting(dto);
      
         return mv;
      }
   
   //주소 추가
   @PostMapping("/enewadress")
   @ResponseBody
      public String enewadress(@RequestParam("apostal") String apostal, @RequestParam("aadress") String aadress,
            @RequestParam("adong") String adong, @RequestParam("adetail") String adetail, @RequestParam("aid") String aid){
      
      adressDTO dto = new adressDTO();
      dto.setApostal(apostal);
      dto.setAadress(aadress);
      dto.setAdong(adong);
      dto.setAdetail(adetail);
      dto.setAid(aid);
      
      String msg = service.newadress(dto);
      
      return msg;
      }
   
   //주소 리스트 팝업창
   @GetMapping("/eapop")
      public ModelAndView apop(@RequestParam("mid") String mid){
      
      mv = service.memberone(mid);
      
      mv.setViewName( "e_apop");
      
         return mv;
      }
   
   //주소 삭제
   @PostMapping("/eadel")
   @ResponseBody
      public String eadel(@RequestParam("aid") String aid, @RequestParam("ano") String ano){
      
      adressDTO dto = new adressDTO();
      dto.setAid(aid);
      dto.setAno(Integer.parseInt(ano));
      
      String response = service.adel(dto);
      
         return response;
      }
   
   //상세 주소 변경
   @PostMapping("/asetting")
   @ResponseBody
      public String asetting(@RequestParam("aid") String aid, 
            @RequestParam("ano") String ano, @RequestParam("adetail") String adetail){
      
      adressDTO dto = new adressDTO();
      dto.setAno(Integer.parseInt(ano));
      dto.setAdetail(adetail);
      dto.setAid(aid);
      
      String response = service.asetting(dto);
      
         return response;
      }
   
   //기본 주소 변경
   @PostMapping("/abasic")
   @ResponseBody
      public String abasic(@RequestParam("aid") String aid, @RequestParam("ano") String ano){
      
      String response = service.abasic(ano, aid);
      
         return response;
      }
   
     
     //회원 탈퇴
   @PostMapping("/mdel")
     public ModelAndView mdel (@RequestParam("middel") String mid, HttpSession session) {
        
        mv = service.mdel(mid,session);
      
       return mv;
        
     }
   
   //공지 리스트
   @GetMapping("/enotice")
   public ModelAndView noticelist() {
      
      mv = service.noticelist();
      
      return mv;
   }
   
   //팝업 상세
   @GetMapping("/epop")
   public ModelAndView popone(@RequestParam("nnum") int nnum) {
      
      mv = service.popone(nnum);
      
      return mv;
   }
   

   
   //룰렛 페이지
   @GetMapping("/eroulette")
      public ModelAndView eroulette(HttpSession session){
      
      loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
      
         if(ldto != null) {
            mv = service.eroulette(ldto.getMid());
            mv.addObject("mid",ldto.getMid());
         } else {
            mv = service.eroulette("none");
            mv.addObject("mid","none");
         }
         
         
         return mv;
      }
   
   	//룰렛 페이지
 	@PostMapping("/epoint")
 	@ResponseBody
 	   public String point(@RequestParam("gid") String gid,
 			   			@RequestParam("gno") int gno,
 			   			@RequestParam("gpoint") int gpoint, HttpSession session){
 		
 		String re = null;
 		
 		try {
 			loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
 			
 			pointgameDTO dto = new pointgameDTO();
 			dto.setGno(gno);
 			dto.setGid(gid);
 			dto.setGpoint(gpoint);
 			
 			re = service.point(dto, ldto.getMid(), session);
 			
 		} catch (Exception e) {
 			re = "redirect:/elogin";
 		}
 		
 	      return re;
 	   }
   
   //문의 창 열기
   @GetMapping("/eQNA")
      public String eQNA(){
      
         return "e_QNA";
      }
   
   //문의 등록
   @PostMapping("/eQNA")
   @ResponseBody
      public String eQNA( HttpSession session,
                     @RequestParam("ititle") String ititle,
                  @RequestParam("idiv") String idiv,
                  @RequestParam("ipw") String ipw,
                  @RequestParam("icontent") String no,
                  @RequestParam("iname") String iname,
                  @RequestParam("iemail") String iemail
                  ){
      
      loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
      
      inquiryDTO dto = new inquiryDTO();
      
      if (ldto != null) {
         dto.setIid(ldto.getMid());
         dto.setIname(ldto.getMname());
      } else {
         dto.setIid("none");
         dto.setIname(iname);
         dto.setIemail(iemail);
      }
      
      dto.setIdiv("Q"+idiv);
      dto.setItitle(ititle);
      dto.setIcontent(no);
      dto.setIpw(ipw);
      
      String re = service.newinquiry(dto);
      
         return re;
      }
   
   //익명 문의 리스트
   @GetMapping("/eQNAlist")
      public ModelAndView eQNAlist(){
      
      mv = service.inquirylist();
         
      
         return mv;
      }
   
   //비회원 비공개 비밀번호 체크
   @PostMapping("/eQNApw")
   @ResponseBody
      public String inquirypw (@RequestParam("ino") int ino){
      
      String pw = service.inquirypw(ino);
         
         return pw;
      }
   
   //익명 문의 내용
   @GetMapping("/eQNAcont")
   @ResponseBody
   public inquiryDTO inquirycont (@RequestParam("ino") int ino){
      
      inquiryDTO dto = service.inquirycont(ino);
         
         return dto;
      }
   
   //내문의 리스트
   @GetMapping("/emyQNAlist")
      public ModelAndView eQNAlist(HttpSession session){
      
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.myinquirylist(ldto.getMid());
         
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
         return mv;
      }
   
   //내문의 취소
   @PostMapping("/emyQNAlist")
   public ModelAndView delinquiry (HttpSession session, @RequestParam("ino")String ino) {
      
      try {
         loginsessionDTO ldto = (loginsessionDTO)session.getAttribute("login");
         mv = service.delinquiry(ldto.getMid(), ino);
         
      } catch (Exception e) {
         mv = new ModelAndView();
         mv.setViewName("redirect:/elogin");
      }
      
         return mv;
      }
   
   
   //리뷰 리스트
   @GetMapping("/eRlist")
   @ResponseBody
      public List<reviewDTO> reviewList(@RequestParam("bono") String bono){
      
         List<reviewDTO> rlist = service.reviewList(bono);
      
         return rlist;
      }
      
   //리뷰 등록
   @PostMapping("/enewR")
   @ResponseBody
      public String newreview(@RequestParam("bono") String bono, @RequestParam("rspoiler") String rspoiler,
            @RequestParam("rcontents") String rcontents,@RequestParam("rrating") String rrating,@RequestParam("rid") String rid){
      
      reviewDTO dto = new reviewDTO();
      dto.setRbookno(bono);
      dto.setRid(rid);
      dto.setRspoiler(Integer.parseInt(rspoiler));
      dto.setRcontents(rcontents);
      dto.setRrating(Integer.parseInt(rrating));
      
         String msg = service.newreview(dto);
         
         return msg;
      }  

   //리뷰 삭제
   @PostMapping("/eRdel")
   @ResponseBody
      public String rdel(@RequestParam("rno") String rno){
      
         String msg = service.rdel(rno);
      
         return msg;
      }
   
      
   //리뷰 수정
   @PostMapping("/eRup")
   @ResponseBody
      public String upreview (@RequestParam("rno1") String rno, @RequestParam("rspoiler1") String rspoiler,
            @RequestParam("rcontents1") String rcontents,@RequestParam("rrating1") String rrating) {
      
         reviewDTO dto = new reviewDTO();
         dto.setRno(rno);
         dto.setRspoiler(Integer.parseInt(rspoiler));
         dto.setRcontents(rcontents);
         dto.setRrating(Integer.parseInt(rrating));
      
         String msg = service.upreview(dto);
      
         return msg;
      
      }
   
   //리뷰 대댓 리스트
   @GetMapping("/eRRlist")
   @ResponseBody
      public List<commentsDTO> comList (@RequestParam("rno") String rno) {
         
         System.out.println("++==========" + rno);
          
          List<commentsDTO> list = service.comList(rno);
          
          System.out.println("++==========" + list);
      
         return list;
      }
   
   //리뷰 대댓 등록
   @PostMapping("/enewRR")
   @ResponseBody
      public String newcom(@RequestParam("rno") String rno, @RequestParam("cspoiler") String cspoiler,
            @RequestParam("ccontents") String ccontents, @RequestParam("cid") String cid){
      
      commentsDTO dto = new commentsDTO();
      dto.setRno(rno);
      dto.setCid(cid);
      dto.setCspoiler(Integer.parseInt(cspoiler));
      dto.setCcontents(ccontents);
      
         String msg = service.newcom(dto);
         
         return msg;
      }  

   //리뷰 삭제
   @PostMapping("/eRRdel")
   @ResponseBody
      public String cdel(@RequestParam("cno") String cno){
      
         String msg = service.cdel(cno);
      
         return msg;
      }
   
      
   //리뷰 수정
   @PostMapping("/eRRup")
   @ResponseBody
      public String upcom (@RequestParam("cno1") String cno, @RequestParam("cspoiler1") String cspoiler,
            @RequestParam("ccontents1") String ccontents) {
      
         commentsDTO dto = new commentsDTO();
         dto.setCno(Integer.parseInt(cno));
         dto.setCspoiler(Integer.parseInt(cspoiler));
         dto.setCcontents(ccontents);
               
         String msg = service.upcom(dto);
      
         return msg;
      
      }
   
   //공감
   @PostMapping("/esympathy")
   @ResponseBody
   public void sympathy (@RequestParam("sno") String sno, @RequestParam("sid") String sid) {
      
      
      service.sympathy(sno, sid);
   }
      


   
   
}
