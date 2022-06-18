package com.eng.taxonhub.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eng.taxonhub.dto.ListOcurrenceDto;
import com.eng.taxonhub.dto.ListTaxonomicaDto;
import com.eng.taxonhub.dto.OcurrenceDto;
import com.eng.taxonhub.dto.TaxonomicaDto;
import com.eng.taxonhub.repository.TheWorldFloraInformationRepository;
import com.eng.taxonhub.service.OcurrenceService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxonhubOccurrenceServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OcurrenceService ocurrenceService;

	@Autowired
	TheWorldFloraInformationRepository worldFloraInformationRepository;

	

	@Test
	@Order(1)
	public void buscaOccurrenceComUmNomeValido() throws Exception {
		File file = new File("./files/Lista-teste-um-nome-valido.csv");
		FileInputStream input = new FileInputStream(file);
		MockMultipartFile file2 = new MockMultipartFile("file", file.getName(), MediaType.TEXT_PLAIN_VALUE,
				IOUtils.toByteArray(input));
		input.close();
		
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/gbif/").file(file2))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.resultados").exists())
		.andReturn();
		
		String responseBody = result.getResponse().getContentAsString();
		Gson gson = new GsonBuilder()
			    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			    .create();
		ListOcurrenceDto responseDto
                = gson.fromJson(responseBody, ListOcurrenceDto.class);
        OcurrenceDto dto = responseDto.getResultados().get(0);
        OcurrenceDto dto1 = responseDto.getResultados().get(1);
        OcurrenceDto dtoEsperado = OcurrenceDto.builder()
        		.nomeAceito("Echinodorus cylindricus Rataj")
        		.baseDeDados("Global Biodiversity Information Facility")
        		.familiaRespectiva("Alismataceae")
        		.nomePesquisado("Aquarius cylindricus")
        		.nomesRetornados("Echinodorus cylindricus Rataj")
        		.pais("Brazil")
        		.dia("28")
        		.ano("2011")
        		.mes("6")
        		.latitude("-19.485")
        		.longitude("-57.026667")
        		.build();
        OcurrenceDto dtoEsperado1 = OcurrenceDto.builder()
        		.nomeAceito("Echinodorus cylindricus Rataj")
        		.baseDeDados("Global Biodiversity Information Facility")
        		.familiaRespectiva("Alismataceae")
        		.nomePesquisado("Aquarius cylindricus")
        		.nomesRetornados("Echinodorus cylindricus Rataj")
        		.pais("Brazil")
        		.dia("16")
        		.ano("2005")
        		.mes("11")
        		.latitude("-16.481111")
        		.longitude("-57.987778")
        		.build();
        Assertions.assertEquals(dto, dtoEsperado);
        Assertions.assertEquals(dto1, dtoEsperado1);
		
	}
	
	@Test
	@Order(3)
	public void buscaOccurrenceComUmNomeInvalido() throws Exception {
		File file = new File("./files/Lista-teste-invalido.csv");
		FileInputStream input = new FileInputStream(file);
		MockMultipartFile file2 = new MockMultipartFile("file", file.getName(), MediaType.TEXT_PLAIN_VALUE,
				IOUtils.toByteArray(input));
		input.close();
		
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/gbif/").file(file2))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultados").isEmpty());
		
	}
}
