import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { DEFAULT_COAT, DEFAULT_PALLETS, DEFAULT_TYPE_COAT, DEFAULT_TYPE_PALLETS } from 'src/app/core/constants/cellers';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler, CodeDocument, DocumentEnum, Location, OptionDocument } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';

@Component({
  selector: 'app-store-tm1',
  templateUrl: './store-tm1.component.html',
  styleUrls: ['./store-tm1.component.scss'],
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
export class StoreTm1Component implements OnInit {

  cellers: Celler[];

  itemDialog: boolean;

  submitted: boolean;

  typeMaterials: TypeMaterial[];

  typeMaterial: TypeMaterial;

  materials: Material[];

  material: Material;

  lotes: Celler[];

  optionDocuments: OptionDocument[];

  optionDocument: OptionDocument;

  observation: string;

  weightTotal: number;

  newCeller: Celler;

  newCellers: Celler[] = [];

  numDocument: CodeDocument;

  isEditing: boolean;

  createdAt: Date = new Date();

  date: Date;

  observations: string;

  locations: Location[];

  location: Location;

  pdfDialog: boolean;

  msgInfo: any = [{
    severity: 'info',
    summary: 'Selecciona un motivo y llena la observación para ingresar items'
  }];

  numberCoat = DEFAULT_COAT;

  numberPallet = DEFAULT_PALLETS;

  itemsCoat = DEFAULT_TYPE_COAT;

  itemsPallets = DEFAULT_TYPE_PALLETS;

  constructor(
    private messageService: MessageService,
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private cellerService: CellerService,
    private authService: AuthService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
      { label: 'TM1', routerLink: ['bodega/TM1'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
    this.getAllOptionsByDocumentCode(DocumentEnum.TM1);
    this.getNewCodeDocumentByDocumentCode(DocumentEnum.TM1);
    this.getAllLocation();
  }

  openNew() {
    this.newCeller = {
      amount: 0,
      balance: 0,
      coat: 0,
      pallets: 0,
      weight: 0,
    };
    this.submitted = false;
    this.itemDialog = true;
  }

  editItem(celler: Celler) {
    this.newCeller = { ...celler };
    this.material = this.newCeller.material;
    this.typeMaterial = this.newCeller.material.typeMaterial;
    this.location = this.newCeller.location;
    this.getAllMaterialByType(this.typeMaterial.id);
    this.getCellerByMaterialCode(this.material.id);
    setTimeout(() => {
      this.calculateWeightAvailable(this.newCeller.lote, this.location);
    }, 1000);
    this.isEditing = true;
    this.itemDialog = true;
  }

  deleteItem(celler: Celler) {
    this.newCellers = this.newCellers.filter(val => val.material !== celler.material);
    this.hideDialog();
  }

  saveItem() {
    this.submitted = true;
    if (this.isEditing) {
      this.newCellers[this.findIndexByMaterial(this.newCeller.material)] = this.newCeller;
      this.isEditing = false;
    } else if (this.isValidToSave()) {
      this.newCeller.lote = this.newCeller.lote.toUpperCase();
      this.newCeller.numberDocument = this.numDocument.numDocument;
      this.newCeller.observation = this.observation;
      this.newCeller.material = this.material;
      this.newCeller.location = this.location;
      this.newCeller.document = { id: DocumentEnum.TM1 };
      this.newCeller.date = this.date;
      this.newCeller.createdAt = this.createdAt;
      this.newCellers.push(this.newCeller);
      this.weightTotal += this.newCeller.weight;
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atención',
        detail: 'Llene todos los campos',
        life: 3000,
      });
      this.itemDialog = true;
      return;
    }

    this.newCellers = [...this.newCellers];
    this.hideDialog();
  }

  hideDialog() {
    this.submitted = false;
    this.itemDialog = false;
    this.newCeller = {};
    this.typeMaterial = null;
    this.material = null;
    this.location = null;
    this.weightTotal = 0;
  }

  findIndexByMaterial(material: Material): number {
    let index = -1;
    for (let i = 0; i < this.newCellers.length; i++) {
      if (this.newCellers[i].material === material) {
        index = i;
        break;
      }
    }
    return index;
  }

  onTypeSelected(e: any) {
    const type = e.value;
    this.getAllMaterialByType(type.id);
  }

  onProductSelected(e: any) {
    const product = e.value;
    this.getCellerByMaterialCode(product.id);
  }

  onLocationSelected(e: any) {
    const loc = e.value;
    this.calculateWeightAvailable(this.newCeller.lote, loc);
  }

  calculateWeightAvailable(lote: string, loc: Location) {
    this.weightTotal = 0;
    this.cellers.forEach(celler => {
      if (celler.lote === lote && celler.location.location === loc.location) {
        this.weightTotal += celler.weight;
      }
    });
  }

  calculateWeight() {
    const balance = this.newCeller.balance ? this.newCeller.balance : 0;
    const coat = (this.newCeller.coat ? this.newCeller.coat : 0) * this.numberCoat;
    const pallets = (this.newCeller.pallets ? this.newCeller.pallets : 0) * this.numberCoat * this.numberPallet;
    this.newCeller.weight = balance + coat + pallets;
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(
      (typeMaterials => {
        this.typeMaterials = typeMaterials;
      })
    );
  }

  getAllMaterialByType(id: number) {
    this.materials = [];
    this.materialService.getAllMaterialByType(id).subscribe(
      (materials => {
        this.materials = materials;
      })
    );
  }

  getAllOptionsByDocumentCode(id: number) {
    this.cellerService.getAllOptionsByDocumentCode(id).subscribe(
      (optionDocument) => {
        this.optionDocuments = optionDocument;
      }
    );
  }

  getCellerByMaterialCode(id: number) {
    this.cellers = [];
    this.cellerService.getCellerByMaterialCode(id).subscribe(
      (cellers => {
        this.cellers = cellers;
        this.lotes = this.deleteCellerDuplicateByLote(cellers);
      }),
      (err) => {
        this.cellers = [];
        this.lotes = [];
      }
    );
  }

  getNewCodeDocumentByDocumentCode(id: number) {
    this.cellerService.getNewCodeDocumentByDocumentCode(id).subscribe(
      (numDocument => {
        this.numDocument = numDocument;
      })
    );
  }

  getAllLocation() {
    this.cellerService.getAllLocation().subscribe(
      (locations => {
        this.locations = locations;
      })
    );
  }

  deleteCellerDuplicateByLote(cellers: any) {
    const cellersMap = cellers.map(celler => {
      return [celler.lote, celler];
    });
    return [...new Map(cellersMap).values()];
  }

  isValidToSave(): boolean {
    return this.newCeller.lote ? true : false;
  }

  saveCib() {
    this.cellerService.create(this.newCellers).subscribe(
      (data) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: this.numDocument + ' Creado',
          life: 3000,
        });
        this.generateReceipt();
        this.newCeller = {};
        this.newCellers = [];
        this.optionDocument = {};
        this.observation = null;
        this.observations = null;
        this.date = null;
        this.getNewCodeDocumentByDocumentCode(DocumentEnum.TM1);
        this.hideDialog();

      },
      (err) => {
        console.log(err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: err.error,
          life: 3000,
        });
      }
    );
  }

  generateReceipt() {

    this.pdfDialog = true;
    // const receiptItems: GenerateReceiptItem[] = [];

    // this.newCellers.forEach(celler => {
    //   receiptItems.push({
    //     productType: celler.material.typeMaterial.name,
    //     productName: celler.material.name,
    //     lot: celler.lote,
    //     units: celler.amount,
    //     bags1KG: celler.balance,
    //     bags25KG: celler.coat,
    //     pallets55: celler.pallets,
    //     totalWeight: celler.weight,
    //     location: celler.location.description
    //   });
    // });

    // const receipt: GenerateReceipt = {
    //   receiptNumber: this.newCellers[0].numberDocument,
    //   receiptDate: this.newCellers[0].date,
    //   reason: this.optionDocument.name,
    //   reasonObservation: this.observation.toUpperCase(),
    //   observations: this.observations.toUpperCase(),
    //   deliveredBy: this.authService.getLoggedUser().name,
    //   receivedBy: null,
    //   items: receiptItems
    // };
    // this.cellerService.generateReceipt(DocumentEnum.TM1, receipt).subscribe(
    //   (data) => {
    //     console.log(data);
    //   }
    // );

  }

}
