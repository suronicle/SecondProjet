package kr.co.book.erp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.book.erp.dao.PurchaseDAO;
import kr.co.book.erp.dto.BranchDTO;
import kr.co.book.ecom.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseService {

	@Autowired
	private PurchaseDAO pdao;
		
	public int purInsert(BookDTO book) {
		log.info("purInsert() brno:{}",book.getBrno());
        
		try {
			pdao.purInsert(book);
			return 1;
		} catch (Exception e) {
			log.error("Failed to insert purchase data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
    }
	public int purUpdate(BookDTO book) {
		log.info("purUpdate() brno:{}",book.getBrno());
        
		try {
			pdao.purUpdate(book);
			return 1;
		} catch (Exception e) {
			log.error("Failed to insert purchase data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
    }

	public int purCancle(BookDTO book) {
		log.info("purCancle() brno:{}",book.getBrno());
		try {
			pdao.purCancle(book);
			return 1;
		} catch (Exception e) {
			log.error("Failed to update purchase data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
	}
	
	public int purAdd(BookDTO book) {
		log.info("purAdd()");
        
		try {
			pdao.purAdd(book);
			return 1;
		} catch (Exception e) {
			log.error("Failed to insert purchase data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
    }

	public List<BookDTO> searchBook() {
		log.info("searchBook()");
		
		List<BookDTO> bookList = pdao.searchBook();
		
		return bookList;
	}
	
	public List<BookDTO> purList() {
		log.info("purList()");
		
		List<BookDTO> purList = pdao.purList();
		for (BookDTO book : purList) {
            book.formatFields();
        }
		
		return purList;
	}
	
	public List<BookDTO> sumyeartotal(int year) {
		log.info("sumyeartotal() with year:{}",year);
		
		List<BookDTO> sumyeartotal = pdao.sumyeartotal(year);
		for (BookDTO book : sumyeartotal) {
            book.formatFields();
        }
		
		return sumyeartotal;
	}
	public List<BookDTO> summonthtotal(int month) {
		log.info("summonthtotal() with month:{}",month);
		
		List<BookDTO> summonthtotal = pdao.summonthtotal(month);
		for (BookDTO book : summonthtotal) {
            book.formatFields();
        }
		
		return summonthtotal;
	}
	
	public List<BookDTO> sumdaytotal(int day) {
		log.info("sumdaytotal() with day:{}",day);
		
		List<BookDTO> sumdaytotal = pdao.sumdaytotal(day);
		for (BookDTO book : sumdaytotal) {
            book.formatFields();
        }
		
		return sumdaytotal;
	}
	
	public List<BookDTO> mSalesList() {
		log.info("mSalesList()");
		
		List<BookDTO> mSalesList = pdao.mSalesList();
		for (BookDTO book : mSalesList) {
            book.formatFields();
        }
		
		return mSalesList;
	}
	
	public List<BookDTO> myeartotal(int year) {
		log.info("myeartotal() with year:{}",year);
		
		List<BookDTO> myeartotal = pdao.myeartotal(year);
		for (BookDTO book : myeartotal) {
            book.formatFields();
        }
		
		return myeartotal;
	}
	public List<BookDTO> mmonthtotal(int month) {
		log.info("mmonthtotal() with month:{}",month);
		
		List<BookDTO> mmonthtotal = pdao.mmonthtotal(month);
		for (BookDTO book : mmonthtotal) {
            book.formatFields();
        }
		
		return mmonthtotal;
	}
	
	public List<BookDTO> mdaytotal(int day) {
		log.info("mdaytotal() with day:{}",day);
		
		List<BookDTO> mdaytotal = pdao.mdaytotal(day);
		for (BookDTO book : mdaytotal) {
            book.formatFields();
        }
		
		return mdaytotal;
	}
	
	public BookDTO purView(int pno) {
	    log.info("purView() with pno: {}", pno);

	    BookDTO book = pdao.purView(pno);
	    if (book != null) {
	        log.info("book에 담긴 pno: {}",book.getPno());
	        book.formatFields();
	    } else {
	        log.error("BookDTO is null for pno: {}", pno);
	    }
	    return book;
	}
	
	public int purEdit(BookDTO book) {
		log.info("purEdit()");
		try {
			pdao.purEdit(book);
			return 1;
		} catch (Exception e) {
			log.error("Failed to update purchase data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
	}

	 
	
	
	public int purDelete(int pno) {
		log.info("purDelete()");
		try {
			pdao.purDelete(pno);
			return 1;
		} catch (Exception e) {
			log.error("Failed to update purchase data: {}", e.getMessage());
			return -1; // 실패 시 -1 반환
        }
	}
	
	// 가맹점
	public List<BookDTO> purList1(String brno) {
		log.info("purList1() with brno:{}",brno);
		
		List<BookDTO> purList = pdao.purList1(brno);
		for (BookDTO book : purList) {
            book.formatFields();
        }
		
		return purList;
	}
	public List<BookDTO> sumyeartotal1(int year, String brno) {
		log.info("sumyeartotal1() with year:{}, brno:{}",year,brno);
    	Map<String, String> map = new HashMap<>();
    	map.put("year", String.valueOf(year));
    	map.put("brno", brno);
    	
		List<BookDTO> sumyeartotal = pdao.sumyeartotal1(map);
		for (BookDTO book : sumyeartotal) {
            book.formatFields();
        }
		
		return sumyeartotal;
	}
	public List<BookDTO> summonthtotal1(int month, String brno) {
		log.info("summonthtotal1() with month:{}, brno:{}",month,brno);
    	Map<String, String> map = new HashMap<>();
    	map.put("month", String.valueOf(month));
    	map.put("brno", brno);
		List<BookDTO> summonthtotal = pdao.summonthtotal1(map);
		for (BookDTO book : summonthtotal) {
            book.formatFields();
        }
		
		return summonthtotal;
	}
	
	public List<BookDTO> sumdaytotal1(int day, String brno) {
		log.info("sumdaytotal1() with day:{}, brno:{}",day,brno);
    	Map<String, String> map = new HashMap<>();
    	map.put("day", String.valueOf(day));
    	map.put("brno", brno);
		List<BookDTO> sumdaytotal = pdao.sumdaytotal1(map);
		for (BookDTO book : sumdaytotal) {
            book.formatFields();
        }
		
		return sumdaytotal;
	}
}
