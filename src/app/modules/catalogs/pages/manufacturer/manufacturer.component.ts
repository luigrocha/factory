import { Component, OnInit } from "@angular/core";
import { ConfirmationService, MenuItem, MessageService } from "primeng/api";
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
  providers: [ConfirmationService, MessageService]
})
export class ManufacturerComponent implements OnInit {
  
  manufacturer: Manufacturer;
  manufacturers: Manufacturer[];
  selectedManufacturers: Manufacturer[] = [];
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
    private messageService: MessageService
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
    this.cols = [
      { field: 'name', header: 'Codigo'},
      { field: 'description', header: 'Descripcion'},
      { field: 'observation', header: 'Observacion'},
    ]
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage().subscribe((data) => {
      this.permissionsPage = data;
      this.getMenuItems();
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

  editManufacturer(manufacturer: Manufacturer) {
    this.manufacturer = {...manufacturer};
    this.manufacturerDialog = true;
  }

  deleteManufacturer(manufacturer: Manufacturer) {
    this.confirmationService.confirm({
      message: `Estás seguro de eliminar el fabricante: ${manufacturer.name}`,
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.manufacturerService.delete(manufacturer.id).subscribe(
          () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Fabricante eliminado',
              life: 3000
            });
            this.manufacturers = [];
            this.getAll();
          },
          (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: err.error,
              life: 3000
            });
          }
        );
      }
    });
  }

  openNew() {
    this.manufacturer = {};
    this.submitted = false;
    this.manufacturerDialog = true;
  }

  saveManufacturer() {
    this.submitted = true;

    if (this.manufacturer.id) {
      this.manufacturerService.update(this.manufacturer.id, this.manufacturer).subscribe(
        () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Fabricante Actualizado',
            life: 3000,
          });
          this.manufacturers = [];
          this.getAll();
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: err.error,
            life: 3000,
          });
        }
      );
    } else if (this.isValidToSave()) {
      this.manufacturerService.create(this.manufacturer).subscribe(
        () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Fabricante creado',
            life: 3000,
          });
          this.manufacturers = [];
          this.getAll();
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: err.error,
            life: 3000,
          });
        }
      );
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Adevertencia',
        detail: 'Llene todos los campos requeridos',
        life: 3000,
      });
      this.manufacturerDialog = true;
      return;
    }

    this.manufacturers = [...this.manufacturers];
    this.manufacturerDialog = false;
    this.manufacturer = {};
  }

  isValidToSave(): boolean {
    return this.manufacturer.name && this.manufacturer.description ? true : false;
  }

  deleteSelectedManufacturers() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los fabricantes seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedManufacturers.forEach(manufacturer => {
          this.manufacturerService.delete(manufacturer.id).subscribe(
            () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Fabricante Eliminado',
                life: 3000,
              });
              this.manufacturers = [];
              this.getAll();
            },
            (err) => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: err.error,
                life: 3000,
              });
            }
          );
        });
        this.selectedManufacturers = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Fabricantes Eliminados',
          life: 3000,
        });
      },
    });
  }

  isAllow(id: number): boolean {
    if (this.permissionsPage) {
      return this.permissionsPage.find((permission) => permission.id === id)
        .flag;
    }
    return false;
  }

  hideDialog() {
    this.manufacturerDialog = false;
    this.submitted = false;
  }
}
