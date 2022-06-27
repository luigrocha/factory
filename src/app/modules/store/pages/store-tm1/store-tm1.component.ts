import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { DEFAULT_COAT, DEFAULT_PALLETS, DEFAULT_TYPE_COAT, DEFAULT_TYPE_PALLETS } from 'src/app/core/constants/cellers';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { CellerDetail, CodeDocument, DocumentEnum, GenerateReceipt, Location, OptionDocument } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { TableColumn } from 'src/app/types/table.types';
import { CellerDetailService } from 'src/app/core/http/celler/celler-detail.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-store-tm1',
  templateUrl: './store-tm1.component.html',
  styleUrls: ['./store-tm1.component.scss'],
  providers: [ConfirmationService],
})
export class StoreTm1Component implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  numDocument: CodeDocument;
  origins: OptionDocument[] = [];
  destinys: OptionDocument[] = [];
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
  pdfDialog: boolean;

  constructor(
    private fb: FormBuilder,
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private cellerService: CellerService,
    private cellerDetailService: CellerDetailService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private toastService: ToastService,
  ) {
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
      { label: 'TM1', routerLink: ['bodega/TM1'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
    this.getNewCodeDocumentByDocumentCode(DocumentEnum.TM1);
    this.getCellerDetailColums();
    this.getAllReasons();
    this.getAllLocations();
    this.form = this.fb.group({
      origin: [null, [
        Validators.required,
      ]],
      destiny: [null, [
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
      numberCoat: [DEFAULT_COAT],
      numberPallet: [DEFAULT_PALLETS],
    });
  }

  getAllReasons() {
    this.cellerService.getAllOptionsByDocumentCode(DocumentEnum.TM1).subscribe((reasons => {
      this.origins = reasons;
    }));
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

  getAllLocations() {
    this.cellerService.getAllLocation().subscribe(locations => {
      this.locations = locations;
    });
  }

  getCellerDetailColums() {
    this.columns = [
      { field: 'type', header: 'Tipo' },
      { field: 'material', header: 'Producto' },
      { field: 'lote', header: 'Lote' },
      { field: 'location', header: 'Ubicación' },
      { field: 'amount', header: 'Unidades' },
      { field: 'balance', header: 'Saldos' },
      { field: 'coat', header: 'Sacos' },
      { field: 'pallets', header: 'Palets' },
      { field: 'weight', header: 'Peso' },
    ];
  }

  get origin() {
    return this.form.get('origin');
  }

  get destiny() {
    return this.form.get('destiny');
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

  getCellerDetailLocation(index: number) {
    return this.cellerItemsFormArray.at(index).get('location');
  }

  searchCellerDetailLocation(id: number): string {
    const location = this.locations.find(lot => lot.id === id);
    return location ? location.description : 'Selecciona ubicación';
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

    const body: GenerateReceipt = { ...this.form.getRawValue() };
    body.numberDocument = this.numDocument.numDocument;
    body.deliveredBy = this.authService.getLoggedUser().name;
    body.receivedBy = null;
    body.cellerItems.forEach(item => item.document = DocumentEnum.TM1);

    this.cellerService.create(body).subscribe(
      (data => {
        this.toastService.success(body.numberDocument + ' Creado');
        // this.generateReceipt(body);
        this.enableButtons = true;
      })
    );
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
      amount: [0],
      balance: [0],
      coat: [0],
      pallets: [0],
      weight: [0]
    }));
  }

  onOriginSelected(e: any) {
    const origin = e.value;
    console.log(origin);

    this.destinys = this.origins.filter(option => option.name !== origin);
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
    // this.getCellerByMaterialCode(product);
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

  onLocationSelected(e: any, index: number) {
    const location = e.value;
    this.calculateWeightAvailable(location, index);
  }

  calculateWeightAvailable(location: number, index) {
    let weightTotal = 0;
    if (this.cellers) {
      this.cellers.forEach(celler => {
        if (celler.location.id === location) {
          weightTotal += celler.weight;
        }
      });
    }
    // this.cellerItemsFormArray.at(index).get('availability').setValue(weightTotal);
  }

  calculateWeight(index: number) {
    const balance = this.getCellerDetailBalance(index).value;
    const coat = this.getCellerDetailCoat(index).value * this.numberCoat.value;
    const pallets = this.getCellerDetailPallets(index).value * this.numberCoat.value * this.numberPallet.value;
    this.getCellerDetailWeight(index).setValue(balance + coat + pallets);
  }

  generateReceipt(body: GenerateReceipt) {
    this.cellerService.generateReceiptPreview(DocumentEnum.TM1, body).subscribe(
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

  onRowEditSave() {
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.cdr.detectChanges();
  }
}
