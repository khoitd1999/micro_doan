import {NgModule} from '@angular/core';

import {CheckoutRoutingModule} from './checkout-routing.module';

import {CheckoutComponent} from './checkout.component';
import {CommonModule, CurrencyPipe} from "@angular/common";
import {NgZorroAntdModule, NzSelectModule} from "ng-zorro-antd";
import {FormsModule} from "@angular/forms";


@NgModule({
  imports: [
    CheckoutRoutingModule,
    CommonModule,
    NzSelectModule,
    NgZorroAntdModule,
    FormsModule
  ],
  declarations: [CheckoutComponent],
  exports: [CheckoutComponent],
  providers: [CurrencyPipe]
})
export class CheckoutModule {
}
