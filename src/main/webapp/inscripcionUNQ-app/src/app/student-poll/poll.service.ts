import { Injectable, Output, EventEmitter } from '@angular/core';
import {Poll} from './poll.model';

@Injectable()
export class PollService {

  @Output() change: EventEmitter<Poll[]> = new EventEmitter();

  sendPolls(polls: any) {
    this.change.emit(polls);
  }
}
