import { NgModule } from '@angular/core';

import { LoginAdminRoutingModule } from './login-admin-routing.module';

import { LoginAdminComponent } from './login-admin.component';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";


@NgModule({
    imports: [
        LoginAdminRoutingModule,
        CommonModule,
        FormsModule,
    ],
  declarations: [LoginAdminComponent],
  exports: [LoginAdminComponent]
})
export class LoginAdminModule { }
