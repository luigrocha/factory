import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Menu } from 'src/app/types/menu.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_MENU = environment.appApiUrl + '/menu';

  constructor(private http: HttpClient) { }

  getAllItems(): Observable<Menu[]> {
    return this.http.get<Menu[]>(this.URL_MENU + '/findAllItems', this.httpOptions);
  }

}
