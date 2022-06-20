import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { ColorB } from 'src/app/types/colorB.types';
import { Observable } from 'rxjs';
import { DieProduct } from 'src/app/types/die-product.types';
import { Printer } from 'src/app/types/printer.types';
import { ColorType } from 'src/app/types/color-type.types';
import { CirelColor, CreateCirel } from 'src/app/types/cirel.types';
import { TableColumn } from 'src/app/types/table.types';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ColorBService } from 'src/app/core/http/catalogs/color-b/color-b.service';
import { DieProductService } from 'src/app/core/http/die-product/die-product.service';
import { PrinterService } from 'src/app/core/http/catalogs/printers/printer.service';
import { ColorTypeService } from 'src/app/core/http/catalogs/color-type/color-type.service';
import { COLOR_TYPE_CYREL, COLOR_TYPE_LEAF } from 'src/app/core/constants/color-type';
import { ColorCService } from 'src/app/core/http/catalogs/color-c/color-c.service';
import { ColorCatalog } from 'src/app/types/color-catalog.types';
import { CirelService } from 'src/app/core/http/cirel/cirel.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-cirel',
  templateUrl: './create-cirel.component.html',
  styleUrls: ['./create-cirel.component.scss']
})
export class CreateCirelComponent implements OnInit {
  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  colorsB$: Observable<ColorB[]>;
  dieProducts$: Observable<DieProduct[]>;
  printers$: Observable<Printer[]>;
  colorTypes: ColorType[] = [];
  colors: ColorCatalog[] = [];
  columns: TableColumn<CirelColor>[];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private fb: FormBuilder,
    private colorBService: ColorBService,
    private dieProductService: DieProductService,
    private printerService: PrinterService,
    private colorTypeService: ColorTypeService,
    private cdr: ChangeDetectorRef,
    private colorCService: ColorCService,
    private cyrelService: CirelService,
    private toastService: ToastService,
    private router: Router
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Cireles', routerLink: ['/home/cireles']},
      {label: 'Crear cirel', routerLink: ['/home/cireles/crear']},
    ]);
  }

  ngOnInit(): void {
    this.colorsB$ = this.colorBService.getAll();
    this.dieProducts$ = this.dieProductService.getAllAvailableDieProducts();
    this.printers$ = this.printerService.getAll();
    this.getColorTypes();
    this.getColorsCatalog();
    this.getCyrelColorsColumns();
    this.form = this.fb.group({
      print: [null, [
        Validators.required,
        Validators.maxLength(8)
      ]],
      description: [null, [
        Validators.required,
        Validators.maxLength(128)
      ]],
      dieProductIds: [null, [
        Validators.required,
        Validators.min(1)
      ]],
      mbLeafId: [null],
      printer: [null, [
        Validators.required
      ]],
      description2: [null],
      observation: [null],
      cyrelColors: this.fb.array([])
    });
  }

  getCyrelColorsColumns(): void {
    this.columns = [
      {field: 'index', header: 'Orden'},
      {field: 'colorType', header: 'Tipo de color'},
      {field: 'color', header: 'Color'},
      {field: 'observation', header: 'Observaciones'}
    ];
  }

  getColorTypes(): void {
    this.colorTypeService.getAllColorTypes()
      .subscribe(colorTypes => {
        this.colorTypes = colorTypes;
      });
  }

  searchColorType(id: string): string {
    const colorType = this.colorTypes.find(colorType => colorType.id === id);
    return colorType ? colorType.name : '';
  }

  get print() {
    return this.form.get('print');
  }

  get description() {
    return this.form.get('description');
  }

  get mbLeafId() {
    return this.form.get('mbLeafId');
  }

  get dieProductIds() {
    return this.form.get('dieProductIds');
  }

  get printer() {
    return this.form.get('printer');
  }

  get cyrelColorsFormArray() {
    return this.form.get('cyrelColors') as FormArray;
  }

  getColorIndex(index: number) {
    return this.cyrelColorsFormArray.at(index).get('index');
  }

  getColorType(index: number) {
    return this.cyrelColorsFormArray.at(index).get('colorType');
  }

  getColor(index: number) {
    return this.cyrelColorsFormArray.at(index).get('color');
  }

  getObservation(index: number) {
    return this.cyrelColorsFormArray.at(index).get('observation');
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    const body: CreateCirel = {...this.form.getRawValue()};
    body.printerId = this.printer.value.id;

    this.cyrelService.createCirel(body)
      .subscribe(() => {
        this.toastService.success('Cirel creado correctamente');
        setTimeout(() => {
          this.router.navigate(['/home/cireles']);
        }, 1000);
      });
  }

  createDieColors(value: any) {
    const printer: Printer = value.value;

    if (!printer) {
      this.cyrelColorsFormArray.clear();
      return;
    }

    for (let i = 0; i < printer.numColors; i++) {
      this.cyrelColorsFormArray.push(this.addCyrelColorForm(i));
    }

    this.addDefaultLeafColor(printer.numColors);
  }

  addCyrelColorForm(index: number): FormGroup {
    return this.fb.group({
      index: [{value: index + 1, disabled: true}],
      colorType: [{value: COLOR_TYPE_CYREL, disabled: true}, Validators.required],
      color: [null, Validators.required],
      observation: [null],
      id: index + 1
    });
  }

  addDefaultLeafColor(printerNumberColors: number): void {
    const defaultLeafIndex = printerNumberColors + 1;
    this.cyrelColorsFormArray.push(this.fb.group({
      index: [{value: defaultLeafIndex, disabled: true}],
      colorType: [{value: COLOR_TYPE_LEAF, disabled: true}, Validators.required],
      color: [null, Validators.required],
      observation: [null],
      id: defaultLeafIndex
    }));
  }

  onRowEditSave() {
    this.cdr.detectChanges();
  }

  onRowEditCancel() {
    this.cdr.detectChanges();
  }

  private getColorsCatalog() {
    this.colorCService.getAll()
      .subscribe(colors => {
        this.colors = colors;
      });
  }
}
