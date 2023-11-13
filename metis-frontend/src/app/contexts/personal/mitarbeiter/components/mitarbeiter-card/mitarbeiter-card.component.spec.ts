import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MitarbeiterCardComponent } from './mitarbeiter-card.component';

describe('MitarbeiterCardComponent', () => {
  let component: MitarbeiterCardComponent;
  let fixture: ComponentFixture<MitarbeiterCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MitarbeiterCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MitarbeiterCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
