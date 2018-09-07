export class User {
  constructor(
    public email: string,
    public password: string,
    public firstName?: string,
    public lastName?: string,
    public _id?: string
  ) { }

  fullName() {
    return `${this.firstName} ${this.lastName}`;
  }
}
