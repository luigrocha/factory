import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';

@Component({
  selector: 'app-confirmation-text',
  templateUrl: './confirmation-text.component.html',
  styleUrls: ['./confirmation-text.component.scss']
})
export class ConfirmationTextComponent implements OnInit {

  formControl = new FormControl(null);
  actionButtonLabel: string = 'Guardar';
  actionButtonCssColorClass: string = 'p-button-success';
  label: string = 'Ingrese el siguiente texto';
  formErrors = FORM_ERROR_MESSAGES;
  required: boolean = true;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) { }

  ngOnInit(): void {
    if (this.config.data) {
      this.actionButtonLabel = this.config.data.actionButtonLabel;
      this.actionButtonCssColorClass = this.config.data.actionButtonCssColorClass;
      this.label = this.config.data.label;
      if (this.config.data.required || this.required) {
        this.formControl.setValidators([Validators.required]);
      }
    }
  }

  save() {
    if (this.formControl.invalid) {
      return;
    }
    this.ref.close(this.formControl.value);
  }

  close() {
    this.ref.close();
  }
}
