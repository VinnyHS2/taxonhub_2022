package com.eng.taxonhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eng.taxonhub.service.TaxonomicaService;


@Validated
@RestController
@RequestMapping(path = { "/the-world-flora" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class TaxonomicaController {
	
	@Autowired
	TaxonomicaService taxonomicaService;
	
	@GetMapping("/version")
	public ResponseEntity<String> verifyVersion() throws Exception {
		return ResponseEntity.ok(taxonomicaService.VerifyVersion());
	}
	
}
