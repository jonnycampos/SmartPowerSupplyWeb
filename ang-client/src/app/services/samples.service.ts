import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class SamplesService {

  constructor(private http:HttpClient) { }

  private subject = new Subject<any>();


  public samplesData(data: any) {
      this.subject.next({ sampleData: data });
  }
  
  public getSamplesData(): Observable<any> {
     return this.subject.asObservable();
  }




  private serviceUrl = 'http://localhost:8080/';

  public launchSimulator(configList) {
	var url = this.serviceUrl + 'simulate/chain';
    return this.http.post(url, configList);
  }

  public getSamples(dateElectricalConfig) {
	var url = this.serviceUrl + 'electricaldata/get';
    return this.http.post(url, dateElectricalConfig);
  }


  public getLabels() {
	var url = this.serviceUrl + 'labeling/labels';
    return this.http.post(url, {});
  }


  public performLabel(start, end, label) {
	var url = this.serviceUrl + 'labeling/addlabel';
	var indata = {'start': start, 'end': end, 'label':label };
    return this.http.post(url, indata);
  }

}
