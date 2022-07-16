import { Component, OnInit } from '@angular/core';
import {
  MaterialRequestService as MaterialRequestServiceTemp
} from '../../../../core/services/material-request.service';
import { Consolidated, MaterialRequest } from 'src/app/types/material-request.types';
import { MaterialRequestService } from 'src/app/core/http/material-request/material-request.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { Turn } from 'src/app/types/turn.types';
import { TurnService } from 'src/app/core/http/turn/turn.service';

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
  turns: Turn[];

  constructor(
    private fb: FormBuilder,
    private materialReqService: MaterialRequestServiceTemp,
    private materialRequestService: MaterialRequestService,
    private turnService: TurnService,
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
    this.turnService.getTurns()
      .subscribe(turns => {
        this.turns = turns;
      });
  }

  addRow() {

  }

  save() {

  }

}
