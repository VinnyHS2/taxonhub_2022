package com.eng.taxonhub.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "the_world_flora_information")
public class TheWorldFloraInformation implements Persistable<UUID> {

	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	@GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "taxon_id")
	private String taxonID;

	@Column(name = "scientific_name_id")
	private String scientificNameID;

	@Column(name = "local_id")
	private String localID;

	@Column(name = "scientific_name")
	private String scientificName;

	@Column(name = "taxon_rank")
	private String taxonRank;

	@Column(name = "parent_name_usage_id")
	private String parentNameUsageID;

	@Column(name = "scientific_name_authorship")
	private String scientificNameAuthorship;

	@Column(name = "family")
	private String family;

	@Column(name = "subfamily")
	private String subfamily;

	@Column(name = "tribe")
	private String tribe;

	@Column(name = "subtribe")
	private String subtribe;

	@Column(name = "genus")
	private String genus;

	@Column(name = "subgenus")
	private String subgenus;

	@Column(name = "specific_epithet")
	private String specificEpithet;

	@Column(name = "infraspecific_epithet")
	private String infraspecificEpithet;

	@Column(name = "verbatim_taxon_rank")
	private String verbatimTaxonRank;
	
	@Column(name = "nomenclatural_status")
	private String nomenclaturalStatus;
	
	@Column(name = "name_published_in")
	private String namePublishedIn;
	
	@Column(name = "taxonomic_status")
	private String taxonomicStatus;
	
	@Column(name = "accepted_name_usage_id")
	private String acceptedNameUsageID;
	
	@Column(name = "original_name_usage_id")
	private String originalNameUsageID;
	
	@Column(name = "name_according_to_id")
	private String nameAccordingToID;
	
	@Column(name = "taxon_remarks")
	private String taxonRemarks;
	
	@Column(name = "created")
	private String created;
	
	@Column(name = "modified")
	private String modified;
	
	@Column(name = "`references`")
	private String references;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "major_group")
	private String majorGroup;
	
	@Column(name = "tpl_id")
	private String tplId;

	@Override
	public boolean isNew() {
		return id == null;
	}
}
