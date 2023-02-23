import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DesiredItem } from './desireditem';
import { environment } from './environments/environments';

@Injectable({
  providedIn: 'root'
})
/**
 * Service that manages Http requests to REST item stock check API
 */
export class DesiredItemService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getDesiredItems(): Observable<DesiredItem[]> {
    return this.http.get<DesiredItem[]>(`${this.apiServerUrl}/items/all`);
  }

  public addDesiredItem(desireditem: DesiredItem): Observable<DesiredItem> {
    return this.http.post<DesiredItem>(`${this.apiServerUrl}/items/add`, desireditem);
  }

  public updateDesiredItem(desireditem: DesiredItem): Observable<DesiredItem> {
    return this.http.put<DesiredItem>(`${this.apiServerUrl}/items/update`, desireditem);
  }

  public updateDesiredItemStock(desireditems: DesiredItem[]): Observable<DesiredItem[]> {
    return this.http.put<DesiredItem[]>(`${this.apiServerUrl}/items/updatestock`, desireditems);
  }

  public deleteDesiredItem(itemID: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/items/delete/${itemID}`);
  }
}
