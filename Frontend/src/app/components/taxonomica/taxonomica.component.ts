import { Component, OnInit } from '@angular/core';
import { resultadoTaxonomica } from 'src/app/model/resultadoTaxonomica';

/*nomePesquisado
  nomeRetornados
  sinonimo
  baseDeDados
  familiaRespectiva*/

  // playerGroups = new Array<PlayerGroup>();

@Component({
  selector: 'app-taxonomica',
  templateUrl: './taxonomica.component.html',
  styleUrls: ['./taxonomica.component.scss']
})

export class TaxonomicaComponent implements OnInit {

  resultadoTaxonomica = new Array<resultadoTaxonomica>();
  teste = new resultadoTaxonomica();

  constructor() { 
  }


  headerTable = ["nomePesquisado", "nomeRetornados", "sinonimo", "baseDeDados", "familiaRespectiva"];

  // resultadoTaxonomica: resultadoTaxonomica[] = [ (nomePesquisado = "teste Pesquisado", 
  //   nomeRetornados: "teste Retornados", 
  //   sinonimo: "teste sinonimo", 
  //   baseDeDados: "teste base de dados", 
  //   familiaRespectiva: "teste familia respectiva"),
  // ];

  ngOnInit(): void {
    this.teste.nomePesquisado = "Teste Pesquisado";
    this.teste.nomeRetornados = "Teste Retornados";
    this.teste.sinonimo = "Teste sinonimo";
    this.teste.baseDeDados = "Teste base de dados";
    this.teste.familiaRespectiva = "Teste familia respectiva";
    this.resultadoTaxonomica.push( this.teste, this.teste, this.teste, this.teste, this.teste);
    console.log(this.resultadoTaxonomica);
  }

}
