import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { INVALID_FILE_SIZE_DETAIL_TEMPLATE, INVALID_FILE_SIZE_SUMMARY_TEMPLATE } from 'src/app/core/constants/upload';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  maxSizeSummary = INVALID_FILE_SIZE_SUMMARY_TEMPLATE;
  maxSizeDetail = INVALID_FILE_SIZE_DETAIL_TEMPLATE;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null, [
        Validators.required,
        Validators.maxLength(16)
      ]],
      name: [null, [
        Validators.required,
        Validators.maxLength(64)
      ]],
      file: [null]
    });
  }

  get id() {
    return this.form.get('id');
  }

  get name() {
    return this.form.get('name');
  }

  get file() {
    return this.form.get('file');
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

  selectLogo($event: any): void {
    this.file.setValue($event.currentFiles[0]);
  }

  removeLogo(): void {
    this.file.reset();
  }
}
