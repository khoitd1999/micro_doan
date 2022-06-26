import {NgModule} from '@angular/core';

import {PolicyRoutingModule} from './policy-routing.module';

import {PolicyComponent} from './policy.component';
import {CommonModule, CurrencyPipe} from "@angular/common";
import {NgZorroAntdModule, NzSelectModule} from "ng-zorro-antd";
import {FormsModule} from "@angular/forms";
import {PagesAdminModule} from "../../page-admin/pages-admin.module";


@NgModule({
  imports: [
    PolicyRoutingModule,
    CommonModule,
    NzSelectModule,
    NgZorroAntdModule,
    FormsModule,
    PagesAdminModule
  ],
  declarations: [PolicyComponent],
  exports: [PolicyComponent],
  providers: [CurrencyPipe]
})
export class PolicyModule {
}
