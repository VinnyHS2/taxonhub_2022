package com.eng.taxonhub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eng.taxonhub.model.SpecieName;
import com.eng.taxonhub.model.TheWorldFloraDatabaseVersion;

@Repository
public interface SpecieNameRepository extends JpaRepository<SpecieName, UUID>{
	
	@Query(nativeQuery = true, value = "SELECT * "
			+ " FROM specie_name name "
			+ "	WHERE name.species_names = :name")
	Optional<SpecieName> findByName(@Param("name") String version);
}

