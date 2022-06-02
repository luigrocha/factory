import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler, DocumentEnum, OptionDocument } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';

@Component({
  selector: 'app-store-ceb',
  templateUrl: './store-ceb.component.html',
  styleUrls: ['./store-ceb.component.scss'],
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
export class StoreCebComponent implements OnInit {

  cellers: Celler[];

  celler: Celler;

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

  //newCellers: Celler[] = [JSON.parse('{ "balance": 0, "coat": 2, "pallets": 0, "weight": 50, "lote": "M240221", "numberDocument": "CEB-3", "material": { "id": 60, "name": "MB AOX 5", "typeMaterial": { "id": 3, "name": "MAB" } }, "location": { "id": 37, "location": "Z-D", "description": "Zona D" }, "document": { "id": 4, "name": "TM3", "description": "TRASLADO DE MATERIALES" } }')];
  newCellers: Celler[];

  numDocument: number;

  isEditing: boolean;

  constructor(
    private messageService: MessageService,
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private cellerService: CellerService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
      { label: 'CEB', routerLink: ['bodega/CEB'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
    this.getAllOptionsByDocumentCode(DocumentEnum.CEB);
    this.countByDocumentCode(DocumentEnum.CEB);
  }

  openNew() {
    this.newCeller = {
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
    this.newCellers = this.newCellers.filter(val => val.material !== celler.material);
    this.hideDialog();
  }

  saveItem() {
    this.submitted = true;
    if (this.isEditing) {
      this.newCellers[this.findIndexByMaterial(this.newCeller.material)] = this.newCeller;
      this.isEditing = false;
    } else if (this.isValidToSave()) {
      this.newCeller.numberDocument = 'CEB-' + this.numDocument;
      this.newCeller.observation = this.observation;
      this.newCeller.material = this.material;
      this.newCeller.location = this.celler.location; // *
      this.newCeller.document = this.celler.document;
      this.newCellers.push(this.newCeller);
      this.weightTotal -= this.newCeller.weight;
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
    this.celler = null;
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

  onLoteSelected(e: any) {
    const lote = e.value;
    this.newCeller.lote = lote.lote;
    this.calculateWeightAvailable(this.newCeller.lote);
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
    const coat = (this.newCeller.coat ? this.newCeller.coat : 0) * 25;
    const pallets = (this.newCeller.pallets ? this.newCeller.pallets : 0) * 1375;
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
        this.lotes = this.deleteCellerDuplicate(cellers);
      })
    );
  }

  countByDocumentCode(id: number) {
    this.cellerService.countByDocumentCode(id).subscribe(
      (count => {
        this.numDocument = count + 1;
      })
    );
  }

  deleteCellerDuplicate(cellers: any) {
    const cellersMap = cellers.map(celler => {
      return [celler.lote, celler];
    });

    return [...new Map(cellersMap).values()];
  }

  isValidToSave(): boolean {
    return this.newCeller.lote ? true : false;
  }

}
