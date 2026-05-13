import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';

@Injectable()
export class HeadersService implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentHeaders = req.headers;
    currentHeaders.append('Authorization', `Bearer ${this.authService.getToken()}`);
    currentHeaders.append('Content-Type', 'application/json');

    return next.handle(req.clone({headers: currentHeaders}));
  }
}
