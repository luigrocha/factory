import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Priority } from 'src/app/types/catalogs.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PriorityService {

  private readonly URL = environment.appApiUrl + '/priorities';

  constructor(private http: HttpClient) {
  }

  getAllByType(type: string): Observable<Priority[]> {
    return this.http.get<Priority[]>(`${this.URL}/${type}`);
  }
}
