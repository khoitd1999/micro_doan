import {Component, OnDestroy, OnInit} from '@angular/core';
import {Bill} from "../../entity/bill";
import {BillService} from "./bill.service";
import {Status_Bill, TypeShip} from "../../app.constant";
import {NzModalService} from "ng-zorro-antd";
import {AlertService} from "../../UtilsService/alert.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-welcome',
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.css']
})
export class BillComponent implements OnInit, OnDestroy {
  bills: Bill[];
  bill: Bill;
  carts: any;
  total = 0;
  loading = false;
  pageSize = 10;
  pageIndex = 1;
  pageSizeDetail = 10;
  pageIndexDetail = 1;
  client: any;
  GIAO_HANG_TAN_NOI = +TypeShip.GIAO_HANG_TAN_NOI;
  NHAN_TAI_CUA_HANG = +TypeShip.NHAN_TAI_CUA_HANG;
  DA_XAC_NHAN = Status_Bill.DA_XAC_NHAN;
  DA_GIAO_HANG = Status_Bill.DA_GIAO_HANG;
  DA_HUY = Status_Bill.DA_HUY;
  fromDateSearch: any;
  typeShips: any;
  statuses: any;
  typeShipSearch: any;
  statusSearch: any;
  dateFormat = 'dd/MM/yyyy';

  constructor(
    private searchBillService: BillService,
    private alertService: AlertService,
    private modal: NzModalService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.bills = [];
    this.bill = new Bill();
    this.carts = [];
    this.typeShips = [
      {id: 0, name: 'GIAO HÀNG TẬN NƠI'},
      {id: 1, name: 'NHẬN TẠI CỬA HÀNG'}
    ];
    this.statuses = [
      {id: this.DA_XAC_NHAN, name: 'ĐÃ XÁC NHẬN'},
      {id: this.DA_GIAO_HANG, name: 'ĐÃ GIAO HÀNG'},
      {id: this.DA_HUY, name: 'ĐÃ HỦY'},
    ]
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ fromDateSearch: this.fromDateSearch, typeShipSearch: this.typeShipSearch, statusSearch: this.statusSearch}));
  }

  ngOnDestroy(): void {
  }

  onCurrentPageDataChange(index, isSize): void {
    if (isSize) {
      this.pageSize = index;
    } else {
      this.pageIndex = index;
    }
    const searchTerm = JSON.stringify({ fromDateSearch: this.fromDateSearch, typeShipSearch: this.typeShipSearch, statusSearch: this.statusSearch });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  loadDataFromServer(
    pageIndex: number,
    pageSize: number,
    searchTerm
  ): void {
    this.loading = true;
    this.searchBillService.loadAllData(pageIndex - 1, pageSize, searchTerm).subscribe(res => {
      this.loading = false;
      this.total = res.body[1];
      this.bills = res.body[0];
      this.pageIndexDetail = 1;
      this.pageSizeDetail = 10;
      this.bill = this.bills[0];
      this.carts = [];
      this.loadDataDetail(this.pageIndexDetail, this.pageSizeDetail, JSON.stringify({ idBil: this.bill.id }));
    });
  }

  loadDataDetail(
    pageIndex: number,
    pageSize: number,
    searchTerm
  ) {
    this.searchBillService.loadAllDetail(pageIndex - 1, pageSize, searchTerm).subscribe(res => {
      this.loading = false;
      this.carts = res.body[0];
      for (let item of this.carts) {
        item.image = 'data:image/jpeg;base64,' + item.image;
      }
    });
  }

  onCurrentPageDataChangeDetail(index, isSize): void {
    if (isSize) {
      this.pageSizeDetail = index;
    } else {
      this.pageIndexDetail = index;
    }
    const searchTerm = JSON.stringify({ idBil: this.bill.id });
    this.loadDataDetail(this.pageIndexDetail, this.pageSizeDetail, searchTerm);
  }

  onSelect(i) {
    this.bill = this.bills[i];
    this.pageIndexDetail = 1;
    this.pageSizeDetail = 10;
    const searchTerm = JSON.stringify({ idBil: this.bill.id });
    this.loadDataDetail(this.pageIndexDetail, this.pageSizeDetail, searchTerm);
  }

  showDeleteConfirm(data, title, next?): void {
    this.modal.confirm({
      nzTitle: title,
      nzOkText: 'OK',
      nzOkType: 'primary',
      // nzOkDanger: true,
      nzOnOk: () => this.cancelOrder(),
      nzCancelText: 'Đóng lại',
      nzOnCancel: () => {
        if (next) {
          // this.function.status = true;
        }
      }
    });
  }

  cancelOrder() {
    this.bill.status = Status_Bill.DA_HUY;
    this.searchBillService.cancelOrder(this.bill).subscribe(res => {
      this.alertService.success('Hủy đơn hàng thành công');
      const searchTerm = JSON.stringify({ idCli: this.client.id });
      this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
    })
  }

  searchPagination() {
    this.pageIndex = 1;
    this.pageSize = 10;
    const searchTerm = JSON.stringify({ fromDateSearch: this.fromDateSearch, typeShipSearch: this.typeShipSearch, statusSearch: this.statusSearch });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  clearText(): void {
    this.fromDateSearch = null;
    this.typeShipSearch = null;
    this.statusSearch = null;
    this.pageIndex = 1;
    this.pageSize = 10;
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ fromDateSearch: this.fromDateSearch, typeShipSearch: this.typeShipSearch, statusSearch: this.statusSearch }));
  }

  createExport(id: any) {
    this.router.navigate(['/pages_admin/bill/', id, 'new']);
  }

  showWareHouseReceipt(idWare) {
    sessionStorage.setItem('fromBill', 'fff');
    this.router.navigate(['/pages_admin/export/', idWare, 'edit']);
  }
}
