import {Injectable, NgModule} from '@angular/core';
import {Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { ListProductComponent } from './list-product.component';
import {WarehouseReceiptService} from '../../page-admin/warehousereceipt/warehouse-receipt.service';
import {map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {of} from 'rxjs';
import {WareHouseReceipt} from '../../entity/warehousereceipt';
import {ProductService} from '../../page-admin/product/product.service';
import {Brand} from '../../entity/brand';
import {WarehouseReceiptResolve} from '../../page-admin/warehousereceipt/warehouse-receipt-routing.module';

@Injectable({ providedIn: 'root' })
export class ListProductResolve implements Resolve<any> {
  constructor(private service: ProductService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params.id ? route.params.id : null;
    if (id) {
      return this.service.loadBrand(id).pipe(map((brand: HttpResponse<any>) => brand));
    }
    return of(new Brand());
  }
}

const routes: Routes = [
  {
    path: '',
    component: ListProductComponent
  },
  {
    path: ':id',
    component: ListProductComponent,
    resolve: {
      brandes: ListProductResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ListProductRoutingModule { }
