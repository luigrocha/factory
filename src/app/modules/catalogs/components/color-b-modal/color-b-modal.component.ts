import { Component, OnInit } from '@angular/core';
import { ColorAService } from 'src/app/core/http/catalogs/color-a/color-a.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ColorB, CreateColorB, GenerateColorBId, UpdateColorB } from 'src/app/types/colorB.types';
import { Observable } from 'rxjs';
import { ColorA } from 'src/app/types/colorA.types';
import { ColorBService } from 'src/app/core/http/catalogs/color-b/color-b.service';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-color-b-modal',
  templateUrl: './color-b-modal.component.html',
  styleUrls: ['./color-b-modal.component.scss']
})
export class ColorBModalComponent implements OnInit {
  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  color: ColorB;
  colorsA$: Observable<ColorA[]>;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private colorAService: ColorAService,
    private colorBService: ColorBService
  ) {
  }

  ngOnInit(): void {
    this.colorsA$ = this.colorAService.getAll();
    if (this.config.data) {
      this.color = this.config.data;
    } else {
      this.color = {} as ColorB;
    }

    this.form = this.fb.group({
      id: [{value: this.color.id, disabled: true}],
      colorA: [this.color.colorA?.id, [Validators.required]],
      index: [this.color.index, [Validators.required]],
      dosage: [this.color.dosage, [Validators.required]],
      description: [this.color.description, [Validators.required]],
      colorCode: [this.color.colorCode, [Validators.required]],
    });

    this.colorA.valueChanges
      .pipe(
        debounceTime(500),
      )
      .subscribe(value => {
        if (value) {
          const body: GenerateColorBId = {
            colorAId: value,
          };
          this.colorBService.generateColorId(body)
            .subscribe(generatedId => {
              this.id.setValue(generatedId.generatedId);
              this.index.setValue(generatedId.nextIndex);
            });
        }
      });
  }

  get id() {
    return this.form.get('id');
  }

  get colorA() {
    return this.form.get('colorA');
  }

  get index() {
    return this.form.get('index');
  }

  get dosage() {
    return this.form.get('dosage');
  }

  get description() {
    return this.form.get('description');
  }

  get colorCode() {
    return this.form.get('colorCode');
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    const body: CreateColorB | UpdateColorB = this.form.getRawValue();
    body.colorAId = this.colorA.value;
    this.ref.close(body);
  }

  close() {
    this.ref.close();
  }
}
