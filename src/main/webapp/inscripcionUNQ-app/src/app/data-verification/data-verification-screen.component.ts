import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { Student } from './student.model';
import {UtilesService} from '../utiles.service';
import {HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-data-verification-screen',
  templateUrl: './data-verification-screen.component.html',
  styleUrls: ['./data-verification-screen.component.css']
})
export class DataVerificationComponent implements OnInit {

  constructor(
    private restService: RestService,
    private formBuilder: FormBuilder,
    private utilesService: UtilesService
  ) {}

  dataVerificationForm: FormGroup;
  idEstudiante: string;
  dniEstudiante: string;
  idEncuestaActual: string;

ngOnInit() {
    this.dniEstudiante = localStorage.getItem('dniEstudiante');
    this.idEncuestaActual = localStorage.getItem('idEncuestaActual');
    this.getStudentData();
    this.createDataStudentFormGroup();
  }

createDataStudentFormGroup() {
    this.dataVerificationForm = this.formBuilder.group({
          name: ['', Validators.required],
          lastName: ['', Validators.required],
          email: ['', [Validators.required, Validators.email]]
      });
  }

getStudentData() {
    this.restService.getInformacionEstudiante(this.dniEstudiante, this.idEncuestaActual)
    .subscribe(data =>
      this.setStudenDataOnForm(data)
    );
  }

setStudenDataOnForm(student) {
  this.idEstudiante = student.id;
  localStorage.setItem('idEstudiante', this.idEstudiante);
  this.dataVerificationForm.setValue({
    'name': student.name,
    'lastName': student.lastName,
    'email': student.mail
  });
}

onSubmit() {
  if (this.dataVerificationForm.valid) {
    const { name, lastName, email} = this.dataVerificationForm.value;
    const studentData = new Student(this.dniEstudiante, name, lastName, email, this.idEstudiante);
    this.restService.updateStudentData(studentData)
      .subscribe(res => {
        const mensaje = 'Los datos fueron actualizados con exito';
         this.utilesService.mostrarMensajeYRedireccionar(mensaje, 'materias-aprobadas');

       },
       (err: HttpErrorResponse) => {
         this.utilesService.mostrarMensajeDeError(err);
       });
  }
}

}
