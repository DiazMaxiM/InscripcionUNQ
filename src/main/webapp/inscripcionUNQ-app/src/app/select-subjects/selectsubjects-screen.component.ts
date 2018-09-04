import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material';
import {CustomDialogSubjectComponent} from '../custom-dialog-subject/custom-dialog-subject.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { PollInfo } from '../poll/poll-info.model';

@Component({
  selector: 'app-selectsubjects-screen',
  templateUrl: './selectsubjects-screen.component.html',
  styleUrls: ['./selectsubjects-screen.component.css']
})

export class SelectSubjectsComponent implements OnInit{

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog
  ) {}

  pollInfo: PollInfo;
  dataVerificationForm: FormGroup;
  subjects: any;
  checked:boolean;

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
        this.getSubjets();
      });
    }

  aceptar() {
    if(this.checked){
      this.openDialog('');
    }
  }

  openDialog(msg: String) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
        message: msg
      };
    const dialogRef = this.dialog.open(CustomDialogSubjectComponent,
      dialogConfig);
    }

    //Modificar y quedarme con las materias no aprobadas
  getSubjets() {
    this.restService.getSubjets(this.pollInfo.idStudent)
    .subscribe(subjects => {
      this.subjects = subjects;
    });
  }
}
