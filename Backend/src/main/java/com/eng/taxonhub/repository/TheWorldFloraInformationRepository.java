package com.eng.taxonhub.repository;

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
}