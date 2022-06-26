import {NgModule} from '@angular/core';

import {BillRoutingModule} from './bill-routing.module';

import {BillComponent} from './bill.component';
import {CommonModule, CurrencyPipe} from "@angular/common";
import {NgZorroAntdModule, NzSelectModule} from "ng-zorro-antd";
import {FormsModule} from "@angular/forms";
import {PagesAdminModule} from "../../page-admin/pages-admin.module";
import {BillUpdateComponent} from "./bill-update.component";


@NgModule({
  imports: [
    BillRoutingModule,
    CommonModule,
    NzSelectModule,
    NgZorroAntdModule,
    FormsModule,
    PagesAdminModule
  ],
  declarations: [BillComponent, BillUpdateComponent],
  exports: [BillComponent, BillUpdateComponent],
  providers: [CurrencyPipe]
})
export class BillModule {
}
