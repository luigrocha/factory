import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from 'src/app/core/http/orders/order.service';
import { CancelOrder, Order, StartOrder, UpdateOrder } from 'src/app/types/order.types';
import { ToastService } from 'src/app/core/services/toast.service';
import { ConfirmationService } from 'primeng/api';
import { OrderStatus } from 'src/app/types/enums/order-status';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationTextComponent } from 'src/app/shared/components/confirmation-text/confirmation-text.component';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.scss']
})
export class ViewOrderComponent implements OnInit {
  tabIndex: number = 0;
  orderId: number;
  order: Order;
  dialogRef: DynamicDialogRef;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private activatedRoute: ActivatedRoute,
    private orderService: OrderService,
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private dialogService: DialogService,
    private router: Router
  ) {
    const orderCode = this.activatedRoute.snapshot.params.code;
    const orderId = this.activatedRoute.snapshot.params.id;
    this.breadcrumbService.setItems([
      {label: 'Pedidos'},
      {label: 'Gestión de Pedidos', routerLink: ['/home/pedidos']},
      {label: `Pedido ${orderCode}`, routerLink: ['/home/pedidos/' + orderId + '/' + orderCode]}
    ]);
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .subscribe(params => {
        this.orderId = Number(params.get('id'));
        this.orderService.getOrder(this.orderId)
          .subscribe(order => {
            this.order = order;
          });
      });
  }

  showCancelButton(): boolean {
    return this.order.status.id === OrderStatus.PENDING ||
      this.order.status.id === OrderStatus.IN_PROGRESS ||
      this.order.status.id === OrderStatus.TO_START ||
      this.order.status.id === OrderStatus.DONE;
  }

  showToDoButton(): boolean {
    return this.order.status.id === OrderStatus.PENDING;
  }

  cancelOrder(): void {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de cancelar la orden ' + this.order.code + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.fillCancellationReason();
      },
    });
  }

  updateOrder(body: UpdateOrder) {
    this.orderService.updateOrder(this.orderId, body)
      .subscribe(order => {
        this.order = order;
        this.toastService.success('Pedido actualizado correctamente');
      });
  }

  toDoOrder() {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de iniciar con el diseño de la mezcla?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        const body: StartOrder = {
          statusCode: OrderStatus.TO_START
        };
        this.orderService.startOrder(this.orderId, body)
          .subscribe(order => {
            this.order = order;
            this.toastService.success('Pedido iniciado correctamente, ahora puede iniciar con el diseño de la mezcla');
            this.router.navigate(['/home/mezcla/crear/' + this.order.lot + '/0']);
          });
      },
    });
  }

  private fillCancellationReason() {
    this.dialogRef = this.dialogService.open(ConfirmationTextComponent, {
      data: {
        label: 'Ingrese el motivo de cancelación',
        actionButtonCssColorClass: 'p-button-danger',
        actionButtonLabel: 'Cancelar orden',
      },
      header: 'Motivo de cancelación',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.dialogRef.onClose
      .subscribe((cancellationReason: string) => {
        if (cancellationReason) {
          const body: CancelOrder = {
            cancellationReason: cancellationReason,
            statusCode: OrderStatus.CANCELLED
          };
          this.orderService.cancelOrder(this.orderId, body)
            .subscribe(order => {
              this.order = order;
              this.toastService.success('Pedido cancelado correctamente');
            });
        }
      });
  }
}
