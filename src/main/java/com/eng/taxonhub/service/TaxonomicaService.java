package com.eng.taxonhub.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
			UpdateDatabase(urlDatabase, version);
		}
		VersionDto response = VersionDto.builder().version(version).build();
		return response;
	}
	
	public static void downloadFile(URL url, String fileName) throws IOException {
		try (InputStream in = url.openStream();
				ReadableByteChannel rbc = Channels.newChannel(in);
				FileOutputStream fos = new FileOutputStream(fileName)) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public void UpdateDatabase(URL url, String version) throws Exception {
		downloadFile(url, "./files/tmpUpdateDatabase.zip");
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream("./files/tmpUpdateDatabase.zip"))) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				boolean isDirectory = false;
				if (zipEntry.getName().endsWith(File.separator)) {
					isDirectory = true;
				}
				Path newPath = zipSlipProtect(zipEntry, Paths.get("files/tmpUpdateDatabaseFolder"));
				if (isDirectory) {
					Files.createDirectories(newPath);
				} else {
					if (newPath.getParent() != null) {
						if (Files.notExists(newPath.getParent())) {
							Files.createDirectories(newPath.getParent());
						}
					}
					Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			TheWorldFloraDatabaseVersion newVersion = TheWorldFloraDatabaseVersion.builder().databaseVersion(version).build();
			databaseVersionRepository.save(newVersion);
		}
		Files.deleteIfExists(Paths.get("./files/tmpUpdateDatabase.zip"));
	}

	public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) throws IOException {

		Path targetDirResolved = targetDir.resolve(zipEntry.getName());

		Path normalizePath = targetDirResolved.normalize();
		if (!normalizePath.startsWith(targetDir)) {
			throw new IOException("Bad zip entry: " + zipEntry.getName());
		}

		return normalizePath;
	}
}
