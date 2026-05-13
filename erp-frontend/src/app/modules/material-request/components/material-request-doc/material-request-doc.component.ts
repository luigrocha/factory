import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MaterialRequestService as MaterialRequestServiceTemp} from '../../../../core/services/material-request.service';
import {Consolidated, MaterialRequest, MaterialRequestCreate} from 'src/app/types/material-request.types';
import {MaterialRequestService} from 'src/app/core/http/material-request/material-request.service';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from 'src/app/core/constants/form-error';
import {TableColumn} from 'src/app/types/table.types';
import {TABLE_REPORT_TEMPLATE} from 'src/app/core/constants/table';
import {Turn} from 'src/app/types/turn.types';
import {TurnService} from 'src/app/core/http/turn/turn.service';
import {AuthService} from '../../../../core/auth/service/auth.service';
import {MaterialRequestStatus} from '../../../../types/enums/material-request-status';
import {ToastService} from '../../../../core/services/toast.service';
import {MaterialService} from '../../../../core/http/materials/materials.service';
import {Material, TypeMaterial} from '../../../../types/material.types';

@Component({
  selector: 'app-material-request-doc',
  templateUrl: './material-request-doc.component.html',
  styleUrls: ['./material-request-doc.component.scss']
})
export class MaterialRequestDocComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  pageSize = 10;
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  idRequest: string;
  status: string;
  consolides: Consolidated[];
  columns: TableColumn<MaterialRequest>[];
  turns: Turn[];
  types: TypeMaterial[] = [];
  materials: Material[] = [];
  allMaterials: Material[] = [];
  isEditing: boolean;
  isConfig: boolean;

  constructor(
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    private materialReqService: MaterialRequestServiceTemp,
    private materialRequestService: MaterialRequestService,
    private materialService: MaterialService,
    private turnService: TurnService,
    private authService: AuthService,
    private toastService: ToastService,
  ) {
    this.materialReqService.consolidated$
      .subscribe((consolides) => {
        this.consolides = consolides;
      });
  }

  get turn() {
    return this.form.get('turn');
  }

  get rowsFormArray() {
    return this.form.get('rows') as FormArray;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      turn: [null, [
        Validators.required,
      ]],
      rows: this.fb.array([]),
    });
    this.columns = [
      {field: 'type', header: 'Tipo'},
      {field: 'material', header: 'Producto'},
      {field: 'quantity', header: 'Cantidad'},
      {field: 'balance', header: 'Saldos'},
      {field: 'coat', header: 'Sacos'},
      {field: 'pallets', header: 'Palets'},
      {field: 'weight', header: 'Total'},
      {field: 'observation', header: 'Observación'},
    ];
    this.getAllTypeMaterial();
    this.getAllMaterial();
    this.getCountMaterialRequest();
    this.getAllValidTurns();
    this.getRowsConsolidated();
  }

  getAllValidTurns() {
    this.turnService.getTurns()
      .subscribe(turns => {
        this.turns = turns;
      });
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

  getCountMaterialRequest() {
    this.materialRequestService.getCountMaterialRequest().subscribe(numberRequest => {
      this.idRequest = 'RM-' + numberRequest;
    });
  }

  getRowsConsolidated() {
    this.consolides.forEach(consolidated => {
      this.rowsFormArray.push(this.addRowData(consolidated));
    });
  }

  addRowData(consolidated: Consolidated): FormGroup {
    return this.fb.group({
      index: this.rowsFormArray.length,
      type: [consolidated.material.typeMaterial.id, [
        // Validators.required,
      ]],
      material: [consolidated.material.id, [
        // Validators.required,
      ]],
      quantity: [consolidated.quantity, [
        // Validators.required,
      ]],
      balance: [0, [
        // Validators.required,
      ]],
      coat: [0, [
        // Validators.required,
      ]],
      pallets: [0, [
        // Validators.required,
      ]],
      weight: [0, [
        // Validators.required,
      ]],
      observation: [null, [
        // Validators.required,
      ]],
    });
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

  getRowsQuantity(index: number) {
    return this.rowsFormArray.at(index).get('quantity');
  }

  getRowsBalance(index: number) {
    return this.rowsFormArray.at(index).get('balance');
  }

  getRowsCoat(index: number) {
    return this.rowsFormArray.at(index).get('coat');
  }

  getRowsPallets(index: number) {
    return this.rowsFormArray.at(index).get('pallets');
  }

  getRowsWeight(index: number) {
    return this.rowsFormArray.at(index).get('weight');
  }

  getRowsObservation(index: number) {
    return this.rowsFormArray.at(index).get('observation');
  }

  addRow() {

  }

  save() {
    if (this.form.invalid) {
      return;
    }

    const body: MaterialRequestCreate = {...this.form.getRawValue()};
    body.number = this.idRequest;
    body.date = new Date();
    body.documentByUsername = this.authService.getLoggedUser().preferred_username;
    body.documentBy = this.authService.getLoggedUser().name;
    body.status = this.status;

    this.materialRequestService.create(body).subscribe(data => {
      if (this.status === MaterialRequestStatus.TERMINADO) {
        this.toastService.success(`Solicitud de Materia ${this.idRequest} Creada`);
      } else {
        this.toastService.success(`Solicitud ${this.idRequest} guardada como borrador`);
      }
    });

  }

  onCreate(type: boolean) {
    type ? this.status = MaterialRequestStatus.TERMINADO : this.status = MaterialRequestStatus.BORRADOR;
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

  onRowEditSave() {
    this.isEditing = false;
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.isEditing = false;
    this.cdr.detectChanges();
  }

  deleteRow(index: number) {
    this.rowsFormArray.removeAt(index);
  }

}
