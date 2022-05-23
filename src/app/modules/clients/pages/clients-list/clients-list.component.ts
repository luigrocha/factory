import { Component, OnDestroy, OnInit } from '@angular/core';
import { TableColumn } from 'src/app/types/table.types';
import { Client, CreateClient } from 'src/app/types/client.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ClientService } from 'src/app/core/http/clients/client.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AddClientComponent } from 'src/app/modules/clients/components/add-client/add-client.component';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-clients-list',
  templateUrl: './clients-list.component.html',
  styleUrls: ['./clients-list.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class ClientsListComponent implements OnInit, OnDestroy {

  columns: TableColumn<Client>[];
  pageSize: number = 10;
  clients: Client[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedClients: Client[] = [];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private clientService: ClientService,
    public dialogService: DialogService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Administración'},
      {label: 'Clientes', routerLink: ['home/clientes']},
    ]);
  }

  ngOnInit() {
    this.getAllClients();
    this.columns = [
      {field: 'id', header: 'Id'},
      {field: 'name', header: 'Nombre'},
    ];
  }

  deleteClient(client: Client) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el client ' + client.name + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.clientAService.delete(client.id).subscribe(
        //   (res) => {
        //     this.messageService.add({
        //       severity: 'success',
        //       summary: 'Éxito',
        //       detail: 'Homopolímero Eliminado',
        //       life: 3000,
        //     });
        //     this.clients = [];
        //     this.getAllUsers();
        //   },
        //   (err) => {
        //     this.messageService.add({
        //       severity: 'error',
        //       summary: 'Error',
        //       detail: err.message,
        //       life: 3000,
        //     });
        //   }
        // );

      },
    });
  }

  deleteSelectedClients() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los clientes seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.selectedHomo.forEach(client => {
        //   this.clientAService.delete(client.id).subscribe(
        //     (res) => {
        //       this.messageService.add({
        //         severity: 'success',
        //         summary: 'Éxito',
        //         detail: 'Homopolímero Eliminado',
        //         life: 3000,
        //       });
        //       this.clients = [];
        //       this.getAllUsers();
        //     },
        //     (err) => {
        //       this.messageService.add({
        //         severity: 'error',
        //         summary: 'Error',
        //         detail: err.message,
        //         life: 3000,
        //       });
        //     }
        //   );
        // })

        this.selectedClients = null;
        // this.messageService.add({
        //   severity: 'success',
        //   summary: 'Correcto',
        //   detail: 'Homopolímeros Elimnados',
        //   life: 3000,
        // });
      },
    });
  }

  getAllClients() {
    this.clientService.getAllClients()
      .subscribe(clients => {
        this.clients = clients;
      });
  }

  addClient(): void {
    this.addDialogRef = this.dialogService.open(AddClientComponent, {
      header: 'Crear nuevo cliente',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((client: CreateClient) => {
        if (client) {
          this.clientService.createClient(client)
            .subscribe(() => {
              this.toastService.success('Cliente creado correctamente');
              this.getAllClients();
            })
        }
      });
  }

  ngOnDestroy(): void {
    if (this.addDialogRef) {
      this.addDialogRef.close();
    }
  }
}
