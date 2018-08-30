export class Student {

  email: string;
  firstName?: string;
  lastName?: string;
  id?: string;
  constructor(
    email: string,
    firstName?: string,
    lastName?: string,
    id?: string
  ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  fullName() {
    return `${this.firstName} ${this.lastName}`;
  }
}
