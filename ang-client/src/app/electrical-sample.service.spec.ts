import { TestBed } from '@angular/core/testing';

import { ElectricalSampleService } from './electrical-sample.service';

describe('ElectricalSampleService', () => {
  let service: ElectricalSampleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElectricalSampleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
