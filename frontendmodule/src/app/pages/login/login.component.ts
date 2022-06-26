import { Component, OnInit } from '@angular/core';
import {LoginService} from './login.service';
import {AlertService} from '../../UtilsService/alert.service';
import {Router} from '@angular/router';
import {Client} from '../../entity/client';

@Component({
  selector: 'app-welcome',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'
  ]
})
export class LoginComponent implements OnInit {
  client: Client;
  isLogin: any;

  constructor(
    private loginService: LoginService,
    private alertService: AlertService,
    private route: Router
  ) { }

  ngOnInit() {
    this.isLogin = true;
    this.client = new Client();
  }

  checkLogin() {
    if (this.checkErr()) {
      return;
    }
    this.loginService.checkLogin(this.client.phone, this.client.password).subscribe(res => {
      if (res.message) {
        this.alertService.error(res.message);
      } else {
        sessionStorage.setItem('client', JSON.stringify({id: res.body.id, phone: res.body.phone, fullName: res.body.fullName}));
        const url = sessionStorage.getItem('url');
        if (url) {
          this.route.navigate([sessionStorage.getItem('url')]);
          sessionStorage.removeItem('url');
        } else {
          this.route.navigate(['/pages']);
        }
      }
    });
  }

  registerClient() {
    if (this.checkErr()) {
      return;
    }
    this.loginService.registerClient(this.client).subscribe(res => {
      if (res.message) {
        this.alertService.error(res.message);
      } else {
        sessionStorage.setItem('client', JSON.stringify({id: res.body.id, phone: res.body.phone, fullName: res.body.fullName}));
        this.route.navigate(['/pages']);
      }
    });
  }

  private checkErr() {
    if (!this.client.phone) {
      this.alertService.error('Chưa nhập số điện thoại');
      return true;
    } else if (!this.client.password) {
      this.alertService.error('Chưa nhập nhập mật khẩu');
      return true;
    }
    if (!this.isLogin) {
      if (!this.client.fullName) {
        this.alertService.error('Chưa nhập tên');
        return true;
      }
    }
    return false;
  }

  changeActivity() {
    this.isLogin = !this.isLogin;
    this.client = new Client();
  }
}
