import { Interval } from './interval.model';

export class RegistrationIntention {
  idCommissions?: number;
  intervals = [];

  constructor(
    idCommissions?: number,
  ) {

    this.idCommissions = idCommissions;
  }

  addInterval(interval) {
    this.intervals.push(interval);
  }
}
