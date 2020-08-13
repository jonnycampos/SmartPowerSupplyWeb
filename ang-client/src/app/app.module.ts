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
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SimCoffeeTypesComponent } from './components/sim-coffee-types/sim-coffee-types.component';
import { HttpClientModule } from '@angular/common/http';
import { ChartsModule } from 'ng2-charts';
import { LineChartComponent } from './components/line-chart/line-chart.component';
import { LabelingComponent } from './components/labeling/labeling.component';


@NgModule({
  declarations: [
    AppComponent,
    SimCoffeeTypesComponent,
    LineChartComponent,
    LabelingComponent
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
	HttpClientModule,
	ChartsModule,
	MatMenuModule,
	MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
