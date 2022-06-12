package com.eng.taxonhub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.ZipEntry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eng.taxonhub.model.TheWorldFloraDatabaseVersion;
import com.eng.taxonhub.repository.TheWorldFloraDatabaseVersionRepository;
import com.eng.taxonhub.service.StorageService;


@SpringBootTest
@AutoConfigureMockMvc
//@DataJpaTest
public class TaxonomicaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	TaxonomicaController taxonomicaController;
	
	@MockBean
	TheWorldFloraDatabaseVersionRepository databaseVersionRepository;
	
//	@InjectMocks
//	TaxonomicaService taxonomicaService;
	
	@MockBean
	StorageService storageService;



	@Test
	public void verifyVersionWithVersionUpdated() throws Exception {
		String url = "http://www.worldfloraonline.org/downloadData";
		Document doc = Jsoup.connect(url).get();
		Element element = doc.getElementsByTag("tr").first();
		String version = element.childNodes().get(3).childNodes().get(0).toString();
		Optional<TheWorldFloraDatabaseVersion> mockVersion = Optional.of(TheWorldFloraDatabaseVersion.builder().databaseVersion(version).build());
		when(databaseVersionRepository.save(mockVersion.get())).thenReturn(mockVersion.get());
		when(databaseVersionRepository.findByVersion(version)).thenReturn(mockVersion); 
		this.mockMvc.perform(MockMvcRequestBuilders.get("/the-world-flora/version")).andExpect(status().isOk())
				.andExpect(jsonPath("$.version").value(version));
	}
	
	@Test
	public void verifyVersionWithVersionOutdated() throws Exception {
		String url = "http://www.worldfloraonline.org/downloadData";
		Document doc = Jsoup.connect(url).get();
		Element element = doc.getElementsByTag("tr").first();
		String version = element.childNodes().get(3).childNodes().get(0).toString();
		Optional<TheWorldFloraDatabaseVersion> mockVersion = Optional.of(TheWorldFloraDatabaseVersion.builder().databaseVersion(version).build());
		when(databaseVersionRepository.findByVersion(version)).thenReturn(Optional.empty());
		when(databaseVersionRepository.save(mockVersion.get())).thenReturn(mockVersion.get());
		doNothing().when(storageService).insertOnDataBase(any(String.class));
		doCallRealMethod().when(storageService).downloadFile(any(URL.class), any(String.class));
		doCallRealMethod().when(storageService).zipSlipProtect(any(ZipEntry.class), any(Path.class));
		doCallRealMethod().when(storageService).UpdateDatabase(any(URL.class), any(String.class));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/the-world-flora/version")).andExpect(status().isOk())
		.andExpect(jsonPath("$.version").value(version));
		Assertions.assertFalse(Files.exists(Paths.get("files/tmpUpdateDatabaseFolder")));
		Assertions.assertFalse(Files.exists(Paths.get("files/tmpUpdateDatabase.zip")));
	}
	

}
