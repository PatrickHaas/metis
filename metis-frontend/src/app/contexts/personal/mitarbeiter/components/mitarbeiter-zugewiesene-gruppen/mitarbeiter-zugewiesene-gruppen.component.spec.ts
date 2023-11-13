import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MitarbeiterZugewieseneGruppenComponent } from './mitarbeiter-zugewiesene-gruppen.component';

describe('MitarbeiterZugewieseneGruppenComponent', () => {
  let component: MitarbeiterZugewieseneGruppenComponent;
  let fixture: ComponentFixture<MitarbeiterZugewieseneGruppenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MitarbeiterZugewieseneGruppenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MitarbeiterZugewieseneGruppenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
