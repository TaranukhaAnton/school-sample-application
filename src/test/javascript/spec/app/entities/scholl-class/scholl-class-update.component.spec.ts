import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { SchollClassUpdateComponent } from 'app/entities/scholl-class/scholl-class-update.component';
import { SchollClassService } from 'app/entities/scholl-class/scholl-class.service';
import { SchollClass } from 'app/shared/model/scholl-class.model';

describe('Component Tests', () => {
  describe('SchollClass Management Update Component', () => {
    let comp: SchollClassUpdateComponent;
    let fixture: ComponentFixture<SchollClassUpdateComponent>;
    let service: SchollClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [SchollClassUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SchollClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchollClassUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchollClassService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SchollClass(123);
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
        const entity = new SchollClass();
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
