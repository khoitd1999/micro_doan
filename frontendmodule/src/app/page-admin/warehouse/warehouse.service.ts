import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  // private sourceUrl = 'http://localhost:1999/warehouse';
  private sourceUrl = environment.apiUrl + 'warehouse';

  constructor(private http: HttpClient) {
  }

  loadAllData(
    pageNo: number,
    pageSize: number,
    searchTerm
  ): Observable<any> {
    const params = new HttpParams()
      .append('page', `${pageNo}`)
      .append('size', `${pageSize}`)
      .append('sort', 'name,desc')
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(`${this.sourceUrl}/pagination`, {params})
      .pipe(catchError(() => of({data: []})));
  }

  loadAll(): Observable<any> {
    return this.http.get<any>(this.sourceUrl + '/load-all').pipe(catchError(() => of({data: []})));
  }

  checkBeforeDelete(req): Observable<any> {
    const params = new HttpParams()
      .append('id', `${req}`);
    return this.http.get<any>(this.sourceUrl + '/check-before-delete', {params});
  }

  save(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/save', req).pipe(catchError(() => of({data: []})));
  }

  getAllArea(searchTerm): Observable<any> {
    const params = new HttpParams()
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(this.sourceUrl + '/get-address', {params}).pipe(catchError(() => of({data: []})));
  }

  loadFunctionForAccount(searchTerm): Observable<any> {
    const params = new HttpParams()
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(this.sourceUrl + '/load-function-for-account', {params});
  }

  loadWarehouse(searchTerm): Observable<any> {
    const params = new HttpParams()
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(this.sourceUrl + '/load-warehouse', {params});
  }

  calculateFee(searchTerm): Observable<any> {
    const params = new HttpParams()
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(this.sourceUrl + '/calculate-fee', {params});
  }
}
