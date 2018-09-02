export class PollInfo {
  idStudent?: number;
  polls?: any;
  idCurrentPoll?: number;

  constructor(
    idStudent?: number,
    polls?: any,
    idCurrentPoll?: number
  ) {
    this.idStudent = idStudent;
    this.polls = polls;
    this.idCurrentPoll = idCurrentPoll;
  }}
