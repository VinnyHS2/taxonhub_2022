package com.eng.taxonhub.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "the_world_flora_information")
public class TheWorldFloraInformation implements Persistable<UUID> {

	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	@GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "taxon_id",length=600)
	private String taxonID;

	@Column(name = "scientific_name_id",length=600)
	private String scientificNameID;

	@Column(name = "local_id",length=600)
	private String localID;

	@Column(name = "scientific_name",length=600)
	private String scientificName;

	@Column(name = "taxon_rank",length=600)
	private String taxonRank;

	@Column(name = "parent_name_usage_id",length=600)
	private String parentNameUsageID;

	@Column(name = "scientific_name_authorship",length=600)
	private String scientificNameAuthorship;

	@Column(name = "family",length=600)
	private String family;

	@Column(name = "subfamily",length=600)
	private String subfamily;

	@Column(name = "tribe",length=600)
	private String tribe;

	@Column(name = "subtribe",length=600)
	private String subtribe;

	@Column(name = "genus",length=600)
	private String genus;

	@Column(name = "subgenus",length=600)
	private String subgenus;

	@Column(name = "specific_epithet",length=600)
	private String specificEpithet;

	@Column(name = "infraspecific_epithet",length=600)
	private String infraspecificEpithet;

	@Column(name = "verbatim_taxon_rank",length=600)
	private String verbatimTaxonRank;
	
	@Column(name = "nomenclatural_status",length=600)
	private String nomenclaturalStatus;
	
	@Column(name = "name_published_in",length=600)
	private String namePublishedIn;
	
	@Column(name = "taxonomic_status",length=600)
	private String taxonomicStatus;
	
	@Column(name = "accepted_name_usage_id",length=600)
	private String acceptedNameUsageID;
	
	@Column(name = "original_name_usage_id",length=600)
	private String originalNameUsageID;
	
	@Column(name = "name_according_to_id",length=600)
	private String nameAccordingToID;
	
	@Column(name = "taxon_remarks",length=600)
	private String taxonRemarks;
	
	@Column(name = "created",length=600)
	private String created;
	
	@Column(name = "modified",length=600)
	private String modified;
	
	@Column(name = "`references`",length=600)
	private String references;
	
	@Column(name = "source",length=600)
	private String source;
	
	@Column(name = "major_group",length=600)
	private String majorGroup;
	
	@Column(name = "tpl_id",length=600)
	private String tplId;

	@Override
	public boolean isNew() {
		return id == null;
	}
}
