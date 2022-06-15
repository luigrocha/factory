import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Die } from 'src/app/types/dies.types';
import { DieService } from 'src/app/core/http/dies/die.service';
import { TableColumn } from 'src/app/types/table.types';
import { Table } from 'primeng/table';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { debounceTime } from 'rxjs/operators';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { TypePermission } from 'src/app/types/permission';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { DieProduct } from "../../../../types/die-product.types";
import { DialogService, DynamicDialogRef } from "primeng/dynamicdialog";
import { Status } from "../../../../types/catalogs.types";
import { DIE_PRODUCT_STATUS_TYPE, DIE_STATUS_TYPE } from "../../../../core/constants/status-types";
import { StatusService } from "../../../../core/http/catalogs/status/status.service";
import { ToastService } from "../../../../core/services/toast.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-dies-list',
  templateUrl: './dies-list.component.html',
  styleUrls: ['./dies-list.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class DiesListComponent implements OnInit {

  pageSize: number = 10;
  dies: Die[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedDie: Die;
  menuItems: MenuItem[];
  dieStates: Status[] = [];

  globalFilterFields: string[] = [
    'name',
    'createdDate',
    'dieProduct.code',
    'dieProduct.name',
    'manufacturer',
    'machines',
    'status.name'
  ];
  permissionsPage: TypePermission[];

  constructor(
    private dieService: DieService,
    private breadcrumbService: BreadcrumbService,
    private permissionService: PermissionService,
    private statusService: StatusService,
    private router: Router,
    private toastService: ToastService,
    public dialogService: DialogService,
    private confirmationService: ConfirmationService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Troqueles', routerLink: ['/home/troqueles'] }
    ]);
  }

  ngOnInit(): void {
    this.getPermissionsPage();
    this.getMenuItems();
    this.getDies();
    this.getDieProductStates();
  }

  private getDies(): void {
    this.dieService.getAllDies()
      .subscribe(dies => {
        this.dies = dies;
      });
  }

  private getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editDie()
      },
      {
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteDie()
      }
    ];
  }


  private editDie(): void {
  }

  private deleteDie(): void {
  }

  private getDieProductStates(): void {
    this.statusService.getAllByType(DIE_STATUS_TYPE)
      .subscribe(dieStates => {
        this.dieStates = dieStates;
      });
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

  addDie(): void {
    this.router.navigate(['/home/troqueles/crear']);
  }

  clear(table: Table) {
    table.clear();
  }
}
