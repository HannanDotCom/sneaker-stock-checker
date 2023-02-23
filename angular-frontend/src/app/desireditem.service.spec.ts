import { TestBed } from '@angular/core/testing';

import { DesiredItemService } from './desireditem.service';

describe('DesiredItemService', () => {
  let service: DesiredItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DesiredItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
