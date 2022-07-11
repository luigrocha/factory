import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ColorA } from 'src/app/types/colorA.types';

@Component({
  selector: 'app-color-a-modal',
  templateUrl: './color-a-modal.component.html',
  styleUrls: ['./color-a-modal.component.scss']
})
export class ColorAModalComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  color: ColorA;
  mode: 'create' | 'update' = 'create';

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.color = this.config.data;
      this.mode = 'update';
    } else {
      this.color = {} as ColorA;
    }

    this.form = this.fb.group({
      id: [this.color.id, Validators.required],
      name: [this.color.name, [Validators.required]],
      colorCode: [this.color.colorCode]
    });
  }

  isUpdateMode(): boolean {
    return this.mode === 'update';
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
