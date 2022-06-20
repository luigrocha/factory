import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Manufacturer } from 'src/app/types/manufacturer.types';
import { Machine } from 'src/app/types/machine.types';
import { DieProduct } from 'src/app/types/die-product.types';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ManufacturerService } from 'src/app/core/http/catalogs/manufacturer/manufacturer.service';
import { MachineService } from 'src/app/core/http/catalogs/machine/machine.service';
import { DieProductService } from 'src/app/core/http/die-product/die-product.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { DieService } from 'src/app/core/http/dies/die.service';
import { Router } from '@angular/router';
import { CreateDie } from 'src/app/types/dies.types';
import { DIE_NAME_PREFIX, DIE_PRODUCT_NAME_PREFIX, DIE_QUANTITY_SUFFIX } from 'src/app/core/constants/generate-die';

@Component({
  selector: 'app-create-die',
  templateUrl: './create-die.component.html',
  styleUrls: ['./create-die.component.scss']
})
export class CreateDieComponent implements OnInit {

  form: FormGroup;
  manufacturers$: Observable<Manufacturer[]>;
  machines$: Observable<Machine[]>;
  dieProducts$: Observable<DieProduct[]>;
  formErrors = FORM_ERROR_MESSAGES;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private fb: FormBuilder,
    private manufacturerService: ManufacturerService,
    private machineService: MachineService,
    private dieProductService: DieProductService,
    private toastService: ToastService,
    private dieService: DieService,
    private router: Router
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Troqueles', routerLink: ['/home/troqueles']},
      {label: 'Crear troquel', routerLink: ['/home/troqueles/crear']}
    ]);
  }

  ngOnInit(): void {
    this.manufacturers$ = this.manufacturerService.getAllManufacturers();
    this.machines$ = this.machineService.getAllMachines();
    this.dieProducts$ = this.dieProductService.getAllAvailableDieProducts();
    this.form = this.fb.group({
      name: [{value: null, disabled: true}, [
        Validators.required,
        Validators.maxLength(64)
      ]],
      manufacturer: [null],
      machines: [null],
      dsbMultiple: [null],
      dieProduct: [null, [Validators.required]],
      observations: [null],
      length: [{value: null, disabled: true}, [Validators.required]],
      width: [{value: null, disabled: true}, [Validators.required]],
      quantity: [{value: 0, disabled: true}, [Validators.required]],
      quantityLength: [0, [Validators.required]],
      separationLength: [0, [Validators.required]],
      quantityWidth: [0, [Validators.required]],
      separationWidth: [0, [Validators.required]],
      borderLength: [0, [Validators.required]],
      borderWidth: [0, [Validators.required]],
      leafLength: [{value: 0, disabled: true}, [Validators.required]],
      leafWidth: [{value: 0, disabled: true}, [Validators.required]]
    });
    this.updateLengthWidth();
    this.addDesbValidators();
    this.updateQuantity();
    this.updateLeafLength();
    this.updateLeafWidth();
    this.updateName();
  }

  get name() {
    return this.form.get('name');
  }

  get manufacturer() {
    return this.form.get('manufacturer');
  }

  get machines() {
    return this.form.get('machines');
  }

  get dsbMultiple() {
    return this.form.get('dsbMultiple');
  }

  get dieProduct() {
    return this.form.get('dieProduct');
  }

  get quantity() {
    return this.form.get('quantity');
  }

  get quantityLength() {
    return this.form.get('quantityLength');
  }

  get separationLength() {
    return this.form.get('separationLength');
  }

  get separationWidth() {
    return this.form.get('separationWidth');
  }

  get borderLength() {
    return this.form.get('borderLength');
  }

  get borderWidth() {
    return this.form.get('borderWidth');
  }

  get leafLength() {
    return this.form.get('leafLength');
  }

  get leafWidth() {
    return this.form.get('leafWidth');
  }

  get quantityWidth() {
    return this.form.get('quantityWidth');
  }

  get width() {
    return this.form.get('width');
  }

  get length() {
    return this.form.get('length');
  }

  save() {
    if (this.form.invalid) {
      return;
    }

    const dieBody: CreateDie = this.form.getRawValue();
    dieBody.dieProductId = this.dieProduct.value.id;
    dieBody.manufacturerId = this.manufacturer.value.id;
    dieBody.machines = this.machines.value.map(({id}) => id);
    this.dieService.createDie(dieBody)
      .subscribe(() => {
        this.toastService.success('Troquel creado correctamente');
        setTimeout(() => {
          this.router.navigate(['/home/troqueles']);
        }, 1000);
      });
  }

  private addDesbValidators() {
    this.machines.valueChanges.subscribe(machines => {
      if (machines && machines.length > 0) {
        const someMachineHasDesb = machines.some(machine => machine.hasDesb);
        if (someMachineHasDesb) {
          this.dsbMultiple.setValidators([Validators.required]);
          this.dsbMultiple.updateValueAndValidity();
        } else {
          this.dsbMultiple.clearValidators();
          this.dsbMultiple.updateValueAndValidity();
        }
      } else {
        this.dsbMultiple.clearValidators();
        this.dsbMultiple.updateValueAndValidity();
      }
    });
  }

  private updateQuantity() {
    this.quantityLength.valueChanges.subscribe(quantityLength => {
      if (quantityLength === 0) {
        this.quantity.setValue(0);
        return;
      }

      if (this.quantityWidth.value) {
        this.quantity.setValue(quantityLength * this.quantityWidth.value);
      }
    });

    this.quantityWidth.valueChanges.subscribe(quantityWidth => {
      if (quantityWidth === 0) {
        this.quantity.setValue(0);
        return;
      }

      if (this.quantityLength.value) {
        this.quantity.setValue(quantityWidth * this.quantityLength.value);
      }
    });
  }

  private updateLengthWidth() {
    this.dieProduct.valueChanges.subscribe(dieProduct => {
      if (dieProduct) {
        if (dieProduct.length) {
          this.length.setValue(dieProduct.length);
        } else {
          this.toastService.warning('El producto no tiene una medida de longitud');
          this.length.setValue(0);
        }
        if (dieProduct.width) {
          this.width.setValue(dieProduct.width);
        } else {
          this.toastService.warning('El producto no tiene una medida de ancho');
          this.width.setValue(0);
        }
      } else {
        this.length.setValue(null);
        this.width.setValue(null);
      }
    });
  }

  private updateLeafLength() {
    this.length.valueChanges.subscribe(length => {
      if (length) {
        this.leafLength.setValue(this.calculateLeafLength());
      }
    });

    this.quantityLength.valueChanges.subscribe(quantityLength => {
      if (quantityLength) {
        this.leafLength.setValue(this.calculateLeafLength());
      }
    });

    this.separationLength.valueChanges.subscribe(separationLength => {
      if (separationLength) {
        this.leafLength.setValue(this.calculateLeafLength());
      }
    });

    this.borderLength.valueChanges.subscribe(borderLength => {
      if (borderLength) {
        this.leafLength.setValue(this.calculateLeafLength());
      }
    });
  }

  private updateLeafWidth() {
    this.width.valueChanges.subscribe(width => {
      if (width) {
        this.leafWidth.setValue(this.calculateLeafWidth());
      }
    });

    this.quantityWidth.valueChanges.subscribe(quantityWidth => {
      if (quantityWidth) {
        this.leafWidth.setValue(this.calculateLeafWidth());
      }
    });

    this.separationWidth.valueChanges.subscribe(separationWidth => {
      if (separationWidth) {
        this.leafWidth.setValue(this.calculateLeafWidth());
      }
    });

    this.borderWidth.valueChanges.subscribe(borderWidth => {
      if (borderWidth) {
        this.leafWidth.setValue(this.calculateLeafWidth());
      }
    });
  }

  private calculateLeafLength(): number {
    const length = this.length.value;
    const quantityLength = this.quantityLength.value;
    const separationLength = this.separationLength.value;
    const borderLength = this.borderLength.value;

    const result = (length * quantityLength) + ((quantityLength - 1) * separationLength) + (borderLength);
    return Math.ceil(result);
  }

  private calculateLeafWidth(): number {
    const width = this.width.value;
    const quantityWidth = this.quantityWidth.value;
    const separationWidth = this.separationWidth.value;
    const borderWidth = this.borderWidth.value;

    const result = (width * quantityWidth) + ((quantityWidth - 1) * separationWidth) + (borderWidth);
    return Math.ceil(result);
  }

  private updateName() {
    this.dieProduct.valueChanges.subscribe(dieProduct => {
      if (dieProduct) {
        this.name.setValue(this.generateName());
      } else {
        this.name.setValue(null);
      }
    });

    this.quantity.valueChanges.subscribe(() => {
      this.name.setValue(this.generateName());
    });

    this.manufacturer.valueChanges.subscribe(() => {
      this.name.setValue(this.generateName());
    });

    this.machines.valueChanges.subscribe(machines => {
      this.name.setValue(this.generateName());
    });
  }

  private generateName(): string {
    const dieProduct: DieProduct = this.dieProduct.value;

    if (!dieProduct) {
      return null;
    }

    const firstPart = dieProduct.code.replace(DIE_PRODUCT_NAME_PREFIX, DIE_NAME_PREFIX);
    const secondPart = `(${this.quantity.value}${DIE_QUANTITY_SUFFIX})`;
    const thirdPart = this.manufacturer.value ? this.manufacturer.value.name : '';
    const fourthPart = this.machines.value ? this.machines.value.map(machine => machine.name).join(', ') : '';
    return `${firstPart}${secondPart}-${thirdPart}-${fourthPart}`;
  }
}
