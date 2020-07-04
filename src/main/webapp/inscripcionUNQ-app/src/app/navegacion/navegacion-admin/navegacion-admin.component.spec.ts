import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavegacionAdminComponent } from './navegacion-admin.component';

describe('NavegacionAdminComponent', () => {
  let component: NavegacionAdminComponent;
  let fixture: ComponentFixture<NavegacionAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavegacionAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavegacionAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
