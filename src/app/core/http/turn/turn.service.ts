import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Turn } from 'src/app/types/turn.types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TurnService {

  private readonly URL = environment.appApiUrl + '/turns';

  constructor(private http: HttpClient) {
  }

  getTurns(isActive?: boolean): Observable<Turn[]> {
    if (isActive === undefined) {
      return this.http.get<Turn[]>(this.URL);
    } else {
      return this.http.get<Turn[]>(`${this.URL}?isActive=${isActive}`);
    }
  }
}
