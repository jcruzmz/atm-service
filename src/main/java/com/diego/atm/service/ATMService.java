package com.diego.atm.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.atm.dto.ATMdto;
import com.diego.atm.feignclient.ATMClient;
import com.diego.atm.util.MessageConvert;

@Service
public class ATMService {

	@Autowired
	private ATMClient atmClient;

	@Autowired
	private MessageConvert messageUtil;

	@SuppressWarnings("unchecked")
	public List<List<String>> getAtmNear(ATMdto atm) {
		JSONParser parser = new JSONParser();
		List<List<String>> response = new ArrayList<List<String>>();
		try {
			JSONObject json = (JSONObject) parser.parse(messageUtil.convert(atmClient.getAll()));
			JSONObject servicios = (JSONObject) json.get("Servicios");
			JSONObject body1 = null;
			JSONObject body2 = null;
			List<String> data = new ArrayList<String>();
			for (Object key1 : servicios.keySet()) {
				body1 = (JSONObject) servicios.get(key1);
				for (Object key2 : body1.keySet()) {
					body2 = (JSONObject) body1.get(key2);
					for (Object key3 : body2.keySet()) {
						data = (List<String>) body2.get(key3);
						double latitud = Double.parseDouble(data.get(16));
						double longitud = Double.parseDouble(data.get(15));
						if (distanciaCoord(atm.getLatitud(), atm.getLongitud(), latitud, longitud) <= 10) {
							response.add(data);
						}
					}
				}
			}
			return response;
		} catch (ParseException e) {
			return response;
		}
	}

	public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
		double radioTierra = 6371;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		double distancia = radioTierra * va2;
		return distancia;
	}
}
