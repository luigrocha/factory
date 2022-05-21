import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ClientService } from 'src/app/core/http/clients/client.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Client } from 'src/app/types/client.types';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
  styles: [
  ],
  providers: [MessageService, ConfirmationService],
})
export class ClientsComponent implements OnInit {

  columns: TableColumn<Client>[];
  pageSize: number = 10;
  clients: Client[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];

  clientDialog: boolean;

  selectedClients: Client[] = [];

  submitted: boolean;

  client: Client;

  loading = true;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private clientService: ClientService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Administración' },
      { label: 'Clientes', routerLink: ['home/clientes'] },
    ]);
  }

  ngOnInit() {
    this.getAllClients();
    this.columns = [
      { field: 'id', header: 'Id' },
      { field: 'name', header: 'Nombre' },
    ];
  }

  openNew() {
    this.client = {};
    this.submitted = false;
    this.clientDialog = true;
  }

  editClient(client: Client) {
    this.client = { ...client };
    this.clientDialog = true;
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
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveClient() {
    this.submitted = true;

    if (this.client.id) {
      // this.clientAService.update(this.client.id, this.client).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Actualizado',
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
    } else {
      // this.clientAService.create(this.client).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Creado',
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
    }

    this.clients = [...this.clients];
    this.clientDialog = false;
    this.client = {};
  }

  hideDialog() {
    this.clientDialog = false;
    this.submitted = false;
  }

  getAllClients() {
    // this.clientAService.getAll().subscribe((clients) => {
    //   this.clients = clients;
    //   this.loading = false;
    // });
    this.clientService.getAllClients().subscribe(clients => {
      this.clients = clients;
      this.loading = false;
    });
  }

}
