import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { FORM_ERROR_MESSAGES } from "../../../../core/constants/form-error";
import { DieProduct } from "../../../../types/die-product.types";
import { DynamicDialogConfig, DynamicDialogRef } from "primeng/dynamicdialog";
import { StatusService } from "../../../../core/http/catalogs/status/status.service";
import { Observable } from "rxjs";
import { Status } from "../../../../types/catalogs.types";
import { DIE_PRODUCT_STATUS_TYPE } from "../../../../core/constants/status-types";

@Component({
  selector: 'app-die-product-modal',
  templateUrl: './die-product-modal.component.html',
  styleUrls: ['./die-product-modal.component.scss']
})
export class DieProductModalComponent implements OnInit {

  mode: 'create' | 'update' = 'create';

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  dieProduct: DieProduct;
  dieProductStates$: Observable<Status[]>;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private statusService: StatusService
  ) { }

  ngOnInit(): void {
    this.dieProductStates$ = this.statusService.getAllByType(DIE_PRODUCT_STATUS_TYPE);
    if (this.config.data) {
      this.dieProduct = this.config.data;
      this.mode = 'update';
    } else {
      this.dieProduct = {} as DieProduct;
    }

    this.form = this.fb.group({
      code: [this.dieProduct.code, [
        Validators.required,
        Validators.maxLength(8)
      ]],
      name: [this.dieProduct.name, [
        Validators.required,
        Validators.maxLength(128)
      ]],
      area: [this.dieProduct.area],
      length: [this.dieProduct.length],
      width: [this.dieProduct.width],
      gsmdis: [this.dieProduct.gsmdis],
      statusId: [this.dieProduct.status?.id]
    });
    this.setStatusValidators();
  }

  get code() {
    return this.form.get('code');
  }

  get name() {
    return this.form.get('name');
  }

  get area() {
    return this.form.get('area');
  }

  get length() {
    return this.form.get('length');
  }

  get width() {
    return this.form.get('width');
  }

  get gsmdis() {
    return this.form.get('gsmdis');
  }

  get statusId() {
    return this.form.get('statusId');
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

  get isUpdateMode(): boolean {
    return this.mode === 'update';
  }

  private setStatusValidators() {
    if (this.isUpdateMode) {
      this.statusId.setValidators(Validators.required);
      this.statusId.updateValueAndValidity();
    } else {
      this.statusId.clearValidators();
      this.statusId.updateValueAndValidity();
    }
  }
}
