import { ErrorHandler, Injectable, NgZone } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastService } from 'src/app/core/services/toast.service';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  constructor(
    private toastService: ToastService,
    private zone: NgZone
  ) {}

  handleError(error: any) {
    // Check if it's an error from an HTTP response
    if (!(error instanceof HttpErrorResponse)) {
      error = error.rejection; // get the error object
    }

    this.zone.run(() => {
      let errorMessage = error.message;

      if (error.status === 0) {
        errorMessage = 'No se pudo conectar con el servidor';
      } else {
        errorMessage = error.error ? error.error.message : error.message;
      }

      this.toastService.errorHttp(
        errorMessage,
        error.status
      )
    });
  }
}
