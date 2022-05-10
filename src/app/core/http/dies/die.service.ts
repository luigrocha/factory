import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Die, DiePageable } from 'src/app/types/dies.types';

@Injectable({
  providedIn: 'root'
})
export class DieService {

  private readonly URL = environment.appApiUrl + '/dies';

  constructor(private http: HttpClient) { }

  getAllDies(page: number, size: number, query: string): Observable<DiePageable> {
    let parameters = `?page=${page}&size=${size}`;

    if (query) {
      parameters += `&query=${query}`;
    }

    return this.http.get<DiePageable>(this.URL + parameters);
  }
}
