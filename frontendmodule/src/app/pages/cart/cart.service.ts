import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  // private sourceUrl = 'http://localhost:1999/cart';
  private sourceUrl = environment.apiUrl + 'cart';

  constructor(private http: HttpClient) {
  }

  addCart(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/add-cart', req).pipe(catchError(() => of({data: []})));
  }

  saveCart(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/save-cart', req).pipe(catchError(() => of({data: []})));
  }

  deleteCart(req): Observable<any> {
    const params = new HttpParams().append("id", req);
    return this.http.delete<any>(this.sourceUrl + '/delete-cart', {params}).pipe(catchError(() => of({data: []})));
  }

  getListCart(req): Observable<any> {
    const params = new HttpParams().append("idCli", req);
    return this.http.get<any>(this.sourceUrl + '/get-cart-by-idCli', {params}).pipe(catchError(() => of({data: []})));
  }
}
