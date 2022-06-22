import { Component, OnInit } from '@angular/core';
import { Die, DieDocument } from 'src/app/types/dies.types';
import { DieService } from 'src/app/core/http/dies/die.service';
import { Table } from 'primeng/table';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { TypePermission } from 'src/app/types/permission';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { DialogService, DynamicDialogRef } from "primeng/dynamicdialog";
import { Status } from 'src/app/types/catalogs.types';
import { StatusService } from 'src/app/core/http/catalogs/status/status.service';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/core/services/toast.service';
import { DIE_STATUS_TYPE } from 'src/app/core/constants/status-types';
import { DieDocumentService } from 'src/app/core/http/cirel/die-document.service';
import { UploadFileComponent } from 'src/app/shared/components/upload-file/upload-file.component';
import { DocumentViewerComponent } from 'src/app/shared/components/document-viewer/document-viewer.component';

@Component({
  selector: 'app-dies-list',
  templateUrl: './dies-list.component.html',
  styleUrls: ['./dies-list.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class DiesListComponent implements OnInit {

  pageSize: number = 10;
  dies: Die[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedDie: Die;
  menuItems: MenuItem[];
  dieStates: Status[] = [];

  globalFilterFields: string[] = [
    'name',
    'createdDate',
    'dieProduct.code',
    'dieProduct.name',
    'manufacturer',
    'machines',
    'status.name'
  ];
  permissionsPage: TypePermission[];
  selectedDocument: DieDocument;
  documentDialogRef: DynamicDialogRef;
  uploadDocumentDialogRef: DynamicDialogRef;

  constructor(
    private dieService: DieService,
    private breadcrumbService: BreadcrumbService,
    private permissionService: PermissionService,
    private statusService: StatusService,
    private router: Router,
    private toastService: ToastService,
    public dialogService: DialogService,
    private confirmationService: ConfirmationService,
    private dieDocumentService: DieDocumentService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Troqueles', routerLink: ['/home/troqueles']}
    ]);
  }

  ngOnInit(): void {
    this.getPermissionsPage();
    this.getMenuItems();
    this.getDies();
    this.getDieProductStates();
  }

  private getDies(): void {
    this.dieService.getAllDies()
      .subscribe(dies => {
        this.dies = dies;
      });
  }

  private getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editDie()
      },
      {
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteDie()
      },
      {
        label: 'Subir documento',
        icon: 'pi pi-upload',
        command: () => this.uploadDocument()
      }
    ];
  }

  private editDie(): void {
  }

  private deleteDie(): void {
  }

  private getDieProductStates(): void {
    this.statusService.getAllByType(DIE_STATUS_TYPE)
      .subscribe(dieStates => {
        this.dieStates = dieStates;
      });
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

  addDie(): void {
    this.router.navigate(['/home/troqueles/crear']);
  }

  private uploadDocument() {
    this.openUploadDocumentDialog();
    this.uploadDocumentDialogRef.onClose
      .subscribe(file => {
        if (file) {
          this.dieDocumentService.uploadDieDocument(this.selectedDie.id, file)
            .subscribe(dieDocument => {
              this.selectedDie.documents.push(dieDocument);
              this.toastService.success(`Documento cargado correctamente`);
            });
        }
      });
  }

  private openUploadDocumentDialog(): void {
    this.uploadDocumentDialogRef = this.dialogService.open(UploadFileComponent, {
      header: 'Cargar documento de diseño',
      data: {
        accept: 'application/pdf'
      },
      width: '500px',
      contentStyle: {'max-width': '100%', 'overflow': 'hidden'},
    });
  }

  getDocumentItems(): MenuItem[] {
    if (!this.selectedDocument) {
      return [];
    }
    return [
      {
        label: 'Ver documento',
        icon: 'pi pi-file-pdf',
        command: () => this.showDocument()
      },
      {
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editDocument()
      }
    ];
  }

  showDocument(): void {
    this.documentDialogRef = this.dialogService.open(DocumentViewerComponent, {
      header: `Version ${this.selectedDocument.version} - ${this.selectedDie.name}`,
      data: {
        fileName: this.selectedDie.name,
        document: this.selectedDocument
      },
      height: '90%',
      width: '90%',
      contentStyle: {'max-width': '100%', 'overflow': 'hidden'},
    });
  }

  editDocument(): void {
    this.openUploadDocumentDialog();
    this.uploadDocumentDialogRef.onClose
      .subscribe(file => {
        if (file) {
          this.dieDocumentService.updateDieDocument(this.selectedDocument.id, file)
            .subscribe(dieDocument => {
              const index = this.selectedDie.documents.findIndex(doc => doc.id === dieDocument.id);
              this.selectedDie.documents[index] = dieDocument;
              this.toastService.success(`Documento actualizado correctamente`);
            });
        }
      });
  }

  documentIsEmpty(document: DieDocument[]): boolean {
    return document.length === 0;
  }

  clear(table: Table) {
    table.clear();
  }
}
