import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Celler, CellerDetail, CodeDocument, Document, GenerateReceipt, Location, OptionDocument } from 'src/app/types/celler.types';
import { environment } from 'src/environments/environment';
import { getFileFromResponse } from 'src/app/core/utils/http-extract-file';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CellerDetailService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_CELLER = environment.appApiUrl + '/cellerDetail';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getByMaterialCode(id: number): Observable<CellerDetail[]> {
    return this.http.get<CellerDetail[]>(this.URL_CELLER + '/findByMaterialCode/' + id, this.httpOptions);
  }

  getByCellerCode(id: number): Observable<CellerDetail[]> {
    return this.http.get<CellerDetail[]>(this.URL_CELLER + '/findByCellerCode/' + id, this.httpOptions);
  }

  create(celler: CellerDetail[]): Observable<any> {
    return this.http.post<any>(this.URL_CELLER + '', celler, this.httpOptions);
  }

}
