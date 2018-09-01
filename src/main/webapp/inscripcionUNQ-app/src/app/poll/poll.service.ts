import { Injectable, Output, EventEmitter } from '@angular/core';
import {PollInfo} from './poll-info.model';

@Injectable()
export class PollService {

  @Output() change: EventEmitter<PollInfo> = new EventEmitter();

  sendSetudentPollInfo(pollInfo:PollInfo) {
    this.change.emit(pollInfo);
  }
}
