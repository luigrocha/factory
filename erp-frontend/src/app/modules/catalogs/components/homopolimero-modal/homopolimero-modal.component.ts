import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Homopolimero } from 'src/app/types/homopolimero.types';

@Component({
  selector: 'app-homopolimero-modal',
  templateUrl: './homopolimero-modal.component.html',
  styleUrls: ['./homopolimero-modal.component.scss']
})
export class HomopolimeroModalComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  homo: Homopolimero;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.homo = this.config.data;
    } else {
      this.homo = {} as Homopolimero;
    }

    this.form = this.fb.group({
      id: [this.homo.id],
      percentage: [this.homo.percentage, [Validators.required]],
      hpCode: [this.homo.hpCode, Validators.required]
    });
  }

  get percentage() {
    return this.form.get('percentage');
  }

  get hpCode() {
    return this.form.get('hpCode');
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
