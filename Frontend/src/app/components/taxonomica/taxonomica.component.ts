import { Component, OnInit, ViewChild } from '@angular/core';
import { resultadoTaxonomica } from 'src/app/model/resultadoTaxonomica';
import { TaxonomicaService } from 'src/app/service/taxonomica.service';

/*nomePesquisado
  nomeRetornados
  sinonimo
  baseDeDados
  familiaRespectiva*/

@Component({
  selector: 'app-taxonomica',
  templateUrl: './taxonomica.component.html',
  styleUrls: ['./taxonomica.component.scss'],
})
export class TaxonomicaComponent implements OnInit {
  resultadoTaxonomica = new Array<resultadoTaxonomica>();
  file: File | any;
  step = 1;
  message = '';
  loading = false;
  complete = false;
  confirmation = false;
  confirmation2 = false;

  @ViewChild('fileUpload') fileUpload: any;

  constructor(private taxonomicaService: TaxonomicaService) {}

  headerTable = [
    'nomePesquisado',
    'nomeRetornados',
    'sinonimo',
    'baseDeDados',
    'familiaRespectiva',
  ];

  ngOnInit(): void {
    console.log(this.resultadoTaxonomica.length)
    if (this.resultadoTaxonomica.length > 0) {
      this.confirmation = true;
      this.confirmation2 = false;
    } else {
      this.confirmation = false;
      this.confirmation2 = true;
    }
  }

  changeListener(files: any) {
    console.log(files);
    files = files.target.files;
    if (files && files.length > 0) {
      this.file = files.item(0);
      console.log(this.file.name);
    }
  }

  onFileUploadClick() {
    this.fileUpload.nativeElement.value = '';
    
  }

  onUpload() {
    this.loading = true;
    this.message = 'Enviando Arquivo!';
    this.step = 2;
    this.taxonomicaService.uploadFile(this.file).subscribe(
      (res: any) => {
        console.log(res);
      },
      (err: any) => {
        setTimeout(() => {
          console.log(err);
          this.message = 'Erro ao enviar arquivo!';
          this.loading = false;
        }, 2000);
      },
      () => {
        setTimeout(() => {
          this.message = "Arquivo enviado com sucesso!";
          this.loading = false;
          this.complete = true;

        }, 2000);
      }
    );
  }

  close() {
    this.step = 1;
    this.loading = false;
    this.complete = false;
    this.message = '';
  }
}
