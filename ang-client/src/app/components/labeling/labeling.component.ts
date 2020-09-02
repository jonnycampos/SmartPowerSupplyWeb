import { Component, OnInit } from '@angular/core';
import { SamplesService } from '../../services/samples.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ElectricalDateConfig } from '../../services/electrical-date-config';
import { saveAs } from "file-saver";


@Component({
  selector: 'app-labeling',
  templateUrl: './labeling.component.html',
  styleUrls: ['./labeling.component.css']
})
export class LabelingComponent implements OnInit {

  constructor(private samplesService:SamplesService) { }

  titlePickerInitial:String;
  titlePickerEnd:String;	
  searchInProgress:Boolean = false;
  exportInProgress:Boolean = false;

  public formGroupInitialDateTime = new FormGroup({
    date: new FormControl(null, [Validators.required])
  })

  public formGroupEndDateTime = new FormGroup({
    date: new FormControl(null, [Validators.required])
  })

  public getElectricalSamples() {
	var electricalDateConfig = new ElectricalDateConfig();
  	electricalDateConfig.initialTimeStr = new DatePipe('en').transform(this.formGroupInitialDateTime.get("date").value, 'yyyy-MM-dd HH:mm:ss');
    electricalDateConfig.endTimeStr = new DatePipe('en').transform(this.formGroupEndDateTime.get("date").value, 'yyyy-MM-dd HH:mm:ss');
	this.searchInProgress = true;
	this.samplesService.getSamples(electricalDateConfig)
        .subscribe( data => {
			this.samplesService.samplesData(data);
			this.searchInProgress = false;
        });
  }



  public exportTimeSeries() {
	this.exportInProgress = true;
	var electricalDateConfig = new ElectricalDateConfig();
  	electricalDateConfig.initialTimeStr = new DatePipe('en').transform(this.formGroupInitialDateTime.get("date").value, 'yyyy-MM-dd HH:mm:ss');
    electricalDateConfig.endTimeStr = new DatePipe('en').transform(this.formGroupEndDateTime.get("date").value, 'yyyy-MM-dd HH:mm:ss');


	this.samplesService.exportTimeSeries(electricalDateConfig).subscribe( 
		data => {
			console.log(data);
			this.exportInProgress = false;
			saveAs(data, "export_time.csv");
    	},
 		err => {
	       console.error(err);
    	});
    }


  ngOnInit(): void {
	this.titlePickerInitial = "Initial Time";
	this.titlePickerEnd = "End Time";
	this.searchInProgress = false;
  }

}
