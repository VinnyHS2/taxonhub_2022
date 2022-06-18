import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaxonomicaService {

  _baseUrl = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

  uploadFile(file: File): Observable<boolean> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.post<any>(`${this._baseUrl}/file/taxonomica`, formData);
  }

  versao() {
    return this.httpClient.get<any>(`${this._baseUrl}/the-world-flora/version`);
  }
}
