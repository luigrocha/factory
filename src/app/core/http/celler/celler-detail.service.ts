import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { AllStock, CellerDetail, LoteCeller, MaterialStock, Stock } from 'src/app/types/celler.types';
import { environment } from 'src/environments/environment';

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

  getByTypeMaterialStock(id: number): Observable<AllStock[]> {
    return this.http.get<AllStock[]>(this.URL_CELLER + '/findByTypeMaterialStock/' + id, this.httpOptions);
  }

  getByMaterialStock(id: number): Observable<MaterialStock[]> {
    return this.http.get<MaterialStock[]>(this.URL_CELLER + '/findByMaterialStock/' + id, this.httpOptions);
  }

  getAllStock(): Observable<AllStock[]> {
    return this.http.get<AllStock[]>(this.URL_CELLER + '/findAllStock', this.httpOptions);
  }

  getMaterialStock(id: number): Observable<AllStock[]> {
    return this.http.get<AllStock[]>(this.URL_CELLER + '/findMaterialStock/' + id, this.httpOptions);
  }

  getMaterialLoteStock(id: number, lote: string): Observable<AllStock[]> {
    return this.http.get<AllStock[]>(this.URL_CELLER + '/findMaterialLoteStock/' + id + '/' + lote, this.httpOptions);
  }




}
