import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BreadcrumbService} from 'src/app/core/services/breadcrumb.service';
import {OrderService} from '../../../../core/http/orders/order.service';
import {Order} from '../../../../types/order.types';
import {ActivatedRoute} from '@angular/router';
import {MixtureService} from '../../../../core/http/mixture/mixture.service';
import {ProjectService} from '../../../../core/http/projects/project.service';
import {Project} from '../../../../types/project.types';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from '../../../../core/constants/form-error';
import {MaterialService} from '../../../../core/http/materials/materials.service';
import {Material, TypeMaterial} from '../../../../types/material.types';
import {TableColumn} from '../../../../types/table.types';
import {MixtureCreate, MixtureDetail, MixtureDetailRes, MixtureRes, MixtureShort} from '../../../../types/mixture.types';
import {ToastService} from '../../../../core/services/toast.service';
import {AuthService} from '../../../../core/auth/service/auth.service';
import {DieService} from '../../../../core/http/dies/die.service';
import {Die} from '../../../../types/dies.types';
import {WEIGHT_EXTRUSION, WEIGHT_NOMINAL} from '../../../../core/constants/mixture';
import {pdfDefaultOptions} from 'ngx-extended-pdf-viewer';

@Component({
  selector: 'app-create-mixture',
  templateUrl: './create-mixture.component.html',
  styleUrls: ['./create-mixture.component.scss']
})
export class CreateMixtureComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  types: TypeMaterial[];
  materials: Material[] = [];
  allMaterials: Material[] = [];
  dies: Die[];
  showFilters: boolean;
  numberMixture: number;
  order: Order;
  project: Project;
  totalPercent = 0;
  totalToStop = 0;
  totalToCreate = 0;
  totalReal = 0;
  mixtureTo: string;
  sheets: number;
  numberParam: number;
  isEditing: boolean;
  isSusses: boolean;
  isSave: boolean;
  mixtureEdit: MixtureRes;
  pdfDialog: boolean;
  fileName: string;
  srcPdf: any;
  mixtures: MixtureShort[];
  mixtureFilter: MixtureShort;

  columns: TableColumn<MixtureDetail>[];

  constructor(
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    private breadcrumbService: BreadcrumbService,
    private activatedRoute: ActivatedRoute,
    private orderService: OrderService,
    private projectService: ProjectService,
    private mixtureService: MixtureService,
    private materialService: MaterialService,
    private dieService: DieService,
    private toastService: ToastService,
    private authService: AuthService,
  ) {
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
    this.breadcrumbService.setItems([
      {label: 'Módulo de Mezcla'},
      {label: 'Diseño de Mezcla', routerLink: ['/home/mezcla']},
      {label: 'Crear Diseño de Mezcla'},
    ]);
  }

  get weightNominal() {
    return WEIGHT_NOMINAL;
  }

  get weightExtrusion() {
    return WEIGHT_EXTRUSION;
  }

  get die() {
    return this.form.get('die');
  }

  get prepare() {
    return this.form.get('prepare');
  }

  get preMixture() {
    return this.form.get('preMixture');
  }

  get rowsFormArray() {
    return this.form.get('rows') as FormArray;
  }

  get observation() {
    return this.form.get('observation');
  }

  ngOnInit() {
    this.getAllMaterial();
    this.form = this.fb.group({
      die: [null, [
        Validators.required,
      ]],
      prepare: [0, [
        Validators.required,
      ]],
      preMixture: [200, [
        Validators.required,
      ]],
      rows: this.fb.array([]),
      observation: [null, [
        Validators.required,
      ]],
    });
    this.columns = [
      {field: 'type', header: 'Tipo'},
      {field: 'material', header: 'Producto'},
      {field: 'percent', header: 'Porcentaje'},
      {field: 'stop', header: 'Kg por parada'},
      {field: 'total', header: 'Kg total '},
    ];
    this.getAllTypeMaterial();
    this.getOrderByLot();
  }

  getAllMaterial() {
    this.materialService.getAllMaterial().subscribe(materials => {
      this.allMaterials = materials;
    });
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(typeMaterial => {
      this.types = typeMaterial;
    });
  }

  getAll() {
    this.mixtureService.getAll().subscribe(mixtures => {
      this.mixtures = mixtures;
    });
  }

  getOrderByLot() {
    this.activatedRoute.params.subscribe(params => {
      this.numberParam = parseInt(params.num, 0);
      if (this.numberParam === 0) {
        this.getNumberToCreate();
        this.orderService.getOrderByLot(params.lote).subscribe(order => {
          this.order = order;
          // this.sheets = order.quantity;
          this.getProjectToCodeGen(order.productCode);
        });
      } else {
        this.isEditing = true;
        this.getMixtureByNumber(params.num);
      }
    });
  }

  getNumberToCreate() {
    this.mixtureService.getNumberToCreate().subscribe(numberMixture => {
      this.numberMixture = numberMixture;
    });
  }

  getProjectToCodeGen(codeGen: string) {
    this.projectService.getProjectToCodeGen(codeGen).subscribe(project => {
      this.project = project;

      this.getByDieProduct(project.dieProduct.id);
      this.mixtureTo = `${project.homoPolymer.hpCode}${project.talc ? project.talc.lpCode : ''}${project.colorB.id}`;
      this.calculateRows();
    });
  }

  getMixtureByNumber(num: number) {
    this.mixtureService.getMixtureByNumber(num).subscribe(
      mixture => {
        this.mixtureEdit = mixture;
        this.numberMixture = mixture.number;
        this.order = mixture.order;
        // this.sheets = mixture.order.quantity;
        this.totalReal = mixture.totalReal;
        this.totalToCreate = mixture.total;
        this.die.setValue(mixture.die.id);
        this.prepare.setValue(mixture.prepare);
        this.preMixture.setValue(mixture.preMixture);
        mixture.rows.forEach(row => {
          this.rowsFormArray.push(this.addRowData(row));
        });
        this.observation.setValue(mixture.observation);
        this.getProjectToCodeGen(mixture.order.productCode);
      }
    );
  }

  addRowData(mixture: MixtureDetailRes): FormGroup {
    return this.fb.group({
      index: this.rowsFormArray.length,
      type: [mixture.material.typeMaterial.id, [
        Validators.required,
      ]],
      material: [mixture.material.id, [
        Validators.required,
      ]],
      percent: [mixture.percent, [
        Validators.required,
      ]],
      stop: [mixture.stop, [
        Validators.required,
      ]],
      total: [mixture.total, [
        Validators.required,
      ]],
    });
  }

  getByDieProduct(code: number) {
    this.dieService.getByDieProduct(code).subscribe(dies => {
      this.dies = dies;
    });
  }

  searchDie(id: number): Die {
    return this.dies.find(die => die.id === id);
  }

  getSheet(id: number) {
    if (this.mixtureEdit) {
      this.sheets = this.mixtureEdit.order.quantity / this.searchDie(id).quantity;
    } else {
      this.sheets = this.order.quantity / this.searchDie(id).quantity;
    }
    return this.sheets;
  }

  getSheetPerCut(id: number) {
    const leafWidth = this.searchDie(id).leafWidth / 1000;
    return Math.trunc(this.weightExtrusion / leafWidth);
  }

  getNumberCut(id: number) {
    return this.order.quantity / this.getSheetPerCut(id);
  }

  getTotalReal(id: number) {
    const leafLength = this.searchDie(id).leafLength / 1000;
    const gsm = this.project.gsm / 1000;
    this.totalReal = this.weightNominal * leafLength * gsm * this.getNumberCut(id);
  }

  getDifference(id: number) {
    this.getTotalReal(id);
    return this.totalToCreate - this.totalReal;
  }

  save() {
    if (!this.isSave) {
      return;
    }
    if (this.form.invalid) {
      return;
    }
    if (this.totalPercent !== 100) {
      this.toastService.warning('No se puede crear mezcla, verifique los porcentajes');
      return;
    }
    const body: MixtureCreate = {...this.form.getRawValue()};
    if (this.mixtureEdit) {
      body.id = this.mixtureEdit.id;
      body.date = this.mixtureEdit.date;
    } else {
      body.date = new Date();
    }
    body.order = this.order.id;
    body.number = this.numberMixture;
    body.documentBy = this.authService.getLoggedUser().name;
    body.documentTo = 'Responsables Mezcla';
    body.mixture = this.mixtureTo;
    body.total = this.totalToCreate;
    body.totalReal = this.totalReal;

    if (body.id) {
      this.mixtureService.edit(body.id, body).subscribe(
        (data => {
          this.toastService.success('Mezcla actualizada');
          this.isSusses = true;
          this.prepare.reset();
        })
      );
    } else {
      this.mixtureService.create(body).subscribe(
        (data => {
          this.toastService.success('Mezcla creada');
          this.isSusses = true;
          this.prepare.reset();
        })
      );
    }
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

  addRow() {
    this.rowsFormArray.push(this.fb.group({
      index: this.rowsFormArray.length,
      type: [null, [
        Validators.required,
      ]],
      material: [null, [
        Validators.required,
      ]],
      percent: [0, [
        Validators.required,
      ]],
      stop: [0, [
        Validators.required,
      ]],
      total: [0, [
        Validators.required,
      ]],
    }));
  }

  getRowsType(index: number) {
    return this.rowsFormArray.at(index).get('type');
  }

  searchRowsType(id: number): string {
    const typeMaterial = this.types.find(type => type.id === id);
    return typeMaterial ? typeMaterial.name : 'Selecciona Tipo';
  }

  getRowsMaterial(index: number) {
    return this.rowsFormArray.at(index).get('material');
  }

  searchRowsMaterial(id: number): string {
    const material = this.allMaterials.find(mat => mat.id === id);
    return material ? material.name : 'Selecciona Material';
  }

  getRowsPercent(index: number) {
    return this.rowsFormArray.at(index).get('percent');
  }

  getRowsStop(index: number) {
    return this.rowsFormArray.at(index).get('stop');
  }

  getRowsTotal(index: number) {
    return this.rowsFormArray.at(index).get('total');
  }

  calculateRows() {
    const length = this.rowsFormArray.length;
    this.totalPercent = 0;
    this.totalToStop = 0;
    this.totalToCreate = 0;
    for (let i = 0; i < length; i++) {
      const rowStop = Math.round(this.preMixture.value * this.getRowsPercent(i).value) / 100;
      const rowTotal = rowStop * this.prepare.value;

      this.getRowsStop(i).setValue(rowStop);
      this.getRowsTotal(i).setValue(rowTotal);

      this.totalPercent += this.getRowsPercent(i).value;
      this.totalToStop += rowStop;
      this.totalToCreate += rowTotal;
    }
  }

  generateReceipt(e: any) {
    this.mixtureService.generateReceipt(this.numberMixture).subscribe(
      (data => {
        const type = data.body.type;
        this.fileName = data.headers.get('content-disposition').split('filename=')[1];
        this.srcPdf = URL.createObjectURL(
          new Blob([data.body], {type})
        );
        this.pdfDialog = true;
      })
    );
  }

  generateNewMixture() {
    this.getNumberToCreate();
    this.isEditing = !this.isEditing;
    this.mixtureEdit = null;
  }

  showFilter() {
    this.showFilters = true;
    // this.getAll();
  }

  filterMixtures($event: any) {
    const query = $event.query;
    if (query) {
      this.mixtureService.search(query)
        .subscribe((res: MixtureShort[]) => {
          this.mixtures = res;
        });
    }
  }

  onSelect(e: any) {
    this.mixtureFilter = e;
  }

  getRowsMixture() {
    this.showFilters = false;
    this.mixtureService.getMixtureByNumber(this.mixtureFilter.number).subscribe(
      mixture => {
        this.rowsFormArray.clear();
        mixture.rows.forEach(row => {
          this.rowsFormArray.push(this.addRowData(row));
        });
      });
  }

  onEditing(index: number) {
    const value = this.getRowsType(index).value;
    if (value) {
      const data = {value};
      this.onTypeSelected(data);
    }
  }

  onRowEditSave() {
    if (this.totalPercent !== 100) {
      this.toastService.warning('No se puede crear mezcla, verifique los porcentajes');
    }
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.cdr.detectChanges();
  }

  deleteRow(index: number) {
    this.rowsFormArray.removeAt(index);
  }
}
