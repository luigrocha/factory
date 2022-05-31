import { Component, OnInit } from '@angular/core';
import { MaterialService } from 'src/app/core/http/materials/materials.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Material, TypeMaterial } from 'src/app/types/material.types';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-material',
  templateUrl: './material.component.html',
  styleUrls: ['./material.component.scss']
})
export class MaterialComponent implements OnInit {

  typeMaterials: TypeMaterial[];

  materials: Material[];

  cols: any[];

  permissionsPage: TypePermission[];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private materialService: MaterialService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Bodega' },
      { label: 'Materia Prima', routerLink: ['/home/material'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllTypeMaterial();
    this.getAllMaterialByType(1);
    this.cols = [
      { field: 'name', header: 'Nombre' },
    ];
  }

  getAllTypeMaterial() {
    this.materialService.getAllTypeMaterial().subscribe(
      (typeMaterials => {
        this.typeMaterials = typeMaterials;
      })
    );
  }

  onChangeTab(e: any) {
    const id = e.index + 1;
    this.getAllMaterialByType(id);
  }

  getAllMaterialByType(id: number) {
    this.materials = [];
    this.materialService.getAllMaterialByType(id).subscribe(
      (materials => {
        this.materials = materials;
      })
    );
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage().subscribe(
      (data) => {
        this.permissionsPage = data;
      }
    );
  }

  isAllow(id: number): boolean {
    if (this.permissionsPage) {
      return this.permissionsPage.find(permission => permission.id === id).flag;
    }
    return false;
  }

}
