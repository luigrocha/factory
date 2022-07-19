import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from 'src/app/core/auth/service/auth.service';
import {FORM_ERROR_MESSAGES} from 'src/app/core/constants/form-error';
import {CellerDetailService} from 'src/app/core/http/celler/celler-detail.service';
import {MaterialService} from 'src/app/core/http/materials/materials.service';
import {PreferencesService} from 'src/app/core/http/preferences/preferences.service';
import {BreadcrumbService} from 'src/app/core/services/breadcrumb.service';
import {ToastService} from 'src/app/core/services/toast.service';
import {AllStock, LoteCeller, Stock} from 'src/app/types/celler.types';
import {Config} from 'src/app/types/config.types';
import {Material, TypeMaterial} from 'src/app/types/material.types';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  types: TypeMaterial[];
  materials: Material[] = [];
  lotes: LoteCeller[] = [];
  stockTotal: number;

  basicData: any;
  basicOptions: any;
  config: Config;

  isReq: boolean;
  cols: any[] = [];
  isAll: boolean;
  isType: boolean;
  isMaterial: boolean;
  isLote: boolean;
  allStock: AllStock[];

  constructor(
    private fb: FormBuilder,
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private cellerDetailService: CellerDetailService,
    private toastService: ToastService,
    private preferencesService: PreferencesService,
    private authService: AuthService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Bodega'},
      {label: 'Stock', routerLink: ['stock']},
    ]);
  }

  get type() {
    return this.form.get('type');
  }

  get material() {
    return this.form.get('material');
  }

  get lote() {
    return this.form.get('lote');
  }

  ngOnInit() {
    this.getPreferencesTheme();
    this.getAllTypeMaterial();
    this.form = this.fb.group({
      type: [null, []],
      material: [null, []],
      lote: [null, []],
    });
    this.cols = [
      {field: 'material', header: 'Material'},
      {field: 'lote', header: 'Lote'},
      {field: 'location', header: 'Ubicación'},
      {field: 'stock', header: 'Stock'},
    ];
  }

  getAllStock() {
    this.cellerDetailService.getAllStock().subscribe(allStock => {
      this.allStock = allStock;
      this.isAll = true;
      this.isReq = true;
      this.buildDataChartMaterial();
    });
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(typeMaterial => {
      this.types = typeMaterial;
    });
  }

  onTypeSelected(e: any) {
    const type = e.value;
    this.isReq = false;
    this.resetAll();
    if (type) {
      this.getAllMaterialByType(type);
    }
  }

  getAllMaterialByType(id: number) {
    this.materials = [];
    this.lotes = [];
    this.materialService.getAllMaterialByType(id).subscribe(
      (materials => {
        this.materials = materials;
      })
    );
  }

  onMaterialSelected(e: any) {
    const material = e.value;
    this.resetAll();
    this.isReq = false;
    if (material) {
      this.getLotByMaterialCode(material);
    }
  }

  getLotByMaterialCode(id: number) {
    this.lotes = [];
    this.cellerDetailService.getLoteByMaterialCode(id).subscribe(
      (lotes => {
        this.lotes = lotes;
      }),
      (err => {
        this.toastService.error('No existen lotes para este material');
      })
    );
  }

  onLotSelected(e: any) {
    this.isReq = false;
    this.resetAll();
  }

  getNameConsult(): string {
    if (this.isAll) {
      return 'Stock de Bodega Total';
    } else if (this.isType) {
      return `Stock Tipo: ${this.types.find(type => this.type.value === type.id).name}`;
    } else if (this.isMaterial) {
      return `Stock Tipo: ${this.types.find(type => this.type.value === type.id).name} y Material: ${this.materials.find(material => this.material.value === material.id).name}`;
    } else if (this.isLote) {
      return `Stock Tipo: ${this.types.find(type => this.type.value === type.id).name} y Material: ${this.materials.find(material => this.material.value === material.id).name} en Lote : ${this.lote.value}`;
    }
    return '';
  }

  resetAll() {
    this.isAll = this.isType = this.isMaterial = this.isLote = false;
  }

  save() {
    const data = {...this.form.getRawValue()};
    this.stockTotal = 0;
    this.allStock = [];
    if (this.lote.value && this.material.value && this.type.value) {
      this.cellerDetailService.getMaterialLoteStock(data.material, data.lote).subscribe(
        (allStock => {
          this.isReq = true;
          this.resetAll();
          this.isLote = true;
          this.allStock = allStock;
          allStock.forEach(type => {
            this.stockTotal += type.stock;
          });
          this.buildDataChartLocation();
        })
      );
    } else if (this.material.value && this.type.value) {
      this.cellerDetailService.getMaterialStock(data.material).subscribe(
        (allStock => {
          this.isReq = true;
          this.resetAll();
          this.isMaterial = true;
          this.allStock = allStock;
          allStock.forEach(type => {
            this.stockTotal += type.stock;
          });
          this.buildDataChartLot();
        })
      );
    } else if (this.type.value) {
      this.cellerDetailService.getByTypeMaterialStock(data.type).subscribe(
        (allStock => {
          this.isReq = true;
          this.resetAll();
          this.isType = true;
          this.allStock = allStock;
          allStock.forEach(type => {
            this.stockTotal += type.stock;
          });
          this.buildDataChartMaterial();
        })
      );
    } else {
      this.getAllStock();
    }
  }

  buildDataChartLocation() {
    const labels = [];
    const hash = {};
    const names = this.allStock.filter(row => hash[row.location] ? false : hash[row.location] = true);
    const locationStock = [];
    names.forEach(name => {
      labels.push(name.location);
      let sumLocations = 0;
      this.allStock.forEach(lot => {
        if (lot.location === name.location) {
          sumLocations += lot.stock;
        }
      });
      locationStock.push(sumLocations);
    });
    this.basicData = {
      labels,
      datasets: [
        {
          label: 'Kg en ubicaciones',
          data: locationStock,
          backgroundColor: this.config.color,
          hoverBackgroundColor: this.config.color
        }
      ]
    };
  }

  buildDataChartLot() {
    const labels = [];
    const hash = {};
    const names = this.allStock.filter(row => hash[row.lote] ? false : hash[row.lote] = true);
    const lotStock = [];
    names.forEach(name => {
      labels.push(name.lote);
      let sumLocations = 0;
      this.allStock.forEach(lot => {
        if (lot.lote === name.lote) {
          sumLocations += lot.stock;
        }
      });
      lotStock.push(sumLocations);
    });
    this.basicData = {
      labels,
      datasets: [
        {
          label: 'Kg en lotes',
          data: lotStock,
          backgroundColor: this.config.color,
          hoverBackgroundColor: this.config.color
        }
      ]
    };
  }

  buildDataChartMaterial() {
    const labels = [];
    const hash = {};
    const names = this.allStock.filter(row => hash[row.name] ? false : hash[row.name] = true);
    console.log(this.config);
    const materialStock = [];
    names.forEach(name => {
      labels.push(name.name);
      let sumLotes = 0;
      this.allStock.forEach(lote => {
        if (lote.name === name.name) {
          sumLotes += lote.stock;
        }
      });
      materialStock.push(sumLotes);
    });
    this.basicData = {
      labels,
      datasets: [
        {
          label: 'Kg en Lotes',
          data: materialStock,
          backgroundColor: this.config.color,
          hoverBackgroundColor: this.config.color
        }
      ]
    };
  }

  getPreferencesTheme() {
    this.preferencesService.getPreferencesByUsername(this.authService.getLoggedUser().preferred_username).subscribe(
      (config => {
        this.config = config;
        this.getAllStock();
        const themes = [
          {name: 'denim', color: '#2f8ee5'},
          {name: 'sea-green', color: '#30A059'},
          {name: 'amber', color: '#D49341'},
        ];
        // tslint:disable-next-line:triple-equals
        this.config.color = themes.find(theme => theme.name == config.color).color;

        if (config.layoutMode === 'dark') {
          this.basicOptions = {
            plugins: {
              legend: {
                labels: {
                  color: '#ebedef'
                }
              }
            },
            scales: {
              x: {
                ticks: {
                  color: '#ebedef'
                },
                grid: {
                  color: 'rgba(255,255,255,0.2)'
                }
              },
              y: {
                ticks: {
                  color: '#ebedef'
                },
                grid: {
                  color: 'rgba(255,255,255,0.2)'
                }
              }
            }
          };

        } else {

          this.basicOptions = {
            plugins: {
              legend: {
                labels: {
                  color: '#495057'
                }
              }
            },
            scales: {
              x: {
                ticks: {
                  color: '#495057'
                },
                grid: {
                  color: '#ebedef'
                }
              },
              y: {
                ticks: {
                  color: '#495057'
                },
                grid: {
                  color: '#ebedef'
                }
              }
            }
          };

        }
      })
    );
  }

}
