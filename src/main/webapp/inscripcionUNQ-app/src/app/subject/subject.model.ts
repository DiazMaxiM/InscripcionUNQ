export class  Subject {
  id?: number;
  code?: string;
  name?: string;
  approved?: boolean;
  checked?: boolean;
  commissionName?: string;

  constructor(
    id?: number,
    code?: string,
    name?: string,
    approved?: boolean,
    checked?: boolean,
    commissionName?: string

  ) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.approved = approved;
    this.checked = checked;
    this.commissionName = commissionName;
  }
}
