package kr.co.book.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class jeoncontroller {

	@GetMapping("/")
	   public String writeFrm(){
	      return "e_default";
	   }
	
}
