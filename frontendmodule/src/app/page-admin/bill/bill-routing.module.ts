import {Injectable, NgModule} from '@angular/core';
import {Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { BillComponent } from './bill.component';
import {AuthGuard} from "../../UtilsService/auth.guard";
import {WarehouseReceiptService} from "../warehousereceipt/warehouse-receipt.service";
import {map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";
import {of} from "rxjs";
import {WareHouseReceipt} from "../../entity/warehousereceipt";
import {BillService} from "./bill.service";
import {Bill} from "../../entity/bill";
import {WarehouseReceiptUpdateComponent} from "../warehousereceipt/warehouse-receipt-update.component";
import {WarehouseReceiptResolve} from "../warehousereceipt/warehouse-receipt-routing.module";
import {BillUpdateComponent} from "./bill-update.component";

@Injectable({ providedIn: 'root' })
export class BillResolve implements Resolve<any> {
  constructor(private service: BillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.findById(id).pipe(map((bill: HttpResponse<any>) => bill.body));
    }
    return of(new Bill());
  }
}

const routes: Routes = [
  {
    path: '',
    component: BillComponent,
    canActivate: [AuthGuard]
  },
  {
    path: ':id/new',
    component: BillUpdateComponent,
    canActivate: [AuthGuard],
    resolve: {
      bill: BillResolve
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BillRoutingModule { }
