import { Component, OnInit } from '@angular/core';
import { SimulatorConfig } from '../../services/simulator-config';
import { SimulatorService } from '../../services/simulator.service';

@Component({
  selector: 'app-sim-coffee-types',
  templateUrl: './sim-coffee-types.component.html',
  styleUrls: ['./sim-coffee-types.component.css']
})
export class SimCoffeeTypesComponent implements OnInit {

  constructor(private simulatorService:SimulatorService) { }
  
  public coffeeTypes = [
    { name: 'Capuccino', color: 'primary', coffeeCode:"capuccino", simType:"coffee" },
    { name: 'Machiato', color: 'primary', coffeeCode:"macchiato", simType:"coffee"},
    { name: 'Espresso', color: 'primary', coffeeCode:"espresso", simType:"coffee" }
  ];


  public idleOptions = [
	{ name: '1s', color: 'accent', seconds:"1", simType:"idle" },
    { name: '30s', color: 'accent', seconds:"30", simType:"idle" },
    { name: '1 Minute', color: 'accent', seconds:"60", simType:"idle"},
    { name: '5 minutes', color: 'accent', seconds:"300", simType:"idle"}
  ];


  index = 0;

  public simulationOptions = [];

  public simulationInProgress = false;

  public chipSelect(chip: any) {

	chip.index = this.index;
	this.simulationOptions.push(chip);
	this.index = this.index + 1;
  }

  public chipRemove(chip: any) {

    this.simulationOptions.forEach( (item, index) => {
     if(item.index === chip.index) this.simulationOptions.splice(index,1);
    });
	this.index = this.index + 1;
  }


  public launchSimulation() {
	var simulationConfigList = [];
	this.simulationOptions.forEach( (chip, index) => {
      var simulatorConfig = new SimulatorConfig();
	  simulatorConfig.type = chip.simType;
      if (chip.simType == 'idle') {
	    simulatorConfig.seconds = chip.seconds;
	  } else if (chip.simType == 'coffee') {
		simulatorConfig.coffeeType = chip.coffeeCode;
	  } else {
		console.error("Chip type invalid " + chip.simType);
	  }
      simulationConfigList.push(simulatorConfig);
    });
	this.simulationInProgress = true;
	this.simulatorService.launchSimulator(simulationConfigList)
        .subscribe( data => {
	      this.simulationOptions = [];
		  //this.samples = this.dataForVisualization(data);
          //Send the data so other components can consume it
	      this.simulatorService.simulationData(data);
		  this.simulationInProgress = false;
        });
  }

  dataForVisualization(sampleList) {
     var samplesVisual = [];
     var amp = [];
     var vol = [];
     for (var item of sampleList) {
		amp.push(item.ma);
		vol.push(item.v);
	 }
     var amperage = {data:[],label:''};
     amperage.data = amp;
     amperage.label = 'Amperage'; 
	 samplesVisual.push(amperage);
     var voltage = {data:[],label:''};
     amperage.data = vol;
     amperage.label = 'Vol'; 
	 samplesVisual.push(voltage);    
	 return samplesVisual;
  }



  ngOnInit(): void {
	
  }

}
