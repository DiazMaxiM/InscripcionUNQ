import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavegacionEstudianteComponent } from './navegacion-estudiante.component';

describe('NavegacionEstudianteComponent', () => {
  let component: NavegacionEstudianteComponent;
  let fixture: ComponentFixture<NavegacionEstudianteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavegacionEstudianteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavegacionEstudianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
