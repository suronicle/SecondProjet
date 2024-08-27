package kr.co.book.erp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.book.ecom.dto.inquiryDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.ecom.dto.noticeDTO;
import kr.co.book.ecom.service.MailService;
import kr.co.book.erp.dao.JerpDAO;

@Service
public class JerpService {

	private ModelAndView mv;
	
	@Autowired
	private JerpDAO dao;
	@Autowired
	private MailService mService;
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	
	//파일처리
    private Map<String, String> fileUpload(MultipartFile file, HttpSession session) throws Exception {
    	
    	Map<String, String> map = new HashMap<>();
    	
		String sysname = null;
		String oriname = null;
		
//		//저장소
//		String realPath = session.getServletContext().getRealPath("/");
//		realPath += "upload/";
		
		
		 Resource resource = resourceLoader.getResource("classpath:/static/files");
		 String projectPath = System.getProperty("user.dir") // 프로젝트 경로를 가져옴
	                + "\\src\\main\\resources\\static\\files\\";
		
		//파일 저장소 설정
		File folder = new File(projectPath);
		
		//파일 저장소 생겼는 지 확인
		if(folder.isDirectory() == false){
			folder.mkdir();
		}
		
		//원본 파일 이름을 반환
		MultipartFile mf = file;
		oriname = mf.getOriginalFilename();
		
		//저장될 이름 = 고유한 파일 이름
		sysname = System.currentTimeMillis()
		+ oriname.substring(oriname.lastIndexOf("."));
		
		//실제 파일이 저장될 경로와 파일 이름을 합친 문자열
		File filey = new File(projectPath + sysname);
		
		//실제 저장
		mf.transferTo(filey);
		
		map.put("oriname", oriname);
		map.put("sysname", sysname);
		
		System.out.println("map" + map);
		
		return map;
		
	}
    
    //파일삭제
    private void fileDelete(String poster, HttpSession session) throws Exception{
		
		Resource resource = resourceLoader.getResource("classpath:/static/files");
		
		String projectPath = System.getProperty("user.dir") // 프로젝트 경로를 가져옴
	                + "\\src\\main\\resources\\static\\files\\" + poster;
		File file = new File(projectPath);
		
		if(file.exists()){
			file.delete();
		}
	}
    
    
    //파일다운로드
    public ResponseEntity<Resource> downloadFile(String filename,String realname, HttpServletResponse response) throws IOException {


        // 파일 경로 설정 (예: upload 폴더 내에 있는 파일)
    	Resource resource = resourceLoader.getResource("classpath:/static/files");
		 String projectPath = System.getProperty("user.dir") // 프로젝트 경로를 가져옴
	                + "\\src\\main\\resources\\static\\files\\";
        Path filePath = Paths.get(projectPath + filename);
        
        // 파일이 존재하는지 확인
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found: " + filename);
        }
        
        // 파일을 Resource로 로드
        Resource resource2 = new InputStreamResource(new FileInputStream(filePath.toFile()));
        
        // 파일 다운로드를 위한 Content-Disposition 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ URLEncoder.encode(realname,StandardCharsets.UTF_8)+"\";");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource2);
	}
	
    
    //로컬 날짜 설정
    private String parts(String date) {
    	
        String[] parts = date.split(" "); // 공백을 기준으로 분리하여 배열로 저장

        if (parts.length == 2) {
            String datePart = parts[0]; // 날짜 부분
            String timePart = parts[1]; // 시간 부분

            // datetime-local 입력 필드에 맞게 변환
            date = datePart + 'T' + timePart.substring(0, 5); // 시간 부분의 초(second) 제거
        }

        return date;
    }
	
	//공지사항 리스트
    public ModelAndView mnoticelist () {
    	
    	mv = new ModelAndView();
    	mv.setViewName("m_notice");
    	
    	try {
    		
    		List<noticeDTO> dtolist = dao.mnoticelist();
    		mv.addObject("dtolist", dtolist);
	        	
    	} catch (Exception e) {
			
    		e.printStackTrace();
		}	
		
    	return mv;
        
    }
    
    //팝업,공지사항 상세
    public noticeDTO noticeditail (int nnum) {
    	
    	noticeDTO nodto = dao.noticeditail(nnum);
    	
    	nodto.setNstartdate(parts(nodto.getNstartdate()));
    	
    	if(nodto.getNenddate() !=  null && !nodto.getNenddate().equals("")) {
    		nodto.setNenddate(parts(nodto.getNenddate()));
    	}
    	
    	return nodto;
    }
    
	//공지,팝업 등록
	public ModelAndView newnotice(noticeDTO dto, HttpSession session,
			MultipartFile file, MultipartFile img) {
		
		mv.setViewName("m_notice");
    	
    	try {
    		
    		
    		if(dto.getNenddate().equals("") || dto.getNenddate() == null) {
    			
    			dto.setNstartdate(null);
    			dto.setNenddate(null);
    		}
    		 
    		
    		if(file != null && !file.isEmpty()) {
    			
    			//파일업로드
                String upFile = file.getOriginalFilename();

                if(!upFile.equals("")) {
                	
                	Map<String, String> map = fileUpload(file, session);
                	
                	dto.setNfilereal(map.get("oriname"));
                	dto.setNfilefake(map.get("sysname"));
            		
                }
                 
    		}
    		
    		if(img != null && !img.isEmpty()) {
    			
    			//파일업로드
                String upFile = img.getOriginalFilename();

                if(!upFile.equals("")) {
                	
                	Map<String, String> map = fileUpload(img, session);
                	
                	dto.setNpicturereal(map.get("oriname"));
                	dto.setNpicturefake(map.get("sysname"));
                }
    		}
    		
            //등록
    		if(dto.getNclass() == 0) {
    			
    			dao.newpopup(dto);
    			//기본정보 담기
        		List<noticeDTO> dtolist = dao.mpopuplist();
        		mv.addObject("dtolist", dtolist);
    			mv.setViewName("m_popup");
    			
    		} else {
    			
    			//기본정보 담기
        		List<noticeDTO> dtolist = dao.mnoticelist();
        		mv.addObject("dtolist", dtolist);
    			dao.newnotice(dto);
    		}
            
            
              
          
	        	
    	} catch (Exception e) {
			
    		e.printStackTrace();
		}	
		
    	return mv;
		
	}
	
	//공지, 팝업 수정
	public ModelAndView upnotice(noticeDTO dto, HttpSession session,
			MultipartFile file, MultipartFile img) {
		
		mv.setViewName("m_notice");
    	
    	try {
    		
    		
    		noticeDTO ndto = dao.noticeditail(dto.getNnum());
    		
    		
    		if(dto.getNclass() != ndto.getNclass()) {
    			ndto.setNclass(dto.getNclass());
    		}
    		if(!dto.getNstartdate().equals(ndto.getNstartdate())) {
    			ndto.setNclass(dto.getNclass());
    		}
    		if(!dto.getNenddate().equals(ndto.getNenddate())) {
    			ndto.setNenddate(dto.getNenddate());
    		}
    		if(!dto.getNtitle().equals(ndto.getNtitle())) {
    			ndto.setNtitle(dto.getNtitle());
    		}
    		if(!dto.getNcontent().equals(ndto.getNcontent())) {
    			ndto.setNcontent(dto.getNcontent());
    		}
    		if(!dto.getNuser().equals(ndto.getNuser())) {
    			ndto.setNuser(dto.getNuser());
    		}
    		
    		
			if(file != null && !file.isEmpty()) {
				
				if(ndto.getNfilefake()!= null && !ndto.getNfilefake().equals("")) {
					fileDelete(ndto.getNfilefake(), session);
				}
    			
    			//파일업로드
                String upFile = file.getOriginalFilename();

                if(!upFile.equals("")) {
                	
                	Map<String, String> map = fileUpload(file, session);
                	
                	ndto.setNfilereal(map.get("oriname"));
                	ndto.setNfilefake(map.get("sysname"));
            		
                }
                 
    		}
    		
        	if(img != null && !img.isEmpty()) {
        		
        		if(ndto.getNpicturefake()!= null && !ndto.getNpicturefake().equals("")) {
        			fileDelete(ndto.getNpicturefake(), session);
        		}
        		
        		//이미지업로드
                String upFile = img.getOriginalFilename();

                if(!upFile.equals("")) {
                	
                	Map<String, String> map = fileUpload(img, session);
                	
                	ndto.setNpicturereal(map.get("oriname"));
                	ndto.setNpicturefake(map.get("sysname"));
                }
        	}
        	
        	//등록
    		if(dto.getNclass() == 0) {
    			
        		ndto.setNwidth(dto.getNwidth());
        		ndto.setNlength(dto.getNlength());
    			
    			dao.uppopup(ndto);
    			//기본정보 담기
        		List<noticeDTO> dtolist = dao.mpopuplist();
        		mv.addObject("dtolist", dtolist);
    			mv.setViewName("m_popup");
    			
    		} else {
    			dao.upnotice(ndto);
    			List<noticeDTO> dtolist = dao.mnoticelist();
        		mv.addObject("dtolist", dtolist);
    		}
    		
    		
	        	
    	} catch (Exception e) {
			
    		e.printStackTrace();
		}	
		
    	return mv;
		
	}
	
	//공지 취소 //팝업 취소
	public String delnotice(int nnum) {
		
		System.out.println(nnum);
		
		String msg = null;
		
		try {
			
			int re = dao.delnotice(nnum);
			
			if(re == 1) {
				msg = "Y";
			} else {
				msg = "N";
			}
			
		} catch (Exception e) {
			
			msg = "E";
			
			e.printStackTrace();
		}
		
		
		return msg;
	}
	
	//팝업 리스트
	public ModelAndView mpopuplist (){
		
    	mv = new ModelAndView();
    	mv.setViewName("m_popup");
    	
    	try {
    		
    		List<noticeDTO> dtolist = dao.mpopuplist();
    		mv.addObject("dtolist", dtolist);
	        	
    	} catch (Exception e) {
			
    		e.printStackTrace();
		}	
		
    	return mv;
	}
	
	
	//완전삭제
	public String delno(int nnum, HttpSession session) {
		
		String msg = null;
		
		try {
			
			noticeDTO ndto = dao.noticeditail(nnum);
			
			System.out.println("dto" + ndto);
			
			if(ndto.getNfilefake()!= null && !ndto.getNfilefake().equals("")) {
				System.out.println("dt1o" + ndto);
				fileDelete(ndto.getNfilefake(), session);
			}
			
			if(ndto.getNfilefake()!= null && !ndto.getNfilefake().equals("")) {
				System.out.println("dto2" + ndto);
				fileDelete(ndto.getNfilefake(), session);
			}
			
			int re = dao.delno(nnum);
			
			
			if(re == 1) {
				msg = "Y";
			} else {
				msg = "N";
			}
			
		} catch (Exception e) {
			
			msg = "E";
			
			e.printStackTrace();
		}
		
		
		return msg;
		
	}
	

	//큐엔에이 리스트
	public ModelAndView qlist() {
		
		mv = new ModelAndView();
		
		try {
			
			List<inquiryDTO> dto = dao.qlist();
			
			for (int a = 0; a < dto.size(); a++) {
				dto.get(a).setIdiv(dao.pname(dto.get(a).getIdiv())); 
			}
			
			mv.addObject("dtolist", dto);
			mv.setViewName("m_QNA");
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return mv;
	}
	
	//큐엔에이 상세
	public inquiryDTO qone(int ino){
		
		inquiryDTO dto = new inquiryDTO();
		
		try {
			
			dto = dao.qone(ino);
			
			dto.setIdiv(dao.pname(dto.getIdiv()));
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return dto;
	}
	
	//큐엔에이 답변 등록
	public ModelAndView answer (inquiryDTO dto) {
		
		mv = new ModelAndView();
		
		try {
			
			dao.answer(dto);
			
			List<inquiryDTO> dtolist = dao.qlist();
			
			mv.addObject("dtolist", dtolist);
			mv.setViewName("m_QNA");
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return mv;
	}
	
	//큐엔에이 완전비공개처리
	public String qdel (int ino) {
		
		String msg = null;
		
		try {
			
			int re = dao.qdel(ino);
			
			if (re == 1) {
				msg = "Y";
			} else {
				msg = "N";
			}
			
		}catch (Exception e) {
			
			msg = "E";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	//큐엔에이 공개처리
	public String qopen (int ino) {
		
		String msg = null;
		
		try {
			
			int re = dao.qopen(ino);
			
			if (re == 1) {
				msg = "Y";
			} else {
				msg = "N";
			}
			
		}catch (Exception e) {
			
			msg = "E";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	//큐엔에이 완전 삭제
	public String qnadel (int ino) {
		
		String msg = null;
		
		try {
			
			int re = dao.qnadel(ino);
			
			if (re == 1) {
				msg = "Y";
			} else {
				msg = "N";
			}
			
		}catch (Exception e) {
			
			msg = "E";
			e.printStackTrace();
		}
		
		return msg;
	}
    
	//회원 상세
	public ModelAndView viewMem2(String mid) {
		
		mv = new ModelAndView();
		
		try {
			memberDTO dto = dao.viewMem2(mid);
			
			mv.addObject("dto", dto);
			
		}catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("msg", "오류 발생");
		}
		mv.setViewName("m_QNAmember");
		return mv;
	}
    
	
	
}

