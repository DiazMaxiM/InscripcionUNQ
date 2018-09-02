import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';
import { Student } from './student.model';

@Component({
  selector: 'app-data-verification-screen',
  templateUrl: './data-verification-screen.component.html',
  styleUrls: ['./data-verification-screen.component.css']
})
export class DataVerificationComponent implements OnInit {

  constructor(
    private restService: RestService,
    private router: Router,
    private pollService: PollService,
    private formBuilder: FormBuilder
  ) {}

  pollInfo: PollInfo;
  dataVerificationForm: FormGroup;

ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo:PollInfo) => {
      this.pollInfo = pollInfo;
      this.getStudentData();
    });
    this.createDataStudentFormGroup();
  }

createDataStudentFormGroup() {
    this.dataVerificationForm = this.formBuilder.group({
          name: ['', Validators.required],
          lastName: ['', Validators.required],
          email: ['', [Validators.required, Validators.email]]
      });
  }

getStudentData() {
    this.restService.getStudentData(this.pollInfo.idStudent,this.pollInfo.idCurrentPoll)
    .subscribe(data =>
      this.setStudenDataOnForm(data)
    );
  }

setStudenDataOnForm(student: Student) {

  this.dataVerificationForm.setValue({
    'name': student.name,
    'lastName': student.lastName,
    'email': student.mail
  });
}
}
