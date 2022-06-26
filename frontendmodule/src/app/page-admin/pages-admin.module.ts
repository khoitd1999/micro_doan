import { NgModule } from '@angular/core';
import {PageAdminRoutingModule} from './page-admin-routing.module';
import {PageAdminComponent} from './page-admin.component';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import {AlertService} from '../UtilsService/alert.service';
import {CustomDatePipe} from '../UtilsService/custom.datepipe';
import {CommonModule, registerLocaleData} from '@angular/common';
import localeVi from '@angular/common/locales/vi';
import {AuthGuard} from '../UtilsService/auth.guard';
import {NotificationService} from '../UtilsService/notification.service';

registerLocaleData(localeVi, 'vi-VN');

@NgModule({
  imports: [
    PageAdminRoutingModule,
    NgZorroAntdModule,
    CommonModule
  ],
  declarations: [
    PageAdminComponent,
    CustomDatePipe
  ],
  exports: [
    CustomDatePipe
  ],
  providers: [
    AlertService,
    AuthGuard
  ]
})
export class PagesAdminModule { }
