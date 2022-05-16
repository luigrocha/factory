import { Component, OnInit } from '@angular/core';
import { Project } from 'src/app/types/project.types';
import { TableHeader } from 'src/app/types/table.types';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss']
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedProjects: Project[] = [];
  initialPage: number = 0;
  pageSize: number = 10;
  actualPage: number = 0;
  query: string = null;
  headers: TableHeader<Project>[] = [
    { label: 'Cliente', property: 'client', frozen: true },
    { label: 'Proyecto', property: 'name', frozen: true },
    { label: 'Code GEN', property: 'code', frozen: true },
    { label: 'GSM', property: 'gsm', frozen: false },
    { label: '%HPo', property: 'gsm', frozen: false },

  ];

  constructor() { }

  ngOnInit(): void {
  }

  private addNewProject(): void {
  }

  private deleteSelectedProjects(): void {
  }


}
