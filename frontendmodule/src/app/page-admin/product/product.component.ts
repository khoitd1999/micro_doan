import {Component, OnInit} from '@angular/core';
import {ProductService} from './product.service';
import {AlertService} from '../../UtilsService/alert.service';
import {NzModalService} from 'ng-zorro-antd';
import {Product} from '../../entity/product';

@Component({
  selector: 'app-welcome',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  products: any;
  total = 0;
  loading = false;
  pageSize = 10;
  pageIndex = 1;
  isVisible: boolean;
  isOkLoading: boolean;
  product: Product;
  titleModal: any;
  isLoading: any;
  timerID: any;
  nameSearch: any;
  addressSearch: any;
  categories: any;
  brandes: any;
  categoriesSearch: any;
  brandesSearch: any;
  categoriesPopup: any;
  brandesPopup: any;
  idCat: any;
  idBra: any;
  fileList1 = [];
  dateFormat = 'dd/MM/yyyy';

  constructor(
    private productService: ProductService,
    private alertService: AlertService,
    private modal: NzModalService
    // private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.isVisible = false;
    this.isOkLoading = false;
    this.brandes = [];
    this.categories = [];
    this.brandesSearch = [];
    this.categoriesSearch = [];
    this.brandesPopup = [];
    this.categoriesPopup = [];
    this.products = [];
    this.product = new Product();
    this.alertService.name = 'DANH SÁCH SẢN PHẨM';
    this.loadCategoryAndBrand();
    // tslint:disable-next-line:max-line-length
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '',  idCat: this.idCat, idBra: this.idBra, isAdmin: true}));
    // this.loadMore(null, null);
  }

  loadCategoryAndBrand() {
    this.productService.loadCategoryAndBrand().subscribe(res => {
      this.brandes = res.body[1];
      this.categories = res.body[0];
      // deep copy
      this.brandesSearch = JSON.parse(JSON.stringify(this.brandes));
      this.brandesPopup = JSON.parse(JSON.stringify(this.brandes));
      this.categoriesSearch = JSON.parse(JSON.stringify(this.categories));
      this.categoriesPopup = JSON.parse(JSON.stringify(this.categories));
    });
  }

  loadDataFromServer(
    pageIndex: number,
    pageSize: number,
    searchTerm
  ): void {
    this.loading = true;
    this.productService.loadAllData(pageIndex - 1, pageSize, searchTerm).subscribe(res => {
      this.loading = false;
      this.total = res.body[1];
      this.products = res.body[0];
      for (const item of this.products) {
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
    const searchTerm = JSON.stringify({ nameSearch: this.nameSearch || '', idCat: this.idCat, idBra: this.idBra, isAdmin: true });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  onChange(result: Date): void {
    console.log('onChange: ', result);
  }

  openModal(obj?): void {
    this.isVisible = true;
    this.brandesPopup = JSON.parse(JSON.stringify(this.brandes));
    this.categoriesPopup = JSON.parse(JSON.stringify(this.categories));
    if (obj) {
      this.titleModal = 'Sửa sản phẩm';
      this.product = Object.assign({}, obj);
      this.fileList1 = [
        {
          uid: '-1',
          name: '',
          url: this.product.image,
          thumbUrl: this.product.image
        }
      ];
      this.categoriesPopup = this.categoriesPopup.filter(n => n.id === this.product.idCat);
      this.brandesPopup = this.brandesPopup.filter(n => n.idCat === this.product.idCat);
    } else {
      this.titleModal = 'Thêm sản phẩm';
      this.product = new Product();
      this.fileList1 = [];
      this.product.status = true;
      // tháng bị tăng lên 1 đơn vị cần set lùi lại
      // this.product.date = new Date(2020, 6, 1);
    }
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  handleOk(): void {
    this.isOkLoading = true;
    this.save();
  }

  async save(obj?, notNext?): Promise<void> {
    if (!obj) {
      if (this.checkErr()) {
        this.isOkLoading = false;
        return;
      }
    }
    if (obj && !notNext) {
      this.product = Object.assign({}, obj);
      this.product.status = false;
    }
    console.log(this.product);
    try {
      let date = this.product.date.getFullYear();
      if (this.product.date.getMonth() + 1 < 10) {
        date += '-0' + (this.product.date.getMonth() + 1);
      } else {
        date += '-' + (this.product.date.getMonth() + 1);
      }
      if (this.product.date.getDate() + 1 < 10) {
        date += '-0' + (this.product.date.getDate() + 1);
      } else {
        date += '-' + (this.product.date.getDate() + 1);
      }
      this.product.date = date; // convert ngày về dạng yyyy-MM-dd
    } catch (e) {

    }
    this.product.image = null;
    console.log(this.fileList1);
    this.productService.save(this.product, this.fileList1[0]).subscribe(res => {
      this.isVisible = false;
      this.isOkLoading = false;
      if (res.body.message) {
        this.alertService.error(res.body.message);
        return;
      }
      this.alertService.success('Thao tác thực hiện thành công');
      this.product = new Product();
      this.product.status = true;
      // tslint:disable-next-line:max-line-length
      this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '',  idCat: this.idCat, idBra: this.idBra, isAdmin: true }));
    }, err => {
      this.isOkLoading = false;
      this.alertService.error(err.error.message);
    });
  }

  searchPagination(): void {
    this.pageIndex = 1;
    this.pageSize = 10;
    const searchTerm = JSON.stringify({ nameSearch: this.nameSearch || '', idCat: this.idCat, idBra: this.idBra, isAdmin: true });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  clearText(): void {
    this.idCat = null;
    this.idBra = null;
    this.nameSearch = null;
    this.pageIndex = 1;
    this.pageSize = 10;
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '', idCat: null, idBra: null, isAdmin: true }));
  }

  showDeleteConfirm(data, title, next?): void {
    this.modal.confirm({
      nzTitle: title,
      nzOkText: 'OK',
      nzOkType: 'primary',
      // nzOkDanger: true,
      nzOnOk: () => this.save(data, next),
      nzCancelText: 'Đóng lại',
      nzOnCancel: () => {
        if (next) {
          // this.function.status = true;
        }
      }
    });
  }

  checkErr() {
    if (!this.product.namePro) {
      this.alertService.error('Chưa nhập tên sản phẩm');
      return true;
    } else if (!this.product.idCat) {
      this.alertService.error('Chưa chọn loại sản phẩm');
      return true;
    } else if (!this.product.idBra) {
      this.alertService.error('Chưa chọn nhãn hàng');
      return true;
    } else if (!this.product.idBra) {
      this.alertService.error('Chưa chọn nhãn hàng');
      return true;
    } else if (!this.product.price) {
      this.alertService.error('Chưa nhập giá');
      return true;
    } else if (!this.product.date) {
      this.alertService.error('Chưa chọn ngày ra mắt');
      return true;
    }
    return false;
  }

  changeCategorySearch() {
    this.idBra = null;
    if (this.idCat) {
      this.brandesSearch = this.brandes.filter(n => n.idCat.includes(this.idCat));
    } else {
      this.brandesSearch = JSON.parse(JSON.stringify(this.brandes));
      this.categoriesSearch = JSON.parse(JSON.stringify(this.categories));
    }
  }

  changeBrandSearch() {
    if (!this.idCat && this.idBra) {
      const categoryIds = this.brandesSearch.find(n => n.id === this.idBra).idCat;
      this.categoriesSearch = this.categories.filter(n => categoryIds.includes(n.id));
      this.idCat = this.categoriesSearch[0].id;
      this.brandesSearch = this.brandes.filter(n => n.idCat.includes(this.idCat));
    }
  }

  changeCategoryPopup() {
    this.product.idBra = null;
    if (this.product.idCat) {
      this.brandesPopup = this.brandes.filter(n => n.idCat.includes(this.product.idCat));
    } else {
      this.brandesPopup = JSON.parse(JSON.stringify(this.brandes));
      this.categoriesPopup = JSON.parse(JSON.stringify(this.categories));
    }
  }

  changeBrandPopup() {
    if (!this.product.idCat && this.product.idBra) {
      const categoryIds = this.brandesPopup.find(n => n.id === this.product.idBra).idCat;
      this.categoriesPopup = this.categories.filter(n => categoryIds.includes(n.id));
      this.product.idCat = this.categoriesPopup[0].id;
      this.brandesPopup = this.brandes.filter(n => n.idCat.includes(this.idCat));
    }
  }
}
