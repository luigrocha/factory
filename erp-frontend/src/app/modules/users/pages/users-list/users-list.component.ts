import { Component, OnInit } from '@angular/core';
import { UpdateUser, User } from 'src/app/types/user.types';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { RoleType } from 'src/app/types/role.types';
import { RoleService } from 'src/app/core/http/roles/role.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { UsersService } from 'src/app/core/http/users/users.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { TypePermission } from 'src/app/types/permission';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/core/services/toast.service';
import {
  DieProductModalComponent
} from 'src/app/modules/dies/components/die-product-modal/die-product-modal.component';
import { UserModalComponent } from 'src/app/modules/users/components/user-modal/user-modal.component';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss'],
  providers: [ConfirmationService],
})
export class UsersListComponent implements OnInit {

  selectedUsers: User[];
  cols: any[];
  users: User[];
  permissionsPage: TypePermission[];
  items: MenuItem[] = [];
  userSelect: User;
  addDialogRef: DynamicDialogRef;

  constructor(
    private userService: UsersService,
    private roleService: RoleService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private permissionService: PermissionService,
    public dialogService: DialogService,
    private toastService: ToastService) {
    this.breadcrumbService.setItems([
      { label: 'Administración' },
      { label: 'Usuarios', routerLink: ['/home/usuarios'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllUsers();
    setTimeout(() => {
      this.getMenuItems();
    }, 500);
    this.cols = [
      { field: 'username', header: 'Usuario' },
      { field: 'firstName', header: 'Nombre' },
      { field: 'lastName', header: 'Apellido' },
      { field: 'email', header: 'Email' },
      { field: 'roles', header: 'Roles' },
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.items.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: (e) => this.editUser(this.userSelect)
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.items.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: (e) => this.deleteUser(this.userSelect)
      });
    }
  }

  openNew() {
    this.addDialogRef = this.dialogService.open(UserModalComponent, {
      header: 'Crear nuevo producto troquelado',
      width: '450px',
      contentStyle: { 'max-width': '100%', overflow: 'auto' },
    });

    this.addDialogRef.onClose
      .subscribe(user => {
        if (user) {
          this.userService.createUser(user)
            .subscribe(response => {
              this.toastService.success('Usuario creado correctamente');
              this.users = [];
              this.getAllUsers();
            });
        }
      });
  }

  editUser(user: User) {
    this.addDialogRef = this.dialogService.open(UserModalComponent, {
      header: 'Crear nuevo producto troquelado',
      width: '450px',
      data: user,
      contentStyle: { 'max-width': '100%', overflow: 'auto' },
    });

    this.addDialogRef.onClose
      .subscribe((updatedUser: UpdateUser) => {
        if (updatedUser) {
          this.userService.updateUserById(user.id, updatedUser)
            .subscribe(response => {
              this.toastService.success('Usuario actualizado correctamente');
              this.users = [];
              this.getAllUsers();
            });
        }
      });
  }

  deleteUser(user: User) {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar al usuario ' + user.username + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.userService.deleteUserById(user.id)
          .subscribe(() => {
            this.toastService.success('Usuario eliminado correctamente');
            this.users = [];
            this.getAllUsers();
          });
      },
    });
  }

  deleteSelectedUsers() {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar los usuarios seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedUsers.forEach(user => {
          this.userService.deleteUserById(user.id)
            .subscribe(() => {
              this.toastService.success('Usuario eliminado correctamente');
              this.users = [];
              this.getAllUsers();
            });
        });

        this.selectedUsers = null;
        this.toastService.success('Usuarios eliminados correctamente');
      },
    });
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((users) => {
      this.users = users;
    });
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
