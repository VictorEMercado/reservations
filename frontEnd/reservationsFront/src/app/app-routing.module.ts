import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReservationsDashboardComponent } from './reservations/components/reservations-dashboard/reservations-dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: ReservationsDashboardComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
