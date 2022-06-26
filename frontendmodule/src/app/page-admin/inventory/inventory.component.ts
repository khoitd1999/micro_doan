import { Component, OnInit } from '@angular/core';
import {WareHouse} from "../../entity/warehouse";
import {InventoryService} from "./inventory.service";
import {AlertService} from "../../UtilsService/alert.service";
import {NzModalService} from "ng-zorro-antd";
import {WarehouseReceiptService} from "../warehousereceipt/warehouse-receipt.service";
import {ProductService} from "../product/product.service";

@Component({
  selector: 'app-welcome',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {
  warehouses: any;
  total = 0;
  loading = false;
  pageSize = 10;
  pageIndex = 1;
  isVisible: boolean;
  isOkLoading: boolean;
  allFunctions: any;
  titleModal: any;
  isLoading: any;
  timerID: any;
  nameSearch: any;
  addressSearch: any;
  provinces: any;
  districts: any;
  wards: any;
  products: any;
  idPro: any;
  idWar: any;
  inventories: any;

  constructor(
    private warehouseService: InventoryService,
    private alertService: AlertService,
    private modal: NzModalService,
    private warehouseReceiptService: WarehouseReceiptService,
    private productService: ProductService
    // private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.isVisible = false;
    this.isOkLoading = false;
    this.warehouses = [];
    this.products = [];
    this.allFunctions = [];
    this.provinces = [];
    this.districts = [];
    this.wards = [];
    this.inventories = [];
    this.alertService.name = 'DANH SÁCH KHO';
    this.loadEmployeeAndWareHouse();
    this.loadAllProduct();
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ idWar: this.idWar, idPro: this.idPro }));
    // this.loadMore(null, null);
  }

  loadAllProduct() {
    this.productService.loadAll().subscribe(res => {
      this.products = res.body;
    });
  }

  loadEmployeeAndWareHouse() {
    this.warehouseReceiptService.loadEmployeeAndWareHouse().subscribe(res => {
      this.warehouses = res.body[0];
    });
  }

  loadDataFromServer(
    pageIndex: number,
    pageSize: number,
    searchTerm
  ): void {
    this.loading = true;
    this.warehouseService.loadAllData(pageIndex - 1, pageSize, searchTerm).subscribe(res => {
      this.loading = false;
      this.total = res.body[1];
      this.inventories = res.body[0];
      for (let item of this.inventories) {
        item.image = 'data:image/jpeg;base64,' + item.image;
      }
    });
  }

  onCurrentPageDataChange(index, isSize): void {
    if (isSize) {
      this.pageSize = index;
    } else {
      this.pageIndex = index;
    }
    const searchTerm = JSON.stringify({ idWar: this.idWar, idPro: this.idPro });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  searchPagination(): void {
    this.pageIndex = 1;
    this.pageSize = 10;
    const searchTerm = JSON.stringify({ idWar: this.idWar, idPro: this.idPro });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  clearText(): void {
    this.idPro = null;
    this.idWar = null;
    this.pageIndex = 1;
    this.pageSize = 10;
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ idWar: this.idWar, idPro: this.idPro }));
  }

  generateID(i): number {
    return (this.pageIndex - 1) * this.pageSize + i;
  }
}
