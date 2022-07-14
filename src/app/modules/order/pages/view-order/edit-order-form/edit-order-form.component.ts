import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Order, UpdateOrder } from 'src/app/types/order.types';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { PriorityService } from 'src/app/core/http/catalogs/priority/priority.service';
import { ORDER_PRIORITY_TYPE } from 'src/app/core/constants/priority-type';
import { Priority } from 'src/app/types/catalogs.types';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { ProjectService } from 'src/app/core/http/projects/project.service';
import { ProjectShort } from 'src/app/types/project.types';
import { Client } from 'src/app/types/client.types';
import { ClientService } from 'src/app/core/http/clients/client.service';
import { OrderStatus } from 'src/app/types/enums/order-status';

@Component({
  selector: 'app-edit-order-form',
  templateUrl: './edit-order-form.component.html',
  styleUrls: ['./edit-order-form.component.scss']
})
export class EditOrderFormComponent implements OnInit, OnChanges {
  @Input() order: Order;
  @Output() saveForm: EventEmitter<UpdateOrder> = new EventEmitter();

  form: FormGroup;
  minDate = new Date();
  formErrors = FORM_ERROR_MESSAGES;
  priorities$: Observable<Priority[]>;
  clientProjects: ProjectShort[] = [];
  clients$: Observable<Client[]>;

  constructor(
    private fb: FormBuilder,
    private priorityService: PriorityService,
    private router: Router,
    private clientService: ClientService,
    private projectService: ProjectService
  ) {
  }

  ngOnInit(): void {
    this.priorities$ = this.priorityService.getAllByType(ORDER_PRIORITY_TYPE);
    this.clients$ = this.clientService.getAllClients();
    this.getClientProjects();
    this.form = this.fb.group({
      id: [this.order.id, Validators.required],
      lot: [this.order.lot],
      client: [this.order.client, [Validators.required]],
      project: [this.order.project, [Validators.required]],
      productCode: [{
        disabled: true,
        value: this.order.productCode
      }, [Validators.required]],
      name: [{
        disabled: true,
        value: this.order.name
      }, [Validators.required]],
      quantity: [this.order.quantity, [Validators.required]],
      priority: [this.order.priority, [Validators.required]],
      stock: [{disabled: true, value: 0}],
      estimatedDeliveryAt: [new Date(this.order.estimatedDeliveryAt)],
      clientOrderCode: [this.order.clientOrderCode],
      observation: [this.order.observation],
    });
    this.searchClientProjects();
    this.populateProductCodeAndName();
    this.disableAllOrderIsCanceledOrShipped();
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

  getClientProjects() {
    this.projectService.searchProjectsByClient(this.order.client.id)
      .subscribe(projects => {
        this.clientProjects = projects;
      });
  }

  get client() {
    return this.form.get('client');
  }

  get quantity() {
    return this.form.get('quantity');
  }

  get priority() {
    return this.form.get('priority');
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

  get lot() {
    return this.form.get('lot');
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

  updateOrder() {
    const order: UpdateOrder = {
      lot: this.lot.value,
      productCode: this.productCode.value,
      name: this.name.value,
      quantity: this.quantity.value,
      clientOrderCode: this.clientOrderCode.value,
      observation: this.observation.value,
      estimatedDeliveryAt: this.estimatedDeliveryAt.value,
      clientId: this.client.value.id,
      projectId: this.project.value.id,
      priorityId: this.priority.value.id
    };
    this.saveForm.emit(order);
  }

  back() {
    this.router.navigate(['/home/pedidos']);
  }

  private disableAllOrderIsCanceledOrShipped() {
    if (
      this.order.status.id === OrderStatus.CANCELLED ||
      this.order.status.id === OrderStatus.SHIPPED) {
      this.form.disable();
    }
  }

  isOrderCancelledOrShipped(): boolean {
    return this.order.status.id === OrderStatus.CANCELLED ||
      this.order.status.id === OrderStatus.SHIPPED;
  }

  isOrderPending(): boolean {
    return this.order.status.id === OrderStatus.PENDING;
  }

  isOrderToStart(): boolean {
    return this.order.status.id === OrderStatus.TO_START;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.order && !changes.order.firstChange) {
      this.order = changes.order.currentValue;
      if (this.isOrderCancelledOrShipped()) {
        this.disableAllOrderIsCanceledOrShipped();
      }
    }
  }
}
