package kr.co.book.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class jeoncontroller {

<<<<<<< HEAD
	@GetMapping("/login")
=======

	@GetMapping("/elogin")
>>>>>>> 46621fc31a45334aae3845df2574e0a64f6cf8ef
	   public String elogin(){
	      return "e_login";
	   }
	
	@GetMapping("/ejoin")
	   public String ejoin(){
	      return "e_join";
	   }
	
}
