import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MaterialRequestRes } from 'src/app/types/material-request.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { FilterMetadata, MenuItem } from 'primeng/api';
import { Status } from 'src/app/types/catalogs.types';
import { SearchRequest } from 'src/app/types/pageable.types';
import { TypePermission } from 'src/app/types/permission';
import { FormControl } from '@angular/forms';
import { TableColumn } from 'src/app/types/table.types';
import { Table } from 'primeng/table';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { Router } from '@angular/router';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Turn } from 'src/app/types/turn.types';
import { TurnService } from 'src/app/core/http/turn/turn.service';
import { StatusService } from 'src/app/core/http/catalogs/status/status.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { MaterialRequestService } from 'src/app/core/http/material-request/material-request.service';
import { MATERIAL_REQUEST_STATUS_TYPE } from 'src/app/core/constants/status-types';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { PFilter } from 'src/app/types/filter.types';
import { getSearchCriteria } from 'src/app/core/utils/filter-table';
import { ExportExcelService } from 'src/app/core/services/export-excel.service';
import { FILE_NAMES } from 'src/app/core/constants/excel';
import { formatDate } from '@angular/common';
import { dateFormat, localString } from 'src/app/core/constants/date';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ColorAModalComponent } from 'src/app/modules/catalogs/components/color-a-modal/color-a-modal.component';
import { NgxPdfViewerComponent } from 'src/app/shared/components/ngx-pdf-viewer/ngx-pdf-viewer.component';

@Component({
  selector: 'app-material-request-list',
  templateUrl: './material-request-list.component.html',
  styleUrls: ['./material-request-list.component.scss']
})
export class MaterialRequestListComponent implements OnInit, AfterViewInit, OnDestroy {

  requests: MaterialRequestRes[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedMaterialRequest: MaterialRequestRes;
  menuItems: MenuItem[] = [];
  turns: Turn[] = [];
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
  columns: TableColumn<MaterialRequestRes>[];
  pdfDialogRef: DynamicDialogRef;

  @ViewChild('dt', {static: true}) table: Table;

  constructor(
    private toastService: ToastService,
    private permissionService: PermissionService,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private turnService: TurnService,
    private statusService: StatusService,
    private materialRequestService: MaterialRequestService,
    private exportExcelService: ExportExcelService,
    private dialogService: DialogService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Extrusión'},
      {label: 'Solicitud de material prima', routerLink: ['/home/solicitud']},
    ]);
  }

  ngOnInit(): void {
    this.getPermissionsToPage();
    this.getTurns();
    this.getStatus();
    this.getMaterialRequests();
    this.searchByInput();
    this.columns = [
      {field: 'number', header: 'Número'},
      {field: 'date', header: 'Fecha'},
      {field: 'documentBy', header: 'Respoonsable'},
      {field: 'status.name', header: 'Estado'},
      {field: 'turn.name', header: 'Turno'},
    ];
  }

  ngAfterViewInit() {
    this.table.onPage
      .subscribe(({first, rows}) => {
        this.searchRequest.page = first / rows;
        this.searchRequest.size = rows;
        this.getMaterialRequests();
      });
    this.table.onFilter
      .subscribe(({filters}) => {
        const statusFilters: Array<FilterMetadata> = filters['status'];
        if (statusFilters && statusFilters.length > 0) {
          const selectedStates: string[] = statusFilters[0].value;
          if (selectedStates) {
            this.searchRequest.filters = selectedStates;
          } else {
            this.searchRequest.filters = [];
          }
        }
        this.buildSearchCriteria(filters);
        this.getMaterialRequests();
      });
  }

  buildSearchCriteria(filters: any): void {
    this.searchRequest.searchCriteria = [];
    let primeFilters: Array<string> = Object.keys(filters);
    primeFilters = primeFilters.filter(filter => filter !== 'status');
    const filterFields: Array<PFilter> = [];
    primeFilters.forEach(field => {
      const filter: PFilter = {
        key: field,
        values: filters[field]
      };
      filterFields.push(filter);
    });
    this.searchRequest.searchCriteria = getSearchCriteria(filterFields);
  }


  getTurns() {
    this.turnService.getTurns()
      .subscribe(turns => {
        this.turns = turns;
      });
  }

  getStatus() {
    this.statusService.getAllByType(MATERIAL_REQUEST_STATUS_TYPE)
      .subscribe(status => {
        this.requestStatus = status;
      });
  }

  getMaterialRequests() {
    this.materialRequestService.getMaterialRequests(this.searchRequest)
      .subscribe(requests => {
        this.requests = requests.content;
        this.table.totalRecords = requests.totalElements;
      });
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

  searchByInput() {
    this.searchFomControl.valueChanges
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(query => {
        this.searchRequest.query = query;
        this.getMaterialRequests();
      });
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.READ)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
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

  clear(dt: Table) {
    dt.clear();
  }

  exportToXSLX() {
    const data = [];
    this.requests.forEach(request => {
      data.push({
        'Número': request.number,
        'Fecha': formatDate(request.date, dateFormat, localString),
        'Responsable': request.documentBy,
        'Estado': request.status.name,
        'Turno': request.turn.name
      });
    });

    this.exportExcelService.exportAsExcelFile(data, FILE_NAMES.MATERIAL_REQUEST);
  }

  generateReceipt(request: MaterialRequestRes) {
    this.materialRequestService.generateReceipt(request.id)
      .subscribe(data => {
        const type = data.body.type;
        const fileName = data.headers.get('content-disposition').split('filename=')[1];
        let srcPdf = URL.createObjectURL(
          new Blob([data.body], {type})
        );

        this.pdfDialogRef = this.dialogService.open(NgxPdfViewerComponent, {
          data: {
            fileName,
            srcPdf
          },
          header: `Comprobante de solicitud de materia prima ${request.number}`,
          width: '90%',
          contentStyle: {'max-width': '100%', 'overflow': 'auto'},
        });
      });
  }

  ngOnDestroy(): void {
    if (this.pdfDialogRef) {
      this.pdfDialogRef.close();
    }
  }
}
