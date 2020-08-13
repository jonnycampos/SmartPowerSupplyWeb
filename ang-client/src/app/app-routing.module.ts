import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SimCoffeeTypesComponent } from './components/sim-coffee-types/sim-coffee-types.component';

const routes: Routes = [
    {path: 'simulation' , component: SimCoffeeTypesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
