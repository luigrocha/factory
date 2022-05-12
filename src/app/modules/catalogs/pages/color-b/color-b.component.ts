import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ColorBService } from 'src/app/core/http/catalogs/color-b/color-b.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ColorB } from 'src/app/types/colorB.types';

@Component({
  selector: 'app-color-b',
  templateUrl: './color-b.component.html',
  styleUrls: ['./color-b.component.scss'],
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
export class ColorBComponent implements OnInit {

  colorDialog: boolean;

  selectedColor: ColorB[];

  submitted: boolean;

  cols: any[];

  colors: ColorB[];

  color: ColorB;

  loading = true;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private colorBService: ColorBService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Colores B', routerLink: ['home/catalogs/colores-b'] },
    ]);
  }

  ngOnInit() {
    this.getAll();
    this.cols = [
      { field: 'color', header: 'Color' },
      { field: 'colorA', header: 'Color Primario' },
      { field: 'index', header: 'Indice' },
      { field: 'dosage', header: 'Dosis' },
      { field: 'description', header: 'Descripción' },
    ];
  }

  openNew() {
    this.color = {};
    this.submitted = false;
    this.colorDialog = true;
  }

  editColor(color: ColorB) {
    this.color = { ...color };
    this.colorDialog = true;
  }

  deleteColor(color: ColorB) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el color ' + color.description + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.colorBService.delete(color.id).subscribe(
        //   (res) => {
        //     this.messageService.add({
        //       severity: 'success',
        //       summary: 'Éxito',
        //       detail: 'Homopolímero Eliminado',
        //       life: 3000,
        //     });
        //     this.colors = [];
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

  deleteSelectedColors() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los colores seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.selectedHomo.forEach(color => {
        //   this.colorBService.delete(color.id).subscribe(
        //     (res) => {
        //       this.messageService.add({
        //         severity: 'success',
        //         summary: 'Éxito',
        //         detail: 'Homopolímero Eliminado',
        //         life: 3000,
        //       });
        //       this.colors = [];
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

        this.selectedColor = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveColor() {
    this.submitted = true;

    if (this.color.color) {
      // this.colorBService.update(this.color.color, this.color).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Actualizado',
      //       life: 3000,
      //     });
      //     this.colors = [];
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
      // this.colorBService.create(this.color).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Creado',
      //       life: 3000,
      //     });
      //     this.colors = [];
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

    this.colors = [...this.colors];
    this.colorDialog = false;
    this.color = {};
  }

  hideDialog() {
    this.colorDialog = false;
    this.submitted = false;
  }

  getAll() {
    // this.colorBService.getAll().subscribe((colors) => {
    //   this.colors = colors;
    //   this.loading = false;
    // });
  }

}
