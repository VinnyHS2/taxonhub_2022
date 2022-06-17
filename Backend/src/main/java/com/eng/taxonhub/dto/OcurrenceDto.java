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
public class OcurrenceDto {

        @JsonProperty("nome_pesquisado")
        public String nomePesquisado;
        
        @JsonProperty("nomes_retornados")
        public String nomesRetornados;
        
        @JsonProperty("nome_aceito")
        public String nomeAceito;
        
        @JsonProperty("base_de_dados")
        public String baseDeDados;
        
        @JsonProperty("familia_respectiva")
        public String familiaRespectiva;

        @JsonProperty("pais")
        public String pais;

        @JsonProperty("ano")
        public String ano;
        
        @JsonProperty("mes")
        public String mes;
        
        @JsonProperty("dia")
        public String dia;
        
        @JsonProperty("latitude")
        public String latitude;
        
        @JsonProperty("longitude")
        public String longitude;
        

}