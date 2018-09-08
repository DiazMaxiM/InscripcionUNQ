import { Interval } from '../custom-dialog-subject/interval.model';
export class PollInfo {
  idStudent?: number;
  polls?: any;
  idCurrentPoll?: number;
  dniStudent?: number;
  intervalSelect?: Interval[];

  constructor(
    dniStudent?: number,
    polls?: any,
    idCurrentPoll?: number,
    idStudent?: number,
    intervalSelect?
  ) {
    this.idStudent = idStudent;
    this.polls = polls;
    this.idCurrentPoll = idCurrentPoll;
    this.dniStudent = dniStudent;
    this.intervalSelect = intervalSelect;
  }}
