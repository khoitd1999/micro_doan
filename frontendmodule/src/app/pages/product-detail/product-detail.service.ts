import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginAdminService {
  // private sourceUrl = 'http://localhost:1999/welcome';
  private sourceUrl = environment.apiUrl + 'welcome';

  constructor(private http: HttpClient) {
  }


}
