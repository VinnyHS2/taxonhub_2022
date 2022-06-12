package com.eng.taxonhub.controller;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.eng.taxonhub.model.TheWorldFloraInformation;
import com.eng.taxonhub.repository.TheWorldFloraInformationRepository;
import com.eng.taxonhub.service.StorageService;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxonhubStorageServiceTest {
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	TheWorldFloraInformationRepository worldFloraInformationRepository;
	
	@Test
	public void insertOnDataBase() throws Exception {
		this.storageService.insertOnDataBase("files/fileTestInsert.txt");
		Optional<TheWorldFloraInformation> newData =  worldFloraInformationRepository.findByTaxonID("pato");
		Assertions.assertTrue(newData.isPresent());
		Assertions.assertTrue(newData.get().getScientificNameID().equals("urn:lsid:ipni.org:names:195146-1"));
		Assertions.assertTrue(newData.get().getLocalID().equals("GCC-FA54B065-8C1D-48CC-8CE0-000012FB41F0"));
		Assertions.assertTrue(newData.get().getScientificName().equals("Cirsium caput-medusae"));
		Assertions.assertTrue(newData.get().getTaxonRank().equals("SPECIES"));
		Assertions.assertTrue(newData.get().getParentNameUsageID().equals(""));
		Assertions.assertTrue(newData.get().getScientificNameAuthorship().equals("Schur ex Nyman"));
		Assertions.assertTrue(newData.get().getFamily().equals("Asteraceae"));
		Assertions.assertTrue(newData.get().getSubfamily().equals(""));
		Assertions.assertTrue(newData.get().getTribe().equals(""));
		Assertions.assertTrue(newData.get().getSubtribe().equals(""));
		Assertions.assertTrue(newData.get().getGenus().equals("Cirsium"));
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
}
