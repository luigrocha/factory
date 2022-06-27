import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Celler, CodeDocument, Document, GenerateReceipt, Location, OptionDocument } from 'src/app/types/celler.types';
import { environment } from 'src/environments/environment';
import { getFileFromResponse } from 'src/app/core/utils/http-extract-file';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CellerService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_CELLER = environment.appApiUrl + '/celler';
  URL_DOCUMENT = environment.appApiUrl + '/document';
  URL_OPTION_DOCUMENT = environment.appApiUrl + '/optionDocument';
  URL_LOCATION = environment.appApiUrl + '/location';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<Celler[]> {
    return this.http.get<Celler[]>(this.URL_CELLER + '', this.httpOptions);
  }

  getAllDocument(): Observable<Document[]> {
    return this.http.get<Document[]>(this.URL_DOCUMENT + '', this.httpOptions);
  }

  getAllOptionsByDocumentCode(id: number): Observable<OptionDocument[]> {
    return this.http.get<OptionDocument[]>(this.URL_OPTION_DOCUMENT + '/' + id, this.httpOptions);
  }

  getNewCodeDocumentByDocumentCode(id: number): Observable<CodeDocument> {
    return this.http.get<CodeDocument>(this.URL_CELLER + '/findNewCodeDocumentByDocumentCode/' + id, this.httpOptions);
  }

  getAllLocation(): Observable<Location[]> {
    return this.http.get<Location[]>(this.URL_LOCATION + '', this.httpOptions);
  }

  create(celler: GenerateReceipt): Observable<any> {
    return this.http.post<any>(this.URL_CELLER + '', celler, this.httpOptions);
  }

  generateReceipt(documentId: number, body: GenerateReceipt) {
    let params = new HttpParams()
      .set('documentId', documentId);

    const url = this.URL_CELLER + '/generate-receipt';

    return this.http.post(url, body, {
      params: params,
      responseType: 'blob',
      observe: 'response'
    }).pipe(
      map(response => getFileFromResponse(response))
    );
  }

  generateReceiptPreview(documentId: number, body: GenerateReceipt) {
    let params = new HttpParams().set('documentId', documentId);

    const url = this.URL_CELLER + '/generate-receipt';

    return this.http.post(url, body, {
      params: params,
      responseType: 'blob',
      observe: 'response'
    });
  }

  getReceipt(numberDocument: string, documentId: number) {
    return this.http.get(this.URL_CELLER + '/get-receipt/' + numberDocument + '/' + documentId, {
      responseType: 'blob',
      observe: 'response'
    });
  }

  anulate(id: number): Observable<any> {
    return this.http.patch<any>(this.URL_CELLER + '/anulate/' + id + '/'
      + this.authService.getLoggedUser().preferred_username, this.httpOptions);
  }
}
