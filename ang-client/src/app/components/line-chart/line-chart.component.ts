import { Component, OnInit, ViewChild} from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, BaseChartDirective, Label } from 'ng2-charts';

import { Subscription } from 'rxjs';
import { SimulatorService } from '../../services/simulator.service';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit {

  
  subscription: Subscription;
  public lineChartData: ChartDataSets[] = [
       { data: [], label: 'Amperage', lineTension: 0 },
       { data: [], label: 'Voltage', lineTension: 0 }
     ];

  public lineChartLabels: Label[] = [];

  public lineChartOptions: (ChartOptions) = {
    responsive: true,
    maintainAspectRatio: false
  };

  public lineChartColors: Color[] = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';


  @ViewChild(BaseChartDirective, { static: true }) chart: BaseChartDirective;

  constructor(private simulatorService : SimulatorService) { 
	//Waiting for the simulated data
	this.subscription = this.simulatorService.getSimulationData().subscribe(sampleList => {	
		this.lineChartData = this.dataForVisualization(sampleList.simulationData);
	    this.lineChartLabels = this.labelsForVisualization(sampleList.simulationData);
	});  

  }


  dataForVisualization(sampleList) {

     var samplesVisual: ChartDataSets[] = [
       { data: [], label: 'Amperage', lineTension: 0 },
       { data: [], label: 'Voltage', lineTension: 0 }
     ];

     var amp = [];
     var vol = [];
     for (var item of sampleList) {
		amp.push(item.ma);
		vol.push(item.v);
	 }

     samplesVisual[0].data = amp;
     samplesVisual[1].data = vol;
	 return samplesVisual;
  }

  labelsForVisualization(sampleList) {
	
	var labels = [];
	for (var item of sampleList) {
	  labels.push(item.time);
	}
	var sampleLabels: Label[] = labels;
	return labels;
  }



  ngOnInit() {
  }

}
