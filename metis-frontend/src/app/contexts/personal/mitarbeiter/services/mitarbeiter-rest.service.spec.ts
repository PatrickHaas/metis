import { TestBed } from '@angular/core/testing';

import { MitarbeiterRestService } from './mitarbeiter-rest.service';

describe('MitarbeiterRestService', () => {
  let service: MitarbeiterRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MitarbeiterRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
