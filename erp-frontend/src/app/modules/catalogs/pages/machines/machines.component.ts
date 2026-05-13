import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { ToastService } from 'src/app/core/services/toast.service';
import {CreateMachine, Machine, MachineCatalog, UpdateMachine} from 'src/app/types/machine.types';
import { MachineService } from 'src/app/core/http/catalogs/machine/machine.service';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { MachineModalComponent } from '../../components/machine-modal/machine-modal.component';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class MachinesComponent implements OnInit {

  indexTab = 1;
  catalogs: MachineCatalog[];
  columns: TableColumn<Machine>[];
  pageSize = 10;
  machines: Machine[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedMachine: Machine;
  menuItems: MenuItem[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private machineService: MachineService,
    public dialogService: DialogService,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Máquinas', routerLink: ['/home/catalogs/maquinas'] },
    ]);
  }

  ngOnInit(): void {
    this.getAllMachinesCatalog();
    this.getAllMachinesByType(this.indexTab);
    this.getMenuItems();
    this.columns = [
      {field: 'name', header: 'Nombre'},
      {field: 'description', header: 'Descripción'},
      {field: 'observation', header: 'Observación'},
    ];
  }

  getAllMachinesCatalog(){
    this.machineService.getAllMachinesCatalog().subscribe(catalogs => {
      this.catalogs = catalogs;
    });
  }

  onChangeTab(e: any) {
    this.indexTab = e.index + 1;
    this.getAllMachinesByType(this.indexTab);
  }

  getAllMachinesByType(id: number) {
    this.machines = [];
    this.machineService.getAllMachinesByType(id).subscribe(machines => {
        this.machines = machines;
        this.getFieldByType();
    });
  }

  getFieldByType(){
    if (this.columns.length > 3){
      this.columns.pop();
    }
    switch (this.indexTab) {
      case 1:
        this.columns.push({field: 'widthExt', header: 'Ancho Extrucción'});
        break;
      case 2:
        this.columns.push({field: 'hasDesb', header: 'Tiene desbrozador'});
        break;
      case 3:
        this.columns.push({field: 'abilityPell', header: 'Capacidad'});
        break;
    }
  }

  getNameTypeMachine(): string{
    return this.catalogs.find(catalog => catalog.id === this.indexTab).name;
  }

  getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editMachine()
      },
      {
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteMachine()
      }
    ];
  }

  editMachine(): void {
    this.addDialogRef = this.dialogService.open(MachineModalComponent, {
      data: this.selectedMachine,
      header: `Actualizar ${this.getNameTypeMachine().toLowerCase()} ${this.selectedMachine.name}`,
      width: '450px',
      contentStyle: {'max-width': '100%', overflow: 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((machine: UpdateMachine) => {
        if (machine) {
          this.machineService.updateMachine(this.selectedMachine.id, machine)
            .subscribe(() => {
              this.toastService.success('Máquina actualizada correctamente');
              this.getAllMachinesByType(this.indexTab);
            });
        }
      });
  }

  deleteMachine(): void {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar la máquina ' + this.selectedMachine.name + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.machineService.deleteMachine(this.selectedMachine.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Máquina eliminada correctamente');
              this.getAllMachinesByType(this.indexTab);
            } else {
              this.toastService.error('Error al eliminar la máquina');
            }
          });
      },
    });
  }

  addMachine(): void {
    this.addDialogRef = this.dialogService.open(MachineModalComponent, {
      data: this.indexTab,
      header: `Crear nueva máquina ${this.getNameTypeMachine().toLowerCase()}`,
      width: '450px',
      contentStyle: {'max-width': '100%', overflow: 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((machine: CreateMachine) => {
        if (machine) {
          // @ts-ignore
          machine.machineCatalog = this.indexTab;
          this.machineService.createMachine(machine)
            .subscribe(() => {
              this.toastService.success('Máquina creada correctamente');
              this.getAllMachinesByType(this.indexTab);
            });
        }
      });
  }

  getDesbMessage(hasDesb: boolean): string {
    if (hasDesb) {
      return 'Si';
    }
    return 'No';
  }
}
