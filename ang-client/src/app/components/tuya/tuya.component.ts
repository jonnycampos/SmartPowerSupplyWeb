import { Component, OnInit } from '@angular/core';
import { SamplesService } from '../../services/samples.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ElectricalDateConfig } from '../../services/electrical-date-config';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-tuya',
  templateUrl: './tuya.component.html',
  styleUrls: ['./tuya.component.css']
})
export class TuyaComponent implements OnInit {

  titlePickerInitial:String;
  titlePickerEnd:String;	
  searchInProgress:Boolean = false;

  constructor(private samplesService:SamplesService) { }

  public formGroupInitialDateTime = new FormGroup({
    date: new FormControl(null, [Validators.required])
  })

  public formGroupEndDateTime = new FormGroup({
    date: new FormControl(null, [Validators.required])
  })

  public getDataFromDevice() {
	var electricalDateConfig = new ElectricalDateConfig();
  	electricalDateConfig.initialTimeStr = new DatePipe('en').transform(this.formGroupInitialDateTime.get("date").value, 'yyyy-MM-dd HH:mm:ss');
    electricalDateConfig.endTimeStr = new DatePipe('en').transform(this.formGroupEndDateTime.get("date").value, 'yyyy-MM-dd HH:mm:ss');
	this.searchInProgress = true;
	this.samplesService.getDataFromDevice(electricalDateConfig)
        .subscribe( data => {
			this.samplesService.samplesData(data);
			this.searchInProgress = false;
        });
  }


  ngOnInit(): void {
	this.titlePickerInitial = "Initial Time";
	this.titlePickerEnd = "End Time";
	this.searchInProgress = false;
  }

}
