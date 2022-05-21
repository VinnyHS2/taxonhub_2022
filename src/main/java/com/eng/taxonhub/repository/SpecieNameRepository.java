package com.eng.taxonhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eng.taxonhub.model.SpecieName;

@Repository
public interface SpecieNameRepository extends JpaRepository<SpecieName, UUID>{
	
}
