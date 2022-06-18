import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { resultadoTaxonomica } from 'src/app/model/resultadoTaxonomica';
import { ExportCsvService } from 'src/app/service/export-csv.service';
import { NotificationService } from 'src/app/service/notificacation.service';
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
  resultadoTaxonomica: MatTableDataSource<resultadoTaxonomica>;
  file: File | any;
  step = 1;
  message = '';
  loading = false;
  lenght = 0;
  confirmation = false;
  confirmation2 = false;
  pageSize: number = 10;


  @ViewChild('fileUpload') fileUpload: any;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(
    private taxonomicaService: TaxonomicaService,
    private cdr: ChangeDetectorRef,
    private exportCsvService: ExportCsvService,
    private notificationService: NotificationService
    ) {}

  headerTable = [
    'nome_pesquisado',
    'nomes_retornados',
    'sinonimo',
    'base_de_dados',
    'familia_respectiva',
  ];

  ngOnInit(): void {

    // this.resultadoTaxonomica.data.push(this.teste);

    console.log(this.resultadoTaxonomica)
    if (this.lenght > 0) {
      this.confirmation = true;
      this.confirmation2 = false;
    } else {
      this.confirmation = false;
      this.confirmation2 = true;
    }

    this.cdr.detectChanges();
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
  
  scroll() {
    window.scrollTo(0, 0);
  }
  
  onUpload() {
    this.loading = true;
    this.message = 'Enviando Arquivo!';
    this.step = 2;
    this.taxonomicaService.uploadFile(this.file).subscribe(
      (res: any) => {
        this.lenght = res.resultados.length
        this.resultadoTaxonomica = new MatTableDataSource<resultadoTaxonomica>(res.resultados);
        this.resultadoTaxonomica.paginator = this.paginator;
        console.log(this.resultadoTaxonomica);
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
          
        }, 2000);
      }
      );
    }
    
    close() {
      this.step = 1;
      this.loading = false;
      this.message = '';
      if (this.lenght > 0) {
        this.confirmation = true;
        this.confirmation2 = false;
      } else {
        this.confirmation = false;
      this.confirmation2 = true;
    }
  }

  downloadCsv(){

    if(this.resultadoTaxonomica.filteredData && this.resultadoTaxonomica.filteredData.length > 0){
      this.exportCsvService.downloadFile(this.resultadoTaxonomica.filteredData);
    }else{
      this.notificationService.showError(
        ' Erro ao exportar arquivo.'
      );
    }
  }

}
