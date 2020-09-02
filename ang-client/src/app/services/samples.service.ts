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

  private subjectMoveTime = new Subject<any>(); 

  public moveData(data: any) {
      this.subjectMoveTime.next(data);
  }

  public getMovementData(): Observable<any> {
     return this.subjectMoveTime.asObservable();
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

  public predictLabel(start, end) {
	var url = this.serviceUrl + 'labeling/predict';
	var indata = {'initialTime': start, 'endTime': end};
    return this.http.post(url, indata);
  }



  public getInteractionsLabeled() {
	var url = this.serviceUrl + 'labeling/interactionlabeled';
    return this.http.post(url, {});
  }


  public exportLabels(idList) {
	var url = this.serviceUrl + 'labeling/export';
    return this.http.post(url, idList,{ 
		responseType: 'blob',
		headers: new HttpHeaders().append("Content-Type", "application/json") 
	});
  }


  public exportTimeSeries(dateElectricalConfig) {
	var url = this.serviceUrl + 'electricaldata/export';
    return this.http.post(url, dateElectricalConfig,{ 
		responseType: 'blob',
		headers: new HttpHeaders().append("Content-Type", "application/json") 
	});
  }

  public moveInteraction(interaction, timePoint,direction) {
	return {'interaction':interaction,
	        'timePoint':timePoint,
            'direction':direction};
  }

}
