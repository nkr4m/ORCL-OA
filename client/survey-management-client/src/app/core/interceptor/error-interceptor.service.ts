import { NotificationService } from './../service/notification.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import {throwError, Observable} from 'rxjs'
import {catchError} from 'rxjs/operators'
@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService {

  constructor(private router: Router, private notifier: NotificationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: any) => {
        if (error.error instanceof ErrorEvent) {
          // A client-side or network error occurred. Handle it accordingly.
          console.error('An error occurred:', error.error.message);
        } else {
          // The backend returned an unsuccessful response code.
          // The response body may contain clues as to what went wrong.
          this.notifier.emitNotification('error','Server Unavailable', 'We are unable to reach the servers at the moment, Please try again later.')
        }
        if (error.status === 404) {
          // Handle 404 errors here, for example, navigate to a not found page
          this.notifier.emitNotification('error','Endpoint is unavailable', 'We are unable to connect to the endpoint, please contact the admin team.')
        } else if (error.error instanceof ProgressEvent && error.error.type === "error" && error.error?.message === "Http failure response for (unknown url): 0 Unknown Error") {
          // Handle ERR_CONNECTION_REFUSED errors
          this.notifier.emitNotification('error','Server Unavailable', 'We are unable to reach the servers at the moment, Please try again later')
          // Handle it accordingly, for example, show a connection refused message to the user
        }
        return throwError(error);
      })
    );
  }
}
