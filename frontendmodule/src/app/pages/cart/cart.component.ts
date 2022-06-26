import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "./cart.service";

@Component({
  selector: 'app-welcome',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit, OnDestroy {
  active: any;
  carts: any;
  client: any;
  constructor(
    private cartService: CartService
  ) {

  }

  ngOnInit() {
    this.carts = [];
    this.client = JSON.parse(sessionStorage.getItem('client'));
    // if (sessionStorage.getItem('cart')) {
    //   this.carts = JSON.parse(sessionStorage.getItem('cart'));
    // }
    this.getListCart();
  }

  getListCart() {
    this.cartService.getListCart(this.client.id).subscribe(res => {
      this.carts = res;
      for (let item of this.carts) {
        item.image = 'data:image/jpeg;base64,' + item.image;
      }
    });
  }

  ngOnDestroy(): void {

  }

  remove(i: number) {
    this.cartService.deleteCart(this.carts[i].id).subscribe(() => {
      this.carts.splice(i, 1);
    });
  }

  changeQuantity(i) {
    const t: any = document.getElementById('quantity' + i);
    this.carts[i].quantity = +t.value;
    this.carts[i].amount = this.carts[i].quantity * this.carts[i].price;
    this.cartService.saveCart(this.carts[i]).subscribe(res => {
      console.log(res);
    });
  }
}
