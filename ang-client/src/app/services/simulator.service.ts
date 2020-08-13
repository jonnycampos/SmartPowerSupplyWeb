import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class SimulatorService {

  constructor(private http:HttpClient) {}
  
  private subject = new Subject<any>();

  private serviceUrl = 'http://localhost:8080/simulate/chain';
  
  public simulationData(data: any) {
      this.subject.next({ simulationData: data });
  }
  
  public getSimulationData(): Observable<any> {
     return this.subject.asObservable();
  }
  
  public launchSimulator(configList) {
    return this.http.post(this.serviceUrl, configList);
  }
}
