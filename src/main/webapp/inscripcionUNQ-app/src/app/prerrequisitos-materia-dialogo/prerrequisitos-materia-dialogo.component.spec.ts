import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrerrequisitosMateriaDialogoComponent } from './prerrequisitos-materia-dialogo.component';

describe('PrerrequisitosMateriaDialogoComponent', () => {
  let component: PrerrequisitosMateriaDialogoComponent;
  let fixture: ComponentFixture<PrerrequisitosMateriaDialogoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrerrequisitosMateriaDialogoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrerrequisitosMateriaDialogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
