import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimCoffeeTypesComponent } from './sim-coffee-types.component';

describe('SimCoffeeTypesComponent', () => {
  let component: SimCoffeeTypesComponent;
  let fixture: ComponentFixture<SimCoffeeTypesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimCoffeeTypesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimCoffeeTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
