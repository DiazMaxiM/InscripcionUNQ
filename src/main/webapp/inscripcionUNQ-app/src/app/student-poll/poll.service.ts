import { Injectable, Output, EventEmitter } from '@angular/core';
import {StudentPollInfo} from './student-poll-info.model';

@Injectable()
export class PollService {

  @Output() change: EventEmitter<StudentPollInfo> = new EventEmitter();

  sendSetudentPollInfo(studentPollInfo: StudentPollInfo) {
    this.change.emit(studentPollInfo);
  }
}
