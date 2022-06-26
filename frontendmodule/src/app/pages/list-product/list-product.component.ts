import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../page-admin/product/product.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {
  active: any;
  categories: any;
  brandes: any;
  products: any;
  prices: any;
  pageIndex: any;
  pageSize: any;
  isLoadMore: any;
  idCat: any;
  idBra: any;
  priceFilter: any;
  isSearching: any;

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.params.subscribe(param => {
      if (param.id) {
        this.idCat = param.id;
      }
    });
  }

  ngOnInit() {
    this.prices = [
      {id: 1, name: 'Dưới 15 triệu'},
      {id: 2, name: 'Từ 15 - 20 triệu'},
      {id: 3, name: 'Từ 20 - 25 triệu'},
      {id: 4, name: 'Từ 25 - 30 triệu'},
      {id: 5, name: 'Trên 30 triệu'},
    ];
    this.isSearching = false;
    this.products = [];
    this.isLoadMore = false;
    this.pageIndex = 1;
    this.pageSize = 8;
    this.activatedRoute.data.subscribe(data => {
      if (data.brandes) {
        this.products = [];
        this.priceFilter = null;
        this.brandes = data.brandes;
        this.idBra = this.brandes[0].id;
        this.loadAll();
      } else {
        this.isSearching = true;
        this.loadAll();
      }
    });
  }

  loadAll() {
    const search: any = document.getElementById('searchText');
    const searchTerm = JSON.stringify({
      idCat: this.idCat,
      idBra: this.idBra,
      sizeCurrent: this.products.length,
      priceFilter: this.priceFilter,
      nameSearch: search.value,
      isAdmin: false
    });
    this.productService.loadAllData(this.pageIndex - 1, this.pageSize, searchTerm).subscribe(res => {
      // this.total = res.body[1];
      for (const item of res.body[0]) {
        item.image = 'data:image/jpeg;base64,' + item.image;
      }
      this.products = [...this.products, ...res.body[0]];
      this.isLoadMore = res.isLoadMore;
    });
  }

  loadMore() {
    this.pageIndex++;
    this.loadAll();
  }

  changeBrand(bra) {
    this.pageIndex = 1;
    this.idBra = bra.id;
    this.products = [];
    this.priceFilter = null;
    this.loadAll();
  }

  filterProducts() {
    this.pageIndex = 1;
    this.products = [];
    this.loadAll();
  }
}
