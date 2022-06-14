import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OcorrenciaComponent } from './components/ocorrencia/ocorrencia.component';
import { TaxonomicaComponent } from './components/taxonomica/taxonomica.component';
import { HomeComponent } from './components/home/home.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: '', component: MainLayoutComponent, children: [
    { path: 'taxonomica', component: TaxonomicaComponent},
    { path: 'ocorrencia', component: OcorrenciaComponent},
  
]}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
