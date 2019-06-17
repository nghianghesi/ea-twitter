import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentTweetsComponent } from './recent-tweets.component';

describe('RecentTweetsComponent', () => {
  let component: RecentTweetsComponent;
  let fixture: ComponentFixture<RecentTweetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecentTweetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecentTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
