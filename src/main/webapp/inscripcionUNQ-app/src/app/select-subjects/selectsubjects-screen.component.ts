import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material';
import {CustomDialogSubjectComponent} from '../custom-dialog-subject/custom-dialog-subject.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { PollInfo } from '../poll/poll-info.model';
import { RegistrationIntention } from '../custom-dialog-subject/registration-intention.model';
import {PageEvent} from '@angular/material';
import {Subject} from '../subject/subject.model';


@Component({
  selector: 'app-selectsubjects-screen',
  templateUrl: './selectsubjects-screen.component.html',
  styleUrls: ['./selectsubjects-screen.component.css']
})

export class SelectSubjectsComponent implements OnInit {

    pollInfo: PollInfo;
    subjectsAvailable: Subject[] = [];
    registrationIntentions: RegistrationIntention[] = [];

    // MatPaginator Output
    pageEvent: PageEvent;
    // MatPaginator Inputs
   length = 0;
   pageSize = 10;
   pageSizeOptions: number[] = [5, 10];
   activesubjectsAvailable: Subject[] = [];

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
  ) {}

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
        if(!(this.subjectsAvailable.length > 0)) {
           this.getSubjetsAvailable();
        }
      });
  }

  selectSubject(subject, checkbox) {
    if (checkbox.checked) {
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
    dialogRef.afterClosed().subscribe(intention => {
       if (intention !== undefined) {
         this.registrationIntentions.push(intention);
         this.showCommisionName(subject.id, intention.commissionValue);
       }
    });
  }

  showCommisionName(idSubject, commissionName) {
     const subject = this.getSubjet(idSubject);
     const newSubject = this.createNewSubject(subject, commissionName);
     this.subjectsAvailable[this.subjectsAvailable.indexOf(subject)] = newSubject;
     this.activesubjectsAvailable = this.subjectsAvailable;
      console.log(this.subjectsAvailable);
      console.log(this.activesubjectsAvailable);

  }

  createNewSubject(oldSubject, commissionName) {
    return {
      'id': oldSubject.id,
      'code': oldSubject.code,
      'name': oldSubject.name,
      'approved': oldSubject.approved,
      'checked': true,
      'commissionName': commissionName
    };
  }

  getSubjet(idSubject) {
    return this.subjectsAvailable.find(function (item) {
        return item.id == idSubject;
    });
}

  getSubjetsAvailable() {
    console.log('hola');
    this.restService.getSubjetsAvailable(this.pollInfo.idStudent)
    .subscribe(subjects => {
      this.subjectsAvailable = subjects;
      this.length = this.subjectsAvailable.length;
      this.activesubjectsAvailable = this.subjectsAvailable.slice(0, this.pageSize);
    });
  }

  onPageChanged(e) {
    const firstCut = e.pageIndex * e.pageSize;
    const secondCut = firstCut + e.pageSize;
    this.activesubjectsAvailable = this.subjectsAvailable.slice(firstCut, secondCut);
}
setPageSizeOptions(setPageSizeOptionsInput: string) {
  this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
}
}
