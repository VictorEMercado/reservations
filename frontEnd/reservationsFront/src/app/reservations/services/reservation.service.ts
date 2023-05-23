import { Injectable } from '@angular/core';
import { backEndURL } from 'src/app/generalServices/backEndURL';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private url = backEndURL;

  constructor(
    private http: HttpClient
  ) { }

  getReservations():Observable<any>{
    return this.http.get(`${this.url}/reservations`);
  }

  createReservation(reservation:Reservation):Observable<any>{
    return this.http.post(`${this.url}/reservation`,reservation);
  }

  updateReservation(reservation:Reservation):Observable<any>{
    return this.http.put(`${this.url}/reservation/${reservation.id}`,reservation);
  }
}
