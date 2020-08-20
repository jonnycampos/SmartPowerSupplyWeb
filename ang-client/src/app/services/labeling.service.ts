import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class LabelingService {

  constructor(private http:HttpClient) {}

  private subject = new Subject<any>();

  private serviceUrl = 'http://localhost:8080/electricaldata/get';

  public sampleData(data: any) {
      this.subject.next({ sampleData: data });
  }
  
  public getSampleDate(): Observable<any> {
     return this.subject.asObservable();
  }

  public launchSimulator(dateElectricalConfig) {
    return this.http.post(this.serviceUrl, dateElectricalConfig);
  }
  
}
