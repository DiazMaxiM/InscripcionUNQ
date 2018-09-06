import {Component, Inject, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DialogData} from './dialog-data.model' ;
import { RegistrationIntention } from './registration-intention.model';
import { Interval } from './interval.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

 @Component({
     selector: 'app-custom-dialog-subject',
     templateUrl: './custom-dialog-subject.component.html'
 })
 export class CustomDialogSubjectComponent implements OnInit{
    subject: any;
    commissions: any;
    commissionCurrent: any;
    selectedTimes: any;
    registrationIntention: RegistrationIntention;
    form: FormGroup;

    constructor(
       public dialogRef: MatDialogRef<CustomDialogSubjectComponent>,
      private fb: FormBuilder,
       @Inject(MAT_DIALOG_DATA) public data: DialogData) {
         this.subject = data.subject;
         this.commissions = this.subject.commissionsJson;
         //this.registrationIntention = new RegistrationIntention(idUsuario);
     }

     ngOnInit() {
        this.form = this.fb.group({
            commissionCurrent: this.commissionCurrent
        });
    }

     getCommission(idCommission){
      let commission = (this.commissions.filter(c => c.id == idCommission))[0];
      return commission;
     }

     getTimesForCommission(idCommission){
       //verificar si minutes tiene una sola cifra, si es true agregar un cero a la izq
       this.selectedTimes = this.getCommission(idCommission).intervalJson;
       this.commissionCurrent = idCommission;
       return this.selectedTimes;
     }

     getIntervalsOfCommission(idCommission){
       var times = this.getCommission(idCommission).intervalJson;
       var intervals = [];

       for(let time of times) {
           var interval = this.createInterval(time);
           intervals.push(interval);
         }
         this.registrationIntention.addInterval(intervals);
     }

    commissionOverlap(idCommission){
      let registrationCurrent = this.registrationIntention.getIntention();
      let timesCurrent = this.selectedTimes;
      let overlap = false;

      for(let r of registrationCurrent){
        for(let t of timesCurrent){
          overlap = overlap || t.overlapInterval(r);
        }
      }
      return overlap;
    }

    accept() {
       /*
       if(!this.commissionOverlap(this.commissionCurrent)){
        }
         this.registrationIntention.addInterval(intervals);
         this.registrationIntention.addCommission(this.commissionCurrent);
       }
       */
       this.save()
       this.dialogRef.close();
     }

     save(){
       this.dialogRef.close(this.form.value);
     }

     createDate(time){
       let date = new Date();
       date.setHours(time.hour);
       date.setMinutes(time.minutes);
       return date;
     }

     createInterval(time){
       let start = this.createDate(time.startDate);
       let end = this.createDate(time.endDate);

       let interval = new Interval(time.day, start, end);
       return interval;
     }
 }
