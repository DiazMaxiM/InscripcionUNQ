import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-signin-screen',
  templateUrl: './signin-screen.component.html'
})
export class SigninScreenComponent {

  constructor(private authService: AuthService) {}

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
    if (this.dniFormControl.valid) {
      this.authService.login(this.dniFormControl.value).subscribe(data => {console.log(data)},
          err => {console.log('error')});
    }}
}
