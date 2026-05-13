import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Material, TypeMaterial} from 'src/app/types/material.types';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  URL_METRIAL = environment.appApiUrl + '/material';
  URL_TYPE_METRIAL = environment.appApiUrl + '/typeMaterial';

  constructor(private http: HttpClient) { }

  getAllMaterialByType(id: number): Observable<Material[]> {
    return this.http.get<Material[]>(this.URL_METRIAL + '/' + id);
  }

  getAllTypeMaterial(): Observable<TypeMaterial[]> {
    return this.http.get<TypeMaterial[]>(this.URL_TYPE_METRIAL + '');
  }

  getAllMaterial(): Observable<Material[]> {
    return this.http.get<Material[]>(this.URL_METRIAL);
  }

}
