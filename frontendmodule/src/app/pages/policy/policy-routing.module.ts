import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import { PolicyComponent } from './policy.component';
import {AuthGuard} from "../../UtilsService/auth.guard";


const routes: Routes = [
  {
    path: '',
    component: PolicyComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PolicyRoutingModule { }
