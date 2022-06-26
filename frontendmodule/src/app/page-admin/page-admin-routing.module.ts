import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PageAdminComponent} from './page-admin.component';
import {AuthGuard} from '../UtilsService/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: PageAdminComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        loadChildren: () => import('./warehouse/warehouse.module').then(m => m.WarehouseModule)
      },
      {
        path: 'warehouse',
        loadChildren: () => import('./warehouse/warehouse.module').then(m => m.WarehouseModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule)
      },
      {
        path: 'import',
        loadChildren: () => import('./warehousereceipt/warehouse-receipt.module').then(m => m.WarehouseReceiptModule)
      },
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.InventoryModule)
      },
      {
        path: 'export',
        loadChildren: () => import('./warehousereceipt/warehouse-receipt.module').then(m => m.WarehouseReceiptModule)
      },
      {
        path: 'bill',
        loadChildren: () => import('./bill/bill.module').then(m => m.BillModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule)
      },
    ],
  },
  {
    path: 'login',
    loadChildren: () => import('./login-admin/login-admin.module').then(m => m.LoginAdminModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PageAdminRoutingModule { }
