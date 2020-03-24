import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { GradeLevelService } from './grade-level.service';

@Component({
  templateUrl: './grade-level-delete-dialog.component.html'
})
export class GradeLevelDeleteDialogComponent {
  gradeLevel?: IGradeLevel;

  constructor(
    protected gradeLevelService: GradeLevelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gradeLevelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gradeLevelListModification');
      this.activeModal.close();
    });
  }
}
