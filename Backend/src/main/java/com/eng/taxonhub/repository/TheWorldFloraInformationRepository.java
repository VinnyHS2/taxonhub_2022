package com.eng.taxonhub.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eng.taxonhub.model.TheWorldFloraInformation;


@Repository
public interface TheWorldFloraInformationRepository extends JpaRepository<TheWorldFloraInformation, UUID> {
	
	@Query(nativeQuery = true, value = "SELECT * "
			+ " FROM the_world_flora_information wfo "
			+ "	WHERE wfo.taxon_id = :taxonId")
	Optional<TheWorldFloraInformation> findByTaxonID(@Param("taxonId") String taxonId);
	
	@Query(nativeQuery = true, value = "SELECT * "
			+ " FROM the_world_flora_information wfo "
			+ "	WHERE wfo.genus = :genus "
			+ " AND wfo.specific_epithet = :specific_epithet ")
	List<TheWorldFloraInformation> findBySpecieName(@Param("genus") String genus, @Param("specific_epithet") String specificEpithet);
	
	@Query(nativeQuery = true, value = "SELECT * "
			+ " FROM the_world_flora_information wfo "
			+ " WHERE wfo.scientific_name LIKE %:name% "
			+ " AND wfo.scientific_name_authorship != '' "
			+ " AND wfo.scientific_name != '' "
			+ " AND wfo.family != '' "
			+ " AND wfo.taxonomic_status != '' ")
	List<TheWorldFloraInformation> findByScientificName(@Param("name") String name);
	
	@Query(nativeQuery = true, value = "SELECT * "
			+ " FROM the_world_flora_information wfo "
			+ " WHERE wfo.accepted_name_usage_id = :taxonId "
			+ " AND wfo.scientific_name_authorship != '' "
			+ " AND wfo.scientific_name != '' "
			+ " AND wfo.family != '' "
			+ " AND wfo.taxonomic_status != '' ")
	List<TheWorldFloraInformation> findByAcceptedNameUsageId(@Param("taxonId") String taxonId);
	
	
	
}
