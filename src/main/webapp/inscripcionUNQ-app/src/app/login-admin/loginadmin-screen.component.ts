
import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-loginadmin-screen',
  templateUrl: './loginadmin-screen.component.html',
  styleUrls: ['./loginadmin-screen.component.css']
})
export class LoginAdminScreenComponent implements OnInit{
    constructor(
      private formBuilder: FormBuilder
    ) {}

    loginVerificationForm: FormGroup;

    ngOnInit() {
      this.createLoginFormGroup();
    }

  createLoginFormGroup() {
      this.loginVerificationForm = this.formBuilder.group({
              password: ['', [Validators.required]],
              email: ['', [Validators.required,Validators.email]]
        });
    }

    onSubmit() {
      console.log("Ha ingresado un email y una contraseña");
    }
}
