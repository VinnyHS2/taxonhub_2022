package com.eng.taxonhub.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcurrenceResultGbifDto {

		@JsonProperty("scientificName")
		public String scientificName;

		@JsonProperty("acceptedScientificName")
		public String acceptedScientificName;

		@JsonProperty("family")
		public String family;
		
		@JsonProperty("country")
		public String country;
		
		@JsonProperty("year")
		public String year;

		@JsonProperty("month")
		public String month;

		@JsonProperty("day")
		public String eventDate;
		
		@JsonProperty("decimalLatitude")
		public String decimalLatitude;

		@JsonProperty("decimalLongitude")
		public String decimalLongitude;
		
}
