import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateOrder, GeneratedOrderCode, Order, OrderPageable } from 'src/app/types/order.types';
import { SearchRequest } from 'src/app/types/pageable.types';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly URL = environment.appApiUrl + '/orders';

  constructor(private http: HttpClient) {
  }

  getOrders(searchRequest: SearchRequest): Observable<OrderPageable> {
    let parameters = `/search?page=${searchRequest.page}&size=${searchRequest.size}`;

    if (searchRequest.query) {
      parameters += `&query=${searchRequest.query}`;
    }

    parameters += `&states=${searchRequest.filters.join(',')}`;

    return this.http.post<OrderPageable>(this.URL + parameters, searchRequest.searchCriteria);
  }

  getOrder(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.URL}/${id}`);
  }

  createNewOrder(order: CreateOrder): Observable<Order> {
    return this.http.post<Order>(this.URL, order);
  }

  generateNextOrderCode(): Observable<GeneratedOrderCode> {
    return this.http.get<GeneratedOrderCode>(`${this.URL}/generate-code`);
  }
}
