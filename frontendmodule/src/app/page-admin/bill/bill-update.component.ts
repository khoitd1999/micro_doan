import {Component, OnInit} from '@angular/core';
import {WarehouseReceiptService} from "../warehousereceipt/warehouse-receipt.service";
import {AlertService} from "../../UtilsService/alert.service";
import {NzModalService} from "ng-zorro-antd";
import {WareHouseReceipt} from "../../entity/warehousereceipt";
import {WareHouseReceiptDetail} from "../../entity/warehousereceiptdetail";
import {ProductService} from "../product/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TypeReceipt} from "../../app.constant";

@Component({
  selector: 'app-welcome',
  templateUrl: './bill-update.component.html',
  styleUrls: ['./bill-update.component.css']
})
export class BillUpdateComponent implements OnInit {
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
  bill: any;

  constructor(
    private warehouseReceiptService: WarehouseReceiptService,
    private alertService: AlertService,
    private productService: ProductService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
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
      if (data.bill && data.bill.id) {
        this.bill = data.bill;
        console.log(this.bill);
        this.wareHouseReceipt = new WareHouseReceipt();
        this.wareHouseReceipt.code = 'EX' + this.bill.id;
        this.wareHouseReceipt.idWar = this.bill.idWar;
        this.wareHouseReceipt.totalAmount = this.bill.totalAmount;
        this.wareHouseReceipt.type = TypeReceipt.EXPORT;
        this.wareHouseReceipt.fee = this.bill.fee;
        this.wareHouseReceipt.wareHouseReceiptDetails = [];
        this.wareHouseReceipt.idBil = this.bill.id;
        for (let item of this.bill.carts) {
          this.wareHouseReceipt.wareHouseReceiptDetails.push({idPro: item.idPro, namePro: item.namePro, quantity: item.quantity, price: item.price, amount: item.amount});
        }
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
      this.alertService.error('Chưa chọn ngày xuất kho');
      return true;
    } else if (!this.wareHouseReceipt.idEmp) {
      this.alertService.error('Chưa chọn nhân viên thực hiện');
      return true;
    } else if (!this.wareHouseReceipt.idWar) {
      this.alertService.error('Chưa chọn kho nhập');
      return true;
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
    this.warehouseReceiptService.createExport(this.wareHouseReceipt).subscribe(res => {
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
    this.router.navigate(['/pages_admin/bill']);
  }
}
