import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import { CartComponent } from './cart.component';
import {AuthGuard} from "../../UtilsService/auth.guard";


const routes: Routes = [
  {
    path: '',
    component: CartComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CartRoutingModule { }
