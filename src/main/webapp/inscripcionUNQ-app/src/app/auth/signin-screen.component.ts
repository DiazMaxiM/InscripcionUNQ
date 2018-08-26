import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { User } from './user.model';

@Component({
  selector: 'app-signin-screen',
  templateUrl: './signin-screen.component.html'
})
export class SigninScreenComponent {

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
    console.log(this.dniFormControl.value);
  }
}
