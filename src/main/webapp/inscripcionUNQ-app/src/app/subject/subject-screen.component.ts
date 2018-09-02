import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';

@Component({
  selector: 'app-subject-screen',
  templateUrl: './subject-screen.component.html'
})
export class SubjectScreenComponent implements OnInit{

  pollInfo: PollInfo;
  subjects: any;

  constructor(
    private restService: RestService,
    private router: Router,
    private pollService: PollService
  ) {}

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
        this.getSubjets();
      });
    }

  getSubjets() {
    this.restService.getSubjets(this.pollInfo.idStudent)
    .subscribe(subjects =>
      this.subjects = subjects
    );
    }


}
