import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TuyaComponent } from './tuya.component';

describe('TuyaComponent', () => {
  let component: TuyaComponent;
  let fixture: ComponentFixture<TuyaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TuyaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TuyaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
