import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Die } from 'src/app/types/dies.types';
import { DieService } from 'src/app/core/http/dies/die.service';
import { TableHeader } from 'src/app/types/table.types';
import { Table } from 'primeng/table';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-dies-list',
  templateUrl: './dies-list.component.html',
  styleUrls: ['./dies-list.component.scss']
})
export class DiesListComponent implements OnInit, AfterViewInit {
  dies: Die[] = [];
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  globalFilterFields: string[] = ['name', 'code', 'description'];
  selectedDies: Die[] = [];
  initialPage: number = 0;
  pageSize: number = 10;
  actualPage: number = 0;
  query: string = null;
  headers: TableHeader<Die>[] = [
    { label: 'Troquel', property: 'name' },
    { label: 'Fecha', property: 'createdDate' },
    { label: 'Fabricante', property: 'name' },
    { label: 'Máquina', property: 'name' },
    { label: 'Prod', property: 'code' },
    { label: 'Referencia', property: 'description' },
    { label: 'Largo', property: 'length' },
    { label: 'Ancho', property: 'width' },
    { label: 'Cant1', property: 'quantity' },
    { label: 'Cant. largo', property: 'quantityLength' },
    { label: 'Sep. largo', property: 'separationLength' },
    { label: 'Cant. ancho', property: 'quantityWidth' },
    { label: 'Sep. ancho', property: 'separationWidth' },
    { label: 'Largo lámina', property: 'leafLength' },
    { label: 'Ancho lámina', property: 'leafWidth' },
    { label: 'Área', property: 'area' },
    { label: 'GSMDIS', property: 'gsmdis' },
    // { label: 'DesbMúltiple', property: 'dsbMultiple' },
    // { label: 'Observaciones.', property: 'observations' },
    { label: 'Estado', property: 'status' },
  ];

  @ViewChild('dt') table: Table;
  constructor(
    private dieService: DieService,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Troqueles', routerLink: ['/home/troqueles'] }
    ]);
  }

  ngAfterViewInit(): void {
    this.table.onPage
      .subscribe(({ first, rows }) => {
        this.actualPage = first / rows
        this.pageSize = rows;
        this.getDies(this.actualPage, this.pageSize, this.query);
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

        this.getDies(this.initialPage, this.pageSize, this.query);
      });
  }

  ngOnInit(): void {
    this.getDies(this.initialPage, this.pageSize, this.query);
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
