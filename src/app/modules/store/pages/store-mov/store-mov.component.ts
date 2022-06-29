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
  selector: 'app-store-mov',
  templateUrl: './store-mov.component.html',
  styleUrls: ['./store-mov.component.scss'],
  providers: [ConfirmationService],
})
export class StoreMovComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  numDocument: CodeDocument;
  types: TypeMaterial[];
  materials: Material[] = [];
  lotes: CellerDetail[] = [];
  locations: Location[] = [];
  destinys: Location[] = [];
  cellers: CellerDetail[];
  columns: TableColumn<CellerDetail>[];
  itemsCoat = DEFAULT_TYPE_COAT;
  itemsPallets = DEFAULT_TYPE_PALLETS;
  srcPdf: any;
  fileName: string;
  enableButtons: boolean;
  items: MenuItem[];
  pdfDialog: boolean;
  isEditing: boolean;
  isConfig: boolean;

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
      { label: 'MOV', routerLink: ['bodega/MOV'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
    this.getNewCodeDocumentByDocumentCode(DocumentEnum.MOV);
    this.getCellerDetailColums();
    this.form = this.fb.group({
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
    });
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
      { field: 'destiny', header: 'Destino' },
      { field: 'availability', header: 'Disponible' },
      { field: 'amount', header: 'Unidades' },
      { field: 'balance', header: 'Saldos' },
      { field: 'coat', header: 'Sacos' },
      { field: 'pallets', header: 'Palets' },
      { field: 'weight', header: 'Peso' },
    ];
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

  getCellerDetailDestiny(index: number) {
    return this.cellerItemsFormArray.at(index).get('destiny');
  }

  searchCellerDetailDestiny(id: number): string {
    const destiny = this.destinys.find(lot => lot.id === id);
    return destiny ? destiny.description : 'Selecciona destino';
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

  getCellerDetailNumberCoat(index: number) {
    return this.cellerItemsFormArray.at(index).get('numberCoat');
  }

  searchCellerDetailNumberCoat(id: number): number {
    const coat = this.itemsCoat.find(c => c === id);
    return coat ? coat : DEFAULT_COAT;
  }

  getCellerDetailNumberPallet(index: number) {
    return this.cellerItemsFormArray.at(index).get('numberPallet');
  }

  searchCellerDetailNumberPallet(id: number): number {
    const pallet = this.itemsCoat.find(pall => pall === id);
    return pallet ? pallet : DEFAULT_PALLETS;
  }

  save() {
    if (this.form.invalid) {
      return;
    }

    const body: GenerateReceipt = { ...this.form.getRawValue() };
    const itemsDown = [...this.form.getRawValue().cellerItems];
    body.numberDocument = this.numDocument.numDocument;
    body.origin = null;
    body.destiny = null;
    body.deliveredBy = this.authService.getLoggedUser().name;
    body.receivedBy = null;
    body.cellerItems.forEach(item => {
      item.document = DocumentEnum.MOV;
      item.amount *= -1;
      item.balance *= -1;
      item.coat *= -1;
      item.pallets *= -1;
      item.weight *= -1;
    });

    itemsDown.forEach(item => {
      item.document = DocumentEnum.MOV;
      item.location = item.destiny;
      body.cellerItems.push(item);
    });

    this.cellerService.create(body).subscribe(
      (data => {
        this.toastService.success(body.numberDocument + ' Creado');
        // this.generateReceipt(body);
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
      destiny: [null, [
        Validators.required,
      ]],
      availability: [0],
      amount: [0],
      balance: [0],
      coat: [0],
      pallets: [0],
      weight: [0],
      numberCoat: [DEFAULT_COAT],
      numberPallet: [DEFAULT_PALLETS],
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
    this.getAllLocationsDestiny(location);
    this.calculateWeightAvailable(location, index);
  }

  getAllLocationsDestiny(location: any) {
    this.cellerService.getAllLocation().subscribe(
      (locations => {
        this.destinys = locations.filter(loc => loc.id !== location);
      })
    );
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
    const coat = this.getCellerDetailCoat(index).value * this.getCellerDetailNumberCoat(index).value;
    const pallets = this.getCellerDetailPallets(index).value * this.getCellerDetailNumberCoat(index).value
      * this.getCellerDetailNumberPallet(index).value;
    this.getCellerDetailWeight(index).setValue(balance + coat + pallets);

    if (this.getCellerDetailWeight(index).value > this.getCellerDetailAvailability(index).value * -1) {
      this.toastService.warning('No se dispone la cantidad seleccionada');
    }
  }

  generateReceipt(body: GenerateReceipt) {
    this.cellerService.generateReceiptPreview(DocumentEnum.MOV, body).subscribe(
      (data => {
        const type = data.body.type;
        this.fileName = data.headers.get('content-disposition').split('filename=')[1];
        this.srcPdf = URL.createObjectURL(
          new Blob([data.body], { type })
        );
        this.pdfDialog = true;
        this.enableButtons = true;
      })
    );

  }

  onRowEditSave() {
    this.isEditing = false;
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.isEditing = false;
    this.cdr.detectChanges();
  }

  deleteRow(index: number) {
    this.cellerItemsFormArray.removeAt(index);
  }

  getSeverityTag(index: number): string {
    return this.getCellerDetailAvailability(index).value > 0 ? 'success' : 'warning';
  }

}
