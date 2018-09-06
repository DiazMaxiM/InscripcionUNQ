import { Interval } from './interval.model';

export class RegistrationIntention {
  idUsuario: number;
  idCommissions?: number[];
  intention?: Interval[];
  constructor(
    idUsuario: number,
    idCommissions?: number[],
    intention?: any
  ) {
    this.idUsuario = idUsuario;
    this.idCommissions = idCommissions;
    this.intention = intention;
  }

  getIntention(){
    return this.intention;
  }

  addInterval(interval){
    this.intention.push(interval);
  }

  addCommission(idCommission){
    this.idCommissions.push(idCommission);
  }
}
