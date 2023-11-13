import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MitarbeiterDetailsComponent } from './mitarbeiter-details.component';

describe('MitarbeiterDetailsComponent', () => {
  let component: MitarbeiterDetailsComponent;
  let fixture: ComponentFixture<MitarbeiterDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MitarbeiterDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MitarbeiterDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
