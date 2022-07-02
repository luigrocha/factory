import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';

@Component({
  selector: 'app-create-mixture',
  templateUrl: './create-mixture.component.html',
  styleUrls: ['./create-mixture.component.scss']
})
export class CreateMixtureComponent implements OnInit {

  showFilters: boolean;

  constructor(
    private breadcrumbService: BreadcrumbService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Módulo de Mezcla' },
      { label: 'Diseño de Mezcla', routerLink: ['/home/mezcla'] },
      { label: 'Crear Diseño de Mezcla', routerLink: ['/home/mezcla/crear'] },
    ]);
  }

  ngOnInit() {
  }

}
