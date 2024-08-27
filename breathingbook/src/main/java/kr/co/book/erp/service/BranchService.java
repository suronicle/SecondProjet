package kr.co.book.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.erp.dao.BranchDAO;
import kr.co.book.erp.dto.BranchDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchService {
	private ModelAndView mv;
    
	@Autowired
	private BranchDAO bDao;
	
	public ModelAndView branchList() {
		log.info("branchList()");
		
		mv = new ModelAndView();
		
		List<BranchDTO> branchList = bDao.branchList();
		for (BranchDTO bdto : branchList) {
            bdto.formatFields();
        }
		mv.addObject("comList", branchList);
		
		mv.setViewName("m_comList");
		
		return mv;
	}
	
	public ModelAndView vendorList() {
		log.info("vendorList()");
		
		mv = new ModelAndView();

		List<BranchDTO> vendorList = bDao.vendorList();
		for (BranchDTO bdto : vendorList) {
            bdto.formatFields();
        }
		mv.addObject("comList", vendorList);
		
		mv.setViewName("m_comList");
		
		return mv;
	}
	
	@Transactional
	public int comInsert(BranchDTO bdto) {
		log.info("comInsert()");
        
		try {
			// brclass에 따라 brno 생성
	        if (bdto.getBrclass() == 1) {
	            bdto.setBrno(bBrno());
	        } else if (bdto.getBrclass() == 2) {
	            bdto.setBrno(vBrno());
	        }
			bDao.comInsert(bdto);
			return 1;
		} catch (Exception e) {
			log.error("Failed to insert branch data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
    }
	
	public BranchDTO comView(String brno) {
	    log.info("comView() with brno: {}", brno);

	    BranchDTO bdto = bDao.comSelect(brno);
	    if (bdto != null) {
	        log.info("bdto에 담긴 brno: {}",bdto.getBrno());
	        bdto.formatFields();
	    } else {
	        log.error("BranchDTO is null for brno: {}", brno);
	    }
	    return bdto;
	}
	
	private String bBrno() {
        // B 타입의 brno 생성 로직
        return "B" + String.format("%05d", bDao.getNextBrno(1));
    }
	private String vBrno() {
        // V 타입의 brno 생성 로직
        return "V" + String.format("%05d", bDao.getNextBrno(2));
    }

	public int comUpdate(BranchDTO bdto) {
		log.info("comUpdate()");
		try {
			bDao.comUpdate(bdto);
			return 1;
		} catch (Exception e) {
			log.error("Failed to update branch data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
	}
	
	public void changePw(String brno, String cpw2) {
		log.info("changePw1() with brno:{}, cpw2:{}", brno,cpw2);
		
		bDao.changePw(brno, cpw2);
	}
	
	public int comDelete(String brno) {
		log.info("comDelete()");
		try {
			bDao.comDelete(brno);
			return 1;
		} catch (Exception e) {
			log.error("Failed to update branch data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
	}
	
	public List<BranchDTO> searchCom() {
		log.info("searchCom()");
		
		List<BranchDTO> comList = bDao.searchCom();
		
		return comList;
	}
	public List<BranchDTO> searchBra() {
		log.info("searchBra()");
		
		List<BranchDTO> comList = bDao.branchList();
		
		return comList;
	}
	public List<BranchDTO> searchVen() {
		log.info("searchCom()");
		
		List<BranchDTO> comList = bDao.vendorList();
		
		return comList;
	}
	
	
	
	//회원정보
	public List<memberDTO> memList() {
		log.info("memList()");
		
		List<memberDTO> memList = bDao.memList();

		return memList;
	}
	
	public memberDTO viewMem(String mid) {
	    log.info("viewMem() with mid: {}", mid);

	    memberDTO mem = bDao.viewMem(mid);
	    if (mem != null) {
	        log.info("bdto에 담긴 mid: {}", mem.getMid());
	        mem.formatFields();
	    } else {
	        log.error("memberDTO is null for mid: {}", mid);
	    }
	    return mem;
	}

	public int memDelete(String mid) {
		log.info("memDelete()");
		try {
			bDao.memDelete(mid);
			return 1;
		} catch (Exception e) {
			log.error("Failed to update branch data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
	}
}
