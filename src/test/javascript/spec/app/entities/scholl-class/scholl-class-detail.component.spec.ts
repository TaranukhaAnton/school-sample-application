import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { SchollClassDetailComponent } from 'app/entities/scholl-class/scholl-class-detail.component';
import { SchollClass } from 'app/shared/model/scholl-class.model';

describe('Component Tests', () => {
  describe('SchollClass Management Detail Component', () => {
    let comp: SchollClassDetailComponent;
    let fixture: ComponentFixture<SchollClassDetailComponent>;
    const route = ({ data: of({ schollClass: new SchollClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [SchollClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SchollClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchollClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schollClass on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schollClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
