import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Reservation } from '../../models/reservation';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
})
export class ReservationComponent implements OnInit {
  @Input() reservation:Reservation = new Reservation;
  @Output() editModalEvt: EventEmitter<Reservation> = new EventEmitter();
  constructor() { }

  ngOnInit(): void {
  }

  handleOpenModal(reservation:Reservation){
    this.editModalEvt.emit(reservation);
  }

}
