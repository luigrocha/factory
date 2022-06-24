import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { DEFAULT_COAT, DEFAULT_PALLETS, DEFAULT_TYPE_COAT, DEFAULT_TYPE_PALLETS } from 'src/app/core/constants/cellers';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler, CellerDetail, CodeDocument, DocumentEnum, GenerateReceipt, GenerateReceiptItem, Location, OptionDocument } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { Observable } from 'rxjs';
import { TableColumn } from 'src/app/types/table.types';
import { CellerDetailService } from 'src/app/core/http/celler/celler-detail.service';

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

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  numDocument: CodeDocument;
  reazon$: Observable<OptionDocument[]>;
  types: TypeMaterial[];
  materials: Material[] = [];
  lotes: CellerDetail[] = [];
  locations: Location[] = [];
  cellers: CellerDetail[];
  columns: TableColumn<CellerDetail>[];
  itemsCoat = DEFAULT_TYPE_COAT;
  itemsPallets = DEFAULT_TYPE_PALLETS;
  srcPdf: any;
  fileName: string;
  enableButtons: boolean;
  items: MenuItem[];
  isSave: boolean;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private cellerService: CellerService,
    private cellerDetailService: CellerDetailService,
    private cdr: ChangeDetectorRef,
  ) {
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
      { label: 'CEB', routerLink: ['bodega/CEB'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
    this.getNewCodeDocumentByDocumentCode(DocumentEnum.CEB);
    this.reazon$ = this.cellerService.getAllOptionsByDocumentCode(DocumentEnum.CEB);
    this.getCellerDetailColums();
    this.form = this.fb.group({
      reazon: [null, [
        Validators.required,
      ]],
      observation: [null, [
        Validators.required,
        Validators.maxLength(128)
      ]],
      date: new Date(),
      dateDocument: [null, [
        Validators.required,
      ]],
      cellerItems: this.fb.array([]),
      observations: [null, [
        Validators.required,
      ]],
      numberCoat: [DEFAULT_COAT],
      numberPallet: [DEFAULT_PALLETS],
    });
  }

  getNewCodeDocumentByDocumentCode(id: number) {
    this.cellerService.getNewCodeDocumentByDocumentCode(id).subscribe(
      (numDocument => {
        this.numDocument = numDocument;
      })
    );
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(typeMaterial => {
      this.types = typeMaterial;
    });
  }

  getCellerDetailColums() {
    this.columns = [
      { field: 'type', header: 'Tipo' },
      { field: 'material', header: 'Producto' },
      { field: 'lote', header: 'Lote' },
      { field: 'location', header: 'Ubicación' },
      { field: 'availability', header: 'Disponible' },
      { field: 'amount', header: 'Unidades' },
      { field: 'balance', header: 'Saldos' },
      { field: 'coat', header: 'Sacos' },
      { field: 'pallets', header: 'Palets' },
      { field: 'weight', header: 'Peso' },
    ];
  }

  get reazon() {
    return this.form.get('reazon');
  }

  get observation() {
    return this.form.get('observation');
  }

  get date() {
    return this.form.get('date');
  }

  get dateDocument() {
    return this.form.get('dateDocument');
  }

  get cellerItemsFormArray() {
    return this.form.get('cellerItems') as FormArray;
  }

  get observations() {
    return this.form.get('observations');
  }

  get numberCoat() {
    return this.form.get('numberCoat');
  }

  get numberPallet() {
    return this.form.get('numberPallet');
  }

  getCellerDetailType(index: number) {
    return this.cellerItemsFormArray.at(index).get('type');
  }

  searchCellerDetailType(id: number): string {
    const typeMaterial = this.types.find(type => type.id === id);
    return typeMaterial ? typeMaterial.name : 'Selecciona Tipo';
  }

  getCellerDetailMaterial(index: number) {
    return this.cellerItemsFormArray.at(index).get('material');
  }

  searchCellerDetailMaterial(id: number): string {
    const material = this.materials.find(mat => mat.id === id);
    return material ? material.name : 'Selecciona Material';
  }

  getCellerDetailLote(index: number) {
    return this.cellerItemsFormArray.at(index).get('lote');
  }

  searchCellerDetailLote(id: number): string {
    const lote = this.lotes.find(lot => lot.id === id);
    return lote ? lote.lote : 'Selecciona Lote';
  }

  getCellerDetailLocation(index: number) {
    return this.cellerItemsFormArray.at(index).get('location');
  }

  searchCellerDetailLocation(id: number): string {
    const location = this.locations.find(lot => lot.id === id);
    return location ? location.description : 'Selecciona ubicación';
  }

  getCellerDetailAvailability(index: number) {
    return this.cellerItemsFormArray.at(index).get('availability');
  }

  getCellerDetailAmount(index: number) {
    return this.cellerItemsFormArray.at(index).get('amount');
  }

  getCellerDetailBalance(index: number) {
    return this.cellerItemsFormArray.at(index).get('balance');
  }

  getCellerDetailCoat(index: number) {
    return this.cellerItemsFormArray.at(index).get('coat');
  }

  getCellerDetailPallets(index: number) {
    return this.cellerItemsFormArray.at(index).get('pallets');
  }

  getCellerDetailWeight(index: number) {
    return this.cellerItemsFormArray.at(index).get('weight');
  }

  save() {
    if (this.form.invalid) {
      return;
    }



    this.generateReceipt();
  }

  openNew() {
    this.cellerItemsFormArray.push(this.fb.group({
      type: [null, [
        Validators.required,
      ]],
      material: [null, [
        Validators.required,
      ]],
      lote: [null, [
        Validators.required,
      ]],
      location: [null, [
        Validators.required,
      ]],
      availability: [0],
      amount: [0],
      balance: [0],
      coat: [0],
      pallets: [0],
      weight: [0]
    }));
  }

  onTypeSelected(e: any) {
    const type = e.value;
    this.getAllMaterialByType(type);
  }

  getAllMaterialByType(id: number) {
    this.materials = [];
    this.materialService.getAllMaterialByType(id).subscribe(
      (materials => {
        this.materials = materials;
      })
    );
  }

  onProductSelected(e: any) {
    const product = e.value;
    this.getCellerByMaterialCode(product);
  }

  getCellerByMaterialCode(id: number) {
    this.lotes = [];
    this.cellerDetailService.getByMaterialCode(id).subscribe(
      (cellers => {
        const lotes = this.deleteCellerDuplicateByLote(cellers);
        this.lotes = lotes.filter((lote: CellerDetail) => lote.weight > 0);
      }),
      (err) => {
        this.lotes = [];
      }
    );
  }

  onLoteSelected(e: any) {
    const lote = e.value;
    const celler = this.lotes.find(lo => lo.id === lote);
    this.getCellerByLocationCode(celler.location.id, celler.material.id);
  }

  getCellerByLocationCode(codeLocation: number, codeMaterial: number) {
    this.locations = [];
    this.cellers = [];
    this.cellerDetailService.getByLocationCode(codeLocation, codeMaterial).subscribe(
      (locations) => {
        this.cellers = locations;
        this.deleteCellerDuplicateByLocation(locations).forEach((location: CellerDetail) => this.locations.push(location.location));
      }
    );
  }

  onLocationSelected(e: any, index: number) {
    const location = e.value;
    this.calculateWeightAvailable(location, index);
  }

  calculateWeightAvailable(location: number, index) {
    let weightTotal = 0;
    this.cellers.forEach(celler => {
      if (celler.location.id === location) {
        weightTotal += celler.weight;
      }
    });
    this.cellerItemsFormArray.at(index).get('availability').setValue(weightTotal);
  }

  deleteCellerDuplicateByLocation(cellers: any) {
    const cellersMap = cellers.map(celler => {
      return [celler.location.id, celler];
    });
    return [...new Map(cellersMap).values()];
  }

  deleteCellerDuplicateByLote(cellers: any) {
    const cellersMap = cellers.map(celler => {
      return [celler.lote, celler];
    });
    return [...new Map(cellersMap).values()];
  }

  calculateWeight(index: number) {
    const balance = this.getCellerDetailBalance(index).value;
    const coat = this.getCellerDetailCoat(index).value * this.numberCoat.value;
    const pallets = this.getCellerDetailPallets(index).value * this.numberCoat.value * this.numberPallet.value;
    this.getCellerDetailWeight(index).setValue(balance + coat + pallets);

    if (this.getCellerDetailWeight(index).value < (this.getCellerDetailAvailability(index).value * -1)) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atención',
        detail: 'No se dispone la cantidad seleccionada',
        life: 3000,
      });
    }
  }

  generateReceipt() {
    const receiptItems: GenerateReceiptItem[] = [];

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

    // this.cellerService.generateReceiptPreview(DocumentEnum.CEB, receipt).subscribe(
    //   (data => {
    //     const type = data.body.type;
    //     this.fileName = data.headers.get('content-disposition').split('filename=')[1];
    //     this.srcPdf = URL.createObjectURL(
    //       new Blob([data.body], { type })
    //     );
    //     this.pdfDialog = true;
    //     this.enableButtons = true;
    //   })
    // );

  }

  onRowEditSave() {
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.cdr.detectChanges();
  }

  // openNew() {
  //   this.newCeller = {
  //     amount: 0,
  //     balance: 0,
  //     coat: 0,
  //     pallets: 0,
  //     weight: 0,
  //   };
  //   this.submitted = false;
  //   this.itemDialog = true;
  // }

  // editItem(celler: Celler) {
  //   this.newCeller = { ...celler };
  //   this.material = this.newCeller.material;
  //   this.typeMaterial = this.newCeller.material.typeMaterial;
  //   this.location = this.newCeller.location;
  //   this.getAllMaterialByType(this.typeMaterial.id);
  //   this.getCellerByMaterialCode(this.material.id);
  //   setTimeout(() => {
  //     this.celler = this.lotes.find(cell => cell.lote === this.newCeller.lote);
  //     this.calculateWeightAvailable(this.newCeller.lote);
  //   }, 1000);
  //   this.isEditing = true;
  //   this.itemDialog = true;
  // }

  // deleteItem(celler: Celler) {
  //   this.newCellers = this.newCellers.filter(val => val.material !== celler.material);
  //   this.hideDialog();
  // }

  // //unidades -> lamina, producto terminado y respuestos

  // saveItem() {
  //   this.submitted = true;
  //   if (this.isEditing) {
  //     this.newCellers[this.findIndexByMaterial(this.newCeller.material)] = this.newCeller;
  //     this.isEditing = false;
  //   } else if (this.isValidToSave()) {
  //     this.newCeller.numberDocument = this.numDocument.numDocument;
  //     this.newCeller.observation = this.observation;
  //     this.newCeller.material = this.material;
  //     this.newCeller.location = this.location;
  //     this.newCeller.document = { id: DocumentEnum.CEB };
  //     this.newCeller.date = this.date;
  //     this.newCeller.createdAt = this.createdAt;
  //     this.newCellers.push(this.newCeller);

  //   } else {
  //     this.messageService.add({
  //       severity: 'warn',
  //       summary: 'Atención',
  //       detail: 'Llene todos los campos',
  //       life: 3000,
  //     });
  //     this.itemDialog = true;
  //     return;
  //   }

  //   this.newCellers = [...this.newCellers];
  //   this.hideDialog();
  // }

  // hideDialog() {
  //   this.submitted = false;
  //   this.itemDialog = false;
  //   this.newCeller = {};
  //   this.typeMaterial = null;
  //   this.material = null;
  //   this.celler = null;
  //   this.location = null;
  //   this.weightTotal = 0;
  // }

  // findIndexByMaterial(material: Material): number {
  //   let index = -1;
  //   for (let i = 0; i < this.newCellers.length; i++) {
  //     if (this.newCellers[i].material === material) {
  //       index = i;
  //       break;
  //     }
  //   }
  //   return index;
  // }






  // getAllTypeMaterial() {
  //   this.materialService.getAllTypeMaterial().subscribe(
  //     (typeMaterials => {
  //       this.typeMaterials = typeMaterials;
  //     })
  //   );
  // }




  // deleteCellerDuplicateByLocation(cellers: any, lote: string) {
  //   const cellerByLote = cellers.filter(celler => celler.lote === lote);
  //   const cellersMap = cellerByLote.map(celler => {
  //     return [celler.location.id, celler];
  //   });
  //   return [...new Map(cellersMap).values()];
  // }

  // isValidToSave(): boolean {
  //   return this.newCeller.lote ? true : false;
  // }

  // saveCeb() {
  //   this.cellerService.create(this.newCellers).subscribe(
  //     (data) => {
  //       this.messageService.add({
  //         severity: 'success',
  //         summary: 'Éxito',
  //         detail: this.numDocument + ' Creado',
  //         life: 3000,
  //       });
  //       this.generateReceipt();
  //       this.newCeller = {};
  //       this.newCellers = [];
  //       this.optionDocument = {};
  //       this.observation = null;
  //       this.observations = null;
  //       this.date = null;
  //       this.getNewCodeDocumentByDocumentCode(DocumentEnum.CEB);
  //       this.hideDialog();

  //     },
  //     (err) => {
  //       console.log(err);
  //       this.messageService.add({
  //         severity: 'error',
  //         summary: 'Error',
  //         detail: err.error,
  //         life: 3000,
  //       });
  //     }
  //   );
  // }

  // generateReceipt() {
  //   const receiptItems: GenerateReceiptItem[] = [];

  //   this.newCellers.forEach(celler => {
  //     receiptItems.push({
  //       productType: celler.material.typeMaterial.name,
  //       productName: celler.material.name,
  //       lot: celler.lote,
  //       units: celler.amount,
  //       bags1KG: celler.balance,
  //       bags25KG: celler.coat,
  //       pallets55: celler.pallets,
  //       totalWeight: celler.weight,
  //       location: celler.location.description
  //     });
  //   });

  //   const receipt: GenerateReceipt = {
  //     receiptNumber: this.newCellers[0].numberDocument,
  //     receiptDate: this.newCellers[0].date,
  //     reason: this.optionDocument.name,
  //     reasonObservation: this.observation.toUpperCase(),
  //     observations: this.observations.toUpperCase(),
  //     deliveredBy: this.authService.getLoggedUser().name,
  //     receivedBy: null,
  //     items: receiptItems
  //   };

  //   this.cellerService.generateReceiptPreview(DocumentEnum.CEB, receipt).subscribe(
  //     (data => {
  //       const type = data.body.type;
  //       this.fileName = data.headers.get('content-disposition').split('filename=')[1];
  //       this.srcPdf = URL.createObjectURL(
  //         new Blob([data.body], { type })
  //       );
  //       this.pdfDialog = true;
  //       this.enableButtons = true;
  //     })
  //   );

  // }

}
