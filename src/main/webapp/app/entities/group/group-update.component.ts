import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGroup, Group } from 'app/shared/model/group.model';
import { GroupService } from './group.service';
import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { GradeLevelService } from 'app/entities/grade-level/grade-level.service';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject/subject.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = IGradeLevel | ISubject | IStudent;

type SelectableManyToManyEntity = ISubject | IStudent;

@Component({
  selector: 'jhi-group-update',
  templateUrl: './group-update.component.html'
})
export class GroupUpdateComponent implements OnInit {
  isSaving = false;
  gradelevels: IGradeLevel[] = [];
  subjects: ISubject[] = [];
  students: IStudent[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    gradeLevel: [],
    subjects: [],
    students: []
  });

  constructor(
    protected groupService: GroupService,
    protected gradeLevelService: GradeLevelService,
    protected subjectService: SubjectService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ group }) => {
      this.updateForm(group);

      this.gradeLevelService.query().subscribe((res: HttpResponse<IGradeLevel[]>) => (this.gradelevels = res.body || []));

      this.subjectService.query().subscribe((res: HttpResponse<ISubject[]>) => (this.subjects = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(group: IGroup): void {
    this.editForm.patchValue({
      id: group.id,
      name: group.name,
      gradeLevel: group.gradeLevel,
      subjects: group.subjects,
      students: group.students
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const group = this.createFromForm();
    if (group.id !== undefined) {
      this.subscribeToSaveResponse(this.groupService.update(group));
    } else {
      this.subscribeToSaveResponse(this.groupService.create(group));
    }
  }

  private createFromForm(): IGroup {
    return {
      ...new Group(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      gradeLevel: this.editForm.get(['gradeLevel'])!.value,
      subjects: this.editForm.get(['subjects'])!.value,
      students: this.editForm.get(['students'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroup>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
