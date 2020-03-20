package com.socio.torcedor.client;

import java.net.ConnectException;
import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.synth.SynthToggleButtonUI;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import com.socio.torcedor.domain.model.Campanha;

@Component
public class CampanhaClient {

	private static final String URL = "http://localhost:8080/campanhas/";

	private RestTemplate restTemplate;

	
	public List<Campanha> listarCampanhasPorClube(long id){
		try {
			
		
		restTemplate = new RestTemplate();
		
		URI resourceUri = URI.create(URL + id);
		
		Campanha [] campanhas = restTemplate.getForObject(resourceUri, Campanha[].class);
		
		

		return Arrays.asList(campanhas);
		
		} catch (Exception e) {
		 return null;
		}
		
	}
	
	

}
