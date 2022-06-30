import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Celler, CellerDetail, CodeDocument, Document, GenerateReceipt, Location, LoteCeller, MaterialStock, OptionDocument, Stock, TypeMaterialStock } from 'src/app/types/celler.types';
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

  getByLocationCode(lote: string, codeMaterial: number): Observable<CellerDetail[]> {
    return this.http.get<CellerDetail[]>(this.URL_CELLER + '/findByLocationCode/' + lote + '/' + codeMaterial, this.httpOptions);
  }

  getByMaterialCode(id: number): Observable<CellerDetail[]> {
    return this.http.get<CellerDetail[]>(this.URL_CELLER + '/findByMaterialCode/' + id, this.httpOptions);
  }

  getByCellerCode(id: number): Observable<CellerDetail[]> {
    return this.http.get<CellerDetail[]>(this.URL_CELLER + '/findByCellerCode/' + id, this.httpOptions);
  }

  create(celler: CellerDetail[]): Observable<any> {
    return this.http.post<any>(this.URL_CELLER + '', celler, this.httpOptions);
  }

  getLoteByMaterialCode(id: number): Observable<LoteCeller[]> {
    return this.http.get<LoteCeller[]>(this.URL_CELLER + '/findLoteByMaterialCode/' + id, this.httpOptions);
  }

  getCellerDetailStock(materialCode: number, lote: string): Observable<Stock> {
    return this.http.get<Stock>(this.URL_CELLER + '/findStock?materialCode=' + materialCode + '&lote=' + lote, this.httpOptions);
  }

  getByTypeMaterialStock(id: number): Observable<TypeMaterialStock[]> {
    return this.http.get<TypeMaterialStock[]>(this.URL_CELLER + '/findByTypeMaterialStock/' + id, this.httpOptions);
  }

  getByMaterialStock(id: number): Observable<MaterialStock[]> {
    return this.http.get<MaterialStock[]>(this.URL_CELLER + '/findByMaterialStock/' + id, this.httpOptions);
  }

}
