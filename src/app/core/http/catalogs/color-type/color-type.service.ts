import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ColorType } from 'src/app/types/color-type.types';

@Injectable({
  providedIn: 'root'
})
export class ColorTypeService {

  private readonly URL = environment.appApiUrl + '/color-types';

  constructor(private http: HttpClient) { }

  getAllColorTypes(): Observable<ColorType[]> {
    return this.http.get<ColorType[]>(this.URL);
  }
}
