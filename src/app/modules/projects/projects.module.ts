import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectsRoutingModule } from './projects-routing.module';
import { ProjectListComponent } from './pages/project-list/project-list.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { InputTextModule } from 'primeng/inputtext';
import { MenuModule } from 'primeng/menu';
import { ToastModule } from 'primeng/toast';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    ProjectListComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    TableModule,
    ButtonModule,
    RippleModule,
    InputTextModule,
    MenuModule,
    ToastModule,
    SharedModule
  ]
})
export class ProjectsModule { }
