import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class ToastService {

  constructor(
    private messageService: MessageService
  ) { }

  success(message: string) {
    this.messageService.add({
      severity: 'success',
      summary: 'Éxito',
      detail: message,
      life: 3000,
    });
  }

  error(message: string) {
    this.messageService.add({
      severity: 'error',
      summary: 'Error',
      detail: message,
      life: 5000,
    });
  }

  warning(message: string) {
    this.messageService.add({
      severity: 'warn',
      summary: 'Alerta',
      detail: message,
      life: 5000,
    });
  }

  errorHttp(message: string, status: number) {
    this.messageService.add({
      severity: 'error',
      summary: `Error ${status}`,
      detail: message,
      life: 5000,
    });
  }
}
