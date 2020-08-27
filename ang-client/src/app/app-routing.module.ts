import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SimCoffeeTypesComponent } from './components/sim-coffee-types/sim-coffee-types.component';
import { LabelingComponent } from './components/labeling/labeling.component';
import { TrainingComponent } from './components/training/training.component';


const routes: Routes = [
    {path: 'simulation' , component: SimCoffeeTypesComponent},
    {path: 'labeling' , component: LabelingComponent},
    {path: 'training' , component: TrainingComponent} 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
