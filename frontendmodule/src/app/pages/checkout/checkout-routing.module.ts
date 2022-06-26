import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import { CheckoutComponent } from './checkout.component';
import {AuthGuard} from "../../UtilsService/auth.guard";


const routes: Routes = [
  {
    path: '',
    component: CheckoutComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CheckoutRoutingModule { }
