package com.eng.taxonhub;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.eng.taxonhub.service.StorageService;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan("com.eng")
class TaxonhubApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private StorageService storageService;

	
	@Test
	public void uploadArquivoOk() throws Exception {

		MockMultipartFile file 
	      = new MockMultipartFile(
	        "file", 
	        "hello.csv", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		
		mvc.perform(MockMvcRequestBuilders.multipart("/file/")
				.file(file))
				.andExpect(status().isOk());

	}

	@Test
	public void uploadArquivoException() throws Exception {
		
		MockMultipartFile file2
		= new MockMultipartFile(
				"file2", 
				"teste.txt", 
				MediaType.TEXT_PLAIN_VALUE, 
				"Hello, World!".getBytes()
				);
		
		System.out.println(file2);
		
		mvc.perform(MockMvcRequestBuilders.multipart("/file/")
				.file(file2))
		.andExpect(status().isBadRequest());
		
	}

}
