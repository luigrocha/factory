import { Component, OnInit, ViewChild } from '@angular/core';
import { Table } from '@fullcalendar/daygrid';
import {
  ConfirmationService,
  MessageService,
  PrimeNGConfig,
} from 'primeng/api';
import { AppBreadcrumbService } from 'src/app/components/breadcrumb/app.breadcrumb.service';
import { User } from 'src/app/types/user.types';
import { UsersService } from './users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
  styles: [
    `
            :host ::ng-deep .p-dialog .product-image {
                width: 150px;
                margin: 0 auto 2rem auto;
                display: block;
            }

            @media screen and (max-width: 960px) {
                :host
                    ::ng-deep
                    .p-datatable.p-datatable-customers
                    .p-datatable-tbody
                    > tr
                    > td:last-child {
                    text-align: center;
                }

                :host
                    ::ng-deep
                    .p-datatable.p-datatable-customers
                    .p-datatable-tbody
                    > tr
                    > td:nth-child(6) {
                    display: flex;
                }
            }
        `,
  ],
  providers: [MessageService, ConfirmationService],
})
export class UsersComponent implements OnInit {
  userDialog: boolean;

  selectedUsers: User[];

  submitted: boolean;

  cols: any[];

  users: User[];

  user: User;

  statuses: any[];

  loading = true;

  constructor(
    private userService: UsersService,
    private primengConfig: PrimeNGConfig,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: AppBreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Administración' },
      { label: 'Usuarios', routerLink: ['/home/users'] },
    ]);
  }

  ngOnInit() {
    this.userService.getAllUsers().subscribe((users) => {
      this.users = users;
      this.loading = false;
    });
    this.primengConfig.ripple = true;

    this.cols = [
      { field: 'userName', header: 'Usuario' },
      { field: 'firstName', header: 'Nombre' },
      { field: 'lastName', header: 'Apellido' },
      { field: 'email', header: 'Email' },
    ];
  }

  openNew() {
    this.user = {};
    this.submitted = false;
    this.userDialog = true;
  }

  editUser(user: User) {
    this.user = { ...user };
    this.userDialog = true;
  }

  deleteUser(user: User) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar al usuario ' + user.userName + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.users = this.users.filter((val) => val.id !== user.id);
        this.user = {};
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Usuario Eliminado',
          life: 3000,
        });
      },
    });
  }

  deleteSelectedUsers() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los usuarios seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.users = this.users.filter(
          (val) => !this.selectedUsers.includes(val)
        );
        this.selectedUsers = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Usuarios Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveProduct() {
    this.submitted = true;

    if (this.user.userName.trim()) {
      if (this.user.id) {
        this.users[this.findIndexById(this.user.id)] = this.user;
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Updated',
          life: 3000,
        });
      } else {
        // this.user.id = this.createId();
        this.users.push(this.user);
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Created',
          life: 3000,
        });
      }

      this.users = [...this.users];
      this.userDialog = false;
      this.user = {};
    }
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].id === id) {
        index = i;
        break;
      }
    }
    return index;
  }

  hideDialog() {
    this.userDialog = false;
    this.submitted = false;
  }
}
