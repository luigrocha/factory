import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Thickness } from 'src/app/types/thickness.types';

@Component({
  selector: 'app-thickness-modal',
  templateUrl: './thickness-modal.component.html',
  styleUrls: ['./thickness-modal.component.scss']
})
export class ThicknessModalComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  thickness: Thickness;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.thickness = this.config.data;
    } else {
      this.thickness = {} as Thickness;
    }

    this.form = this.fb.group({
      id: [this.thickness.id],
      weight: [this.thickness.weight, [Validators.required]],
      thick: [this.thickness.thick]
    });
  }

  get weight() {
    return this.form.get('weight');
  }

  get thick() {
    return this.form.get('thick');
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
