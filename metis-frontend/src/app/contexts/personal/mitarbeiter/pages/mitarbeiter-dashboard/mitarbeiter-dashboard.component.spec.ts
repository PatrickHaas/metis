import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MitarbeiterDashboardComponent } from './mitarbeiter-dashboard.component';

describe('MitarbeiterDashboardComponent', () => {
  let component: MitarbeiterDashboardComponent;
  let fixture: ComponentFixture<MitarbeiterDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MitarbeiterDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MitarbeiterDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
