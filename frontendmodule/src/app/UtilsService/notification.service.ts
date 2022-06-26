import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notification = new BehaviorSubject('');
  currentMessage = this.notification.asObservable();

  constructor() {
  }

  sendMessage(message) {
    this.notification.next(message);
  }
}
