import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CirelService } from 'src/app/core/http/cirel/cirel.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Cirel } from 'src/app/types/cirel.types';

@Component({
  selector: 'app-cireles-list',
  templateUrl: './cireles-list.component.html',
  styleUrls: ['./cireles-list.component.scss'],
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
export class CirelesListComponent implements OnInit {

  cirelDialog: boolean;

  selectedCirel: Cirel[];

  submitted: boolean;

  cols: any[];

  cirels: Cirel[];

  cirel: Cirel;

  loading = true;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private cirelService: CirelService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Cireles', routerLink: ['home/catalogs/cireles'] },
    ]);
  }

  ngOnInit() {
    this.getAll();
    this.cols = [
      { field: 'print', header: 'Cirel' },
      { field: 'description', header: 'Descripción' },
      { field: 'description2', header: 'Descripción 2' },
      { field: 'observation', header: 'Observación' },
      { field: 'leafColor', header: 'Color Lámina' },
    ];
  }

  openNew() {
    this.cirel = {};
    this.submitted = false;
    this.cirelDialog = true;
  }

  editCirel(cirel: Cirel) {
    this.cirel = { ...cirel };
    this.cirelDialog = true;
  }

  deleteCirel(cirel: Cirel) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el cirel ' + cirel.code + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.cirelService.delete(cirel.id).subscribe(
        //   (res) => {
        //     this.messageService.add({
        //       severity: 'success',
        //       summary: 'Éxito',
        //       detail: 'Homopolímero Eliminado',
        //       life: 3000,
        //     });
        //     this.cirels = [];
        //     this.getAllUsers();
        //   },
        //   (err) => {
        //     this.messageService.add({
        //       severity: 'error',
        //       summary: 'Error',
        //       detail: err.message,
        //       life: 3000,
        //     });
        //   }
        // );

      },
    });
  }

  deleteSelectedCirels() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los cireles seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.selectedHomo.forEach(cirel => {
        //   this.cirelService.delete(cirel.id).subscribe(
        //     (res) => {
        //       this.messageService.add({
        //         severity: 'success',
        //         summary: 'Éxito',
        //         detail: 'Homopolímero Eliminado',
        //         life: 3000,
        //       });
        //       this.cirels = [];
        //       this.getAllUsers();
        //     },
        //     (err) => {
        //       this.messageService.add({
        //         severity: 'error',
        //         summary: 'Error',
        //         detail: err.message,
        //         life: 3000,
        //       });
        //     }
        //   );
        // })

        this.selectedCirel = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveCirel() {
    this.submitted = true;

    if (this.cirel.code) {
      // this.cirelService.update(this.cirel.code, this.cirel).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Actualizado',
      //       life: 3000,
      //     });
      //     this.cirels = [];
      //     this.getAllUsers();
      //   },
      //   (err) => {
      //     this.messageService.add({
      //       severity: 'error',
      //       summary: 'Error',
      //       detail: err.message,
      //       life: 3000,
      //     });
      //   }
      // );
    } else {
      // this.cirelService.create(this.cirel).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Creado',
      //       life: 3000,
      //     });
      //     this.cirels = [];
      //     this.getAllUsers();
      //   },
      //   (err) => {
      //     this.messageService.add({
      //       severity: 'error',
      //       summary: 'Error',
      //       detail: err.message,
      //       life: 3000,
      //     });
      //   }
      // );
    }

    this.cirels = [...this.cirels];
    this.cirelDialog = false;
    this.cirel = {};
  }

  hideDialog() {
    this.cirelDialog = false;
    this.submitted = false;
  }

  getAll() {
    // this.cirelService.getAll().subscribe((cirels) => {
    //   this.cirels = cirels;
    //   this.loading = false;
    // });
  }

}
