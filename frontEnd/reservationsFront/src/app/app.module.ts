
//Modules
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';

//Components
import { AppComponent } from './app.component';
import { ReservationsDashboardComponent } from './reservations/components/reservations-dashboard/reservations-dashboard.component';
import { DateListComponent } from './reservations/components/date-list/date-list.component';
import { ReservationFormComponent } from './reservations/components/reservation-form/reservation-form.component';
import { ReservationComponent } from './reservations/components/reservation/reservation.component';



@NgModule({
  declarations: [
    AppComponent,
    ReservationsDashboardComponent,
    ReservationComponent,
    ReservationFormComponent,
    DateListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    MatDialogModule,
  ],
  providers: [],
  entryComponents: [
    ReservationFormComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
