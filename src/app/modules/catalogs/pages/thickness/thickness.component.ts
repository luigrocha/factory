import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ThicknessService } from 'src/app/core/http/catalogs/thickness/thickness.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Thickness } from 'src/app/types/thickness.types';

@Component({
  selector: 'app-thickness',
  templateUrl: './thickness.component.html',
  styleUrls: ['./thickness.component.scss'],
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
export class ThicknessComponent implements OnInit {

  thicknesDialog: boolean;

  selectedThicknes: Thickness[];

  submitted: boolean;

  cols: any[];

  thickness: Thickness[];

  thicknes: Thickness;

  loading = true;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private thicknessService: ThicknessService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Grosor', routerLink: ['home/catalogs/grosor'] },
    ]);
  }

  ngOnInit() {
    this.getAll();
    this.cols = [
      { field: 'weight', header: 'Peso' },
      { field: 'thickness', header: 'Grosor' },
      { field: 'usd', header: 'USD' },
    ];
  }

  openNew() {
    this.thicknes = {};
    this.submitted = false;
    this.thicknesDialog = true;
  }

  editThickness(thicknes: Thickness) {
    this.thicknes = { ...thicknes };
    this.thicknesDialog = true;
  }

  deleteThickness(thicknes: Thickness) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el item ' + thicknes.weight + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.thicknessService.delete(thicknes.id).subscribe(
        //   (res) => {
        //     this.messageService.add({
        //       severity: 'success',
        //       summary: 'Éxito',
        //       detail: 'Homopolímero Eliminado',
        //       life: 3000,
        //     });
        //     this.thickness = [];
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

  deleteSelectedThicknesss() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los colores seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.selectedHomo.forEach(thicknes => {
        //   this.thicknessService.delete(thicknes.id).subscribe(
        //     (res) => {
        //       this.messageService.add({
        //         severity: 'success',
        //         summary: 'Éxito',
        //         detail: 'Homopolímero Eliminado',
        //         life: 3000,
        //       });
        //       this.thickness = [];
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

        this.selectedThicknes = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveThickness() {
    this.submitted = true;

    if (this.thicknes.id) {
      // this.thicknessService.update(this.thicknes.id, this.thicknes).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Actualizado',
      //       life: 3000,
      //     });
      //     this.thickness = [];
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
      // this.thicknessService.create(this.thicknes).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Creado',
      //       life: 3000,
      //     });
      //     this.thickness = [];
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

    this.thickness = [...this.thickness];
    this.thicknesDialog = false;
    this.thicknes = {};
  }

  hideDialog() {
    this.thicknesDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.thicknessService.getAll().subscribe((thickness) => {
      this.thickness = thickness;
      this.loading = false;
    });
  }

}
