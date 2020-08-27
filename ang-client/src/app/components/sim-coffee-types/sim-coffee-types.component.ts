import { Component, OnInit } from '@angular/core';
import { SimulatorConfig } from '../../services/simulator-config';
import { SamplesService } from '../../services/samples.service';

@Component({
  selector: 'app-sim-coffee-types',
  templateUrl: './sim-coffee-types.component.html',
  styleUrls: ['./sim-coffee-types.component.css']
})
export class SimCoffeeTypesComponent implements OnInit {

  constructor(private samplesService:SamplesService) { }
  
  public coffeeTypes = [
    { name: 'Capuccino', color: 'primary', coffeeCode:"Capuccino", simType:"coffee" },
    { name: 'Machiato', color: 'primary', coffeeCode:"Macchiato", simType:"coffee"},
    { name: 'Espresso', color: 'primary', coffeeCode:"Espresso", simType:"coffee" },
    { name: 'Capuccino Decaf', color: 'primary', coffeeCode:"CapuccinoDecaf", simType:"coffee" },
    { name: 'Espresso Decaf', color: 'primary', coffeeCode:"EspressoDecaf", simType:"coffee" },

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

  public saveLabel:boolean = false;

  public chipSelect(chip: any) {
    var chipToInsert = chip;
	chipToInsert.index = this.index;
	this.simulationOptions.push(chipToInsert);
	this.index = this.index + 1;
  }

  public chipRemove(chip: any) {

    this.simulationOptions.forEach( (item, index) => {
     if(item.index === chip.index) this.simulationOptions.splice(index,1);
    });
	this.index = this.index + 1;
  }

   public changeValue() {
      this.saveLabel = !this.saveLabel;
   }

  
  public randomCoffeeSelection() {
	this.simulationOptions = [];
	for (let i = 0; i < 100; i++) {
		//Choose the 1 second chip
		var chipTime:any = this.idleOptions[0];
		chipTime.index = this.index;
		this.simulationOptions.push(chipTime);
		this.index = this.index + 1;
		
		//Choose randomly one of coffee types
		var chipCoffee:any = this.coffeeTypes[this.randomIntFromInterval(0,this.coffeeTypes.length -1)];
		chipCoffee.index = this.index;
		this.simulationOptions.push(chipCoffee);
		this.index = this.index + 1;

	}
  }

	private randomIntFromInterval(min:number, max:number) { // min and max included 
	  return Math.floor(Math.random() * (max - min + 1) + min);
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
	  simulatorConfig.saveLabel = this.saveLabel;
      simulationConfigList.push(simulatorConfig);
    });
	
	if (simulationConfigList.length > 0) {
		this.simulationInProgress = true;
		this.samplesService.launchSimulator(simulationConfigList)
	        .subscribe( data => {
		      this.simulationOptions = [];
			  //this.samples = this.dataForVisualization(data);
	          //Send the data so other components can consume it
		      this.samplesService.samplesData(data);
			  this.simulationInProgress = false;
	        });
	}
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
