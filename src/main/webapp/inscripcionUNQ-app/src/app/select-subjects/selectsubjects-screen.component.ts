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
   currentPage;

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
    } else{
      this.uncheckSubject(subject.id);
      const intentionToDelete = this.getIntention(subject.id);
      const intentions = this.registrationIntentions.filter(function(intention) { return intention.idSubject != subject.id; });
      this.registrationIntentions = intentions;
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
       } else {
         this.uncheckSubject(subject.id);
       }
    });
  }

 uncheckSubject(idSubject){
   const subject = this.getSubjet(idSubject);
   const newSubject = this.createNewSubject(subject,'', false);
   this.subjectsAvailable[this.subjectsAvailable.indexOf(subject)] = newSubject;
   this.onPageChanged(this.currentPage);

 }


  showCommisionName(idSubject, commissionName) {
     const subject = this.getSubjet(idSubject);
     const newSubject = this.createNewSubject(subject, commissionName,true);
     this.subjectsAvailable[this.subjectsAvailable.indexOf(subject)] = newSubject;
     this.onPageChanged(this.currentPage);

  }

  createNewSubject(oldSubject, commissionName, checked) {
    return {
      'id': oldSubject.id,
      'code': oldSubject.code,
      'name': oldSubject.name,
      'approved': oldSubject.approved,
      'checked': checked,
      'commissionName': commissionName,
      'commissionsJson': oldSubject.commissionsJson
    };
  }

  getIntention(idSubject) {
    return this.registrationIntentions.find(function (item) {
        return item.idSubject == idSubject;
    });
}

  getSubjet(idSubject) {
    return this.subjectsAvailable.find(function (item) {
        return item.id == idSubject;
    });
}

  getSubjetsAvailable() {
    this.restService.getSubjetsAvailable(this.pollInfo.idStudent)
    .subscribe(subjects => {
      this.subjectsAvailable = subjects;
      this.length = this.subjectsAvailable.length;
      this.activesubjectsAvailable = this.subjectsAvailable.slice(0, this.pageSize);
    });
  }

  onPageChanged(e) {
    this.currentPage = e;
    const firstCut = e.pageIndex * e.pageSize;
    const secondCut = firstCut + e.pageSize;
    this.activesubjectsAvailable = this.subjectsAvailable.slice(firstCut, secondCut);
}
setPageSizeOptions(setPageSizeOptionsInput: string) {
  this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
}
}
