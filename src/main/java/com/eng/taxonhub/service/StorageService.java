package com.eng.taxonhub.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eng.taxonhub.config.StorageConfig;
import com.eng.taxonhub.exceptions.BadRequestException;
import com.eng.taxonhub.exceptions.NotFoundException;

@Service
public class StorageService {

	private final Path rootLocation;

	@Autowired
	public StorageService(StorageConfig properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	public void salvar(MultipartFile file) throws Exception {

		try {
			if (file.isEmpty()) {
				throw new BadRequestException("Arquivo vazio");
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new BadRequestException("Você não quer fazer isso");
			}
			if (!extensaoCsv(file)) {
				throw new BadRequestException("Arquivo não é csv");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new NotFoundException("Arquivo inacessivel");
		}
	}

	public boolean extensaoCsv(MultipartFile file) throws Exception {

		String extensao = file.getOriginalFilename().split("\\.")[1];
		
		if ("csv".equals(extensao)) {

			Tika tika = new Tika();
			String detectedType = tika.detect(file.getBytes());
			if ("text/plain".equals(detectedType)) {

				return true;

			}
		}

		return false;

	}

}
