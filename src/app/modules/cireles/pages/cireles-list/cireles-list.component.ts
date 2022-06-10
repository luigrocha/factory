import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { CirelService } from 'src/app/core/http/cirel/cirel.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Cirel, CirelColor } from 'src/app/types/cirel.types';
import { TableColumn, TableHeader } from 'src/app/types/table.types';
import { Table } from 'primeng/table';
import { debounceTime } from 'rxjs/operators';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-cireles-list',
  templateUrl: './cireles-list.component.html',
  styleUrls: ['./cireles-list.component.scss'],
  providers: [ConfirmationService],
})
export class CirelesListComponent implements OnInit {

  columns: TableColumn<Cirel>[];
  pageSize: number = 10;
  cirels: Cirel[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];

  initialPage: number = 0;
  actualPage: number = 0;
  query: string = null;
  menuItems: MenuItem[];
  selectedCirel: Cirel;

  subHeaders: TableHeader<CirelColor>[] = [
    { label: 'Tipo', property: 'colorType' },
    { label: 'Color', property: 'color' }
  ];

  @ViewChild('dt') table: Table;

  permissionsPage: TypePermission[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private cirelService: CirelService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Cireles', routerLink: ['/home/cireles'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getMenuItems();
    this.getCirels(this.initialPage, this.pageSize, this.query);
    this.columns = [
      { field: 'print', header: 'Impresión' },
      { field: 'printer', header: 'Impresora' },
      { field: 'description', header: 'Descripción' },
      { field: 'cyrelColors', header: 'Colores' },
      { field: 'die', header: 'Troquel' },
      { field: 'observation', header: 'Observaciones' },
    ];
  }

  getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Editar',
        icon: 'pi pi-pencil'
      },
      {
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteCirel()
      }
    ];
  }

  ngAfterViewInit(): void {
    this.table.onPage
      .subscribe(({ first, rows }) => {
        this.actualPage = first / rows
        this.pageSize = rows;
        this.getCirels(this.actualPage, this.pageSize, this.query);
      });
    this.table.onFilter
      .pipe(
        debounceTime(500)
      )
      .subscribe(({ filters }) => {
        const isEmpty = Object.keys(filters).length === 0;
        if (isEmpty) {
          this.query = null;
        } else {
          this.query = filters.global.value;
        }

        this.getCirels(this.initialPage, this.pageSize, this.query);
      });
  }

  private getCirels(page: number, size: number, query: string): void {
    this.cirelService.getAllCirels(page, size, query)
      .subscribe(response => {
        this.cirels = response.content;
        this.table.totalRecords = response.totalElements;
      });
  }

  addNewCyrel(): void {

  }

  deleteCirel(): void {
  }

  editCirel(): void {
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
