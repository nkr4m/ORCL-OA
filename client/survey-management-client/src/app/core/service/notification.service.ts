import { MessageService } from 'primeng/api';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
interface notifierObj{
  "type":string;
  "msg":string
}
@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private messageService:MessageService){

  }

  private notifier:BehaviorSubject<notifierObj | null> = new BehaviorSubject<notifierObj | null>(null);
  public notifierObs = this.notifier.asObservable;

  // type= success, error, warn, info
  emitNotification(type:string, msgHead:string ,msg:string){
    this.messageService.clear();
    this.messageService.add({ key: 'tc', severity: `${type}`, summary: `${msgHead}`, detail: `${msg}`});

  }

  hideNotification(){
    this.notifier.next(null);
  }
}
