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
 export class CustomDialogSubjectComponent implements OnInit {
    subject: any;
    commissions: any;
    commissionCurrent: any;
    selectedTimes: any;
    intervalSelect = [];
    form: FormGroup;

    constructor(
      public dialogRef: MatDialogRef<CustomDialogSubjectComponent>,
      private fb: FormBuilder,
       @Inject(MAT_DIALOG_DATA) public data: DialogData) {
         this.subject = data.subject;
         this.commissions = this.subject.commissionsJson;
     }

     ngOnInit() {
        this.form = this.fb.group({
            commissionCurrent: this.commissionCurrent
        });
    }

     getCommission(idCommission) {
      const commission = (this.commissions.filter(c => c.id === idCommission))[0];
      return commission;
     }

     getTimesForCommission(idCommission) {
       this.selectedTimes = this.getCommission(idCommission).intervalJson;
       this.commissionCurrent = idCommission;
       return this.selectedTimes;
     }

     getIntervalsOfCommission(idCommission) {
       const times = this.getCommission(idCommission).intervalJson;
       const intervals = [];

       for (const time of times) {
           const interval = this.createInterval(time);
           intervals.push(interval);
         }
     }

    commissionOverlap(intention) {
      let overlap = false;
      for (const interval of intention.intervals) {
        for (const intervalSelect of this.selectedTimes) {
          overlap = overlap || this.overlapInterval(intervalSelect, interval);
        }
      }
      return overlap;
  }

    accept() {
      console.log("interval select");
      console.log(this.intervalSelect);
      const intention = this.createRegistrationIntention();
      console.log("interval intencion");
      console.log(intention.intervals);
      if (!this.verificarSuperposicion(this.intervalSelect,intention.intervals)) {
        for (const interval of intention.intervals) {
            this.intervalSelect.push(interval);
       }
     } else {
       console.log("Horario ocupado");
     }
     }

     verificarSuperposicion(horariosOcupados, horarioPorOcupar) {
        let overlap = false;
         for (const ho of horariosOcupados) {
           for (const hi of horarioPorOcupar) {
              overlap = overlap || this.verificarSuperposicionPorUnIntervalo(ho, hi)
           }
         return overlap;
     }
   }

     verificarSuperposicionPorUnIntervalo(ho,hi){
       let overlap = false;
       if (ho.day === hi.day) {
           overlap = this.checkOverlap(ho, hi);
       }
       return overlap;
     }

     createRegistrationIntention() {
       const registrationIntention = new RegistrationIntention(this.commissionCurrent);
       for (const interval of this.selectedTimes) {
          const startDate = this.createDate(interval.startDate);
          const endDate = this.createDate(interval.endDate);
          const intervalSelect = new Interval(interval.day, startDate, endDate);
          registrationIntention.addInterval(intervalSelect);
        }
        return registrationIntention;
     }

     save() {
       this.dialogRef.close(this.form.value);
     }

     createDate(time) {
       const date = new Date();
       date.setHours(time.hour);
       date.setMinutes(time.minute);
       date.setSeconds(0);
       return date;
     }

     createInterval(time) {
       const start = this.createDate(time.startDate);
       const end = this.createDate(time.endDate);

       const interval = new Interval(time.day, start, end);
       return interval;
     }

      overlapInterval(interval, otherInterval) {
       let overlap = false;

       if (interval.day === otherInterval.day) {
           overlap = this.checkOverlap(interval, otherInterval);
       }
       return overlap;
     }

     checkOverlap(interval, otherInterval) {
       return this.timeOverlap(interval, otherInterval) || this.timeOverlap(otherInterval, interval);
     }

     timeOverlap(interval, otherInterval) {
       console.log("interval");
       console.log(interval);
       console.log("otherInterval");
       console.log(otherInterval);
       return this.dateRangeOverlaps(interval.start, interval.end, otherInterval.start, otherInterval.end);
     }

     dateRangeOverlaps(a_start:Date, a_end:Date, b_start:Date, b_end:Date) {
       console.log(a_start.getTime);
       if (a_start <= b_start && b_start <= a_end) {return true;}
       if (a_start <= b_end && b_end <= a_end) {return true;}
       if (b_start < a_start && a_end < b_end) {return true;}
       return false;
     }
 }
