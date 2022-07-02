import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { Observable } from 'rxjs';
import { Client } from 'src/app/types/client.types';
import { ClientService } from 'src/app/core/http/clients/client.service';
import { ProjectService } from 'src/app/core/http/projects/project.service';
import { ProjectShort } from 'src/app/types/project.types';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.scss']
})
export class CreateOrderComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  clients$: Observable<Client[]>;
  clientProjects: ProjectShort[] = [];
  minDate = new Date();

  constructor(
    private breadcrumbService: BreadcrumbService,
    private fb: FormBuilder,
    private clientService: ClientService,
    private projectService: ProjectService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Pedidos'},
      {label: 'Gestión de Pedidos', routerLink: ['/home/pedidos']},
      {label: 'Crear pedido', routerLink: ['/home/pedidos/crear']}
    ]);
  }

  ngOnInit(): void {
    this.clients$ = this.clientService.getAllClients();
    this.form = this.fb.group({
      client: ['', [Validators.required]],
      project: ['', [Validators.required]],
      productCode: [{
        disabled: true,
        value: null
      }, [Validators.required]],
      name: [{
        disabled: true,
        value: null
      }, [Validators.required]],
      quantity: ['', [Validators.required]],
      estimatedDeliveryAt: [null],
      clientOrderCode: [null],
      observation: [null],
    });
    this.searchClientProjects();
    this.populateProductCodeAndName();
  }

  get client() {
    return this.form.get('client');
  }

  get project() {
    return this.form.get('project');
  }

  get productCode() {
    return this.form.get('productCode');
  }

  get name() {
    return this.form.get('name');
  }

  get quantity() {
    return this.form.get('quantity');
  }

  save() {

  }

  private searchClientProjects() {
    this.client.valueChanges.subscribe(client => {
      if (client) {
        this.projectService.searchProjectsByClient(client.id)
          .subscribe(projects => {
            this.clientProjects = projects;
          });
      } else {
        this.clientProjects = [];
      }
    });
  }

  private populateProductCodeAndName() {
    this.project.valueChanges.subscribe(project => {
      if (project) {
        this.productCode.setValue(project.codeGen);
        this.name.setValue(project.name);
      } else {
        this.productCode.setValue(null);
        this.name.setValue(null);
      }
    });
  }
}
