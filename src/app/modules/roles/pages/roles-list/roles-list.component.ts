import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Role, RoleType } from 'src/app/types/role.types';
import { User } from 'src/app/types/user.types';
import { RoleService } from 'src/app/core/http/roles/role.service';
import { UsersService } from 'src/app/core/http/users/users.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';

@Component({
  selector: 'app-roles-list',
  templateUrl: './roles-list.component.html',
  styleUrls: ['./roles-list.component.scss'],
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
  providers: [MessageService, ConfirmationService]
})
export class RolesListComponent implements OnInit {

  roleDialog: boolean;

  selectedRoles: Role[];

  submitted: boolean;

  cols: any[];

  roles: Role[];

  role: Role;

  statuses: any[];

  loading = true;

  userName: string;

  user: User;

  rolesActually: string[];

  roleName: string[];

  constructor(
    private roleService: RoleService,
    private userService: UsersService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Administración'},
      {label: 'Roles', routerLink: ['/home/role']},
    ]);
  }

  ngOnInit() {
    this.getAllRole();
    this.cols = [
      {field: 'name', header: 'Nombre'},
      {field: 'description', header: 'Descripción'},
    ];
  }

  openNew() {
    this.role = {};
    this.submitted = false;
    this.roleDialog = true;
  }

  addRoleUser() {
    this.roleDialog = true;
  }

  hideDialog() {
    this.roleDialog = false;
    this.submitted = false;
    this.user = {};
  }

  getAllRole() {
    this.roleService.getAllRole().subscribe((role) => {
      this.roles = role;
      this.loading = false;
    });
  }

  getRoleName() {
    this.roleName = [];
    const roles: Role[] = this.roles.filter(role => !this.rolesActually.includes(role.name));
    roles.forEach(role => this.roleName.push(role.name));
  }

  getRoleType(name: string): RoleType {
    return this.roleService.getRoleType(name);
  }

  getUserByUserName(userName: string) {
    if (userName) {
      this.userService.getUserByUserName(userName).subscribe(
        (res) => {
          this.user = res;
          this.rolesActually = this.user.roles;
          this.getRoleName();
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No existe usuario ' + userName,
            life: 3000,
          });
        }
      );
    }
  }

  saveRoles() {
    this.submitted = true;
    const roleName = [];
    const rolesActually = [];

    this.roleName.forEach(role => roleName.push({name: role}));
    this.rolesActually.forEach(role => rolesActually.push({name: role}));

    this.roleService.addRolesUser(this.user.id, rolesActually).subscribe(
      (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: 'Roles actualizados.',
          life: 3000,
        });
      },
      (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudo actualizar los roles.',
          life: 3000,
        });
      }
    );
    this.roleService.removeRolesUser(this.user.id, roleName).subscribe(
      (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: 'Roles actualizados.',
          life: 3000,
        });
      },
      (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudo actualizar los roles.',
          life: 3000,
        });
      }
    );

    this.roleDialog = false;
    this.user = {};
  }
}
