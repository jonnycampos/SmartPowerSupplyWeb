import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatGridListModule} from '@angular/material/grid-list';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SimCoffeeTypesComponent } from './components/sim-coffee-types/sim-coffee-types.component';
import { HttpClientModule } from '@angular/common/http';
import { ChartsModule } from 'ng2-charts';
import { LineChartComponent } from './components/line-chart/line-chart.component';
import { LabelingComponent } from './components/labeling/labeling.component';
import { NgxMatDatetimePickerModule, NgxMatTimepickerModule, NgxMatNativeDateModule } from '@angular-material-components/datetime-picker';
import { DatetimepickerComponent } from './components/datetimepicker/datetimepicker.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LabelListComponent } from './components/label-list/label-list.component';



@NgModule({
  declarations: [
    AppComponent,
    SimCoffeeTypesComponent,
    LineChartComponent,
    LabelingComponent,
    DatetimepickerComponent,
    LabelListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
	MatCardModule,
	MatChipsModule,
	MatSidenavModule,
	MatButtonModule,
	MatToolbarModule,
	MatDividerModule,
	MatProgressSpinnerModule,
	MatFormFieldModule,
	MatDatepickerModule,
	MatGridListModule,   
	MatSelectModule,
	MatInputModule,
	HttpClientModule,
	ChartsModule,
	MatMenuModule,
	MatIconModule,
	NgxMatDatetimePickerModule, 
	NgxMatTimepickerModule,
	NgxMatNativeDateModule,
	FlexLayoutModule,
	FormsModule,ReactiveFormsModule
  ],
  providers: [NgxMatNativeDateModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
