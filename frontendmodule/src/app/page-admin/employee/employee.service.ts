import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  // private sourceUrl = 'http://localhost:1999/login';
  private sourceUrl = environment.apiUrl + 'login';

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
    return this.http.get<any>(`${this.sourceUrl}/pagination-admin`, {params})
      .pipe(catchError(() => of({data: []})));
  }

  save(req): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/save-user-admin', req).pipe(catchError(() => of({data: []})));
  }
}
