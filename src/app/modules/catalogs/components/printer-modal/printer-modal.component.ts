import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Printer } from 'src/app/types/printer.types';

@Component({
  selector: 'app-printer-modal',
  templateUrl: './printer-modal.component.html',
  styleUrls: ['./printer-modal.component.scss']
})
export class PrinterModalComponent implements OnInit {


  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  printer: Printer;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.printer = this.config.data;
    } else {
      this.printer = {} as Printer;
    }

    this.form = this.fb.group({
      id: [this.printer.id],
      name: [this.printer.name, [Validators.required]],
      numColors: [this.printer.numColors, [Validators.required]],
      description: [this.printer.description],
    });
  }

  get name() {
    return this.form.get('name');
  }

  get description() {
    return this.form.get('description');
  }

  get numColors() {
    return this.form.get('numColors');
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
