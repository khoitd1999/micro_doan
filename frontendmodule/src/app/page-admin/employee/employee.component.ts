import { Component, OnInit } from '@angular/core';
import {WareHouse} from "../../entity/warehouse";
import {EmployeeService} from "./employee.service";
import {AlertService} from "../../UtilsService/alert.service";
import {NzModalService} from "ng-zorro-antd";
import {Employee} from "../../entity/employee";

@Component({
  selector: 'app-welcome',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {
  employees: any;
  total = 0;
  loading = false;
  pageSize = 10;
  pageIndex = 1;
  isVisible: boolean;
  isOkLoading: boolean;
  employee: Employee;
  titleModal: any;
  isLoading: any;
  timerID: any;
  usernameSearch: any;
  fullNameSearch: any;

  constructor(
    private employeeService: EmployeeService,
    private alertService: AlertService,
    private modal: NzModalService
    // private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.isVisible = false;
    this.isOkLoading = false;
    this.employees = [];
    this.employee = new Employee();
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ usernameSearch: '', fullNameSearch: '' }));
    // this.loadMore(null, null);
  }

  loadDataFromServer(
    pageIndex: number,
    pageSize: number,
    searchTerm
  ): void {
    this.loading = true;
    this.employeeService.loadAllData(pageIndex - 1, pageSize, searchTerm).subscribe(res => {
      this.loading = false;
      this.total = res.body[1];
      this.employees = res.body[0];
    });
  }

  onCurrentPageDataChange(index, isSize): void {
    if (isSize) {
      this.pageSize = index;
    } else {
      this.pageIndex = index;
    }
    const searchTerm = JSON.stringify({ usernameSearch: this.usernameSearch || '', fullNameSearch: this.fullNameSearch || '' });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  openModal(obj?): void {
    this.isVisible = true;
    if (obj) {
      this.titleModal = 'Sửa nhân viên';
      this.employee = Object.assign({}, obj);
      this.employee.rePassword = this.employee.password;
    } else {
      this.titleModal = 'Thêm nhân viên';
      this.employee = new Employee();
      this.employee.status = true;
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
      this.employee = Object.assign({}, obj);
      this.employee.status = false;
      // const status = await this.checkBefore(obj.id);
      // if (status) {
      //   this.showDeleteConfirm(this.warehouse, 'Đã có ít nhất một tài khoản sử dụng chức năng này. ' +
      //     'Bạn có muốn tiếp tục không?', true);
      //   return;
      // }
    }
    this.employeeService.save(this.employee).subscribe(res => {
      this.isVisible = false;
      this.isOkLoading = false;
      if (res.message) {
        this.alertService.error(res.message);
        return;
      }
      this.alertService.success('Thao tác thực hiện thành công');
      this.employee = new Employee();
      this.employee.status = true;
      this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ usernameSearch: '', fullNameSearch: '' }));
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

  searchPagination(): void {
    this.pageIndex = 1;
    this.pageSize = 10;
    const searchTerm = JSON.stringify({ usernameSearch: this.usernameSearch || '', fullNameSearch: this.fullNameSearch || '' });
    this.loadDataFromServer(this.pageIndex, this.pageSize, searchTerm);
  }

  clearText(): void {
    this.usernameSearch = '';
    this.fullNameSearch = '';
    this.pageIndex = 1;
    this.pageSize = 10;
    this.loadDataFromServer(this.pageIndex, this.pageSize, JSON.stringify({ nameSearch: '' }));
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

  generateID(i): number {
    return (this.pageIndex - 1) * this.pageSize + i;
  }

  checkErr() {
    if (!this.employee.username) {
      this.alertService.error('Chưa nhập tài khoản');
      return true;
    } else if (!this.employee.fullName) {
      this.alertService.error('Chưa nhập tên hiển thị');
      return true;
    } else if (!this.employee.password) {
      this.alertService.error('Chưa nhập mật khẩu');
      return true;
    } else if (!this.employee.rePassword) {
      this.alertService.error('Chưa nhập lại mật khẩu');
      return true;
    } else if (this.employee.rePassword !== this.employee.password) {
      this.alertService.error('Mật khẩu không giống với mật khẩu nhập lại');
      return true;
    }
    return false;
  }
}
