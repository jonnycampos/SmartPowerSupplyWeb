import { Component, OnInit, ViewChild} from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, BaseChartDirective, Label } from 'ng2-charts';

import { Subscription } from 'rxjs';
import { SamplesService } from '../../services/samples.service';
import { formatDate} from '@angular/common';
import * as pluginAnnotations from 'chartjs-plugin-annotation';

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
  public lineChartPlugins = [pluginAnnotations];


  public lineChartOptions: (ChartOptions & { annotation: any }) = {
    responsive: true,
    maintainAspectRatio: false,
    annotation: {
      annotations: [
      
      ],
    }
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

  constructor(private samplesService : SamplesService) { 
	//Waiting for the simulated data
	this.subscription = this.samplesService.getSamplesData().subscribe(sampleList => {	
		this.lineChartData = this.dataForVisualization(sampleList.sampleData.electricalSamples);
	    this.lineChartLabels = this.labelsForVisualization(sampleList.sampleData.electricalSamples);
        this.lineChartOptions.annotation.annotations = this.addAnnotations(sampleList.sampleData.electricalInteractions);
		//Welcome to angular workarounds!!!  
		this.chart.chart.options = this.lineChartOptions;
		this.chart.chart.update();
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
	  labels.push(formatDate(item.time, 'dd/MM/yyyy HH:mm:ss.SSS','en-US'));
	}
	return labels;
  }

  addAnnotations(interactionList) {
     var annotations = [];
     var number = 1;
     for (var interaction of interactionList) {
	    var startitem =  {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: formatDate(interaction.start, 'dd/MM/yyyy HH:mm:ss.SSS','en-US'),
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'orange',
            content: 'Start : ' + number.toString()
          }
        };
		annotations.push(startitem);
		
	    var enditem =  {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: formatDate(interaction.end, 'dd/MM/yyyy HH:mm:ss.SSS','en-US'),
          borderColor: 'red',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'red',
            content: 'End : ' + number
          }
        };
		annotations.push(enditem);
		number = number + 1;
		
	 }
     return annotations;
  }

  ngOnInit() {
  }

}
