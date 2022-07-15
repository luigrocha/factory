import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from '../../../../core/constants/form-error';
import {MachineService} from '../../../../core/http/catalogs/machine/machine.service';
import {Machine} from '../../../../types/machine.types';
import {TableColumn} from '../../../../types/table.types';
import {MixtureDetail, MixtureDetailRes} from '../../../../types/mixture.types';
import {TABLE_REPORT_TEMPLATE} from '../../../../core/constants/table';

@Component({
  selector: 'app-material-request-info',
  templateUrl: './material-request-info.component.html',
  styleUrls: ['./material-request-info.component.scss']
})
export class MaterialRequestInfoComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  machines: Machine[];
  columnsExt: TableColumn<MixtureDetail>[];
  columnsConsolidated: TableColumn<any>[];
  mixtures: MixtureDetail[][] = [];
  consolides: any[] = [];

  constructor(
    private fb: FormBuilder,
    private machineService: MachineService,
  ) { }

  ngOnInit(): void {
    this.getAllMachinesByType(1);
    this.form = this.fb.group({
      numberMixture: [null, [Validators.required]],
      numberStop: [null, [Validators.required]],
    });
    this.columnsExt = [
      {field: 'type', header: 'Tipo'},
      {field: 'product', header: 'Producto'},
      {field: 'total', header: 'Total'},
    ];
    this.columnsConsolidated = [
      {field: 'product', header: 'Producto'},
      {field: 'total', header: 'Total'},
    ];
  }

  get numberMixture(){
    return this.form.get('numberMixture');
  }

  get numberStop(){
    return this.form.get('numberStop');
  }

  getAllMachinesByType(id: number) {
    this.machines = [];
    this.machineService.getAllMachinesByType(id).subscribe(machines => {
      this.machines = machines;
    });
  }

}
