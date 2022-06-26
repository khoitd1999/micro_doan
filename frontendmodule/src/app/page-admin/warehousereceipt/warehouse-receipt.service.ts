import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WarehouseReceiptService {
  // private sourceUrl = 'http://localhost:1999/warehousereceipt';
  private sourceUrl = environment.apiUrl + 'warehousereceipt';

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
      .append('sort', 'date,desc')
      .append('searchTerm', `${searchTerm}`);
    return this.http.get<any>(`${this.sourceUrl}/pagination`, {params})
      .pipe(catchError(() => of({data: []})));
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

  save(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/save', req, {observe: 'response'});
  }

  createExport(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/create-export', req, {observe: 'response'});
  }

  loadEmployeeAndWareHouse(): Observable<any> {
    return this.http.get<any>(this.sourceUrl + '/get-employee-warehouse');
  }

  findById(id: any): Observable<any> {
    return this.http
      .get<any>(`${this.sourceUrl}/${id}`, { observe: 'response' });
  }
}
