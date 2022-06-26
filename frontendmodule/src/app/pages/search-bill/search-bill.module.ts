import {NgModule} from '@angular/core';

import {SearchBillRoutingModule} from './search-bill-routing.module';

import {SearchBillComponent} from './search-bill.component';
import {CommonModule, CurrencyPipe} from "@angular/common";
import {NgZorroAntdModule, NzSelectModule} from "ng-zorro-antd";
import {FormsModule} from "@angular/forms";
import {PagesAdminModule} from "../../page-admin/pages-admin.module";


@NgModule({
  imports: [
    SearchBillRoutingModule,
    CommonModule,
    NzSelectModule,
    NgZorroAntdModule,
    FormsModule,
    PagesAdminModule
  ],
  declarations: [SearchBillComponent],
  exports: [SearchBillComponent],
  providers: [CurrencyPipe]
})
export class SearchBillModule {
}
