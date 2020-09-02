import { Component, OnInit } from '@angular/core';
import { SamplesService } from '../../services/samples.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-label-list',
  templateUrl: './label-list.component.html',
  styleUrls: ['./label-list.component.css']
})
export class LabelListComponent implements OnInit {

  subscription: Subscription;
  interactionsToLabel:any[];
  labelsList;
  samplesList;
  labelInProgress:Boolean[];
  labelDone:Boolean[];
  predictionDone:Boolean[];
  predictionText:String[];


  constructor(private samplesService : SamplesService) { 
	this.interactionsToLabel = [];
	this.labelInProgress= [];
	this.labelDone= [];
	this.predictionDone = [];
	this.predictionText = [];

	this.subscription = this.samplesService.getSamplesData().subscribe(sampleList => {	
       for (var index in sampleList.sampleData.electricalInteractions) {
			this.labelInProgress.push(false);
			this.labelDone.push(false);
			this.predictionDone.push(false);
			this.predictionText.push("");
		}		
		this.interactionsToLabel = sampleList.sampleData.electricalInteractions;
	    this.samplesList = sampleList.sampleData.electricalSamples;
	});  
	
  }
  
  public performLabel(label, interaction, i) {
	console.log(label);
	console.log(interaction);
	this.labelInProgress[i] = true;
	this.samplesService.performLabel(interaction.start, interaction.end, label)
        .subscribe( data => {
			//Mark the card as labeled
			this.labelInProgress[i] = false;
			this.labelDone[i] = true;
        });
  }



  public predict(interaction,i) {
	console.log("Predicting:"+interaction);
	this.labelInProgress[i] = true;
	this.samplesService.predictLabel(interaction.start, interaction.end)
        .subscribe( data => {
			//Mark the card as labeled
			this.labelInProgress[i] = false;
			this.predictionDone[i] = true;
			console.log("Prediction:" + data);
			this.predictionText[i] = data['result'][0];		
        });
  }


   public shift(interaction, timePoint:String, direction:String) {
      var movementParams = {interaction:interaction, timePoint:timePoint, direction:direction};
      
      //We add the change in the service so the annotation moves visually
      this.samplesService.moveData(movementParams);
      
      //But we need to update this component as well ... Change interaction time 
	  //Look for the interaction
	  var i = 0;
      var date;
	  if (timePoint == 'start') {
        while (this.interactionsToLabel[i].start !=  interaction.start) {
		    i++;
	    }
        date = interaction.start;
	  } else if (timePoint == 'end') {
        while (this.interactionsToLabel[i].end !=  interaction.end) {
		    i++;            
	    }
        date = interaction.end;
      }

      //Look for the sample
      var s = 0;
      while (this.samplesList[s].time != date) {
		s++;
	  }

      if (timePoint == 'start') {
	     if (direction == 'left' && s>0) {
		    this.interactionsToLabel[i].start = this.samplesList[s-1].time;
	     } else if (direction == 'right' && s<this.samplesList.length-1) {
		    this.interactionsToLabel[i].start = this.samplesList[s+1].time;
	     }
	  } else if (timePoint == 'end' && s>0) {
	     if (direction == 'left') {
		    this.interactionsToLabel[i].end = this.samplesList[s-1].time;		    
	     } else if (direction == 'right' && s<this.samplesList.length-1) {
		    this.interactionsToLabel[i].end = this.samplesList[s+1].time;
	     }		
	  }      

      //Move annotation
   }

  ngOnInit(): void {
	this.samplesService.getLabels().subscribe( data => {
			//Mark the card as labeled
			this.labelsList = data;
			
     });
  }

}
