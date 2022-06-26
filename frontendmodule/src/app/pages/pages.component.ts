import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AlertService} from "../UtilsService/alert.service";

@Component({
  selector: 'app-welcome',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit {
  isCollapsed = false;
  user: any;
  constructor(private route: Router,
              public alertService: AlertService) { }
  ngOnInit() {

  }
}
