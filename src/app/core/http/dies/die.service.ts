import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateDie, Die } from 'src/app/types/dies.types';

@Injectable({
  providedIn: 'root'
})
export class DieService {

  private readonly URL = environment.appApiUrl + '/dies';

  constructor(private http: HttpClient) { }

  getAllDies(): Observable<Die[]> {
    return this.http.get<Die[]>(this.URL);
  }

  createDie(die: CreateDie): Observable<Die> {
    return this.http.post<Die>(this.URL, die);
  }
}
