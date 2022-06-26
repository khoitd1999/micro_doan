import {Component, OnDestroy, OnInit} from '@angular/core';
import {Bill} from "../../entity/bill";
import {PolicyService} from "./policy.service";
import {Status_Bill, TypeShip} from "../../app.constant";
import {NzModalService} from "ng-zorro-antd";
import {AlertService} from "../../UtilsService/alert.service";

@Component({
  selector: 'app-welcome',
  templateUrl: './policy.component.html',
  styleUrls: ['./policy.component.css']
})
export class PolicyComponent implements OnInit, OnDestroy {
  policies: any;

  constructor(
    private policyService: PolicyService
  ) {
  }

  ngOnInit() {
    this.policies = [];
    this.loadDataFromServer();
  }

  ngOnDestroy(): void {
  }

  loadDataFromServer(
  ): void {
    this.policyService.loadAllData().subscribe(res => {
      this.policies = res;
    });
  }
}
