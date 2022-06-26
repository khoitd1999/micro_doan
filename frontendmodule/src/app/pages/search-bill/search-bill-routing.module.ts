import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import { SearchBillComponent } from './search-bill.component';
import {AuthGuard} from "../../UtilsService/auth.guard";


const routes: Routes = [
  {
    path: '',
    component: SearchBillComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchBillRoutingModule { }
