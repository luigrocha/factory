import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService, SelectItem, TreeNode } from 'primeng/api';
import { MenuService } from 'src/app/core/http/menu/menu.service';
import { RoleService } from 'src/app/core/http/roles/role.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Menu } from 'src/app/types/menu.types';
import { Role } from 'src/app/types/role.types';

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

  roleValue: SelectItem[];

  childSelected: number;

  constructor(
    private menuService: MenuService,
    private roleService: RoleService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService) {
    this.breadcrumbService.setItems([
      { label: 'Administración' },
      { label: 'Menú', routerLink: ['/home/menu'] },
    ]);
  }

  ngOnInit() {
    this.cols = [
      { field: 'label', header: 'Nombre' },
      { field: 'icon', header: 'Icono' },
      { field: 'routerLink', header: 'Url' }
    ];

    this.getAllItemsTree();
    this.getRoles();
  }

  openNew(code: number) {
    this.item = {};
    this.submitted = false;
    this.childSelected = code;
    this.menuDialog = true;
  }

  editItem(item: Menu) {
    this.item = { ...item };
    this.item.data = item.label;
    this.item.url = item.routerLink[0];
    this.menuDialog = true;
  }

  saveItem() {
    this.submitted = true;

    const role: any = this.item.role;
    this.item.role = role.name;

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
      this.item.data = this.item.label;
      this.item.child = this.childSelected;
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
    }
    this.menu = [...this.menu];
    this.menuDialog = false;
    this.childSelected = 0;
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
  }

  getAllItemsTree() {
    this.menuService.getAllItemsTree().subscribe(
      (data => {
        this.menu = data;
      })
    );
  }

  getRoles() {
    this.roleValue = [];
    this.roleService.getAllRole().subscribe(
      (data => {
        this.roles = data;
        data.forEach(element => {
          this.roleValue.push({ label: element.name, value: { id: element.name, name: element.name } });
        });
      })
    );
  }




}
