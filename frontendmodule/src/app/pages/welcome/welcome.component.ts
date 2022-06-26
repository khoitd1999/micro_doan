import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../page-admin/product/product.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {
  urlLinkImages: any;
  active: any;
  categories: any;
  brandes: any;
  products: any;
  brandesShow: any;
  pageBrand: any;

  constructor(
    private productService: ProductService
  ) { }

  ngOnInit() {
    this.urlLinkImages = [
      './assets/images/slide/slide1.png',
      './assets/images/slide/slide2.png',
      './assets/images/slide/slide3.png',
      './assets/images/slide/slide4.png'
    ];
    this.brandesShow = [];
    this.brandes = [];
    this.categories = [];
    this.products = [];
    this.pageBrand = [];
    this.active = 0;
    this.setActiveImage();
    this.loadCategoryAndBrand();
  }

  setActiveImage() {
    setInterval(() => {
      if (this.active + 1 < 4) {
        this.active++;
      } else {
        this.active = 0;
      }
    }, 4000);
  }

  changeImage(next) {
    if (next) {
      if (this.active === 3) {
        this.active = 0;
      } else {
        this.active++;
      }
    } else {
      if (this.active === 0) {
        this.active = 3;
      } else {
        this.active--;
      }
    }
  }

  loadCategoryAndBrand() {
    this.productService.loadCategoryAndBrand().subscribe(res => {
      this.brandes = res.body[1].sort((a, b) =>  a.id - b.id);
      this.categories = res.body[0].sort((a, b) => a.id - b.id);
      this.categories.forEach(n => {
          this.brandesShow.push(this.brandes.find(m => m.idCat.includes(n.id)).id);
          this.pageBrand.push(0);
      });
      const listID = [];
      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < this.brandes.length; i++) {
        const listIDC = this.brandes[i].idCat.split(',');
        // tslint:disable-next-line:prefer-for-of
        for (let j = 0; j < listIDC.length; j++) {
          listID.push({idCat: listIDC[j], idBra: this.brandes[i].id});
        }
      }
      this.getProductDefaultForWelcome(listID);
    });
  }

  getProductDefaultForWelcome(listID) {
    this.productService.getProductDefaultForWelcome(listID).subscribe(res => {
      this.products = res;
      for (const item of this.products) {
        item.image = 'data:image/jpeg;base64,' + item.image;
      }
    });
  }

  getBrandFollowUpCategory(index) {
    return this.brandes.filter(n => n.idCat.includes(index));
  }

  getProductFollowUpBrandAndCategory(id, index) {
    return this.products.filter(n => n.idCat === id && n.idBra === this.brandesShow[index]);
  }

  changePageBrand(index) {
    this.pageBrand[index] = this.pageBrand[index] === 0 ? 1 : 0;
  }

  changeBrand(index, id) {
    this.pageBrand[index] = 0;
    this.brandesShow[index] = id;
  }

  checkChangePage(id, index) {
    const list = this.getProductFollowUpBrandAndCategory(id, index);
    return list.length > 3;
  }
}
