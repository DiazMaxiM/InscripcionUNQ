export class RegistrationIntention {
  idCommissions?: number;
  commissionValue?: string;
  intervals = [];
  idSubject?: number;

  constructor(
    idCommissions?: number,
    commissionValue?: string,
    idSubject?: number,

  ) {

    this.idCommissions = idCommissions;
    this.commissionValue = commissionValue;
    this.idSubject = idSubject;
  }

  addInterval(interval) {
    this.intervals.push(interval);
  }
}
