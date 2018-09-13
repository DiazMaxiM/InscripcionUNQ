import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';
import { Student } from './student.model';
import {UtilesService} from '../utiles.service';
@Component({
  selector: 'app-data-verification-screen',
  templateUrl: './data-verification-screen.component.html',
  styleUrls: ['./data-verification-screen.component.css']
})
export class DataVerificationComponent implements OnInit {

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private formBuilder: FormBuilder,
    private utilesService: UtilesService
  ) {}

  pollInfo: PollInfo;
  dataVerificationForm: FormGroup;
  idCurrentStudent: number;

ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo:PollInfo) => {
      this.pollInfo = pollInfo;
      this.getStudentData();
    });
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
    this.restService.getStudentData(this.pollInfo.dniStudent,this.pollInfo.idCurrentPoll)
    .subscribe(data =>
      this.setStudenDataOnForm(data)
    );
  }

setStudenDataOnForm(student: Student) {
  this.idCurrentStudent = student.id;
  this.dataVerificationForm.setValue({
    'name': student.name,
    'lastName': student.lastName,
    'email': student.mail
  });
}

onSubmit() {
  if (this.dataVerificationForm.valid) {
    const { name, lastName, email} = this.dataVerificationForm.value;
    const studentData = new Student(this.pollInfo.dniStudent, name, lastName, email, this.idCurrentStudent);
    this.restService.updateStudentData(studentData)
      .subscribe(res => {
        this.updatePollInfo();
        const mensaje = 'Los datos fueron actualizados con exito';
         this.utilesService.mostrarMensajeYRedireccionar(mensaje, 'materias-aprobadas');

       });
  }
}

updatePollInfo(){
  this.pollInfo.idStudent =  this.idCurrentStudent;
  this.pollService.sendStudentPollInfo(this.pollInfo);
}

}
