import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Location } from 'src/app/types/celler.types';

@Component({
  selector: 'app-location-modal',
  templateUrl: './location-modal.component.html',
  styleUrls: ['./location-modal.component.scss']
})
export class LocationModalComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  locationCeller: Location;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.locationCeller = this.config.data;
    } else {
      this.locationCeller = {} as Location;
    }

    this.form = this.fb.group({
      id: [this.locationCeller.id],
      location: [this.locationCeller.location, [Validators.required]],
      description: [this.locationCeller.description, Validators.required]
    });
  }

  get id() {
    return this.form.get('id');
  }

  get location() {
    return this.form.get('location');
  }

  get description() {
    return this.form.get('description');
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
