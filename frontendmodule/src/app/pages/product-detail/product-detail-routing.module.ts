import {Injectable, NgModule} from '@angular/core';
import {Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { ProductDetailComponent } from './product-detail.component';
import {map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";
import {of} from "rxjs";
import {ProductService} from "../../page-admin/product/product.service";
import {Product} from "../../entity/product";

@Injectable({ providedIn: 'root' })
export class ProductDetailResolve implements Resolve<any> {
  constructor(private service: ProductService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.findById(id).pipe(map((product: HttpResponse<any>) => product));
    }
    return of(new Product());
  }
}

const routes: Routes = [
  {
    path: ':id',
    component: ProductDetailComponent,
    resolve: {
      product: ProductDetailResolve
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductDetailRoutingModule { }
