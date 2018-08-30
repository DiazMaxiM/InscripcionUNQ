import { Component, OnInit} from '@angular/core';
import { PollService } from './poll.service';
import {Router} from '@angular/router';
import {Poll} from './poll.model';

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

  polls: any;

  ngOnInit() {
    this.pollService.change.subscribe(polls => {
      console.log(polls);
      this.polls = polls;
    });
  }
}
