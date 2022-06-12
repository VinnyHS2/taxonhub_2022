package com.eng.taxonhub;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eng.taxonhub.model.CsvUpload;
import com.eng.taxonhub.model.SpecieName;
import com.eng.taxonhub.repository.CsvUploadRepository;
import com.eng.taxonhub.repository.SpecieNameRepository;
import com.eng.taxonhub.service.StorageService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan("com.eng")
class TaxonhubApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mvc;

	@Autowired
	SpecieNameRepository specieRepository;

	@Autowired
	CsvUploadRepository csvRepository;

	//@MockBean
	//private StorageService storageService;

	@Test
	public void uploadArquivoOk() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file", "hello.csv", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());

		mvc.perform(MockMvcRequestBuilders.multipart("/file/").file(file)).andExpect(status().isOk());

	}

	@Test
	public void uploadArquivoException() throws Exception {

		MockMultipartFile file2 = new MockMultipartFile("file2", "teste.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());

		System.out.println(file2);

		mvc.perform(MockMvcRequestBuilders.multipart("/file/").file(file2)).andExpect(status().isBadRequest());

	}

	@Test
	public void validateUploadCsvOk() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get("/file/Lista-teste-valido.csv")).andExpect(status().isOk());
	
		Reader reader = Files.newBufferedReader(Paths.get("./files/Lista-teste-valido.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		
		List<String[]> nomesBinominais = csvReader.readAll();
		for( String[] nomeBinominal : nomesBinominais) {
			Assertions.assertTrue(specieRepository.findByName(nomeBinominal[0]).isPresent());
			
		}
	}
	
	@Test
	public void validateUploadCsvException() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get("/file/Lista-teste-invalido.csv")).andExpect(status().isOk());
	
		Reader reader = Files.newBufferedReader(Paths.get("./files/Lista-teste-invalido.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		
		List<String[]> nomesBinominais = csvReader.readAll();
		for( String[] nomeBinominal : nomesBinominais) {
			Assertions.assertTrue(specieRepository.findByName(nomeBinominal[0]).isEmpty());
			
		}
	}
}