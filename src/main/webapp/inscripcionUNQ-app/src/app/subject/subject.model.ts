export class  Subject {
  id?: number;
  code?: string;
  name?: string;
  approved?: boolean;

  constructor(
    id?: number,
    code?: string,
    name?: string,
    approved?: boolean,
  ) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.approved = approved;
  }
}
