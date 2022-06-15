package com.eng.taxonhub.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eng.taxonhub.dto.ListTaxonomicaDto;
import com.eng.taxonhub.dto.PathDto;
import com.eng.taxonhub.service.StorageService;

@Validated
@RestController
@RequestMapping(path = { "/file" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageController {
	
	private final StorageService storageService;

	@Autowired
	public StorageController(StorageService storageService) {
		this.storageService = storageService;
	}

	@PostMapping("/")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

		storageService.salvar(file);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/validate")
	public ResponseEntity<?> validationCSV(@Valid @RequestBody PathDto dto) throws Exception{
		
		storageService.validarCSV(dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/taxonomica")
	public ResponseEntity<ListTaxonomicaDto> BuscaTaxonomica(@RequestParam("file") MultipartFile file) throws Exception{
				
		return ResponseEntity.ok(storageService.buscaTaxonomica(file));
	}
	
}
