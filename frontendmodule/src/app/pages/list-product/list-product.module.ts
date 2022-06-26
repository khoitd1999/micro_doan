import { NgModule } from '@angular/core';

import { ListProductRoutingModule } from './list-product-routing.module';

import { ListProductComponent } from './list-product.component';
import {CommonModule} from "@angular/common";
import {NgZorroAntdModule, NzSelectModule} from "ng-zorro-antd";
import {FormsModule} from "@angular/forms";


@NgModule({
  imports: [
    ListProductRoutingModule,
    FormsModule,
    CommonModule,
    NzSelectModule,
    NgZorroAntdModule
  ],
  declarations: [ListProductComponent],
  exports: [ListProductComponent]
})
export class ListProductModule { }
