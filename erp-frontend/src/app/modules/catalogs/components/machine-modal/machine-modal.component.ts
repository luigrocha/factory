import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {FORM_ERROR_MESSAGES} from '../../../../core/constants/form-error';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';
import {Machine, MachineCatalog} from '../../../../types/machine.types';
import {MachineService} from '../../../../core/http/catalogs/machine/machine.service';

@Component({
  selector: 'app-machine-modal',
  templateUrl: './machine-modal.component.html',
  styleUrls: ['./machine-modal.component.scss']
})
export class MachineModalComponent implements OnInit {
  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  indexTab: number;
  hasDesbFormCtrl: FormControl;
  machine: Machine;
  catalogs: MachineCatalog[];

  constructor(
    private fb: FormBuilder,
    private machineService: MachineService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  get name() {
    return this.form.get('name');
  }

  get description(){
    return this.form.get('description');
  }

  get observation(){
    return this.form.get('observation');
  }

  get widthExt(){
    return this.form.get('widthExt');
  }

  get abilityPell(){
    return this.form.get('abilityPell');
  }

  get machineCatalog(){
    return this.form.get('machineCatalog');
  }

  ngOnInit(): void {
    this.getAllMachinesCatalog();
    if (this.config.data?.id) {
      this.machine = this.config.data;
      this.indexTab = this.machine.machineCatalog.id;
    } else {
      this.indexTab = this.config.data;
      this.machine = {
        description: null,
        observation: null,
        machineCatalog: null,
        name: null,
        widthExt: null,
        hasDesb: false,
        abilityPell: null,
        id: null
      };
    }

    this.hasDesbFormCtrl = new FormControl(this.machine.hasDesb);
    this.form = this.fb.group({
      name: [this.machine.name, [
        Validators.required,
        Validators.maxLength(64)
      ]],
      hasDesb: this.hasDesbFormCtrl,
      widthExt: [this.machine.widthExt, []],
      abilityPell: [this.machine.abilityPell, []],
      description: [this.machine.description, []],
      observation: [this.machine.observation, []],
      machineCatalog: [this.machine.machineCatalog?.id, []]
    });
  }

  getAllMachinesCatalog(){
    this.machineService.getAllMachinesCatalog().subscribe(catalogs =>{
      this.catalogs = catalogs;
    });
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    this.ref.close(this.form.value);
  }


  close() {
    this.ref.close();
  }

}
