import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { HomopolimerosService } from 'src/app/core/http/catalogs/homopolimeros/homopolimeros.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Homopolimero } from 'src/app/types/homopolimero.types';
import { User } from 'src/app/types/user.types';

@Component({
  selector: 'app-homopolimeros',
  templateUrl: './homopolimeros.component.html',
  styleUrls: ['./homopolimeros.component.scss'],
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
export class HomopolimerosComponent implements OnInit {

  homoDialog: boolean;

  selectedHomo: Homopolimero[];

  submitted: boolean;

  cols: any[];

  homos: Homopolimero[];

  homo: Homopolimero;

  loading = true;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private homoService: HomopolimerosService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Homopolímeros', routerLink: ['home/catalogs/homopolimeros'] },
    ]);
  }

  ngOnInit() {
    this.getAllUsers();
    this.cols = [
      { field: 'percent', header: 'Porcentaje' },
      { field: 'hp', header: 'HP' },
    ];
  }

  openNew() {
    this.homo = {};
    this.submitted = false;
    this.homoDialog = true;
  }

  editHomo(homo: Homopolimero) {
    this.homo = { ...homo };
    this.homoDialog = true;
  }

  deleteHomo(homo: Homopolimero) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el homopolímero ' + homo.hp + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.homoService.delete(homo.id).subscribe(
        //   (res) => {
        //     this.messageService.add({
        //       severity: 'success',
        //       summary: 'Éxito',
        //       detail: 'Homopolímero Eliminado',
        //       life: 3000,
        //     });
        //     this.homos = [];
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

  deleteSelectedHomo() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los homopolímeros seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.selectedHomo.forEach(homo => {
        //   this.homoService.delete(homo.id).subscribe(
        //     (res) => {
        //       this.messageService.add({
        //         severity: 'success',
        //         summary: 'Éxito',
        //         detail: 'Homopolímero Eliminado',
        //         life: 3000,
        //       });
        //       this.homos = [];
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

        this.selectedHomo = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveHomo() {
    this.submitted = true;

    if (this.homo.id) {
      // this.homoService.update(this.homo.id, this.homo).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Actualizado',
      //       life: 3000,
      //     });
      //     this.homos = [];
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
      // this.homoService.create(this.homo).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Creado',
      //       life: 3000,
      //     });
      //     this.homos = [];
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

    this.homos = [...this.homos];
    this.homoDialog = false;
    this.homo = {};
  }

  hideDialog() {
    this.homoDialog = false;
    this.submitted = false;
  }

  getAllUsers() {
    // this.homoService.getAll().subscribe((homos) => {
    //   this.homos = homos;
    //   this.loading = false;
    // });
  }

}
