import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler, Document, DocumentEnum, GenerateReceipt, Location } from 'src/app/types/celler.types';
import { TypeMaterial } from 'src/app/types/material.types';
import { TypePermission } from 'src/app/types/permission';
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';

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

  items: MenuItem[] = [];

  cellerSelect: Celler;

  types: TypeMaterial[];

  locations: Location[];

  pdfDialog: boolean;

  srcPdf: any;

  fileName: string;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private cellerService: CellerService,
    private permissionService: PermissionService,
  ) {
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
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
    this.items = [
      {
        label: 'Ver Documento',
        icon: 'pi pi-file-pdf',
        command: (e) => this.getReceipt(this.cellerSelect)
      },
      {
        label: 'Anular',
        icon: 'pi pi-trash',
        // command: (e) => this.deleteUser(this.cellerSelect)
      }
    ];
  }

  hideDialog() {
    this.cellerDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.cellerService.getAll().subscribe((cellers) => {
      this.cellers = cellers;
      this.loading = false;
      this.cellers.forEach(celler => celler.date = new Date(celler.date));
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


  getReceipt(celler: Celler) {
    let typeDocument = 0;
    const type = celler.numberDocument.split('-')[0];
    if (type === 'CEB') {
      typeDocument = DocumentEnum.CEB;
    } else if (type === 'CIB') {
      typeDocument = DocumentEnum.CIB;
    } else if (type === 'MOV') {
      typeDocument = DocumentEnum.MOV;
    } else if (type === 'TM1') {
      typeDocument = DocumentEnum.TM1;
    } else if (type === 'TM5') {
      typeDocument = DocumentEnum.TM5;
    } else if (type === 'CEP') {
      typeDocument = DocumentEnum.CEP;
    }

    this.cellerService.getReceipt(celler.numberDocument, typeDocument).subscribe(
      (data => {
        const type = data.body.type;
        this.fileName = data.headers.get('content-disposition').split('filename=')[1];
        this.srcPdf = URL.createObjectURL(
          new Blob([data.body], { type })
        );
        this.pdfDialog = true;
      })
    );
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
