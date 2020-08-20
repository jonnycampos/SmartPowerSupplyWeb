import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SamplesService } from '../../services/samples.service';


@Component({
  selector: 'app-datetimepicker',
  templateUrl: './datetimepicker.component.html',
  styleUrls: ['./datetimepicker.component.css']
})
export class DatetimepickerComponent implements OnInit {

  public formGroupDateTime = new FormGroup({
    date: new FormControl(null, [Validators.required]),
    time: new FormControl(null, [Validators.required])
  })

  @Input() formGroup: FormGroup;

  constructor(private samplesService:SamplesService) { }
  
  public dateChanged() {
	console.log(this.getDate());
  }	

  public getDate() {
		return this.formGroup.get('date').value?.toLocaleString();
  }
  
  public getTime() {
		return this.formGroup.get('time').value?.toLocaleString();
  }


  @Input() title:string;
  
  ngOnInit(): void {
  }

}
