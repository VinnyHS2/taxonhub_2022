package com.eng.taxonhub.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jsoup.select.Evaluator.IsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.eng.taxonhub.config.StorageConfig;
import com.eng.taxonhub.dto.GbifSpeciesDto;
import com.eng.taxonhub.dto.ListOcurrenceDto;
import com.eng.taxonhub.dto.OcurrenceDto;
import com.eng.taxonhub.dto.OcurrenceGbifDto;
import com.eng.taxonhub.dto.OcurrenceResultGbifDto;
import com.eng.taxonhub.dto.PathDto;
import com.eng.taxonhub.model.CsvUpload;
import com.eng.taxonhub.repository.CsvUploadRepository;
import com.eng.taxonhub.repository.SpecieNameRepository;
import com.eng.taxonhub.repository.TheWorldFloraInformationRepository;

import reactor.core.publisher.Mono;

@Service
public class OcurrenceService {

	@Autowired
	SpecieNameRepository specieRepository;

	@Autowired
	CsvUploadRepository csvRepository;

	@Autowired
	TheWorldFloraInformationRepository theWorldFloraInformationRepository;

	@Autowired
	StorageService storageService;

	private final Path rootLocation;

	@Autowired
	public OcurrenceService(StorageConfig properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	@Autowired
	@Qualifier("webClientGbif")
	private WebClient webClientGbif;
	
	public GbifSpeciesDto getKeyGbif(String scientificName){
		Mono<GbifSpeciesDto> keySpecieDto = this.webClientGbif.get()
				.uri(uriBuilder -> uriBuilder.path("species/match")
						.queryParam("name", scientificName ).build())
				.retrieve().bodyToMono(GbifSpeciesDto.class);
		
		return keySpecieDto.block();
		
	}
	
	public OcurrenceGbifDto getOcurrenceGbif(String key){
		Mono<OcurrenceGbifDto> ocurrenceGbifDto = this.webClientGbif.get()
				.uri(uriBuilder -> uriBuilder.path("occurrence/search")
						.queryParam("taxonKey", key)
						.queryParam("limit", 300)
						.build())
				.retrieve().bodyToMono(OcurrenceGbifDto.class);
		
		return ocurrenceGbifDto.block();
		
	}
	
	public OcurrenceGbifDto filtroDadosGbif(OcurrenceGbifDto dto){
		
		dto.setResults(dto.getResults().stream()
			.filter(occurrence -> occurrence.getDecimalLatitude() != null)
			.filter(occurrence -> occurrence.getDecimalLongitude() != null)
			.filter(occurrence -> occurrence.getDecimalLatitude() != "0" && occurrence.getDecimalLongitude() != "0")
			.collect(Collectors.toList()));			
		return dto;
	}

	
	
	public ListOcurrenceDto buscaOcorrencia(MultipartFile file) throws Exception {
		this.storageService.salvar(file);
//		@formatter:off
		PathDto pathDto = PathDto.builder()
				.path(this.rootLocation.resolve(
						Paths.get(file.getOriginalFilename()))
						.normalize()
						.toAbsolutePath()
						.toString())
				.build();
//		@formatter:on
		CsvUpload csvFiltrado = this.storageService.validarCSV(pathDto);

		List<OcurrenceDto> dto = new ArrayList<OcurrenceDto>();
		csvFiltrado.getSpeciesNames().forEach(nome -> {
			GbifSpeciesDto speciesGbif = this.getKeyGbif(nome.getSpeciesNames());
			OcurrenceGbifDto ocurrenceGbif = this.getOcurrenceGbif(speciesGbif.getAcceptedUsageKey());
			OcurrenceGbifDto filtroDadosGbif = this.filtroDadosGbif(ocurrenceGbif);
			
			filtroDadosGbif.getResults().forEach(results -> {
        
//		@formatter:off				
				OcurrenceDto ocurrence = OcurrenceDto.builder()
						.nomePesquisado(nome.getSpeciesNames())
						.nomesRetornados(results.getScientificName())
						.nomeAceito(results.getAcceptedScientificName())
						.baseDeDados("Global Biodiversity Information Facility")
						.familiaRespectiva(results.getFamily())
						.pais(results.getCountry())
						.ano(results.getYear())
						.mes(results.getMonth())
						.dia(results.getEventDate())
						.latitude(results.getDecimalLatitude())
						.longitude(results.getDecimalLongitude())
						.build();
//		@formatter:on
				dto.add(ocurrence);
			});
		});
		
		ListOcurrenceDto response = ListOcurrenceDto.builder()
				.resultados(dto).build();
		
		return response;
		
	}

}