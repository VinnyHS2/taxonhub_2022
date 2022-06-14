import { Component, OnInit } from '@angular/core';
import { resultadoOcorrencia } from 'src/app/model/resultadoOcorrencia';

@Component({
  selector: 'app-ocorrencia',
  templateUrl: './ocorrencia.component.html',
  styleUrls: ['./ocorrencia.component.scss']
})
export class OcorrenciaComponent implements OnInit {

  resultadoTaxonomica = new Array<resultadoOcorrencia>();
  teste = new resultadoOcorrencia();

  constructor() { 
  }


  headerTable = ["nomePesquisado", "nomeRetornados", "sinonimo", "baseDeDados", "familiaRespectiva", "pais", "ano", "mes", "dia", "latitude", "longitude", "coordenadaMunicipal"];


  ngOnInit(): void {
    this.teste.nomePesquisado = "Teste Pesquisado";
    this.teste.nomeRetornados = "Teste Retornados";
    this.teste.sinonimo = "Teste sinonimo";
    this.teste.baseDeDados = "Teste base de dados";
    this.teste.familiaRespectiva = "Teste familia respectiva";
    this.teste.pais = "Brasil";
    this.teste.ano = 2022;
    this.teste.mes = "oct";
    this.teste.dia = 5;
    this.teste.latitude = "0º0'0.0''";
    this.teste.longitude = "0º0'0.0''";
    this.teste.coordenadaMunicipal = "Teste familia respectiva";
    this.resultadoTaxonomica.push( this.teste, this.teste, this.teste, this.teste, this.teste);
    console.log(this.resultadoTaxonomica);
  }

}
