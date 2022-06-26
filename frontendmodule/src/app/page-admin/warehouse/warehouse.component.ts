import { Component, OnInit } from '@angular/core';
import {WareHouse} from "../../entity/warehouse";
import {WarehouseService} from "./warehouse.service";
import {AlertService} from "../../UtilsService/alert.service";
import {NzModalService} from "ng-zorro-antd";

@Component({
  selector: 'app-welcome',
  templateUrl: './warehouse.component.html',
  styleUrls: ['./warehouse.component.css']
})
export class WarehouseComponent implements OnInit {
  warehouses: any;
  total = 0;
  loading = false;
  pageSize = 10;
  pageIndex = 1;
  isVisible: boolean;
  isOkLoading: boolean;
  allFunctions: any;
  warehouse: WareHouse;
  titleModal: any;
  isLoading: any;
  timerID: any;
  nameSearch: any;
  addressSearch: any;
  provinces: any;
  districts: any;
  wards: any;

  constructor(
    private warehouseService: WarehouseService,
    private alertService: AlertService,
    private modal: NzModalService
    // private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.isVisible = false;
    this.isOkLoading = false;
    this.warehouses = [];
    this.allFunctions = [];
    this.provinces = [];
    this.districts = [];
    this.wards = [];
    this.warehouse = new WareHouse();
    this.alertService.name = 'DANH SÁCH KHO';
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '' }));
    // this.loadMore(null, null);
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
      this.warehouses = res.body[0];
    });
  }

  onCurrentPageDataChange(index, isSize): void {
    if (isSize) {
      this.pageSize = index;
    } else {
      this.pageIndex = index;
    }
    const searchTerm = JSON.stringify({ nameSearch: this.nameSearch || '' });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  openModal(obj?): void {
    this.isVisible = true;
    this.loadMoreProvince(null);
    if (obj) {
      this.titleModal = 'Sửa kho';
      this.warehouse = Object.assign({}, obj);
      this.loadMoreDistrict(null);
      this.loadMoreWard(null);
    } else {
      this.titleModal = 'Thêm kho';
      this.warehouse = new WareHouse();
      this.warehouse.status = true;
    }
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  handleOk(): void {
    this.isOkLoading = true;
    this.save();
  }

  async save(obj?): Promise<void> {
    if (!obj) {
      if (this.checkErr()) {
        this.isOkLoading = false;
        return;
      }
    }
    if (obj) {
      // xác nhận bỏ hoạt động của kho
      this.warehouse = Object.assign({}, obj);
      this.warehouse.status = false;
    } else {
      this.warehouse.address = '';
      if (this.warehouse.street) {
        this.warehouse.address += this.warehouse.street;
      }
      this.warehouse.address += ' ' + (this.wards.find(n => n.code === this.warehouse.idWar).name);
      this.warehouse.address += ' ' + (this.districts.find(n => n.code === this.warehouse.idDis).name);
      this.warehouse.address += ' ' + (this.provinces.find(n => n.code === this.warehouse.idPro).name);
      this.warehouse.address = this.warehouse.address.trim();
    }
    this.warehouseService.save(this.warehouse).subscribe(res => {
      this.isVisible = false;
      this.isOkLoading = false;
      if (res.message) {
        this.alertService.error(res.message);
        return;
      }
      this.alertService.success('Thao tác thực hiện thành công');
      this.warehouse = new WareHouse();
      this.warehouse.status = true;
      this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '' }));
      this.loadMoreProvince(null);
    }, err => {
      this.isOkLoading = false;
      this.alertService.error(err.error.message);
    });
  }

  checkBefore(id): any {
    return new Promise(resolve => {
      // this.functionService.checkBeforeDelete(id).subscribe(res => {
      //   resolve(res.data);
      // });
    });
  }

  onSearch(searchText?): void {
    if (!this.warehouse.idPro) {
      this.provinces = [];
      this.districts = [];
      this.wards = [];
      this.loadMoreProvince(searchText);
    } else if (!this.warehouse.idDis) {
      this.districts = [];
      this.wards = [];
      this.loadMoreDistrict(searchText);
    } else {
      this.wards = [];
      this.loadMoreWard(searchText);
    }
  }

  loadMoreProvince(searchText?: string) {
    this.isLoading = true;
    this.warehouseService.getAllArea(
      JSON.stringify({ nameSearch: searchText || '',
        code: null })).subscribe(res => {
      this.isLoading = false;
      this.provinces = res;
    });
  }

  loadMoreDistrict(searchText?: string) {
    this.isLoading = true;
    this.warehouseService.getAllArea(
      JSON.stringify({ nameSearch: searchText || '',
        code: this.warehouse.idPro})).subscribe(res => {
      this.isLoading = false;
      this.districts = res;
    });
  }

  loadMoreWard(searchText?: string) {
    this.isLoading = true;
    this.warehouseService.getAllArea(
      JSON.stringify({ nameSearch: searchText || '',
        code: this.warehouse.idDis})).subscribe(res => {
      this.isLoading = false;
      this.wards = res;
    });
  }

  searchPagination(): void {
    this.pageIndex = 1;
    this.pageSize = 10;
    const searchTerm = JSON.stringify({ nameSearch: this.nameSearch || '' });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  clearText(): void {
    this.addressSearch = '';
    this.nameSearch = '';
    this.pageIndex = 1;
    this.pageSize = 10;
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '' }));
  }

  showDeleteConfirm(data, title): void {
    this.modal.confirm({
      nzTitle: title,
      nzOkText: 'OK',
      nzOkType: 'primary',
      // nzOkDanger: true,
      nzOnOk: () => this.save(data),
      nzCancelText: 'Đóng lại',
      nzOnCancel: () => {

      }
    });
  }

  generateID(i): number {
    return (this.pageIndex - 1) * this.pageSize + i;
  }

  checkErr() {
    if (!this.warehouse.nameWar) {
      this.alertService.error('Chưa nhập tên kho');
      return true;
    } else if (!this.warehouse.idPro || !this.warehouse.idDis || !this.warehouse.idWar || !this.warehouse.street) {
      this.alertService.error('Chưa nhập đủ đầy đủ thông tin về địa chỉ');
      return true;
    }
    return false;
  }
}
