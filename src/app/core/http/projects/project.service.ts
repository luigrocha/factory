import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpClient} from '@angular/common/http';
import {Project, ProjectShort} from 'src/app/types/project.types';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private readonly URL = environment.appApiUrl + '/projects';

  constructor(private http: HttpClient) {
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.URL);
  }

  searchProjectsByClient(clientId: number): Observable<ProjectShort[]> {
    return this.http.get<ProjectShort[]>(`${this.URL}/search/client/${clientId}`);
  }

  getProjectToCodeGen(codeGen: string): Observable<Project> {
    return this.http.get<Project>(`${this.URL}/search/code/${codeGen}`);
  }
}
