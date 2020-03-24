import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { GradeLevelComponent } from './grade-level.component';
import { GradeLevelDetailComponent } from './grade-level-detail.component';
import { GradeLevelUpdateComponent } from './grade-level-update.component';
import { GradeLevelDeleteDialogComponent } from './grade-level-delete-dialog.component';
import { gradeLevelRoute } from './grade-level.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(gradeLevelRoute)],
  declarations: [GradeLevelComponent, GradeLevelDetailComponent, GradeLevelUpdateComponent, GradeLevelDeleteDialogComponent],
  entryComponents: [GradeLevelDeleteDialogComponent]
})
export class SchoolGradeLevelModule {}
