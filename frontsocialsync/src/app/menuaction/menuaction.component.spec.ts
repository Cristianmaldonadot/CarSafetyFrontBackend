import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuactionComponent } from './menuaction.component';

describe('MenuactionComponent', () => {
  let component: MenuactionComponent;
  let fixture: ComponentFixture<MenuactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuactionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MenuactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
