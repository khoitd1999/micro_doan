import { Component, OnInit } from '@angular/core';
import {LoginAdminService} from './login-admin.service';
import {AlertService} from '../../UtilsService/alert.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './login-admin.component.html',
  styleUrls: ['./login-admin.component.css'
  ]
})
export class LoginAdminComponent implements OnInit {
  username: any;
  password: any;

  constructor(
    private loginService: LoginAdminService,
    private alertService: AlertService,
    private route: Router
  ) { }

  ngOnInit() {
  }

  checkLogin() {
    if (this.checkErr()) {
      return;
    }
    this.loginService.checkLogin(this.username, this.password).subscribe(res => {
      if (res.message) {
        this.alertService.error(res.message);
      } else {
        sessionStorage.setItem('employee', JSON.stringify({id: res.body.id, fullName: res.body.fullName, role: res.body.role}));
        this.route.navigate(['/pages_admin']);
      }
    });
  }

  private checkErr() {
    if (!this.username) {
      this.alertService.error('Chưa nhập tên đăng nhập');
      return true;
    } else if (!this.password) {
      this.alertService.error('Chưa nhập nhập mật khẩu');
      return true;
    }
    return false;
  }
}
