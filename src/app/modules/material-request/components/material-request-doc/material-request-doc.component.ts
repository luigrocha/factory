import {Component, OnInit} from '@angular/core';
import {MaterialRequestService as MaterialRequestServiceTemp} from '../../../../core/services/material-request.service';
import {Consolidated, MaterialRequest, MaterialRequestCreate} from 'src/app/types/material-request.types';
import {MaterialRequestService} from 'src/app/core/http/material-request/material-request.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from 'src/app/core/constants/form-error';
import {TableColumn} from 'src/app/types/table.types';
import {TABLE_REPORT_TEMPLATE} from 'src/app/core/constants/table';
import {Turn} from 'src/app/types/turn.types';
import {TurnService} from 'src/app/core/http/turn/turn.service';
import {AuthService} from '../../../../core/auth/service/auth.service';
import {MaterialRequestStatus} from '../../../../types/enums/material-request-status';
import {ToastService} from '../../../../core/services/toast.service';

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
  materialRequest: MaterialRequest[];
  turns: Turn[];

  constructor(
    private fb: FormBuilder,
    private materialReqService: MaterialRequestServiceTemp,
    private materialRequestService: MaterialRequestService,
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

  ngOnInit(): void {
    this.getCountMaterialRequest();
    this.getAllValidTurns();
    this.form = this.fb.group({
      turn: [null, [
        Validators.required,
      ]],
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
  }

  getAllValidTurns() {
    this.turnService.getTurns()
      .subscribe(turns => {
        this.turns = turns;
      });
  }

  getCountMaterialRequest(){
    this.materialRequestService.getCountMaterialRequest().subscribe(numberRequest => {
      this.idRequest = 'RM-' + numberRequest;
    });
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
      if (this.status === MaterialRequestStatus.TERMINADO){
        this.toastService.success(`Solicitud de Materia ${this.idRequest} Creada`);
      }else{
        this.toastService.success(`Solicitud ${this.idRequest} guardada como borrador`);
      }
    });

  }

  onCreate(type: boolean){
    type ? this.status = MaterialRequestStatus.TERMINADO : this.status = MaterialRequestStatus.BORRADOR;
  }

}
