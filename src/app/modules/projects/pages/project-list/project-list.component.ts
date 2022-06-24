import { Component, OnInit } from '@angular/core';
import { Project } from 'src/app/types/project.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { Table } from 'primeng/table';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { MenuItem } from 'primeng/api';
import { ProjectService } from 'src/app/core/http/projects/project.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss'],
  styles: [`
        :host ::ng-deep  .p-frozen-column {
            font-weight: bold;
        }

        :host ::ng-deep .p-datatable-frozen-tbody {
            font-weight: bold;
        }

        :host ::ng-deep .p-progressbar {
            height:.5rem;
        }
    `]
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  selectedProject: Project;
  globalFilterFields: string[] = [
    'name',
    'codeGen',
    'client.name'
  ];
  pageSize: number = 10;
  menuItems: MenuItem[];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private projectService: ProjectService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Proyectos', routerLink: ['/home/proyectos']},
    ]);
  }

  ngOnInit(): void {
    this.getAllProjects();
    this.getMenuItems();
  }

  private getAllProjects() {
    this.projectService.getAllProjects()
      .subscribe(projects => {
        this.projects = projects;
      });
  }

  private getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editProject()
      },
      {
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteProject()
      }
    ];
  }

  addNewProject(): void {
  }

  clear(table: Table) {
    table.clear();
  }

  private editProject() {
    return undefined;
  }

  private deleteProject() {
    return undefined;
  }
}
