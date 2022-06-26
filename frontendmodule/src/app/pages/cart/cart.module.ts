import { NgModule } from '@angular/core';

import { CartRoutingModule } from './cart-routing.module';

import { CartComponent } from './cart.component';
import {CommonModule} from "@angular/common";
import {NgZorroAntdModule, NzSelectModule} from "ng-zorro-antd";
import {FormsModule} from "@angular/forms";


@NgModule({
    imports: [
        CartRoutingModule,
        CommonModule,
        NzSelectModule,
        NgZorroAntdModule,
        FormsModule
    ],
  declarations: [CartComponent],
  exports: [CartComponent]
})
export class CartModule { }
