import {Component, Inject, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DialogData} from './dialog-data.model' ;
import { RegistrationIntention } from './registration-intention.model';
import { Interval } from './interval.model';
import { FormBuilder, FormGroup} from '@angular/forms';
import { PollInfo } from '../poll/poll-info.model';
import { PollService } from '../poll/poll.service';

@Component({
     selector: 'app-custom-dialog-subject',
     templateUrl: './custom-dialog-subject.component.html'
 })
 export class CustomDialogSubjectComponent implements OnInit {
    subject: any;
    commissions: any;
    commissionCurrent: any;
    selectedTimes: any;
    intervalSelect: Interval[];
    form: FormGroup;
    pollInfo: PollInfo;
    commissionOverlapMessage;

    constructor(
      private pollService: PollService,
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
        this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
          this.pollInfo = pollInfo;
          this.intervalSelect = this.pollInfo.intervalSelect  == null ? [] : this.pollInfo.intervalSelect ;
        });
    }

     getCommission(idCommission) {
      const commission = (this.commissions.filter(c => c.id === idCommission))[0];
      return commission;
     }

     getTimesForCommission(idCommission) {
       this.selectedTimes = this.getCommission(idCommission).intervalJson;
       this.commissionCurrent = idCommission;
       this.commissionOverlapMessage = '';
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
      const intention = this.createRegistrationIntention();
      if (!this.verificarSuperposicion(this.intervalSelect,intention.intervals)) {
        for (const interval of intention.intervals) {
            this.intervalSelect.push(interval);
       }
       this.updatePollInfo();
       this.save(intention);
     } else {
       this.commissionOverlapMessage = 'No se pueden cursar materias con horarios superpuestos';
     }
     }

     updatePollInfo(){
       this.pollInfo.intervalSelect = this.intervalSelect;
       this.pollService.sendStudentPollInfo(this.pollInfo);
     }

     save(intention: RegistrationIntention) {
       this.dialogRef.close(intention);
     }

   close() {
       this.dialogRef.close();
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
       const commissionName = this.getCommission(this.commissionCurrent).name;
       const registrationIntention = new RegistrationIntention(this.commissionCurrent, commissionName);
       for (const interval of this.selectedTimes) {
          const startDate = this.createDate(interval.startDate);
          const endDate = this.createDate(interval.endDate);
          const intervalSelect = new Interval(interval.day, startDate, endDate);
          registrationIntention.addInterval(intervalSelect);
        }
        return registrationIntention;
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
       return this.dateRangeOverlaps(interval.start, interval.end, otherInterval.start, otherInterval.end);
     }

     dateRangeOverlaps(a_start: Date, a_end: Date, b_start: Date, b_end: Date) {
       if (a_start <= b_start && b_start <= a_end) {return true;}
       if (a_start <= b_end && b_end <= a_end) {return true;}
       if (b_start < a_start && a_end < b_end) {return true;}
       return false;
     }
 }
