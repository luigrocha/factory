import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { CellerDetailService } from 'src/app/core/http/celler/celler-detail.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { PreferencesService } from 'src/app/core/http/preferences/preferences.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { LayoutService } from 'src/app/core/services/layout.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { LoteCeller, Stock, TypeMaterialStock } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';

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
  stock: Stock;
  typeMaterialStock: TypeMaterialStock[];
  stockTotal: number;
  pieData: any;
  basicData: any;
  pieOptions: any;
  basicOptions: any;
  isReq: boolean;

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
      { label: 'Bodega' },
      { label: 'Stock', routerLink: ['stock'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
    this.form = this.fb.group({
      type: [null, [
        Validators.required,
      ]],
      material: [null, [
        Validators.required,
      ]],
      lote: [null, [
        Validators.required,
      ]],
    });
    this.getPreferencesTheme();
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(typeMaterial => {
      this.types = typeMaterial;
    });
  }

  onTypeSelected(e: any) {
    const type = e.value;
    this.isReq = false;
    this.getAllMaterialByType(type);
  }

  getAllMaterialByType(id: number) {
    this.materials = [];
    this.materialService.getAllMaterialByType(id).subscribe(
      (materials => {
        this.materials = materials;
      })
    );
  }

  onMaterialSelected(e: any) {
    const material = e.value;
    this.isReq = false;
    this.getLoteByMaterialCode(material);
  }

  getLoteByMaterialCode(id: number) {
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

  onLoteSelected(e: any) {
    this.isReq = false;
  }

  getMatereialName(id: number) {
    return this.materials.find(material => material.id === id).name;
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

  save() {
    const data = { ...this.form.getRawValue() };
    this.stock = null;
    this.typeMaterialStock = null;
    const colors = [];
    if (this.lote.value && this.material.value && this.type.value) {
      this.cellerDetailService.getCellerDetailStock(data.material, data.lote).subscribe(
        (stock => {
          this.isReq = true;
          this.stock = stock;
          this.stockTotal = stock.stock;
          const locations = [];
          const stocks = [];
          this.stock.locationStock.forEach(location => {
            locations.push(location.location.description);
            stocks.push(location.stock);
            colors.push(this.generateAleatoryColor());
          });
          this.pieData = {
            labels: locations,
            datasets: [
              {
                data: stocks,
                backgroundColor: colors,
                hoverBackgroundColor: colors
              }
            ]
          };
        })
      );
    } else if (this.material.value && this.type.value) {

    } else if (this.type.value) {
      this.cellerDetailService.getByTypeMaterialStock(data.type).subscribe(
        (typeMaterialStock => {
          this.isReq = true;
          const labels = [];
          const values = [];
          this.stockTotal = 0;
          this.typeMaterialStock = typeMaterialStock;
          typeMaterialStock.forEach(type => {
            labels.push(type.name);
            values.push(type.stock);
            colors.push(this.generateAleatoryColor());
            this.stockTotal += type.stock;
          });
          this.basicData = {
            labels,
            datasets: [
              {
                label: this.types.find(type => this.type.value === type.id).name,
                data: values,
                backgroundColor: colors,
                hoverBackgroundColor: colors
              }
            ]
          };
        })
      );
    }

  }

  random(min: number, max: number) {
    return Math.floor((Math.random() * (max - min + 1)) + min);
  }

  generateAleatoryColor() {
    const hexadecimal = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');
    let aleatoryColor = '#';
    for (let i = 0; i < 6; i++) {
      const posarray = this.random(0, hexadecimal.length);
      aleatoryColor += hexadecimal[posarray];
    }
    return aleatoryColor;
  }

  getPreferencesTheme() {
    this.preferencesService.getPreferencesByUsername(this.authService.getLoggedUser().preferred_username).subscribe(
      (config => {
        if (config.layoutMode === 'dark') {
          this.pieOptions = {
            plugins: {
              legend: {
                labels: {
                  color: '#ebedef'
                }
              }
            }
          };
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
          this.pieOptions = {
            plugins: {
              legend: {
                labels: {
                  color: '#495057'
                }
              }
            }
          };
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
