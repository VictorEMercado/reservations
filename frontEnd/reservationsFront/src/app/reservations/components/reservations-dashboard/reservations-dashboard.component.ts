import { Component, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation';
import { ReservationService } from '../../services/reservation.service';
import { MatDialog } from '@angular/material/dialog';
import { ReservationFormComponent } from '../reservation-form/reservation-form.component';

@Component({
  selector: 'app-reservations-dashboard',
  templateUrl: './reservations-dashboard.component.html'
})
export class ReservationsDashboardComponent implements OnInit {
  reservations:Reservation[] = [];
  constructor(
    private _reservationService:ReservationService,
    public dialog: MatDialog,
    ) { }

  ngOnInit(): void {
    this.getReservations();
  }

  getReservations():void{
    this._reservationService.getReservations().subscribe({
      next: response=>{
        this.reservations = response.registers;
      },
      error: (error) =>{
        alert("Problemas conectando al servidor");
      }
    });
  }

  openModal(reservation:Reservation = new Reservation){
    //We don´t want to change our original object until the update is done
    let reservation_copy = JSON.parse(JSON.stringify(reservation));
    const dialogRef = this.dialog.open(ReservationFormComponent,{
      width:'1200px',
      height: '600px',
      data: reservation_copy
    });

    dialogRef.afterClosed().subscribe((createdReservation:Reservation) => {
      if(createdReservation==undefined){
        return;
      }
      if(reservation.id==null){
        this.reservations.push(createdReservation);
      }else{
        this.reservations[this.reservations.indexOf(reservation)] = createdReservation;
      }
    });
  }




}
