import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { Observable } from 'rxjs';
import { Client } from 'src/app/types/client.types';
import { ClientService } from 'src/app/core/http/clients/client.service';
import { ProjectService } from 'src/app/core/http/projects/project.service';
import { ProjectShort } from 'src/app/types/project.types';
import { Priority } from 'src/app/types/catalogs.types';
import { PriorityService } from 'src/app/core/http/catalogs/priority/priority.service';
import { ORDER_PRIORITY_TYPE } from 'src/app/core/constants/priority-type';
import { CreateOrder, GeneratedOrderCode } from 'src/app/types/order.types';
import { OrderService } from 'src/app/core/http/orders/order.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Router } from '@angular/router';

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
  priorities$: Observable<Priority[]>;
  orderPriorityType = ORDER_PRIORITY_TYPE;
  generateNextOrderCode: GeneratedOrderCode;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private fb: FormBuilder,
    private clientService: ClientService,
    private projectService: ProjectService,
    private priorityService: PriorityService,
    private orderService: OrderService,
    private toastService: ToastService,
    private router: Router
  ) {
    this.breadcrumbService.setItems([
      {label: 'Pedidos'},
      {label: 'Gestión de Pedidos', routerLink: ['/home/pedidos']},
      {label: 'Crear pedido', routerLink: ['/home/pedidos/crear']}
    ]);
  }

  ngOnInit(): void {
    this.clients$ = this.clientService.getAllClients();
    this.priorities$ = this.priorityService.getAllByType(this.orderPriorityType);
    this.getNextOrderCode();
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
      priority: [null, [Validators.required]],
      stock: [{disabled: true, value: 0}],
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

  get stock() {
    return this.form.get('stock');
  }

  get priority() {
    return this.form.get('priority');
  }

  get clientOrderCode() {
    return this.form.get('clientOrderCode');
  }

  get observation() {
    return this.form.get('observation');
  }

  get estimatedDeliveryAt() {
    return this.form.get('estimatedDeliveryAt');
  }

  save() {
    if (this.form.invalid) {
      return;
    }

    const body: CreateOrder = {
      code: this.generateNextOrderCode.nextOrderCode,
      productCode: this.productCode.value,
      name: this.name.value,
      quantity: this.quantity.value,
      clientOrderCode: this.clientOrderCode.value,
      observation: this.observation.value,
      estimatedDeliveryAt: this.estimatedDeliveryAt.value,
      clientId: this.client.value.id,
      priorityId: this.priority.value.id,
      projectId: this.project.value.id
    };

    this.orderService.createNewOrder(body)
      .subscribe(() => {
        this.form.reset();
        this.toastService.success('Pedido creado correctamente');
        setTimeout(() => {
          this.back();
        }, 1500);
      });
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

  private getNextOrderCode() {
    this.orderService.generateNextOrderCode()
      .subscribe(code => {
        this.generateNextOrderCode = code;
      });
  }

  back() {
    this.router.navigate(['/home/pedidos']);
  }
}
