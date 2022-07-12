import {Component, OnInit} from '@angular/core';
import {BreadcrumbService} from 'src/app/core/services/breadcrumb.service';
import {OrderService} from '../../../../core/http/orders/order.service';
import {Order} from '../../../../types/order.types';
import {OrderStatus} from '../../../../types/enums/order-status';
import {MixtureShort} from '../../../../types/mixture.types';
import {MixtureService} from '../../../../core/http/mixture/mixture.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from '../../../../core/constants/form-error';
import {Router} from '@angular/router';

@Component({
  selector: 'app-mixture-list',
  templateUrl: './mixture-list.component.html',
  styleUrls: ['./mixture-list.component.scss']
})
export class MixtureListComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  orders: Order[];
  mixtures: MixtureShort[];

  constructor(
    private fb: FormBuilder,
    private breadcrumbService: BreadcrumbService,
    private orderService: OrderService,
    private mixtureService: MixtureService,
    private router: Router,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Módulo de Mezcla'},
      {label: 'Diseño de Mezcla', routerLink: ['/home/mezcla']},
    ]);
  }

  get mixture() {
    return this.form.get('mixture');
  }

  get lot() {
    return this.form.get('lot');
  }

  ngOnInit() {
    this.getOrdersByStatus();
    this.form = this.fb.group({
      mixture: [null, [
        Validators.required
      ]],
      lot: [null, [
        Validators.required
      ]]
    });
  }

  getOrdersByStatus() {
    this.orderService.getOrdersByStatus(OrderStatus.PENDING).subscribe(orders => {
      this.orders = orders;
      this.orders.forEach(order => this.getNumberByLot(order));
    });
  }

  getNumberByLot(order: Order){
    this.mixtureService.getNumberByLot(order.lot).subscribe(num => {
      order.numberMixture = num;
    });
  }

  filterMixtures($event: any) {
    const query = $event.query;
    if (query) {
      this.mixtureService.search(query)
        .subscribe((res: MixtureShort[]) => {
          this.mixtures = res;
        });
    }
  }

  onSelect(e: any) {
    this.lot.setValue(e);
  }

  save() {
    if (this.form.invalid) {
      return;
    }
    const mixture: MixtureShort = this.form.getRawValue().lot;
    this.router.navigate(['/home/mezcla/crear/' + mixture.order.lot + '/' + mixture.number]);
  }
}
