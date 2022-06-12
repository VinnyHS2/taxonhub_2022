package com.eng.taxonhub.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class CsvUpload implements Persistable<UUID> {

	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	@GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "species_name_id", updatable = false, nullable = false)
	private List<SpecieName> speciesNames;
	

	@Override
	public boolean isNew() {
		return id == null;
	}
}