import { Injectable, Output, EventEmitter } from '@angular/core';
import {PollInfo} from './poll-info.model';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class PollService {
  private pollInfoSource = new BehaviorSubject<PollInfo>(new PollInfo());
  currentPollInfo = this.pollInfoSource.asObservable();
  constructor() { }

  sendSetudentPollInfo(pollInfo:PollInfo) {
    this.pollInfoSource.next(pollInfo);
 }
}
