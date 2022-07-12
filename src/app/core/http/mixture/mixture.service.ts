import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MixtureCreate, MixtureRes, MixtureShort} from '../../../types/mixture.types';

@Injectable({
  providedIn: 'root'
})
export class MixtureService {

  private readonly URL = environment.appApiUrl + '/mixture';

  constructor(private http: HttpClient) {
  }

  getNumberToCreate(): Observable<number> {
    return this.http.get<number>(this.URL + '/findNumberToCreate');
  }

  create(body: MixtureCreate): Observable<any> {
    return this.http.post<any>(this.URL, body);
  }

  search(query: string): Observable<MixtureShort[]> {
    const url = this.URL + '/search?query=' + query;
    return this.http.get<MixtureShort[]>(url);
  }

  getMixtureByNumber(num: number): Observable<MixtureRes> {
    const url = this.URL + '/search/' + num;
    return this.http.get<MixtureRes>(url);
  }

  getAll(): Observable<MixtureRes[]> {
    return this.http.get<MixtureRes[]>(this.URL);
  }

  getNumberByLot(lot: string): Observable<number>{
    const url = this.URL + '/findNumberByLot/' + lot;
    return this.http.get<number>(url);
  }

  edit(id: number, body: MixtureCreate): Observable<any> {
    return this.http.put<any>(this.URL + '/' + id , body);
  }

  generateReceipt(id: number){
    return this.http.get(this.URL + '/get-receipt/' + id, {
      responseType: 'blob',
      observe: 'response'
    });
  }

}
