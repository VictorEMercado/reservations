import { Component, Inject, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ReservationService } from '../../services/reservation.service';


@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent implements OnInit {
  reservation:Reservation = new Reservation;
  errors:{
    clientFullName:string|null,
    roomNumber:string|null,
    reservationDates:string|null
  } = {
    clientFullName:null,
    roomNumber:null,
    reservationDates:null
  };
  title="New Reservation"

  constructor(
    @Inject(MAT_DIALOG_DATA) public data:Reservation,
    private _reservationService:ReservationService,
    private dialogRef: MatDialogRef<ReservationFormComponent>,
  ) {

    this.reservation = data;
    if(this.reservation.id){ //If it´s not null
      this.title = "Update Reservation";
    }
  }

  ngOnInit(): void {
  }

  addReservation():void{
    this._reservationService.createReservation(this.reservation).subscribe({
      next: (response:{message:string,register:Reservation})=>{
        alert(response.message);
        this.dialogRef.close(response.register);
      },error: (error) =>{
        this.errors = error.error.errors;
      }
    })
  }

  updateReservation():void{
    this._reservationService.updateReservation(this.reservation).subscribe({
      next: (response:{message:string,register:Reservation})=>{
        alert(response.message);
        this.dialogRef.close(response.register);
      },error: (error) =>{
        this.errors = error.error.errors;
        console.log(this.errors);
      }
    })
  }

  closeModal(){
    this.dialogRef.close();
  }

}
