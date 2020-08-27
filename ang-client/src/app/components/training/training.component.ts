import { Component, OnInit, ViewChild } from '@angular/core';
import { SamplesService } from '../../services/samples.service';
import { Subscription } from 'rxjs';
import  {SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { saveAs } from "file-saver";


export interface InteractionLabeled {
  id: string;
  startFormatted: string;
  endFormatted: string;
  label: string;
}

@Component({
  selector: 'app-training',
  templateUrl: './training.component.html',
  styleUrls: ['./training.component.css']
})
export class TrainingComponent implements OnInit {

  constructor(private samplesService:SamplesService) { }
  
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  //List of all labeled interactions
  interactions:MatTableDataSource<InteractionLabeled> = new MatTableDataSource<InteractionLabeled>();
  
  //Selected items from the table
  selection = new SelectionModel(true, []);

  displayedColumns: string[] = ['select','id','startFormatted', 'endFormatted', 'label'];

  subscription: Subscription;

  inprogress: Boolean;

  ngOnInit(): void {
	  this.inprogress = false;
	  this.subscription = this.samplesService.getInteractionsLabeled().subscribe(data => {	
 	     this.interactions.data = data as InteractionLabeled[];
		 this.interactions.sort = this.sort;
      });  
  }

 /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.interactions.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.interactions.data.forEach(row => this.selection.select(row));
  }

  public export() {
	this.inprogress = true;
	var idList:String[] = [];
	for (var item of this.selection.selected) {
		idList.push(item.id);
	}
	this.samplesService.exportLabels(idList).subscribe( 
		data => {
			console.log(data);
			this.inprogress = false;
			saveAs(data, "export_labels.csv");
    	},
 		err => {
	       console.error(err);
    	});
    }

}
