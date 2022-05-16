import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CirelService } from 'src/app/core/http/cirel/cirel.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Cirel, CirelColor } from 'src/app/types/cirel.types';
import { TableHeader } from 'src/app/types/table.types';
import { Die } from 'src/app/types/dies.types';
import { Table } from 'primeng/table';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-cireles-list',
  templateUrl: './cireles-list.component.html',
  styleUrls: ['./cireles-list.component.scss'],
  styles: [`
      :host ::ng-deep .p-frozen-column {
          font-weight: bold;
      }

      :host ::ng-deep .p-datatable-frozen-tbody {
          font-weight: bold;
      }

      :host ::ng-deep .p-progressbar {
          height: .5rem;
      }
  `],
  providers: [MessageService, ConfirmationService],
})
export class CirelesListComponent implements OnInit {

  cirels: Cirel[] = [];
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedCirels: Cirel[] = [];
  initialPage: number = 0;
  pageSize: number = 10;
  actualPage: number = 0;
  query: string = null;

  headers: TableHeader<Cirel>[] = [
    {label: 'Impresión', property: 'print'},
    {label: 'Impresora', property: 'printer'},
    {label: 'Descripción', property: 'description'},
    {label: 'Colores', property: 'cyrelColors'},
    {label: 'MB Lamina', property: 'mbLeaf'},
    {label: 'Troquel', property: 'die'},
    {label: 'Observaciones', property: 'observation'},
    {label: 'Descripción 2', property: 'description2'}
  ];

  subHeaders: TableHeader<CirelColor>[] = [
    {label: 'Tipo', property: 'colorType'},
    {label: 'Número color', property: 'index'},
    {label: 'Color', property: 'color'}
  ];


  @ViewChild('dt') table: Table;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private cirelService: CirelService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Cireles', routerLink: ['/home/cireles']},
    ]);
  }

  ngOnInit() {
    this.getCirels(this.initialPage, this.pageSize, this.query);
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

  // private expandAll() {
  //   if (!this.isExpanded) {
  //     this.products.forEach(product => this.expandedRows[product.name] = true);
  //   } else {
  //     this.expandedRows = {};
  //   }
  //   this.isExpanded = !this.isExpanded;
  // }

  private addNewCyrel(): void {

  }

  private deleteSelectedCirels(): void {
  }

  private deleteCirel(cirel: any): void {
  }

  private editCirel(cirel: any): void {

  }
}
