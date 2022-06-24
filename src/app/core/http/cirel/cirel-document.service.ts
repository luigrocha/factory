import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CirelDocument } from 'src/app/types/cirel.types';

@Injectable({
  providedIn: 'root'
})
export class CirelDocumentService {
  private readonly URL = environment.appApiUrl + '/cyrels/{id}/documents';

  constructor(private http: HttpClient) {
  }

  uploadCirelDocument(cyrelId: number, file: File): Observable<CirelDocument> {
    const body: FormData = new FormData();
    body.append('file', file);

    return this.http.post<CirelDocument>(this.URL.replace('{id}', cyrelId.toString()), body);
  }

  updateCirelDocument(documentId: number, file: File): Observable<CirelDocument> {
    const body: FormData = new FormData();
    body.append('file', file);

    const url = `${this.URL}/${documentId}`;

    return this.http.put<CirelDocument>(url, body);
  }
}
