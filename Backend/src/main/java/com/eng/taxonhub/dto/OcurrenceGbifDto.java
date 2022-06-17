package com.eng.taxonhub.dto;

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
public class OcurrenceGbifDto {

	@JsonProperty("offset")
	public String offset;

	@JsonProperty("limit")
	public String limit;

	@JsonProperty("endOfRecords")
	public String endOfRecords;

	@JsonProperty("count")
	public String count;
	
    @JsonProperty("results")
    List<OcurrenceResultGbifDto> results;

}