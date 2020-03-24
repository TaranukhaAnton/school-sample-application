import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { SchollClassComponent } from './scholl-class.component';
import { SchollClassDetailComponent } from './scholl-class-detail.component';
import { SchollClassUpdateComponent } from './scholl-class-update.component';
import { SchollClassDeleteDialogComponent } from './scholl-class-delete-dialog.component';
import { schollClassRoute } from './scholl-class.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(schollClassRoute)],
  declarations: [SchollClassComponent, SchollClassDetailComponent, SchollClassUpdateComponent, SchollClassDeleteDialogComponent],
  entryComponents: [SchollClassDeleteDialogComponent]
})
export class SchoolSchollClassModule {}
