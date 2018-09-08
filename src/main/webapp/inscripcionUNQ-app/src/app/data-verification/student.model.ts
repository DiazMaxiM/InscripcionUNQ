export class Student {

  mail: string;
  name: string;
  lastName: string;
  dni: number;
  id: number;
  constructor(
    dni: number,
    name: string,
    lastName: string,
    mail: string,
    id: number
  ) {
    this.dni = dni;
    this.name = name;
    this.lastName = lastName;
    this.mail = mail;
    this.id = id;
  }
}
