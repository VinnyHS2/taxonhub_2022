package com.eng.taxonhub.service;

import java.net.URL;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eng.taxonhub.dto.VersionDto;
import com.eng.taxonhub.model.TheWorldFloraDatabaseVersion;
import com.eng.taxonhub.repository.TheWorldFloraDatabaseVersionRepository;

@Service
public class TaxonomicaService {

	@Autowired
	TheWorldFloraDatabaseVersionRepository databaseVersionRepository;

	@Autowired
	StorageService	storageService;

	@Transactional(rollbackFor = Throwable.class)
	public VersionDto VerifyVersion() throws Exception {
		String url = "http://www.worldfloraonline.org/downloadData";
		Document doc = Jsoup.connect(url).get();

		Element element = doc.getElementsByTag("tr").first();
		String version = element.childNodes().get(3).childNodes().get(0).toString();
		Optional<TheWorldFloraDatabaseVersion> localVersion = databaseVersionRepository.findByVersion(version);
		if (localVersion.isEmpty()) {
			String urlDownload = element.childNodes().get(1).childNodes().get(0).attr("abs:href");
			URL urlDatabase = new URL(urlDownload);
			this.storageService.UpdateDatabase(urlDatabase, version);
			TheWorldFloraDatabaseVersion newVersion = TheWorldFloraDatabaseVersion.builder().databaseVersion(version)
					.build();
			databaseVersionRepository.save(newVersion);
		}
		VersionDto response = VersionDto.builder().version(version).build();
		return response;
	}

	
}
