import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  // private sourceUrl = 'http://localhost:1999/login';
  private sourceUrl = environment.apiUrl + 'login';

  constructor(private http: HttpClient) {
  }

  checkLogin(phone, password): Observable<any> {
    const params = new HttpParams()
      .append('phone', `${phone}`)
      .append('password', `${password}`);
    return this.http.post<any>(this.sourceUrl + '/check-login', null, {params}).pipe(catchError(() => of({data: []})));
  }

  registerClient(client): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/register', client).pipe(catchError(() => of({data: []})));
  }
}
