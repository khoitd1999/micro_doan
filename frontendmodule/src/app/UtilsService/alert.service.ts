import {Injectable} from '@angular/core';
import {NzNotificationService} from 'ng-zorro-antd/notification';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  name: any;
  constructor(private notification: NzNotificationService) {
  }

  success(message): void {
    this.notification.success(
      'Thông báo',
      message
    );
  }

  error(message): void {
    this.notification.error(
      'Thông báo',
      message
    );
  }
}
