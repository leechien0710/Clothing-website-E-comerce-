package com.sda.java3.ecommerce.twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwilioController {
	@Autowired
	private TwilioService service;
	@GetMapping("/twilio")
	public String twilo(@RequestParam("phone") String phone) {
		service.sendSms(phone);
		return "redirect:/";
	}
}
