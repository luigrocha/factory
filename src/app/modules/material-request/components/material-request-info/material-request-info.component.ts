import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from '../../../../core/constants/form-error';
import {MachineService} from '../../../../core/http/catalogs/machine/machine.service';
import {Machine} from '../../../../types/machine.types';
import {TableColumn} from '../../../../types/table.types';
import {MixtureShort} from '../../../../types/mixture.types';
import {MixtureService} from '../../../../core/http/mixture/mixture.service';
import {Consolidated, MaterialRequestDetail} from '../../../../types/material-request.types';

@Component({
  selector: 'app-material-request-info',
  templateUrl: './material-request-info.component.html',
  styleUrls: ['./material-request-info.component.scss']
})
export class MaterialRequestInfoComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  numberMixture: number[] = [];
  numberStop: number[] = [];
  machines: Machine[];
  columnsExt: TableColumn<MaterialRequestDetail>[];
  columnsConsolidated: TableColumn<Consolidated>[];
  mixtures: MaterialRequestDetail[][] = [];
  mixturesSearch: MixtureShort[];
  consolides: Consolidated[] = [];
  disabled: boolean[] = [];
  disabledNext: boolean;
  indexTab = 0;

  constructor(
    private fb: FormBuilder,
    private machineService: MachineService,
    private mixtureService: MixtureService,
  ) {
  }

  ngOnInit(): void {
    this.getAllMachinesByType(1);
    this.columnsExt = [
      {field: 'product', header: 'Producto'},
      {field: 'quantity', header: 'Cantidad'},
      {field: 'stopMp', header: 'StopMP'},
      {field: 'necessary', header: 'Necesario'},
      {field: 'request', header: 'Requerido'}
    ];
    this.columnsConsolidated = [
      {field: 'product', header: 'Producto'},
      {field: 'total', header: 'Total'},
    ];
  }

  getAllMachinesByType(id: number) {
    this.machines = [];
    this.machineService.getAllMachinesByType(id).subscribe(machines => {
      this.machines = machines;
      this.machines.forEach(machine => {
        this.numberStop.push(0);
      });
    });
  }

  filterMixtures($event: any) {
    const query = $event.query;
    if (query) {
      this.mixtureService.search(query)
        .subscribe((res: MixtureShort[]) => {
          this.mixturesSearch = res;
        });
    }
  }

  onChangeTab(e: any) {
    this.indexTab = e.index;
  }

  onSelect(e: any) {
    const mixture: MixtureShort = e;
    this.getRowsMixture(mixture.number);
  }

  calculateRows() {
    if (this.mixtures[this.indexTab]) {
      this.disabled[this.indexTab] = true;
      this.mixtures[this.indexTab].forEach(row => {
        const necessary = this.numberStop[this.indexTab] * row.quantity;
        row.necessary = necessary;
        // TODO necessary - stopMp
        row.request = necessary;
      });
    }
  }

  getRowsMixture(num: number) {
    this.mixtureService.getMixtureByNumber(num).subscribe(
      mixture => {
        this.mixtures[this.indexTab] = [];
        mixture.rows.forEach(row => {
          const necessary = this.numberStop[this.indexTab] * row.total;
          this.mixtures[this.indexTab].push({
            product: row.material.name,
            quantity: row.total,
            // TODO get stopMp
            stopMp: 0,
            necessary,
            // TODO necessary - stopMp
            request: necessary
          });
        });
      });
  }

  addToList() {
    this.consolides = [];
    this.mixtures.forEach(mixture => {
      mixture.forEach(item => {
        const isExist = this.consolides.find(consoled => consoled.product === item.product);
        if (isExist) {
          isExist.quantity += item.request;
        } else {
          this.consolides.push({
            product: item.product,
            quantity: item.request
          });
        }
      });
    });
    this.disabled[this.indexTab] = false;
  }

  addToRequest() {
    this.disabledNext = true;
  }

}
