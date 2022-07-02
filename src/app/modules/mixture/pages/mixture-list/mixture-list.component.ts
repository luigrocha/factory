import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';

@Component({
  selector: 'app-mixture-list',
  templateUrl: './mixture-list.component.html',
  styleUrls: ['./mixture-list.component.scss']
})
export class MixtureListComponent implements OnInit {

  constructor(
    private breadcrumbService: BreadcrumbService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Módulo de Mezcla' },
      { label: 'Diseño de Mezcla', routerLink: ['/home/mezcla'] },
    ]);
  }

  ngOnInit() {
  }

}
