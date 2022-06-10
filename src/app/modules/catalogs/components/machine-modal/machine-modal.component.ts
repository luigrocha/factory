import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {FORM_ERROR_MESSAGES} from "../../../../core/constants/form-error";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {Machine} from "../../../../types/machine.types";

@Component({
  selector: 'app-machine-modal',
  templateUrl: './machine-modal.component.html',
  styleUrls: ['./machine-modal.component.scss']
})
export class MachineModalComponent implements OnInit {
  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  hasDesbFormCtrl: FormControl;
  machine: Machine;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.machine = this.config.data;
    } else {
      this.machine = {
        name: null,
        hasDesb: false,
        id: null
      };
    }

    this.hasDesbFormCtrl = new FormControl(this.machine.hasDesb, Validators.required);
    this.form = this.fb.group({
      name: [this.machine.name, [
        Validators.required,
        Validators.maxLength(64)
      ]],
      hasDesb: this.hasDesbFormCtrl
    });
  }

  get name() {
    return this.form.get('name');
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
