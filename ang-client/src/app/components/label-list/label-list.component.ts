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

  ngOnInit(): void {
	this.samplesService.getLabels().subscribe( data => {
			//Mark the card as labeled
			this.labelsList = data;
			
     });
  }

}
