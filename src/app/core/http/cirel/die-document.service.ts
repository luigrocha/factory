import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CirelDocument } from 'src/app/types/cirel.types';
import { DieDocument } from 'src/app/types/dies.types';

@Injectable({
  providedIn: 'root'
})
export class DieDocumentService {
  private readonly URL = environment.appApiUrl + '/dies/{id}/documents';

  constructor(private http: HttpClient) {
  }

  uploadDieDocument(dieId: number, file: File): Observable<DieDocument> {
    const body: FormData = new FormData();
    body.append('file', file);

    return this.http.post<CirelDocument>(this.URL.replace('{id}', dieId.toString()), body);
  }

  updateDieDocument(documentId: number, file: File): Observable<DieDocument> {
    const body: FormData = new FormData();
    body.append('file', file);

    const url = `${this.URL}/${documentId}`;

    return this.http.put<CirelDocument>(url, body);
  }
}
