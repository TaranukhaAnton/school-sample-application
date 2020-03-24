import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { GradeLevelUpdateComponent } from 'app/entities/grade-level/grade-level-update.component';
import { GradeLevelService } from 'app/entities/grade-level/grade-level.service';
import { GradeLevel } from 'app/shared/model/grade-level.model';

describe('Component Tests', () => {
  describe('GradeLevel Management Update Component', () => {
    let comp: GradeLevelUpdateComponent;
    let fixture: ComponentFixture<GradeLevelUpdateComponent>;
    let service: GradeLevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeLevelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GradeLevelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradeLevelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradeLevelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GradeLevel(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GradeLevel();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
