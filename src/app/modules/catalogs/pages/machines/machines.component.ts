import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { ToastService } from 'src/app/core/services/toast.service';
import { CreateMachine, Machine, UpdateMachine } from 'src/app/types/machine.types';
import { MachineService } from 'src/app/core/http/catalogs/machine/machine.service';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { MachineModalComponent } from "../../components/machine-modal/machine-modal.component";
import { BreadcrumbService } from "../../../../core/services/breadcrumb.service";

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class MachinesComponent implements OnInit {

  columns: TableColumn<Machine>[];
  pageSize: number = 10;
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
    this.getAllMachines();
    this.getMenuItems();
    this.columns = [
      {field: 'name', header: 'Nombre'},
      {field: 'hasDesb', header: 'Tiene desbrozador'},
    ];
  }

  getAllMachines() {
    this.machineService.getAllMachines()
      .subscribe(machines => {
        this.machines = machines;
      });
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
      header: `Actualizar ${this.selectedMachine.name}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((machine: UpdateMachine) => {
        if (machine) {
          this.machineService.updateMachine(this.selectedMachine.id, machine)
            .subscribe(() => {
              this.toastService.success('Máquina actualizada correctamente');
              this.getAllMachines();
            })
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
              this.getAllMachines();
            } else {
              this.toastService.error('Error al eliminar la máquina');
            }
          });
      },
    });
  }

  addMachine(): void {
    this.addDialogRef = this.dialogService.open(MachineModalComponent, {
      header: 'Crear nueva máquina',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((machine: CreateMachine) => {
        if (machine) {
          this.machineService.createMachine(machine)
            .subscribe(() => {
              this.toastService.success('Máquina creada correctamente');
              this.getAllMachines();
            })
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
