import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DEFAULT_COAT, DEFAULT_PALLETS, DEFAULT_TYPE_COAT, DEFAULT_TYPE_PALLETS } from 'src/app/core/constants/cellers';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler, CodeDocument, DocumentEnum, Location } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';

@Component({
  selector: 'app-store-mov',
  templateUrl: './store-mov.component.html',
  styleUrls: ['./store-mov.component.scss'],
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
export class StoreMovComponent implements OnInit {

  numDocument: CodeDocument;

  createdAt: Date = new Date();

  date: Date;

  observations: string;

  newCeller: Celler;

  newCellers: Celler[] = [];

  itemDialog: boolean;

  submitted: boolean;

  typeMaterials: TypeMaterial[];

  typeMaterial: TypeMaterial;

  materials: Material[];

  material: Material;

  cellers: Celler[];

  celler: Celler;

  lotes: Celler[];

  locationsOrigin: Location[];

  locationOrigin: Location;

  locationsDestiny: Location[];

  locationDestiny: Location;

  weightTotal: number;

  numberCoat = DEFAULT_COAT;

  numberPallet = DEFAULT_PALLETS;

  itemsCoat = DEFAULT_TYPE_COAT;

  itemsPallets = DEFAULT_TYPE_PALLETS;

  isEditing: boolean;

  msgInfo: any = [{
    severity: 'info',
    summary: 'Selecciona la fecha del docuemento para ingresar items'
  }];

  constructor(
    private messageService: MessageService,
    private breadcrumbService: BreadcrumbService,
    private cellerService: CellerService,
    private materialService: MaterialService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
      { label: 'MOV', routerLink: ['bodega/MOV'] },
    ]);
  }

  ngOnInit() {
    this.getNewCodeDocumentByDocumentCode(DocumentEnum.MOV);
    this.getAllTypeMaterial();
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

  saveItem() {
    this.submitted = true;
    if (this.isEditing) {
      const index = this.findIndexByMaterial(this.newCeller.material);
      this.newCellers[index] = this.newCeller;


      this.isEditing = false;
    } else if (this.isValidToSave()) {
      this.newCeller.numberDocument = this.numDocument.numDocument;
      this.newCeller.observation = this.observations;
      this.newCeller.material = this.material;
      this.newCeller.document = { id: DocumentEnum.MOV };
      this.newCeller.date = this.date;
      this.newCeller.createdAt = this.createdAt;
      this.createCellers(this.newCeller);
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

  editItem(celler: Celler) {
    this.newCeller = { ...celler };
    this.material = this.newCeller.material;
    this.typeMaterial = this.newCeller.material.typeMaterial;
    this.locationOrigin = this.newCeller.location;
    this.newCeller.location = this.locationOrigin;
    this.newCeller.amount *= -1;
    this.newCeller.balance *= -1;
    this.newCeller.coat *= -1;
    this.newCeller.pallets *= -1;
    this.newCeller.weight *= -1;

    const destiny = this.newCellers.findIndex(cell => cell === celler);
    this.locationDestiny = this.newCellers[destiny + 1].location;

    this.getAllMaterialByType(this.typeMaterial.id);
    this.getCellerByMaterialCode(this.material.id);
    setTimeout(() => {
      this.celler = this.lotes.find(cell => cell.lote === this.newCeller.lote);
      this.calculateWeightAvailable(this.newCeller.lote);
    }, 1000);
    this.isEditing = true;
    this.itemDialog = true;
  }

  deleteItem(celler: Celler) {
    const destiny = this.newCellers.findIndex(cell => cell === celler);
    const celler2 = this.newCellers[destiny + 1];

    this.newCellers = this.newCellers.filter(val => val.material !== celler.material);
    this.newCellers = this.newCellers.filter(cell => cell !== celler2);

    this.hideDialog();
  }

  createCellers(celler: Celler) {
    this.newCellers.push({
      ...celler,
      amount: celler.amount * -1,
      balance: celler.balance *= -1,
      coat: celler.coat *= -1,
      pallets: celler.pallets *= -1,
      weight: celler.weight *= -1,
      location: this.locationOrigin
    });

    this.newCellers.push({
      ...celler,
      amount: celler.amount * -1,
      balance: celler.balance *= -1,
      coat: celler.coat *= -1,
      pallets: celler.pallets *= -1,
      weight: celler.weight *= -1,
      location: this.locationDestiny
    });
  }


  hideDialog() {
    this.submitted = false;
    this.itemDialog = false;
    this.newCeller = {};
    this.typeMaterial = null;
    this.material = null;
    this.celler = null;
    this.locationOrigin = null;
    this.locationDestiny = null;
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

  getIndexPar(index: number): boolean {
    return index % 2 === 0 ? true : false;
  }

  onTypeSelected(e: any) {
    const type = e.value;
    this.getAllMaterialByType(type.id);
  }

  onProductSelected(e: any) {
    const product = e.value;
    this.getCellerByMaterialCode(product.id);
  }

  onLoteSelected(e: any) {
    const lote = e.value;
    this.newCeller.lote = lote.lote;
    this.calculateWeightAvailable(this.newCeller.lote);

    this.locationOrigin = this.celler.location;
    this.locationsOrigin = [];
    const locationsFilter: Celler[] = this.deleteCellerDuplicateByLocation(this.cellers, this.newCeller.lote);
    locationsFilter.forEach(loc => { this.locationsOrigin.push(loc.location); });
    this.getAllLocation();
  }

  onLocationSelected(e: any) {
    const loc = e.value;
    this.locationOrigin = loc;
    this.newCeller.lote = this.cellers.find(celler => celler.location.id === loc.id).lote;
    this.calculateWeightAvailable(this.newCeller.lote);
    this.getAllLocation();
  }

  calculateWeightAvailable(lote: string) {
    this.weightTotal = 0;
    this.cellers.forEach(celler => {
      if (celler.lote === lote) {
        this.weightTotal += celler.weight;
      }
    });
  }

  calculateWeight() {
    const balance = this.newCeller.balance ? this.newCeller.balance : 0;
    const coat = (this.newCeller.coat ? this.newCeller.coat : 0) * this.numberCoat;
    const pallets = (this.newCeller.pallets ? this.newCeller.pallets : 0) * this.numberCoat * this.numberPallet;
    this.newCeller.weight = balance + coat + pallets;

    if (this.newCeller.weight > this.weightTotal) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atención',
        detail: 'No se dispone la cantidad seleccionada',
        life: 3000,
      });
    }
  }

  getNewCodeDocumentByDocumentCode(id: number) {
    this.cellerService.getNewCodeDocumentByDocumentCode(id).subscribe(
      (numDocument => {
        this.numDocument = numDocument;
      }),
      (err) => {
        this.numDocument = {
          document: 'MOV',
          number: 1,
          numDocument: 'MOV-1'
        };
      }
    );
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

  getCellerByMaterialCode(id: number) {
    this.cellers = [];
    this.cellerService.getCellerByMaterialCode(id).subscribe(
      (cellers => {
        this.cellers = cellers;
        const lotes = this.deleteCellerDuplicateByLote(cellers);
        this.lotes = lotes.filter((lote: Celler) => lote.weight > 0);

      }),
      (err) => {
        this.celler = null;
        this.cellers = [];
        this.lotes = [];
      }
    );
  }

  getAllLocation() {
    this.locationsDestiny = [];
    this.cellerService.getAllLocation().subscribe(
      (locations => {
        // this.locationsDestiny = locations;
        this.locationsDestiny = locations.filter(location => this.locationOrigin.id !== location.id);
      })
    );
  }

  deleteCellerDuplicateByLote(cellers: any) {
    const cellersMap = cellers.map(celler => {
      return [celler.lote, celler];
    });
    return [...new Map(cellersMap).values()];
  }

  deleteCellerDuplicateByLocation(cellers: any, lote: string) {
    const cellerByLote = cellers.filter(celler => celler.lote === lote);
    const cellersMap = cellerByLote.map(celler => {
      return [celler.location.id, celler];
    });
    return [...new Map(cellersMap).values()];
  }

  isValidToSave(): boolean {
    return this.newCeller.lote ? true : false;
  }

}
