import { TestBed } from '@angular/core/testing';

import { APIHttpService } from './apihttp.service';

describe('APIHttpService', () => {
  let service: APIHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(APIHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
