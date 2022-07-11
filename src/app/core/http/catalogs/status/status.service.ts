import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Status } from 'src/app/types/catalogs.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StatusService {
  private readonly URL = environment.appApiUrl + '/states';

  constructor(private http: HttpClient) {
  }

  getAllByType(type: string): Observable<Status[]> {
    const url = `${this.URL}/${type}`;
    return this.http.get<Status[]>(url);
  }
}
