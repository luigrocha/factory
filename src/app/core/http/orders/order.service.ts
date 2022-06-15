import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from 'src/app/types/order.types';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/order';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Order[]> {
    return this.http.get<Order[]>(this.URL_USER + '', this.httpOptions);
  }
}
