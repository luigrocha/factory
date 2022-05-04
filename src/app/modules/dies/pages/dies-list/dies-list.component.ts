import { Component, OnInit, ViewChild } from '@angular/core';
import { Die } from 'src/app/types/dies.types';
import { DieService } from 'src/app/core/http/dies/die.service';
import { TableHeader } from 'src/app/types/table.types';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-dies-list',
  templateUrl: './dies-list.component.html',
  styleUrls: ['./dies-list.component.scss'],
  styles: [`
        :host ::ng-deep .p-datatable-gridlines p-progressBar {
            width: 100%;
        }

        @media screen and (max-width: 960px) {
            :host ::ng-deep .p-datatable.p-datatable-customers.rowexpand-table .p-datatable-tbody > tr > td:nth-child(6) {
                display: flex;
            }
        }

    `],
})
export class DiesListComponent implements OnInit {
  dies: Die[] = [];
  headers: TableHeader<Die>[] = [
    {label: 'Troquel', property: 'name'},
    {label: 'Fecha', property: 'createdDate'},
    // {label: 'Fabricante', property: 'name'},
    // {label: 'Máquina', property: 'name'},
    {label: 'DesbMúltiple', property: 'dsbMultiple'},
    {label: 'Observaciones', property: 'observations'},
    {label: 'Prod', property: 'code'},
    {label: 'Referencia', property: 'description'},
    {label: 'Largo', property: 'length'},
    {label: 'Ancho', property: 'width'},
    {label: 'Cant1', property: 'quantity'},
    // {label: 'Cant. largo', property: 'quantityLength'},
    // {label: 'Sep. largo', property: 'separationLength'},
    // {label: 'Cant. ancho', property: 'quantityWidth'},
    // {label: 'Sep. ancho', property: 'separationWidth'},
    // {label: 'Largo lámina', property: 'leafLength'},
    // {label: 'Ancho lámina', property: 'leafWidth'},
    // {label: 'Área', property: 'area'},
    // {label: 'GSMDIS', property: 'gsmdis'},
    // {label: 'Estado', property: 'status'},
  ]

  @ViewChild('dt') table: Table;
  constructor(
    private dieService: DieService
  ) {
  }

  ngOnInit(): void {
    this.getDies();
  }

  private getDies(): void {
    this.dieService.getAllDies()
      .subscribe(dies => this.dies = dies);
  }
}
