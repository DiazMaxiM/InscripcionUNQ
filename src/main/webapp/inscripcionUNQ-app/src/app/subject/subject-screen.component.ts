import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';
import { element } from '@angular/core/src/render3/instructions';

@Component({
  selector: 'app-subject-screen',
  templateUrl: './subject-screen.component.html',
  styleUrls: ['./subject-screen.component.css']
})
export class SubjectScreenComponent implements OnInit{

  pollInfo: PollInfo;
  subjects: any;

  constructor(
    private restService: RestService,
    private router: Router,
    private pollService: PollService
  ) {}

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
        this.getSubjets();
      });
    }

  getSubjets() {
    this.restService.getSubjets(this.pollInfo.idStudent)
    .subscribe(subjects => {
      this.subjects = subjects;
    });
  }

    confirmar() {
      this.restService.updateStubjets(this.pollInfo.idStudent, this.subjects)
      .subscribe(res => {
        console.log(res);
      });
      this.router.navigate(['seleccionar-materias']);
    }

    update(id){
      let result = []
      for (const i in this.subjects) {
        if (this.subjects[i].id == id){
          result.push({
            'id': this.subjects[i].id,
            'code': this.subjects[i].code,
            'name':this.subjects[i].name,
            'approved': !this.subjects[i].approved
          })
        }else{
          result.push(this.subjects[i])  
        }
      }
      this.subjects = result;
    }
}
