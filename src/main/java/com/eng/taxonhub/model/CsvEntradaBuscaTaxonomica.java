package com.eng.taxonhub.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class CsvEntradaBuscaTaxonomica implements Persistable<UUID> {
	
	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	@GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "species_names", updatable = true, nullable = true)
	private List<String> speciesNames;

	@Override
	public boolean isNew() {
		return id == null;
	}
}
