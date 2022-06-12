package com.eng.taxonhub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eng.taxonhub.model.TheWorldFloraDatabaseVersion;


@Repository
public interface TheWorldFloraDatabaseVersionRepository extends JpaRepository<TheWorldFloraDatabaseVersion, UUID> {

	@Query(nativeQuery = true, value = "SELECT * "
			+ " FROM the_world_flora_database_version version "
			+ "	WHERE version.database_version = :version ")
	Optional<TheWorldFloraDatabaseVersion> findByVersion(@Param("version") String version);
}
