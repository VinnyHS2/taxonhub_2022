package com.eng.taxonhub.dto;

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
public class TaxonomicaDto {

        @JsonProperty("nome_pesquisado")
        public String nomePesquisado;
        
        @JsonProperty("nomes_retornados")
        public String nomesRetornados;
        
        @JsonProperty("sinonimo")
        public String sinonimo;
        
        @JsonProperty("base_de_dados")
        public String baseDeDados;
        
        @JsonProperty("familia_respectiva")
        public String familiaRespectiva;

        @JsonProperty("autor")
        public String autor;

}