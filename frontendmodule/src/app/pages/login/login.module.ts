import { NgModule } from '@angular/core';

import { LoginRoutingModule } from './login-routing.module';

import { LoginComponent } from './login.component';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";


@NgModule({
    imports: [
        LoginRoutingModule,
        CommonModule,
        FormsModule,
    ],
  declarations: [LoginComponent],
  exports: [LoginComponent]
})
export class LoginModule { }
