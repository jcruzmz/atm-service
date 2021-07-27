package com.diego.atm.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "atm", url="https://www.banamex.com")
public interface ATMClient {
	
	@GetMapping("/localizador/jsonP/json5.json")
	public String getAll();
}
