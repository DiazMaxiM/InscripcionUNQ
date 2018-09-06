import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';
import { CustomDialogComponent } from '../custom-dialog/custom-dialog.component';
import { MatDialog, MatDialogConfig} from '@angular/material';
import {HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-signin-screen',
  templateUrl: './signin-screen.component.html',
  styleUrls: ['./signin-screen.component.css']
})
export class SigninScreenComponent {

  constructor(
    private restService: RestService,
    private router: Router,
    private pollService: PollService,
    private dialog: MatDialog
  ) {}

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
      const dni = this.dniFormControl.value;

      this.restService.getPolls(dni).subscribe(polls => {
        const pollInfo = new PollInfo(dni,polls);
        this.pollService.sendStudentPollInfo(pollInfo);
        this.router.navigate(['encuestas']);
      },
      (err:HttpErrorResponse) => {
          this.router.navigate(['home']);
          //Acá hay que llamar al servicio que me devuelve el mensaje de error
          this.openDialog('ACÁ VA EL MENSAJE QUE ME VIENE DEL JSON');
      });
  }

  openDialog(msg: String) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
          message: msg
        };
      const dialogRef = this.dialog.open(CustomDialogComponent,
        dialogConfig);
  }
}
