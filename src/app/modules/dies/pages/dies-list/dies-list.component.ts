import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Die } from 'src/app/types/dies.types';
import { DieService } from 'src/app/core/http/dies/die.service';
import { TableColumn } from 'src/app/types/table.types';
import { Table } from 'primeng/table';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { debounceTime } from 'rxjs/operators';
import { ConfirmationService } from 'primeng/api';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';

@Component({
  selector: 'app-dies-list',
  templateUrl: './dies-list.component.html',
  styleUrls: ['./dies-list.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class DiesListComponent implements OnInit, AfterViewInit {

  columns: TableColumn<Die>[];
  pageSize: number = 10;
  dies: Die[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];

  globalFilterFields: string[] = ['name', 'code', 'description'];
  initialPage: number = 0;
  actualPage: number = 0;
  query: string = null;

  @ViewChild('dt') table: Table;

  constructor(
    private dieService: DieService,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Troqueles', routerLink: ['/home/troqueles']}
    ]);
  }

  ngAfterViewInit(): void {
    this.table.onPage
      .subscribe(({first, rows}) => {
        this.actualPage = first / rows
        this.pageSize = rows;
        this.getDies(this.actualPage, this.pageSize, this.query);
      });
    this.table.onFilter
      .pipe(
        debounceTime(500)
      )
      .subscribe(({filters}) => {
        const isEmpty = Object.keys(filters).length === 0;
        if (isEmpty) {
          this.query = null;
        } else {
          this.query = filters.global.value;
        }

        this.getDies(this.initialPage, this.pageSize, this.query);
      });
  }

  ngOnInit(): void {
    this.getDies(this.initialPage, this.pageSize, this.query);

    this.columns = [
      {header: 'Troquel', field: 'name'},
      {header: 'Fecha', field: 'createdDate'},
      {header: 'Fabricante', field: 'name'},
      {header: 'Máquina', field: 'name'},
      {header: 'Prod', field: 'code'},
      {header: 'Referencia', field: 'description'},
      {header: 'Largo (mm)', field: 'length'},
      {header: 'Ancho (mm)', field: 'width'},
      {header: 'Cant1 (u)', field: 'quantity'},
      {header: 'Cant. largo (u)', field: 'quantityLength'},
      {header: 'Sep. largo (u)', field: 'separationLength'},
      {header: 'Cant. ancho (u)', field: 'quantityWidth'},
      {header: 'Sep. ancho (u)', field: 'separationWidth'},
      {header: 'Bordes largo (mm)', field: 'borderLength'},
      {header: 'Bordes ancho (mm)', field: 'borderWidth'},
      {header: 'Largo lámina (mm)', field: 'leafLength'},
      {header: 'Ancho lámina (mm)', field: 'leafWidth'},
      {header: 'Área', field: 'area'},
      {header: 'GSMDIS', field: 'gsmdis'},
      // { label: 'DesbMúltiple', field: 'dsbMultiple' },
      // { label: 'Observaciones.', field: 'observations' },
      {header: 'Estado', field: 'status'},
    ];
  }

  private getDies(page: number, size: number, query: string): void {
    this.dieService.getAllDies(page, size, query)
      .subscribe(response => {
        this.dies = response.content;
        this.table.totalRecords = response.totalElements;
      });
  }

  addNewDie(dt: Table): void {
    console.log(dt);
  }

  deleteSelectedDies(): void {

  }
}
