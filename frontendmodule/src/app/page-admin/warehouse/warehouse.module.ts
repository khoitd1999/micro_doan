import { NgModule } from '@angular/core';

import { WarehouseRoutingModule } from './warehouse-routing.module';

import { WarehouseComponent } from './warehouse.component';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {NzGridModule} from 'ng-zorro-antd/grid';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzTableModule} from 'ng-zorro-antd/table';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {NzModalModule} from 'ng-zorro-antd/modal';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzCheckboxModule} from 'ng-zorro-antd/checkbox';
import {NzAlertModule} from 'ng-zorro-antd/alert';
import {NzNotificationModule} from 'ng-zorro-antd/notification';
import {NzSpinModule} from 'ng-zorro-antd/spin';
import {NzDividerModule} from 'ng-zorro-antd/divider';
import {NzTransferModule} from 'ng-zorro-antd/transfer';
import {NzBreadCrumbModule} from 'ng-zorro-antd';


@NgModule({
    imports: [
        WarehouseRoutingModule,
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
        NzBreadCrumbModule
    ],
  declarations: [WarehouseComponent],
  exports: [WarehouseComponent,

  ]
})
export class WarehouseModule { }
