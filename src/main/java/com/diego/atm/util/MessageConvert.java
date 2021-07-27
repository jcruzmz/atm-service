package com.diego.atm.util;

import org.springframework.stereotype.Component;

@Component
public class MessageConvert {
	
	public String convert(String response) {
		return response.replace("jsonCallback(", "").replace(");", "");
	}
}
