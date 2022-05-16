package com.eng.taxonhub.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eng.taxonhub.config.StorageConfig;

@Service
public class StorageService {
	
	private final Path rootLocation;

	@Autowired
	public StorageService(StorageConfig properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	public void salvar(MultipartFile file) throws Exception {

		System.out.println(this.rootLocation);
		
		try {
			if (file.isEmpty()) {
				throw new Exception("1");
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new Exception("2");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new Exception("3");
		}
	}
	
}
