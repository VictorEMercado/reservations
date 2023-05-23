import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-date-list',
  templateUrl: './date-list.component.html',
  styleUrls: ['./date-list.component.css']
})
export class DateListComponent implements OnInit {
  @Input() reservationDates:Date[] = []
  constructor() { }

  ngOnInit(): void {
  }

  deleteDate(index:number):void{
    this.reservationDates.splice(index,1);
  }

  addDate(){
    this.reservationDates.push(new Date());
  }

}
