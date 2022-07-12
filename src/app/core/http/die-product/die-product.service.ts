import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { CreateDieProduct, DieProduct } from 'src/app/types/die-product.types';

@Injectable({
  providedIn: 'root'
})
export class DieProductService {

  private readonly URL = environment.appApiUrl + '/die-products';

  constructor(private http: HttpClient) { }

  getAllDieProducts(): Observable<DieProduct[]> {
    return this.http.get<DieProduct[]>(this.URL);
  }

  getAllAvailableDieProducts(): Observable<DieProduct[]> {
    return this.http.get<DieProduct[]>(this.URL + '/available');
  }

  createDieProduct(dieProduct: CreateDieProduct): Observable<DieProduct> {
    return this.http.post<DieProduct>(this.URL, dieProduct);
  }

  updateDieProduct(id: number, dieProduct: DieProduct): Observable<DieProduct> {
    const url = `${this.URL}/${id}`;
    return this.http.put<DieProduct>(url, dieProduct);
  }

  deleteDieProduct(id: number): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<boolean>(url);
  }
}
