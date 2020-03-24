import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchollClass } from 'app/shared/model/scholl-class.model';
import { SchollClassService } from './scholl-class.service';

@Component({
  templateUrl: './scholl-class-delete-dialog.component.html'
})
export class SchollClassDeleteDialogComponent {
  schollClass?: ISchollClass;

  constructor(
    protected schollClassService: SchollClassService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schollClassService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schollClassListModification');
      this.activeModal.close();
    });
  }
}
