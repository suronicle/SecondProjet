package kr.co.book.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.erp.dao.HmainDAO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HmainService {
    
	@Autowired
	private HmainDAO hDao;
	
	public int countVen() {
		int result = hDao.countVen();
		return result;
	}
	public BookDTO bestVen() {
		BookDTO result = hDao.bestVen();
		return result;
	}
	public int countBra() {
		int result = hDao.countBra();
		return result;
	}
	public BookDTO bestBra() {
		BookDTO result = hDao.bestBra();
	    if (result != null) {
	        result.formatFields();
	    } else {
	        log.error("BookDTO is null for result: {}", result);
	    }
		return result;
	}
	public int countBook() {
		int result = hDao.countBook();
		return result;
	}
	public BookDTO bestBook() {
		BookDTO result = hDao.bestBook();
	    if (result != null) {
	        result.formatFields();
	    } else {
	        log.error("BookDTO is null for result: {}", result);
	    }
		return result;
	}
	public BookDTO lastmonth() {
		BookDTO result = hDao.lastmonth();
		if (result != null) {
	        result.formatFields();
		}
		return result;
	}
	public BookDTO curmonth() {
		BookDTO result = hDao.curmonth();
		if (result != null) {
	        result.formatFields();
		}
		return result;
	}
	public List<BookDTO> getMonthlySales() {
		List<BookDTO> result = hDao.getMonthlySales();
	    if (result != null) {
	    	for (BookDTO dto : result) {
	    		dto.formatFields(); // 포맷팅 메소드 호출
	        }
	    }
	    return result;
	}

	public List<BookDTO> getMonthlyBuy() {
		List<BookDTO> result = hDao.getMonthlyBuy();
	    if (result != null) {
	    	for (BookDTO dto : result) {
	    		dto.formatFields(); // 포맷팅 메소드 호출
	        }
	    }
	    return result;
	}
}
