import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Material, TypeMaterial } from 'src/app/types/material.types';
import { Thickness } from 'src/app/types/thickness.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MaterialService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_METRIAL = environment.appApiUrl + '/material';
  URL_TYPE_METRIAL = environment.appApiUrl + '/typeMaterial';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllMaterialByType(id: number): Observable<Material[]> {
    return this.http.get<Material[]>(this.URL_METRIAL + '/' + id, this.httpOptions);
  }

  getAllTypeMaterial(): Observable<TypeMaterial[]> {
    return this.http.get<TypeMaterial[]>(this.URL_TYPE_METRIAL + '', this.httpOptions);
  }

}
