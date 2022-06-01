import { Component, OnInit } from '@angular/core';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler } from 'src/app/types/celler.types';
import { Material, TypeMaterial } from 'src/app/types/material.types';

@Component({
  selector: 'app-store-ceb',
  templateUrl: './store-ceb.component.html',
  styleUrls: ['./store-ceb.component.scss']
})
export class StoreCebComponent implements OnInit {

  cellers: Celler[];

  celler: Celler;

  itemDialog: boolean;

  submitted: boolean;

  typeMaterials: TypeMaterial[];

  typeMaterial: TypeMaterial;

  materials: Material[];

  material: Material;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private cellerService: CellerService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Gestión de bodega', routerLink: ['bodega'] },
      { label: 'CEB', routerLink: ['bodega/CEB'] },
    ]);
  }

  ngOnInit() {
    this.getAllTypeMaterial();
  }

  openNew() {
    this.celler = {};
    this.submitted = false;
    this.itemDialog = true;
  }

  hideDialog() {
    this.itemDialog = false;
    this.submitted = false;
  }

  onTypeSelected(e: any) {
    const type = e.value;
    this.getAllMaterialByType(type.id);
  }

  onProductSelected(e: any) {
    const product = e.value;
    this.getCellerByMaterialCode(product.id);
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(
      (typeMaterials => {
        this.typeMaterials = typeMaterials;
      })
    );
  }

  getAllMaterialByType(id: number) {
    this.materials = [];
    this.materialService.getAllMaterialByType(id).subscribe(
      (materials => {
        this.materials = materials;
      })
    );
  }

  getCellerByMaterialCode(id: number) {
    this.cellers = [];
    this.cellerService.getCellerByMaterialCode(id).subscribe(
      (cellers => {
        this.cellers = cellers;
      })
    );
  }

}
