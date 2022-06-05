import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler, Document, GenerateReceipt } from 'src/app/types/celler.types';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss'],
  styles: [
    `
        :host ::ng-deep .p-dialog .product-image {
            width: 150px;
            margin: 0 auto 2rem auto;
            display: block;
        }

        @media screen and (max-width: 960px) {
            :host
            ::ng-deep
            .p-datatable.p-datatable-customers
            .p-datatable-tbody
            > tr
            > td:last-child {
                text-align: center;
            }

            :host
            ::ng-deep
            .p-datatable.p-datatable-customers
            .p-datatable-tbody
            > tr
            > td:nth-child(6) {
                display: flex;
            }
        }
    `,
  ],
  providers: [MessageService, ConfirmationService],
})
export class StoreComponent implements OnInit {

  cellerDialog: boolean;

  selectedCeller: Celler[];

  submitted: boolean;

  cols: any[];

  cellers: Celler[];

  celler: Celler;

  loading = true;

  documents: Document[];

  documentsMenu: MenuItem[];

  permissionsPage: TypePermission[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private cellerService: CellerService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    this.getAllDocuments();
    this.cols = [
      { field: 'lote', header: 'Lote' },
      { field: 'amount', header: 'Cantidad' },
      { field: 'balance', header: 'Saldos' },
      { field: 'coat', header: 'Sacos' },
      { field: 'pallets', header: 'Pallets' },
      { field: 'weight', header: 'Peso Kg' },
      { field: 'date', header: 'Fecha' },
      { field: 'observation', header: 'Observación' },
      { field: 'material', header: 'Producto' },
      { field: 'location', header: 'Ubicación' },
      { field: 'document', header: 'Documento' },
    ];
  }

  // openNew() {
  //   this.order = {};
  //   this.submitted = false;
  //   this.orderDialog = true;
  // }

  // editOrder(order: Order) {
  //   this.order = { ...order };
  //   this.orderDialog = true;
  // }

  // deleteOrder(order: Order) {
  //   this.confirmationService.confirm({
  //     message:
  //       'Estas seguro de eliminar el order ' + order.lote + '?',
  //     header: 'Confirmación',
  //     icon: 'pi pi-exclamation-triangle',
  //     accept: () => {
  //       // this.orderService.delete(order.id).subscribe(
  //       //   (res) => {
  //       //     this.messageService.add({
  //       //       severity: 'success',
  //       //       summary: 'Éxito',
  //       //       detail: 'Homopolímero Eliminado',
  //       //       life: 3000,
  //       //     });
  //       //     this.orders = [];
  //       //     this.getAllUsers();
  //       //   },
  //       //   (err) => {
  //       //     this.messageService.add({
  //       //       severity: 'error',
  //       //       summary: 'Error',
  //       //       detail: err.error,
  //       //       life: 3000,
  //       //     });
  //       //   }
  //       // );

  //     },
  //   });
  // }

  // deleteSelectedOrders() {
  //   this.confirmationService.confirm({
  //     message: 'Estás seguro de eliminar los orderes seleccionados?',
  //     header: 'Confirmación',
  //     icon: 'pi pi-exclamation-triangle',
  //     accept: () => {
  //       // this.selectedHomo.forEach(order => {
  //       //   this.orderService.delete(order.id).subscribe(
  //       //     (res) => {
  //       //       this.messageService.add({
  //       //         severity: 'success',
  //       //         summary: 'Éxito',
  //       //         detail: 'Homopolímero Eliminado',
  //       //         life: 3000,
  //       //       });
  //       //       this.orders = [];
  //       //       this.getAllUsers();
  //       //     },
  //       //     (err) => {
  //       //       this.messageService.add({
  //       //         severity: 'error',
  //       //         summary: 'Error',
  //       //         detail: err.error,
  //       //         life: 3000,
  //       //       });
  //       //     }
  //       //   );
  //       // })

  //       this.selectedOrder = null;
  //       this.messageService.add({
  //         severity: 'success',
  //         summary: 'Correcto',
  //         detail: 'Homopolímeros Elimnados',
  //         life: 3000,
  //       });
  //     },
  //   });
  // }

  // saveOrder() {
  //   this.submitted = true;

  //   if (this.order.id) {
  //     // this.orderService.update(this.order.id, this.order).subscribe(
  //     //   (res) => {
  //     //     this.messageService.add({
  //     //       severity: 'success',
  //     //       summary: 'Éxito',
  //     //       detail: 'Homopolímero Actualizado',
  //     //       life: 3000,
  //     //     });
  //     //     this.orders = [];
  //     //     this.getAllUsers();
  //     //   },
  //     //   (err) => {
  //     //     this.messageService.add({
  //     //       severity: 'error',
  //     //       summary: 'Error',
  //     //       detail: err.error,
  //     //       life: 3000,
  //     //     });
  //     //   }
  //     // );
  //   } else {
  //     // this.orderService.create(this.order).subscribe(
  //     //   (res) => {
  //     //     this.messageService.add({
  //     //       severity: 'success',
  //     //       summary: 'Éxito',
  //     //       detail: 'Homopolímero Creado',
  //     //       life: 3000,
  //     //     });
  //     //     this.orders = [];
  //     //     this.getAllUsers();
  //     //   },
  //     //   (err) => {
  //     //     this.messageService.add({
  //     //       severity: 'error',
  //     //       summary: 'Error',
  //     //       detail: err.error,
  //     //       life: 3000,
  //     //     });
  //     //   }
  //     // );
  //   }

  //   this.orders = [...this.orders];
  //   this.orderDialog = false;
  //   this.order = {};
  // }

  hideDialog() {
    this.cellerDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.cellerService.getAll().subscribe((cellers) => {
      this.cellers = cellers;
      this.loading = false;
    });
  }

  getAllDocuments() {
    this.documentsMenu = [];
    this.cellerService.getAllDocument().subscribe((documents) => {
      documents.forEach(document => {
        this.documentsMenu.push({ label: document.description, routerLink: '/home/bodega/' + document.name });
      });
    });
  }

  generateReceipt(documentId: number, body: GenerateReceipt): void {
    const mock = {
      receiptNumber: "CIB123",
      receiptDate: new Date(),
      reason: "Producción de",
      reasonObservation: "Observacion de produccion",
      observations: "It compiles well  witho",
      deliveredBy: "Luis Antonio Pillaga Zhagnay",
      receivedBy: null,
      items: [
        {
          productType: "HPO",
          productName: "EXPORT PP PT100 GLOBALENE",
          lot: "M14784",
          units: 12,
          bags1KG: null,
          bags25KG: 2,
          pallets55: null,
          totalWeight: 50.00,
          location: "Zona 02"
        }
      ]
    };
    this.cellerService.generateReceipt(documentId, mock)
      .subscribe();
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage().subscribe(
      (data) => {
        this.permissionsPage = data;
      }
    );
  }

  isAllow(id: number): boolean {
    if (this.permissionsPage) {
      return this.permissionsPage.find(permission => permission.id === id).flag;
    }
    return false;
  }

}
