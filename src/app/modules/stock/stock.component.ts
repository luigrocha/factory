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
import { LoteCeller, MaterialStock, Stock, TypeMaterialStock } from 'src/app/types/celler.types';
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
  materialStock: MaterialStock[];
  stockTotal: number;
  pieData: any;
  basicData: any;
  dougData: any;
  pieOptions: any;
  basicOptions: any;
  dougOptions: any;
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
    this.stockTotal = 0;
    this.stock = null;
    this.typeMaterialStock = null;
    this.materialStock = null;
    const colors = [];
    const labels = [];
    const values = [];
    if (this.lote.value && this.material.value && this.type.value) {
      this.cellerDetailService.getCellerDetailStock(data.material, data.lote).subscribe(
        (stock => {
          this.isReq = true;
          this.stock = stock;
          this.stockTotal = stock.stock;
          this.stock.locationStock.forEach(location => {
            labels.push(location.location.description);
            values.push(location.stock);
            colors.push(this.generateAleatoryColor());
          });
          this.pieData = {
            labels,
            datasets: [
              {
                data: values,
                backgroundColor: colors,
                hoverBackgroundColor: colors
              }
            ]
          };
        })
      );
    } else if (this.material.value && this.type.value) {
      this.cellerDetailService.getByMaterialStock(data.material).subscribe(
        (materialStock => {
          this.isReq = true;
          this.materialStock = materialStock;
          materialStock.forEach(material => {
            labels.push(material.lote);
            values.push(material.weight);
            colors.push(this.generateAleatoryColor());
            this.stockTotal += material.weight;
          });
          this.dougData = {
            labels,
            datasets: [
              {
                data: values,
                backgroundColor: colors,
                hoverBackgroundColor: colors
              }
            ]
          };
        })
      )
    } else if (this.type.value) {
      this.cellerDetailService.getByTypeMaterialStock(data.type).subscribe(
        (typeMaterialStock => {
          this.isReq = true;
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
          this.dougOptions = {
            plugins: {
              legend: {
                labels: {
                  color: '#ebedef'
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
          this.dougOptions = {
            plugins: {
              legend: {
                labels: {
                  color: '#495057'
                }
              }
            }
          };
        }
      })
    );
  }

}
