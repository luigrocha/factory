import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService, TreeNode } from 'primeng/api';
import { MenuService } from 'src/app/core/http/menu/menu.service';
import { RoleService } from 'src/app/core/http/roles/role.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Menu } from 'src/app/types/menu.types';
import { Role, RoleType } from 'src/app/types/role.types';
import { IconService } from 'src/app/core/services/icon.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { Permission } from 'src/app/types/permission';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
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
export class MenuComponent implements OnInit {

  menuDialog: boolean;

  cols: any[];

  menu: TreeNode[];

  item: Menu;

  submitted: boolean;

  roles: Role[];

  roleSelected: any;

  childSelected: number;

  sugederOrder: number;

  iconSelected: any;

  icons: any[] = [];

  permissionDialog: boolean;

  permissions: Permission[];

  headerPermission = 'Permisos Menu ';

  constructor(
    private menuService: MenuService,
    private permissionService: PermissionService,
    private roleService: RoleService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private iconService: IconService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Administración' },
      { label: 'Menú', routerLink: ['/home/menu'] },
    ]);
  }

  ngOnInit() {
    this.getIcons();
    this.cols = [
      { field: 'label', header: 'Nombre' },
      { field: 'icon', header: 'Icono' },
      { field: 'routerLink', header: 'Url' },
      { field: 'permission', header: 'Permisos' }
    ];

    this.getAllItemsTree();
    this.getRoles();
  }

  getPermissions(code: string) {
    this.permissionService.findPermissionsByMenuCode(code).subscribe(
      (data => {
        this.permissions = data;
      })
    );
  }

  openPermission(item: Menu) {
    this.permissionDialog = true;
    this.item = { ...item };
    this.getPermissions(item.id);
  }

  savePermisions(code: string) {
    this.permissions.forEach(per => {
      this.permissionService.updatePermissionByMenuCode(code, per.id, per.typePermission).subscribe(
        (res => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Item Actualizado',
            life: 3000,
          });
          this.hidePermissionDialog();
        }), (err => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: err.message,
            life: 3000,
          });
        })
      );
    });

  }

  getRoleType(name: string): RoleType {
    return this.roleService.getRoleType(name);
  }

  hidePermissionDialog() {
    this.permissionDialog = false;
    this.permissions = [];
  }

  openNew(code: number) {
    this.item = {};
    this.submitted = false;
    this.childSelected = code;
    this.iconSelected = null;
    this.roleSelected = null;
    this.calculateOrder();
    this.menuDialog = true;
  }

  editItem(item: Menu) {
    this.item = { ...item };
    this.item.data = item.label;
    this.item.url = item.routerLink[0];
    this.iconSelected = this.icons.find(icon => icon.icon === this.item.icon);
    this.roleSelected = this.roles.find(role => role.name === this.item.role);
    this.menuDialog = true;
  }

  saveItem() {
    this.submitted = true;
    if (this.item.id) {
      this.menuService.updateItem(this.item.id, this.item).subscribe(
        (res => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Item Actualizado',
            life: 3000,
          });
          this.menu = [];
          this.getAllItemsTree();
        }),
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
      this.item.url = this.childSelected === 0 ? ' ' : this.item.url;
      this.item.data = this.item.label;
      this.item.child = this.childSelected;
      this.item.icon = this.iconSelected ? this.iconSelected.icon : null;
      this.item.role = this.roleSelected ? this.roleSelected.name : null;

      if (this.isValidateToSave()) {
        this.menuService.createItem(this.item).subscribe(
          (res => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Item Creado',
              life: 3000,
            });
            this.menu = [];
            this.getAllItemsTree();
          }),
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
        this.messageService.add({
          severity: 'warn',
          summary: 'Atención',
          detail: 'Llene todos los campos',
          life: 3000,
        });
        this.menuDialog = true;
        return;
      }
    }
    this.menu = [...this.menu];
    this.menuDialog = false;
    this.childSelected = 0;
    this.sugederOrder = 0;
    this.item = {};
  }

  deleteItem(item: Menu) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar ' + item.label + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.menuService.deleteItem(item.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Item Eliminado',
              life: 3000,
            });
            this.menu = [];
            this.getAllItemsTree()
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

  hideDialog() {
    this.menuDialog = false;
    this.submitted = false;
    this.iconSelected = null;
    this.roleSelected = null;
    this.sugederOrder = 0;
  }

  getAllItemsTree() {
    this.menuService.getAllItemsTree().subscribe(
      (data => {
        this.menu = data;
      })
    );
  }

  getRoles() {
    this.roles = [];
    this.roleService.getAllRole().subscribe(
      (data => {
        this.roles = data;
      })
    );
  }

  calculateOrder() {
    if (this.childSelected === 0) {
      this.sugederOrder = this.menu.length + 1;
    } else {
      this.findLevel(this.menu);
    }
  }

  findLevel(list: Array<TreeNode>) {
    list.forEach((item) => {
      if (item.data.id === this.childSelected) {
        if (!item.children) {
          this.sugederOrder = 1;
        } else {
          this.sugederOrder = item.children.length + 1;
        }
      }
      if (item.children) {
        this.findLevel(item.children);
      }
    });
  }

  getIcons() {
    this.iconService.getIcons().subscribe(data => {
      data = data.filter(value => {
        return value.icon.tags.indexOf('deprecate') === -1;
      });

      const icons = data;
      icons.sort((icon1, icon2) => {
        if (icon1.properties.name < icon2.properties.name) {
          return -1;
        }
        else if (icon1.properties.name < icon2.properties.name) {
          return 1;
        }
        else {
          return 0;
        }
      });

      icons.forEach(icon => {
        this.icons.push({ name: icon.properties.name, icon: `pi pi-fw pi-${icon.properties.name}` });
      });

    });
  }

  isValidateToSave(): boolean {
    return this.item.label && this.item.icon && this.item.role && this.item.order && this.item.url ? true : false;
  }
}
