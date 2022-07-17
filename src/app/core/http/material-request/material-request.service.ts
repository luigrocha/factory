import {Injectable} from '@angular/core';
import {SearchRequest} from 'src/app/types/pageable.types';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {MaterialRequestCreate, MaterialRequestPageable} from 'src/app/types/material-request.types';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MaterialRequestService {

  private readonly URL = environment.appApiUrl + '/material-requests';

  constructor(private http: HttpClient) {
  }

  getMaterialRequests(searchRequest: SearchRequest): Observable<MaterialRequestPageable> {
    let parameters = `/search?page=${searchRequest.page}&size=${searchRequest.size}`;

    if (searchRequest.query) {
      parameters += `&query=${searchRequest.query}`;
    }

    parameters += `&states=${searchRequest.filters.join(',')}`;

    return this.http.post<MaterialRequestPageable>(this.URL + parameters, searchRequest.searchCriteria);
  }

  getCountMaterialRequest(): Observable<number> {
    return this.http.get<number>(`${this.URL}/count`);
  }

  create(body: MaterialRequestCreate): Observable<any> {
    return this.http.post<any>(this.URL, body);
  }

}
