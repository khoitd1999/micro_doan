import {Injectable} from '@angular/core';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  name: any;
  constructor(private notification: NzNotificationService) {

  }
}
