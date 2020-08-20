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

  constructor(private samplesService : SamplesService) { 
			this.interactionsToLabel = [];
			this.labelInProgress= [];
			this.labelDone= [];

	this.subscription = this.samplesService.getSamplesData().subscribe(sampleList => {	
       for (var index in sampleList.sampleData.electricalInteractions) {
			this.labelInProgress.push(false);
			this.labelDone.push(false);
		}		
		this.interactionsToLabel = sampleList.sampleData.electricalInteractions;
	});  
	
  }
  
  public performLabel(event, interaction, i) {
	console.log(event);
	console.log(interaction);
	this.labelInProgress[i] = true;
	this.samplesService.performLabel(interaction.start, interaction.end, event.value)
        .subscribe( data => {
			//Mark the card as labeled
			this.labelInProgress[i] = false;
			this.labelDone[i] = true;
        });
  }

  ngOnInit(): void {
	this.samplesService.getLabels().subscribe( data => {
			//Mark the card as labeled
			this.labelsList = data;
			
     });
  }

}
