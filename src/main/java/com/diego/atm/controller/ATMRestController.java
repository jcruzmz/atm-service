package com.diego.atm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.atm.dto.ATMdto;
import com.diego.atm.service.ATMService;
import com.diego.atm.vo.Response;

@RestController
@RequestMapping("/atm")
public class ATMRestController {

	@Autowired
	private ATMService atmService;

	@GetMapping()
	public ResponseEntity<Response<List<List<String>>>> getAllAtm(@RequestBody ATMdto atm) {
		Response<List<List<String>>> response = new Response<>();
		List<List<String>> responseService = new ArrayList<>();
		try {
			responseService = atmService.getAtmNear(atm);
			if(!responseService.isEmpty()) {
				response.setData(responseService);
				response.setMessage("Consulta realizada correctamente");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setMessage("No se encontraron atm cercanos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMessage("Error del servidor");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
