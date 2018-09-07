export class RegistrationIntention {
  idCommissions?: number;
  commissionValue?: string;
  intervals = [];

  constructor(
    idCommissions?: number,
    commissionValue?: string
  ) {

    this.idCommissions = idCommissions;
    this.commissionValue = commissionValue;
  }

  addInterval(interval) {
    this.intervals.push(interval);
  }
}
