import { Interval } from './interval.model'

export class RegistrationIntention {

  intention: Interval[];
  id: number;
  constructor(
    intention: any,
    id: number
  ) {
    this.id = id;
    this.intention = intention;
  }

  addInterval(interval){
    this.intention.push(interval);
  }
}
