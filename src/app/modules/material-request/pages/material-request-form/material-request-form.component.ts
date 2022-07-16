import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-material-request-form',
  templateUrl: './material-request-form.component.html',
  styleUrls: ['./material-request-form.component.scss']
})
export class MaterialRequestFormComponent implements OnInit {

  steps: MenuItem[];

  constructor(
    private router: Router,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Extrusión'},
      {label: 'Solicitud de material prima', routerLink: ['/home/solicitud']},
      {label: 'Formulario solicitud', routerLink: ['/home/solicitud/form']}
    ]);
  }

  ngOnInit(): void {
    this.steps = [
      {
        label: 'Step 1',
        routerLink: 'info'
      },
      {label: 'Step 2'},
      {label: 'Step 3'}
    ];
  }

}
