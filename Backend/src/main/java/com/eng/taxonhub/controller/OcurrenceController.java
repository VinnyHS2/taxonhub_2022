package com.eng.taxonhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eng.taxonhub.dto.ListOcurrenceDto;
import com.eng.taxonhub.service.OcurrenceService;

@Validated
@RestController
@RequestMapping(path = { "/gbif" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class OcurrenceController {
	
	@Autowired
	OcurrenceService ocurrenceService;

	@PostMapping("/")
	public ResponseEntity<ListOcurrenceDto> FileUpload(@RequestParam("file") MultipartFile file) throws Exception {

		
		return ResponseEntity.ok(this.ocurrenceService.buscaOcorrencia(file));
	}

}
