import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertService} from "../UtilsService/alert.service";
import {NotificationService} from "../UtilsService/notification.service";
import {Subscription} from "rxjs";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-welcome',
  templateUrl: './page-admin.component.html',
  styleUrls: ['./page-admin.component.css']
})
export class PageAdminComponent implements OnInit, OnDestroy {
  isCollapsed = false;
  user: any;
  subscription: Subscription;
  private stompClient = null;

  constructor(private route: Router,
              public alertService: AlertService,
              public notificationService: NotificationService) {
  }

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem('employee'));
    // this.connect();
  }

  logout() {
    sessionStorage.removeItem('employee');
    this.route.navigate(['/pages_admin/login']);
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    // this.disconnect();
  }

  // connect() {
  //   const socket = new SockJS('http://localhost:8081/gkz-stomp-endpoint');
  //   this.stompClient = Stomp.Stomp.over(socket);
  //
  //   const _this = this;
  //   this.stompClient.connect({}, function (frame) {
  //     console.log('Connected: ' + frame);
  //
  //     _this.stompClient.subscribe('/topic/hi', function (hello) {
  //       if (hello.body) {
  //         const res = JSON.parse(hello.body);
  //         _this.alertService.success(res.notification);
  //       }
  //     });
  //   });
  // }
  //
  // disconnect() {
  //   if (this.stompClient != null) {
  //     this.stompClient.disconnect();
  //   }
  //   console.log('Disconnected!');
  // }
}
