import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private route: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (state.url.includes('admin')) {
      const employee = sessionStorage.getItem('employee');
      if (employee) {
        return true;
      } else {
        this.route.navigate(['/pages_admin/login']);
        return false;
      }
    } else {
      const employee = sessionStorage.getItem('client');
      if (employee) {
        return true;
      } else {
        sessionStorage.setItem('url', state.url);
        this.route.navigate(['/pages/login']);
        return false;
      }
    }
  }

}
