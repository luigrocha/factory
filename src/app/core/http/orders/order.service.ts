import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Order} from 'src/app/types/order.types';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly URL = environment.appApiUrl + '/orders';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Order[]> {
    return this.http.get<Order[]>(this.URL);
  }

  getOrdersByStatus(status: string): Observable<Order[]> {
    return this.http.get<Order[]>(this.URL + '/findOrdersByStatus/' + status);
  }
}
