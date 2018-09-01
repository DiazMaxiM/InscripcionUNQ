import { Component, OnInit} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';

@Component({
  selector: 'app-data-verification-screen',
  templateUrl: './data-verification-screen.component.html'
})
export class DataVerificationComponent implements OnInit {

  constructor(
    private restService: RestService,
    private router: Router,
    private pollService: PollService
  ) {}

  pollInfo: PollInfo;

  ngOnInit() {
    this.pollService.change.subscribe((pollInfo:PollInfo) => {
      this.pollInfo = pollInfo;
    });
  }
}
