import { Component, OnInit } from "@angular/core";
import { ConfirmationService, MenuItem } from "primeng/api";
import { PermissionEnum } from "src/app/core/constants/permisions";
import { ManufacturerService } from "src/app/core/http/catalogs/manufacturer/manufacturer.service";
import { PermissionService } from "src/app/core/http/permissions/permission.service";
import { BreadcrumbService } from "src/app/core/services/breadcrumb.service";
import { Manufacturer } from "src/app/types/manufacturer.types";
import { TypePermission } from "src/app/types/permission";

@Component({
  selector: "app-manufacturer",
  templateUrl: "./manufacturer.component.html",
  styleUrls: ["./manufacturer.component.scss"],
  providers: [ConfirmationService]
})
export class ManufacturerComponent implements OnInit {
  manufacturer: Manufacturer;
  manufacturers: Manufacturer[];
  selectedManufacturers: Manufacturer[];
  selectedManufacturer: Manufacturer;

  manufacturerDialog: boolean;
  loading = true;
  permissionsPage: TypePermission[];
  items: MenuItem[] = [];
  submitted: boolean;
  cols: any[];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private manufacturerService: ManufacturerService,
    private permissionService: PermissionService,
    private confirmationService: ConfirmationService,
  ) {
    this.breadcrumbService.setItems([
      { label: "Diseño" },
      { label: "Catálogos" },
      { label: "Fabricantes", routerLink: ["home/catalogs/fabricantes"] },
    ]);
  }

  ngOnInit(): void {
    this.getPermissionsPage();
    this.getAll();
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage().subscribe((data) => {
      this.permissionsPage = data;
    });
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.items.push({
        label: "Editar",
        icon: "pi pi-pencil",
        command: () => this.editManufacturer(this.selectedManufacturer),
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.items.push({
        label: "Eliminar",
        icon: "pi pi-trash",
        command: () => this.deleteManufacturer(this.selectedManufacturer),
      });
    }
  }

  getAll() {
    this.manufacturerService
      .getAllManufacturers()
      .subscribe((manufacturers) => {
        this.manufacturers = manufacturers;
        this.loading = false;
      });
  }

  editManufacturer(manufacturer: Manufacturer) {}

  deleteManufacturer(manufacturer: Manufacturer) {}

  openNew() {}

  deleteSelectedManufacturers() {}

  isAllow(id: number): boolean {
    if (this.permissionsPage) {
      return this.permissionsPage.find((permission) => permission.id === id)
        .flag;
    }
    return false;
  }
}
