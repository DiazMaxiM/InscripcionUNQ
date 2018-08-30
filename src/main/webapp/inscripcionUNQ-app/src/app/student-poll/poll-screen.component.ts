import { Component, OnInit} from '@angular/core';
import { PollService } from './poll.service';
import {Router} from '@angular/router';
import {StudentPollInfo} from './student-poll-info.model';

@Component({
  selector: 'app-poll-screen',
  templateUrl: './poll-screen.component.html',
  styleUrls: ['./poll-screen.component.css']
})
export class PollScreenComponent implements OnInit {
  constructor(
  private pollService: PollService,
  private router: Router
) { }
  studentPollInfo : StudentPollInfo;
  polls: any;

  ngOnInit() {
    this.pollService.change.subscribe((studentPollInfo :StudentPollInfo) => {
      this.studentPollInfo = studentPollInfo;
      this.polls = studentPollInfo.polls;
    });
  }

  editPoll(idPoll:number){
    const studentPollInfo = new StudentPollInfo();
    studentPollInfo.idStudent = this.studentPollInfo.idStudent;
    studentPollInfo.polls = this.studentPollInfo.polls;
    studentPollInfo.currentIdPoll = idPoll;
    console.log(studentPollInfo);
  }
}
