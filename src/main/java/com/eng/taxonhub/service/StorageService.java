package com.eng.taxonhub.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eng.taxonhub.config.StorageConfig;
import com.eng.taxonhub.exceptions.BadRequestException;
import com.eng.taxonhub.exceptions.NotFoundException;
import com.eng.taxonhub.model.CsvUpload;
import com.eng.taxonhub.model.SpecieName;
import com.eng.taxonhub.model.TheWorldFloraInformation;
import com.eng.taxonhub.repository.CsvUploadRepository;
import com.eng.taxonhub.repository.SpecieNameRepository;
import com.eng.taxonhub.repository.TheWorldFloraInformationRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class StorageService {

	@Autowired
	SpecieNameRepository specieRepository;
	
	@Autowired
	CsvUploadRepository csvRepository;
	
	@Autowired
	TheWorldFloraInformationRepository theWorldFloraInformationRepository;
	
	private final Path rootLocation;

	@Autowired
	public StorageService(StorageConfig properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	public void salvar(MultipartFile file) throws Exception {

		try {
			if (file.isEmpty()) {
				throw new BadRequestException("Arquivo vazio");
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new BadRequestException("Você não quer fazer isso");
			}
			if (!extensaoCsv(file)) {
				throw new BadRequestException("Arquivo não é csv");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new NotFoundException("Arquivo inacessivel");
		}
	}

	public boolean extensaoCsv(MultipartFile file) throws Exception {

		String extensao = file.getOriginalFilename().split("\\.")[1];
		
		if ("csv".equals(extensao)) {

			Tika tika = new Tika();
			String detectedType = tika.detect(file.getBytes());
			if ("text/plain".equals(detectedType)) {

				return true;

			}
		}

		return false;

	}
	
	public void validarCSV(String validateCSV) throws Exception {
		Reader reader = Files.newBufferedReader(Paths.get("./files/"+validateCSV));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		
		List<String[]> nomesBinominais = csvReader.readAll();
		List<SpecieName> species = new ArrayList<SpecieName>(); 
		for( String[] nomeBinominal : nomesBinominais) {
			String[] verificarNomes = nomeBinominal[0].split(" ");
			int size = verificarNomes.length;
			if(size == 2) {
				SpecieName especie = SpecieName.builder().speciesNames(nomeBinominal[0]).build();
				species.add(especie);
			}
		}
		specieRepository.saveAll(species);
		CsvUpload csv = CsvUpload.builder().speciesNames(species).build();
		csvRepository.save(csv);
	}
	
	public void downloadFile(URL url, String fileName) throws IOException {
		try (InputStream in = url.openStream();
				ReadableByteChannel rbc = Channels.newChannel(in);
				FileOutputStream fos = new FileOutputStream(fileName)) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
	}

	public void insertOnDataBase(String path) throws Exception {
		List<TheWorldFloraInformation> theWorldFloraInformations = new ArrayList<>();

		Scanner in = new Scanner(new FileReader(path));
		in.nextLine();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] dadosBruto = line.split("\t");
//			@formatter:off
			if(dadosBruto.length <= 1) {
				continue;
			}
		    TheWorldFloraInformation theWorldFloraInformation = TheWorldFloraInformation.builder()
		    		.taxonID(dadosBruto[0])
		    		.scientificName(dadosBruto[1])
					.localID(dadosBruto[2])
					.scientificName(dadosBruto.length >= 4? dadosBruto[3]: "")
					.taxonRank(dadosBruto.length >= 5? dadosBruto[4]: "")
					.parentNameUsageID(dadosBruto.length >= 6? dadosBruto[5]: "")
					.scientificNameAuthorship(dadosBruto.length >= 7? dadosBruto[6]: "")
					.family(dadosBruto.length >= 8? dadosBruto[7]: "")
					.subfamily(dadosBruto.length >= 9? dadosBruto[8]: "")
					.tribe(dadosBruto.length >= 10? dadosBruto[9]: "")
					.subtribe(dadosBruto.length >= 11? dadosBruto[10]: "")
					.genus(dadosBruto.length >= 12? dadosBruto[11]: "")
					.subgenus(dadosBruto.length >= 13? dadosBruto[12]: "")
					.specificEpithet(dadosBruto.length >= 14? dadosBruto[13]: "")
					.infraspecificEpithet(dadosBruto.length >= 15? dadosBruto[14]: "")
					.verbatimTaxonRank(dadosBruto.length >= 16? dadosBruto[15]: "")
					.nomenclaturalStatus(dadosBruto.length >= 17? dadosBruto[16]: "")
					.namePublishedIn(dadosBruto.length >= 18? dadosBruto[17]: "")
					.taxonomicStatus(dadosBruto.length >= 19? dadosBruto[18]: "")
					.acceptedNameUsageID(dadosBruto.length >= 20? dadosBruto[19]: "")
					.originalNameUsageID(dadosBruto.length >= 21? dadosBruto[20]: "")
					.nameAccordingToID(dadosBruto.length >= 22? dadosBruto[21]: "")
					.taxonRemarks(dadosBruto.length >= 23? dadosBruto[22]: "")
					.created(dadosBruto.length >= 24? dadosBruto[23]: "")
					.modified(dadosBruto.length >= 25? dadosBruto[24]: "")
					.references(dadosBruto.length >= 26? dadosBruto[25]: "")
					.source(dadosBruto.length >= 27? dadosBruto[26]: "")
					.majorGroup(dadosBruto.length >= 28? dadosBruto[27]: "")
					.tplId(dadosBruto.length >= 29? dadosBruto[28]: "")
		    		.build();
//			@formatter:on

				theWorldFloraInformations.add(theWorldFloraInformation);
		}
		theWorldFloraInformationRepository.saveAll(theWorldFloraInformations);

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
		}
		insertOnDataBase("files/tmpUpdateDatabaseFolder/classification.txt");
		Files.deleteIfExists(Paths.get("./files/tmpUpdateDatabase.zip"));
		File index = new File("files/tmpUpdateDatabaseFolder");
		String[]entries = index.list();
		for(String s: entries){
		    File currentFile = new File(index.getPath(),s);
		    currentFile.delete();
		}
		Files.deleteIfExists(Paths.get("./files/tmpUpdateDatabaseFolder"));
	}

	public Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) throws IOException {

		Path targetDirResolved = targetDir.resolve(zipEntry.getName());

		Path normalizePath = targetDirResolved.normalize();
		if (!normalizePath.startsWith(targetDir)) {
			throw new IOException("Bad zip entry: " + zipEntry.getName());
		}

		return normalizePath;
	}

}
