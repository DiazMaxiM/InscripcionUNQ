import { Component, OnInit} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { PollInfo } from '../poll/poll-info.model';
import {HttpErrorResponse } from '@angular/common/http';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-encuesta-finalizada',
  templateUrl: './encuesta-finalizada.component.html',
  styleUrls: ['./encuesta-finalizada.component.css']
})
export class EncuestaFinalizadaComponent implements OnInit {

  pollInfo: PollInfo;

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private utilesService: UtilesService
  ) {}

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
      });
    }

}
