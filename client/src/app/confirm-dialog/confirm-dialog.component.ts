import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

export interface ConfirmDialgData{
  buttons?:'yesno'|'okcancel'|'okonly',
  message:string,
  icon?:string
}
@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.css']
})
export class ConfirmDialogComponent<T,R> implements OnInit {
  message:string;
  icon:string;
  buttons={}
  constructor(
    private dialogRef: MatDialogRef<T,R>,
    @Inject(MAT_DIALOG_DATA) 
    private data: ConfirmDialgData) { 
      this.message= data.message;
      this.icon = data.icon;
      this.buttons = data.buttons !=null?data.buttons:'yesno';
    }

    accept(value){
      this.dialogRef.close(value);
    }
  ngOnInit() {
  }

}
