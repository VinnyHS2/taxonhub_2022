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

import com.eng.taxonhub.service.StorageService;

@Validated
@RestController
@RequestMapping(path = { "/file" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadFileController {
	
	@Autowired
	StorageService storageService;

	@PostMapping("/")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

		storageService.store(file);
		
		return ResponseEntity.ok().build();
	}
	
}
