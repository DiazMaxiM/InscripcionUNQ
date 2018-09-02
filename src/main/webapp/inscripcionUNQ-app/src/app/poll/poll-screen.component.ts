import { Component, OnInit} from '@angular/core';
import { PollService } from './poll.service';
import {Router} from '@angular/router';
import {PollInfo} from './poll-info.model';

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
  pollInfo: PollInfo;
  polls: any;

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo:PollInfo) => {
      this.pollInfo = pollInfo;
      this.polls = pollInfo.polls;
    });
  }

  editPoll(idPoll:number){
    const pollInfo = new PollInfo(this.pollInfo.dniStudent,this.pollInfo.polls,idPoll);
    this.pollService.sendStudentPollInfo(pollInfo);
    this.router.navigate(['verificacion-de-datos']);
  }
}
