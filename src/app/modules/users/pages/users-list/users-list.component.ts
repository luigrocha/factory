import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/types/user.types';
import { ConfirmationService, MessageService } from 'primeng/api';
import { RoleType } from 'src/app/types/role.types';
import { RoleService } from 'src/app/core/http/roles/role.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { UsersService } from 'src/app/core/http/users/users.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss'],
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
export class UsersListComponent implements OnInit {

  userDialog: boolean;

  selectedUsers: User[];

  submitted: boolean;

  cols: any[];

  users: User[];

  user: User;

  loading = true;

  permissionsPage: TypePermission[];

  constructor(
    private userService: UsersService,
    private roleService: RoleService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Administración' },
      { label: 'Usuarios', routerLink: ['/home/usuarios'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllUsers();
    this.cols = [
      { field: 'userName', header: 'Usuario' },
      { field: 'firstName', header: 'Nombre' },
      { field: 'lastName', header: 'Apellido' },
      { field: 'email', header: 'Email' },
      { field: 'roles', header: 'Roles' },
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
        this.userService.deleteUserById(user.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Usuario Eliminado',
              life: 3000,
            });
            this.users = [];
            this.getAllUsers();
          },
          (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: err.message,
              life: 3000,
            });
          }
        );

      },
    });
  }

  deleteSelectedUsers() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los usuarios seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedUsers.forEach(user => {
          this.userService.deleteUserById(user.id).subscribe(
            (res) => {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Usuario Eliminado',
                life: 3000,
              });
              this.users = [];
              this.getAllUsers();
            },
            (err) => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: err.message,
                life: 3000,
              });
            }
          );
        })

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

  saveUser() {
    this.submitted = true;

    if (this.user.userName.trim()) {
      if (this.user.id) {
        this.userService.updateUserById(this.user.id, this.user).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Usuario Actualizado',
              life: 3000,
            });
            this.users = [];
            this.getAllUsers();
          },
          (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: err.message,
              life: 3000,
            });
          }
        );
      } else {
        this.userService.createUser(this.user).subscribe(
          (res) => {
            if (res.status === 201) {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Usuario Creado',
                life: 3000,
              });
              this.users = [];
              this.getAllUsers();
            } else if (res.status === 409) {
              this.messageService.add({
                severity: 'warn',
                summary: 'Alerta',
                detail: res.message,
                life: 3000,
              });
            }
          },
          (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: err.message,
              life: 3000,
            });
          }
        );
      }

      this.users = [...this.users];
      this.userDialog = false;
      this.user = {};
    }
  }

  hideDialog() {
    this.userDialog = false;
    this.submitted = false;
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((users) => {
      this.users = users;
      this.loading = false;
    });
  }

  removeAccents(str: string): string {
    return str.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
  }

  createFirstLetterUserName() {
    if (!this.user.id) {
      this.user.userName = this.removeAccents(this.user.firstName.charAt(0)).toLowerCase();
    }
  }

  createUsername(e: string) {
    if (!this.user.id) {
      let lastName = e;
      if (e.includes(' ')) {
        lastName = e.split(' ')[0];
      }
      this.user.userName = this.removeAccents((this.user.firstName.charAt(0) + lastName).toLowerCase());
      this.verifyUsername();
      this.user.email = this.user.userName;
    }
  }

  verifyUsername() {
    const repeatedRecords = this.users.filter(user => user.userName.includes(this.user.userName));
    if (repeatedRecords.length > 1) {
      this.user.userName = this.user.userName + (repeatedRecords.length + 1);
    }
  }

  getRoleType(name: string): RoleType {
    return this.roleService.getRoleType(name);
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage().subscribe(
      (data) => {
        this.permissionsPage = data;
      }
    );
  }

  isAllow(id: number): boolean {
    if (this.permissionsPage) {
      return this.permissionsPage.find(permission => permission.id === id).flag;
    }
    return false;
  }

}
