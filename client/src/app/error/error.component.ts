import { Component, OnInit } from '@angular/core';
import { ErrorService } from '../services/errorService';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  constructor(private error:ErrorService) { }

  message:string;
  ngOnInit() {
    this.message = this.error.lastError;
  }

}
