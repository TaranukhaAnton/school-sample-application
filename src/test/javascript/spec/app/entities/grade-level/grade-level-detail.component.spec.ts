import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { GradeLevelDetailComponent } from 'app/entities/grade-level/grade-level-detail.component';
import { GradeLevel } from 'app/shared/model/grade-level.model';

describe('Component Tests', () => {
  describe('GradeLevel Management Detail Component', () => {
    let comp: GradeLevelDetailComponent;
    let fixture: ComponentFixture<GradeLevelDetailComponent>;
    const route = ({ data: of({ gradeLevel: new GradeLevel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeLevelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GradeLevelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GradeLevelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gradeLevel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gradeLevel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
