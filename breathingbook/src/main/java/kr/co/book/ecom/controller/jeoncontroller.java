package kr.co.book.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class jeoncontroller {


	@GetMapping("/elogin")
	
	   public String elogin(){
	      return "e_login";
	   }
	
	@GetMapping("/ejoin")
	   public String ejoin(){
	      return "e_join";
	   }
	
}
