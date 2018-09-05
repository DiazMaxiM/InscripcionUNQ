import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DialogData} from './dialog-data.model' ;
import { RegistrationIntention } from './registration-intention.model'
import { Interval } from './interval.model'

 @Component({
     selector: 'app-custom-dialog-subject',
     templateUrl: './custom-dialog-subject.component.html'
 })
 export class CustomDialogSubjectComponent {
    subject: any;
    commissions: any;
    commissionCurrent: any;
    selectedTimes: any;
    registrationIntention: RegistrationIntention;

    constructor(
       public dialogRef: MatDialogRef<CustomDialogSubjectComponent>,
       @Inject(MAT_DIALOG_DATA) public data: DialogData) {
         this.subject = data.subject;
         this.commissions = this.subject.commissionsJson;
     }

     getTimesForCommission(idCommission){
       this.selectedTimes = this.getCommission(idCommission).intervalJson;
       this.commissionCurrent = idCommission;
     }

     getCommission(idCommission){
      let commission = (this.commissions.filter(c => c.id == idCommission))[0];
      return commission;
     }

     createDate(time){
       let date = new Date();
       date.setHours(time.hour);
       date.setMinutes(time.minute);
       return date;
     }

     createInterval(time){
       let start = this.createDate(time.startDate);
       let end = this.createDate(time.endDate);

       let interval = new Interval(time.day, start, end);
       return interval;
     }

     getIntervalsOfCommission(idCommission){
       let times = this.getCommission(idCommission).intervalJson;
       let intervals;

       for (let time of times) {
         let interval = this.createInterval(time);
           intervals.push(interval);
         }
         this.registrationIntention.addInterval(intervals);
     }

    commissionOverlap(idCommission){

    }

    accept() {
       /*
       if(!this.commissionOverlap(this.commissionCurrent)){

        }
         this.registrationIntention.addInterval(intervals);
       }
       */
       this.dialogRef.close();
     }
 }
