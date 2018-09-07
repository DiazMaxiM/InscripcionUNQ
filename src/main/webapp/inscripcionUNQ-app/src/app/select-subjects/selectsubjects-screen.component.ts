import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material';
import {CustomDialogSubjectComponent} from '../custom-dialog-subject/custom-dialog-subject.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { PollInfo } from '../poll/poll-info.model';
import { RegistrationIntention } from '../custom-dialog-subject/registration-intention.model';

@Component({
  selector: 'app-selectsubjects-screen',
  templateUrl: './selectsubjects-screen.component.html',
  styleUrls: ['./selectsubjects-screen.component.css']
})

export class SelectSubjectsComponent implements OnInit {

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
  ) {}

  pollInfo: PollInfo;
  subjectsAvailable: any;
  registrationIntentions: RegistrationIntention[] = [];

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
        this.getSubjetsAvailable();
      });
  }

  selectSubject(subject) {
    if (subject.checked) {
      this.openDialog(subject);
    }
  }

  openDialog(subject) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
        subject: subject,
      };
    const dialogRef = this.dialog.open(CustomDialogSubjectComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(data => {
        this.registrationIntentions.push(data);
        console.log(data);
        this.showCommisionName(subject.id, data.commissionValue);
    });
  }

  showCommisionName(idSubject, commissionName) {
      const result = [];
      for (const i in this.subjectsAvailable) {
        if (this.subjectsAvailable[i].id === idSubject) {
          result.push({
            'id': this.subjectsAvailable[i].id,
            'code': this.subjectsAvailable[i].code,
            'name': this.subjectsAvailable[i].name,
            'approved': !this.subjectsAvailable[i].approved,
            'checked': true,
            'commissionName': commissionName
          });
        } else {
          result.push(this.subjectsAvailable[i]);
        }
      }
      this.subjectsAvailable = result;
      console.log(result);
  }

  getSubjetsAvailable() {
    this.restService.getSubjetsAvailable(this.pollInfo.idStudent)
    .subscribe(subjects => {
      this.subjectsAvailable = subjects;
    });
  }
}
