import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SearchBillService {
  // private sourceUrl = 'http://localhost:1999/bill';
  private sourceUrl = environment.apiUrl + 'bill';

  constructor(private http: HttpClient) {
  }

  loadAllData(pageNo: number, pageSize: number, searchTerm) {
    const params = new HttpParams()
      .append('page', `${pageNo}`)
      .append('size', `${pageSize}`)
      .append('sort', 'date,desc')
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(this.sourceUrl + '/pagination', {params}).pipe(catchError(() => of({data: []})));
  }

  loadAllDetail(
    pageNo: number,
    pageSize: number,
    searchTerm
  ): Observable<any> {
    const params = new HttpParams()
      .append('page', `${pageNo}`)
      .append('size', `${pageSize}`)
      .append('sort', 'date,desc')
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(this.sourceUrl + '/pagination-detail', {params}).pipe(catchError(() => of({data: []})));
  }

  cancelOrder(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/cancel-order', req).pipe(catchError(() => of({data: []})));
  }
}
