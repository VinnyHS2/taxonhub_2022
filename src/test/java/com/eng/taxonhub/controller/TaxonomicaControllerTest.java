package com.eng.taxonhub.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eng.taxonhub.model.TheWorldFloraDatabaseVersion;
import com.eng.taxonhub.repository.TheWorldFloraDatabaseVersionRepository;
import com.eng.taxonhub.service.TaxonomicaService;

@SpringBootTest
@AutoConfigureMockMvc
//@DataJpaTest
public class TaxonomicaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	TaxonomicaController taxonomicaController;
	
//	@MockBean
//	private TaxonomicaService taxonomicaService;


	@MockBean
	TheWorldFloraDatabaseVersionRepository databaseVersionRepository;

	@Test
	public void verifyVersionWithVersionUpdated() throws Exception {
		String url = "http://www.worldfloraonline.org/downloadData";
		Document doc = Jsoup.connect(url).get();
		Element element = doc.getElementsByTag("tr").first();
		String version = element.childNodes().get(3).childNodes().get(0).toString();
		Optional<TheWorldFloraDatabaseVersion> mockVersion = Optional.of(TheWorldFloraDatabaseVersion.builder().databaseVersion(version).build());
		when(databaseVersionRepository.findByVersion(version)).thenReturn(mockVersion);
		when(databaseVersionRepository.save(mockVersion.get())).thenReturn(mockVersion.get());
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/the-world-flora/version")).andExpect(status().isOk())
		.andExpect(jsonPath("$.version").value(version));
		Assertions.assertTrue(Files.exists(Paths.get("files/tmpUpdateDatabaseFolder")));
	}

}
