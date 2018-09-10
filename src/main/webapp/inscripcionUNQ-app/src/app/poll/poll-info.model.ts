export class PollInfo {
  idStudent?: number;
  polls?: any;
  idCurrentPoll?: number;
  dniStudent?: number;

  constructor(
    dniStudent?: number,
    polls?: any,
    idCurrentPoll?: number,
    idStudent?: number,
  ) {
    this.idStudent = idStudent;
    this.polls = polls;
    this.idCurrentPoll = idCurrentPoll;
    this.dniStudent = dniStudent;
  }}
