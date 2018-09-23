export class  Materia {
  id?: number;
  code?: string;
  name?: string;
  approved?: boolean;
  checked?: boolean;
  commissionName?: string;
  comisionRegistrado?: any;

  constructor(
    id?: number,
    code?: string,
    name?: string,
    approved?: boolean,
    checked?: boolean,
    commissionName?: string,
    comisionRegistrado?: any

  ) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.approved = approved;
    this.checked = checked;
    this.commissionName = commissionName;
    this.comisionRegistrado = comisionRegistrado;
  }
}
