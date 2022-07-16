import {Component, OnInit, ViewChild} from '@angular/core';
import {MaterialRequest} from 'src/app/types/material-request.types';
import {TABLE_REPORT_TEMPLATE} from 'src/app/core/constants/table';
import {MenuItem} from 'primeng/api';
import {Status} from 'src/app/types/catalogs.types';
import {SearchRequest} from 'src/app/types/pageable.types';
import {TypePermission} from 'src/app/types/permission';
import {FormControl} from '@angular/forms';
import {TableColumn} from 'src/app/types/table.types';
import {Table} from 'primeng/table';
import {checkIfOptionIsAllowed} from 'src/app/core/utils/permission';
import {PermissionService} from 'src/app/core/http/permissions/permission.service';
import {PermissionEnum} from 'src/app/core/constants/permisions';
import {Router} from '@angular/router';
import {BreadcrumbService} from 'src/app/core/services/breadcrumb.service';

@Component({
  selector: 'app-material-request-list',
  templateUrl: './material-request-list.component.html',
  styleUrls: ['./material-request-list.component.scss']
})
export class MaterialRequestListComponent implements OnInit {

  requests: MaterialRequest[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedMaterialRequest: MaterialRequest;
  menuItems: MenuItem[] = [];
  requestStatus: Status[] = [];
  searchRequest: SearchRequest = {
    page: 0,
    size: 20,
    filters: [],
    query: '',
    searchCriteria: []
  };

  permissions: TypePermission[];
  searchFomControl = new FormControl();
  columns: TableColumn<MaterialRequest>[];

  @ViewChild('dt', {static: true}) table: Table;

  constructor(
    private permissionService: PermissionService,
    private router: Router,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Extrusión'},
      {label: 'Solicitud de material prima', routerLink: ['/home/solicitud']},
    ]);
  }

  ngOnInit(): void {
    this.getPermissionsToPage();
  }

  createMaterialRequest() {
    this.router.navigate(['/home/solicitud/form/info']);
  }

  getPermissionsToPage() {
    this.permissionService.findPermissionPage()
      .subscribe(permissions => {
          this.permissions = permissions;
          this.getMenuItems();
        }
      );
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.READ)) {
      this.menuItems.push({
        label: 'Ver solicitud',
        icon: 'pi pi-eye',
        command: () => this.editRequest()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteRequest()
      });
    }
  }

  isAllow(id: number): boolean {
    return checkIfOptionIsAllowed(this.permissions, id);
  }

  private editRequest() {
  }

  private deleteRequest() {
  }
}
