import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DialogData} from './dialog-data.model' ;

@Component({
    selector: 'app-custom-dialog',
    templateUrl: './custom-dialog.component.html'
})
export class CustomDialogComponent {

   message: string;

    constructor(
      public dialogRef: MatDialogRef<CustomDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DialogData) {
        this.message = data.message;
      }

    aceptar() {
        this.dialogRef.close();
    }
}
