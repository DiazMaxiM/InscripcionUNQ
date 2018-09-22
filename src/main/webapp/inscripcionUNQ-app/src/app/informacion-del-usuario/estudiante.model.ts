export class Estudiante {

  mail: string;
  name: string;
  lastName: string;
  dni: string;
  id: string;
  constructor(
    dni: string,
    name: string,
    lastName: string,
    mail: string,
    id: string
  ) {
    this.dni = dni;
    this.name = name;
    this.lastName = lastName;
    this.mail = mail;
    this.id = id;
  }
}
