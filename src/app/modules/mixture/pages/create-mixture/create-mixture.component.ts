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
import {MixtureDetail} from '../../../../types/mixture.types';

@Component({
  selector: 'app-create-mixture',
  templateUrl: './create-mixture.component.html',
  styleUrls: ['./create-mixture.component.scss']
})
export class CreateMixtureComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  showFilters: boolean;
  numberMixture: number;
  order: Order;
  project: Project;
  totalPercent = 0;
  totalToStop = 0;
  totalToCreate = 0;
  types: TypeMaterial[];
  materials: Material[] = [];

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
  ) {
    this.breadcrumbService.setItems([
      {label: 'Módulo de Mezcla'},
      {label: 'Diseño de Mezcla', routerLink: ['/home/mezcla']},
      {label: 'Crear Diseño de Mezcla', routerLink: ['/home/mezcla/crear']},
    ]);
  }

  get prepare() {
    return this.form.get('prepare');
  }

  get rowsFormArray() {
    return this.form.get('rows') as FormArray;
  }

  ngOnInit() {
    this.getOrderByLot();
    this.getNumberToCreate();
    this.getAllTypeMaterial();
    this.form = this.fb.group({
      prepare: [null, [
        Validators.required,
      ]],
      rows: this.fb.array([]),
    });
    this.columns = [
      {field: 'type', header: 'Tipo'},
      {field: 'material', header: 'Producto'},
      {field: 'percent', header: 'Porcentaje'},
      {field: 'stop', header: 'Kg por parada'},
      {field: 'total', header: 'Kg total '},
    ];
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(typeMaterial => {
      this.types = typeMaterial;
    });
  }

  getOrderByLot() {
    const lot = this.activatedRoute.snapshot.params.lote;
    this.orderService.getOrderByLot(lot).subscribe(order => {
      this.order = order;
      this.getProjectToCodeGen(order.productCode);
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
    });
  }

  save() {
    //
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
    const material = this.materials.find(mat => mat.id === id);
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

  onRowEditSave() {
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.cdr.detectChanges();
  }

  deleteRow(index: number) {
    this.rowsFormArray.removeAt(index);
  }
}
