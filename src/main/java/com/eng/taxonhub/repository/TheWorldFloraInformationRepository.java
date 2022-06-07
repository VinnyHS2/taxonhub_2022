package com.eng.taxonhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eng.taxonhub.model.TheWorldFloraInformation;


@Repository
public interface TheWorldFloraInformationRepository extends JpaRepository<TheWorldFloraInformation, UUID> {

}
