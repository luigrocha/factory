import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CirelPageable } from 'src/app/types/cirel.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CirelService {
  private readonly URL = environment.appApiUrl + '/cyrels';

  constructor(private http: HttpClient) {
  }

  getAllCirels(page: number, size: number, query: string)
    : Observable<CirelPageable> {
    let parameters = `?page=${page}&size=${size}`;

    if (query) {
      parameters += `&query=${query}`;
    }

    return this.http.get<CirelPageable>(this.URL + parameters);
  }
}
