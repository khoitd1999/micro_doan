import {Injectable, NgModule} from '@angular/core';
import {Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { WarehouseReceiptComponent } from './warehouse-receipt.component';
import {WarehouseReceiptUpdateComponent} from "./warehouse-receipt-update.component";
import {WarehouseReceiptService} from "./warehouse-receipt.service";
import {HttpResponse} from "@angular/common/http";
import {of} from "rxjs";
import { map } from 'rxjs/operators';
import {WareHouseReceipt} from "../../entity/warehousereceipt";
import {AuthGuard} from "../../UtilsService/auth.guard";

@Injectable({ providedIn: 'root' })
export class WarehouseReceiptResolve implements Resolve<any> {
  constructor(private service: WarehouseReceiptService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.findById(id).pipe(map((warehouseReceipt: HttpResponse<any>) => warehouseReceipt.body));
    }
    return of(new WareHouseReceipt());
  }
}

const routes: Routes = [
  {
    path: '',
    component: WarehouseReceiptComponent
  },
  {
    path: 'new',
    component: WarehouseReceiptUpdateComponent,
    canActivate: [AuthGuard],
    resolve: {
      warehouseReceipt: WarehouseReceiptResolve
    }
  },
  {
    path: ':id/edit',
    component: WarehouseReceiptUpdateComponent,
    canActivate: [AuthGuard],
    resolve: {
      warehouseReceipt: WarehouseReceiptResolve
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WarehouseReceiptRoutingModule { }
