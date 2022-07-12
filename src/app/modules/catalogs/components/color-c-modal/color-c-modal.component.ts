import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ColorCatalog } from 'src/app/types/color-catalog.types';

@Component({
  selector: 'app-color-c-modal',
  templateUrl: './color-c-modal.component.html',
  styleUrls: ['./color-c-modal.component.scss']
})
export class ColorCModalComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  color: ColorCatalog;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.color = this.config.data;
    } else {
      this.color = {} as ColorCatalog;
    }

    this.form = this.fb.group({
      id: [this.color.id],
      name: [this.color.name, [Validators.required]],
      colorCode: [this.color.colorCode, Validators.required]
    });
  }

  get id() {
    return this.form.get('id');
  }

  get name() {
    return this.form.get('name');
  }

  get colorCode() {
    return this.form.get('colorCode');
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    this.ref.close(this.form.getRawValue());
  }

  close() {
    this.ref.close();
  }

}
