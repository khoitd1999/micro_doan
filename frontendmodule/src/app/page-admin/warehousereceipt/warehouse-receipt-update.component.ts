import {Component, OnInit} from '@angular/core';
import {WarehouseReceiptService} from "./warehouse-receipt.service";
import {AlertService} from "../../UtilsService/alert.service";
import {NzModalService} from "ng-zorro-antd";
import {WareHouseReceipt} from "../../entity/warehousereceipt";
import {WareHouseReceiptDetail} from "../../entity/warehousereceiptdetail";
import {ProductService} from "../product/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TypeReceipt} from "../../app.constant";

@Component({
  selector: 'app-welcome',
  templateUrl: './warehouse-receipt-update.component.html',
  styleUrls: ['./warehouse-receipt-update.component.css']
})
export class WarehouseReceiptUpdateComponent implements OnInit {
  wareHouseReceipts: any;
  total = 0;
  loading = false;
  pageSize = 10;
  pageIndex = 1;
  isVisible: boolean;
  isOkLoading: boolean;
  wareHouseReceipt: WareHouseReceipt;
  wareHouseReceiptDetails: WareHouseReceiptDetail[];
  titleModal: any;
  isLoading: any;
  timerID: any;
  nameSearch: any;
  addressSearch: any;
  employees: any;
  wareHouses: any;
  idWar: any;
  idEmp: any;
  dateFormat = 'dd/MM/yyyy';
  i = 0;
  editId: string | null = null;
  products: any;
  type: any;
  IMPORT = TypeReceipt.IMPORT;
  EXPORT = TypeReceipt.EXPORT;

  constructor(
    private warehouseReceiptService: WarehouseReceiptService,
    private alertService: AlertService,
    private productService: ProductService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    if (this.router.url.includes('import')) {
      this.type = TypeReceipt.IMPORT;
    } else {
      this.type = TypeReceipt.EXPORT;
    }
  }

  ngOnInit(): void {
    this.isVisible = false;
    this.isOkLoading = false;
    this.employees = [];
    this.wareHouses = [];
    this.products = [];
    this.wareHouseReceipts = [];
    this.wareHouseReceipt = new WareHouseReceipt();
    this.wareHouseReceiptDetails = [];
    // this.alertService.name = 'QUẢN LÝ NHẬP KHO';
    this.activatedRoute.data.subscribe(data => {
      if (data.warehouseReceipt && data.warehouseReceipt.id) {
        this.wareHouseReceipt = data.warehouseReceipt;
        this.wareHouseReceiptDetails = this.wareHouseReceipt.wareHouseReceiptDetails;
      } else {

      }
    })
    this.loadEmployeeAndWareHouse();
    this.loadAllProduct();
  }

  loadEmployeeAndWareHouse() {
    this.warehouseReceiptService.loadEmployeeAndWareHouse().subscribe(res => {
      this.employees = res.body[1];
      this.wareHouses = res.body[0];
    });
  }

  loadAllProduct() {
    this.productService.loadAll().subscribe(res => {
      this.products = res.body;
    });
  }

  checkErr() {
    if (!this.wareHouseReceipt.code) {
      this.alertService.error('Chưa nhập mã nhập kho');
      return true;
    } else if (!this.wareHouseReceipt.date) {
      this.alertService.error('Chưa chọn ngày nhập kho');
      return true;
    } else if (!this.wareHouseReceipt.idEmp) {
      this.alertService.error('Chưa chọn nhân viên thực hiện');
      return true;
    } else if (!this.wareHouseReceipt.idWar) {
      this.alertService.error('Chưa chọn kho nhập');
      return true;
    }
    if (this.wareHouseReceiptDetails.length === 0) {
      this.alertService.error('Chưa nhập chi tiết cho phiếu');
      return true;
    }
    for (let i = 0; i < this.wareHouseReceiptDetails.length; i++) {
      const item = this.wareHouseReceiptDetails[i];
      if (!item.namePro) {
        this.alertService.error('Hàng ' + (i + 1) + ' chưa chọn sản phẩm');
        return true;
      }
      if (!item.quantity) {
        this.alertService.error('Hàng ' + (i + 1) + ' chưa nhập số lượng');
        return true;
      }
      if (!item.price) {
        this.alertService.error('Hàng ' + (i + 1) + ' chưa nhập đơn giá');
        return true;
      }
    }
    return false;
  }

  onChange(result: Date): void {
    console.log('onChange: ', result);
  }

  startEdit(id: string): void {
    this.editId = id;
  }

  stopEdit(): void {
    this.editId = null;
  }

  addRow(): void {
    this.wareHouseReceiptDetails = [
      ...this.wareHouseReceiptDetails,
      {
        id: `${this.i}`,
        namePro: null,
        quantity: null,
        price: null,
        amount: null
      }
    ];
    this.i++;
  }

  deleteRow(id: string): void {
    this.wareHouseReceiptDetails = this.wareHouseReceiptDetails.filter(d => d.id !== id);
  }

  changeProduct(data) {
    data.namePro = this.products.find(n => n.id === data.idPro).namePro;
    data.price = this.products.find(n => n.id === data.idPro).price;
    this.changeAmount(data);
  }

  changeAmount(data) {
    if (!data.price || !data.quantity) {
      data.amount = null;
    } else {
      data.amount = data.price * data.quantity;
    }
  }

  save() {
    if (this.checkErr()) {
      return;
    }
    let total = 0;
    for (let i = 0; i < this.wareHouseReceiptDetails.length; i++) {
      const item = this.wareHouseReceiptDetails[i];
      total += item.amount;
    }
    this.wareHouseReceipt.totalAmount = total;
    this.wareHouseReceipt.wareHouseReceiptDetails = this.wareHouseReceiptDetails;
    this.wareHouseReceipt.type = 1;
    this.warehouseReceiptService.save(this.wareHouseReceipt).subscribe(res => {
      if (res.message) {
        this.alertService.error(res.message);
        return;
      }
      this.alertService.success('Thao tác thực hiện thành công');
      this.wareHouseReceipt = res.body.body;
      this.wareHouseReceiptDetails = this.wareHouseReceipt.wareHouseReceiptDetails;
    });
  }

  back() {
    if (this.type === 1) {
      this.router.navigate(['/pages_admin/import']);
    } else {
      if (sessionStorage.getItem('fromBill')) {
        this.router.navigate(['/pages_admin/bill']);
        sessionStorage.removeItem('fromBill');
      } else {
        this.router.navigate(['/pages_admin/export']);
      }
    }
  }
}
