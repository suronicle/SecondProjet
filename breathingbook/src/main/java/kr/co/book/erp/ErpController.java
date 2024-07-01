package kr.co.book.erp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class ErpController {

	@GetMapping("/m")
	public String m_main() {
		return "m_main";
	}
	@GetMapping("/one")
	public String m_one() {
		return "m_one_column";
	}
	@GetMapping("/two")
	public String m_two() {
		return "m_two_column";
	}
	@GetMapping("/m_companyList")
	public String m_companyList(@RequestParam("source") String source, Model model) {
		model.addAttribute("source", source);
		return "m_companyList";
	}
	
} 
