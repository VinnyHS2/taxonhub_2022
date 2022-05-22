package com.eng.taxonhub.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eng.taxonhub.config.StorageConfig;
import com.eng.taxonhub.exceptions.BadRequestException;
import com.eng.taxonhub.exceptions.NotFoundException;
import com.eng.taxonhub.model.CsvUpload;
import com.eng.taxonhub.model.SpecieName;
import com.eng.taxonhub.repository.CsvUploadRepository;
import com.eng.taxonhub.repository.SpecieNameRepository;
import com.eng.taxonhub.repository.TheWorldFloraDatabaseVersionRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class StorageService {

	@Autowired
	SpecieNameRepository specieRepository;
	
	@Autowired
	CsvUploadRepository csvRepository;
	
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
	
	public void validarCSV(String validateCSV) throws Exception {
		Reader reader = Files.newBufferedReader(Paths.get("./files/"+validateCSV));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		
		List<String[]> nomesBinominais = csvReader.readAll();
		List<SpecieName> species = new ArrayList<SpecieName>(); 
		for( String[] nomeBinominal : nomesBinominais) {
			String[] verificarNomes = nomeBinominal[0].split(" ");
			int size = verificarNomes.length;
			if(size == 2) {
				SpecieName especie = SpecieName.builder().speciesNames(nomeBinominal[0]).build();
				species.add(especie);
			}
		}
		specieRepository.saveAll(species);
		CsvUpload csv = CsvUpload.builder().speciesNames(species).build();
		csvRepository.save(csv);
	}

}
