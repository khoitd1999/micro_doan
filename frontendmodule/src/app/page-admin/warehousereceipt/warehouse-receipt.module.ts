import {NgModule} from '@angular/core';

import {WarehouseReceiptRoutingModule} from './warehouse-receipt-routing.module';

import {WarehouseReceiptComponent} from './warehouse-receipt.component';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import {NzAlertModule} from "ng-zorro-antd/alert";
import {NzNotificationModule} from "ng-zorro-antd/notification";
import {NzSpinModule} from "ng-zorro-antd/spin";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzTransferModule} from "ng-zorro-antd/transfer";
import {NzBreadCrumbModule, NzDatePickerModule, NzUploadModule} from "ng-zorro-antd";
import {CustomDatePipe} from "../../UtilsService/custom.datepipe";
import {PagesAdminModule} from "../pages-admin.module";
import {WarehouseReceiptUpdateComponent} from "./warehouse-receipt-update.component";


@NgModule({
  imports: [
    WarehouseReceiptRoutingModule,
    CommonModule,
    NzGridModule,
    NzInputModule,
    NzButtonModule,
    NzTableModule,
    NzIconModule,
    NzModalModule,
    NzSelectModule,
    NzCheckboxModule,
    NzAlertModule,
    NzNotificationModule,
    FormsModule,
    NzSpinModule,
    NzDividerModule,
    NzTransferModule,
    FormsModule,
    NzBreadCrumbModule,
    NzDatePickerModule,
    NzUploadModule,
    PagesAdminModule
  ],
  declarations: [
    WarehouseReceiptComponent,
    WarehouseReceiptUpdateComponent
  ],
  exports: [WarehouseReceiptComponent,

  ]
})
export class WarehouseReceiptModule {
}
