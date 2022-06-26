import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  // private sourceUrl = 'http://localhost:1999/product';
  private sourceUrl = environment.apiUrl + 'product';

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
    return this.http.get<any>(this.sourceUrl + '/load-all');
  }

  save(req, image): Observable<any> {
    const formData = new FormData();
    if (image && image.size) {
      formData.append('imageFile', image.originFileObj, image.name);
    }
    formData.append('productString', JSON.stringify(req));
    return this.http.post<any>(this.sourceUrl + '/save', formData, {observe: 'response'}).pipe(catchError(() => of({data: []})));
  }

  getProductDefaultForWelcome(listID): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/get-product-default-for-welcome', listID).pipe(catchError(() => of({data: []})));
  }

  loadCategoryAndBrand(): Observable<any> {
    return this.http.get<any>(this.sourceUrl + '/get-brand-category');
  }

  loadBrand(idCat): Observable<any> {
    const params = new HttpParams()
      .append('idCat', `${idCat}`);
    return this.http.get<any>(this.sourceUrl + '/get-brand', {params});
  }

  findById(id): Observable<any> {
    const params = new HttpParams()
      .append('id', `${id}`);
    return this.http.get<any>(this.sourceUrl + '/find-by-id', {params});
  }

  getAllComment(id): Observable<any> {
    const params = new HttpParams()
      .append('id', `${id}`);
    return this.http.get<any>(this.sourceUrl + '/get-all-comment', {params});
  }

  submitComment(comment): Observable<any> {
    return this.http.post<any>(this.sourceUrl + '/submit-comment', comment).pipe(catchError(() => of({data: []})));
  }
}
