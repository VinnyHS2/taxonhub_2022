package com.eng.taxonhub.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

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

import com.eng.taxonhub.dto.ListTaxonomicaDto;
import com.eng.taxonhub.dto.TaxonomicaDto;
import com.eng.taxonhub.model.TheWorldFloraInformation;
import com.eng.taxonhub.repository.TheWorldFloraInformationRepository;
import com.eng.taxonhub.service.StorageService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxonhubStorageServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StorageService storageService;

	@Autowired
	TheWorldFloraInformationRepository worldFloraInformationRepository;

	@Test
	@Order(2)
	public void insertOnDataBase() throws Exception {
		this.storageService.insertOnDataBase("files/fileTestInsert.txt");
		Optional<TheWorldFloraInformation> newData = worldFloraInformationRepository.findByTaxonID("pato");
		Assertions.assertTrue(newData.isPresent());
		Assertions.assertTrue(newData.get().getScientificNameID().equals("urn:lsid:ipni.org:names:195146-1"));
		Assertions.assertTrue(newData.get().getLocalID().equals("GCC-FA54B065-8C1D-48CC-8CE0-000012FB41F0"));
		Assertions.assertTrue(newData.get().getScientificName().equals("cirsium caput-medusae"));
		Assertions.assertTrue(newData.get().getTaxonRank().equals("SPECIES"));
		Assertions.assertTrue(newData.get().getParentNameUsageID().equals(""));
		Assertions.assertTrue(newData.get().getScientificNameAuthorship().equals("Schur ex Nyman"));
		Assertions.assertTrue(newData.get().getFamily().equals("asteraceae"));
		Assertions.assertTrue(newData.get().getSubfamily().equals(""));
		Assertions.assertTrue(newData.get().getTribe().equals(""));
		Assertions.assertTrue(newData.get().getSubtribe().equals(""));
		Assertions.assertTrue(newData.get().getGenus().equals("cirsium"));
		Assertions.assertTrue(newData.get().getSubgenus().equals(""));
		Assertions.assertTrue(newData.get().getSpecificEpithet().equals("caput-medusae"));
		Assertions.assertTrue(newData.get().getInfraspecificEpithet().equals(""));
		Assertions.assertTrue(newData.get().getVerbatimTaxonRank().equals(""));
		Assertions.assertTrue(newData.get().getNomenclaturalStatus().equals(""));
		Assertions.assertTrue(newData.get().getNamePublishedIn().equals("Consp. Fl. Eur. 2: 408 (1879)"));
		Assertions.assertTrue(newData.get().getTaxonomicStatus().equals("SYNONYM"));
		Assertions.assertTrue(newData.get().getAcceptedNameUsageID().equals("wfo-0000027702"));
		Assertions.assertTrue(newData.get().getOriginalNameUsageID().equals(""));
		Assertions.assertTrue(newData.get().getNameAccordingToID().equals(""));
		Assertions.assertTrue(newData.get().getCreated().equals("2012-02-11"));
		Assertions.assertTrue(newData.get().getModified().equals(""));
		Assertions.assertTrue(newData.get().getSource().equals("gcc"));
		Assertions.assertTrue(newData.get().getMajorGroup().equals("A"));
		Assertions.assertTrue(newData.get().getTplId().equals("http://www.theplantlist.org/tpl1.1/record/gcc-1"));
		Assertions.assertTrue(newData.get().getReferences().equals("http://www.theplantlist.org/tpl1.1/record/gcc-1"));

		worldFloraInformationRepository.delete(newData.get());
	}

	@Test
	@Order(1)
	public void buscaTaxonomicaComUmNomeValido() throws Exception {
		File file = new File("./files/Lista-teste-um-nome-valido.csv");
		FileInputStream input = new FileInputStream(file);
		MockMultipartFile file2 = new MockMultipartFile("file", file.getName(), MediaType.TEXT_PLAIN_VALUE,
				IOUtils.toByteArray(input));
		input.close();
		
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/file/taxonomica").file(file2))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.resultados").exists())
		.andReturn();
		
		String responseBody = result.getResponse().getContentAsString();
		Gson gson = new GsonBuilder()
			    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			    .create();
        ListTaxonomicaDto responseDto
                = gson.fromJson(responseBody, ListTaxonomicaDto.class);
        TaxonomicaDto dto = responseDto.getResultados().get(0);
        TaxonomicaDto dto1 = responseDto.getResultados().get(1);
        TaxonomicaDto dtoEsperado = TaxonomicaDto.builder()
        		.autor("(Rataj) Christenh. & Byng")
        		.baseDeDados("The World Flora Online")
        		.familiaRespectiva("alismataceae")
        		.nomePesquisado("Echinodorus cylindricus")
        		.nomesRetornados("aquarius cylindricus")
        		.sinonimo("ACCEPTED")
        		.build();
        TaxonomicaDto dtoEsperado1 = TaxonomicaDto.builder()
        		.autor("Rataj")
        		.baseDeDados("The World Flora Online")
        		.familiaRespectiva("alismataceae")
        		.nomePesquisado("Echinodorus cylindricus")
        		.nomesRetornados("echinodorus cylindricus")
        		.sinonimo("SYNONYM")
        		.build();
        Assertions.assertEquals(dto, dtoEsperado);
        Assertions.assertEquals(dto1, dtoEsperado1);
		
	}
	
	@Test
	@Order(3)
	public void buscaTaxonomicaComUmNomeInvalido() throws Exception {
		File file = new File("./files/Lista-teste-invalido.csv");
		FileInputStream input = new FileInputStream(file);
		MockMultipartFile file2 = new MockMultipartFile("file", file.getName(), MediaType.TEXT_PLAIN_VALUE,
				IOUtils.toByteArray(input));
		input.close();
		
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/file/taxonomica").file(file2))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultados").isEmpty());
		
	}
}
