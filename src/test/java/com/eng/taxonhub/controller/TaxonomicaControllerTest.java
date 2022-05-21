package com.eng.taxonhub.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eng.taxonhub.model.TheWorldFloraDatabaseVersion;
import com.eng.taxonhub.repository.TheWorldFloraDatabaseVersionRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxonomicaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	TheWorldFloraDatabaseVersionRepository databaseVersionRepository;

	@Test
	public void verificarVersao() throws Exception {
		String url = "http://www.worldfloraonline.org/downloadData";
		Document doc = Jsoup.connect(url).get();

		Element element = doc.getElementsByTag("tr").first();
		String version = element.childNodes().get(3).childNodes().get(0).toString();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/the-world-flora/version")).andExpect(status().isOk())
				.andExpect(jsonPath("$.version").value(version));
		Optional<TheWorldFloraDatabaseVersion> localVersion = databaseVersionRepository.findByVersion(version);
		Assertions.assertTrue(localVersion.isPresent());
		Assertions.assertTrue(Files.exists(Paths.get("files/tmpUpdateDatabaseFolder")));
	}

}
