import {Component, OnInit} from '@angular/core';
import {MaterialRequestService as MaterialRequestServiceTemp} from '../../../../core/services/material-request.service';
import {Consolidated, MaterialRequest, Turns} from '../../../../types/material-request.types';
import {MaterialRequestService} from '../../../../core/http/material-request/material-request.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from '../../../../core/constants/form-error';
import {TableColumn} from '../../../../types/table.types';
import {TABLE_REPORT_TEMPLATE} from '../../../../core/constants/table';

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
  consolides: Consolidated[];
  columns: TableColumn<MaterialRequest>[];
  materialRequest: MaterialRequest[];
  turns: Turns[];

  constructor(
    private fb: FormBuilder,
    private materialReqService: MaterialRequestServiceTemp,
    private materialRequestService: MaterialRequestService
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
    this.materialRequestService.getAllValidTurns().subscribe(turns => {
      this.turns = turns;
    });
  }

  addRow() {

  }

  save() {

  }

}
