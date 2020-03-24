import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.SchoolStudentModule)
      },
      {
        path: 'scholl-class',
        loadChildren: () => import('./scholl-class/scholl-class.module').then(m => m.SchoolSchollClassModule)
      },
      {
        path: 'group',
        loadChildren: () => import('./group/group.module').then(m => m.SchoolGroupModule)
      },
      {
        path: 'grade-level',
        loadChildren: () => import('./grade-level/grade-level.module').then(m => m.SchoolGradeLevelModule)
      },
      {
        path: 'subject',
        loadChildren: () => import('./subject/subject.module').then(m => m.SchoolSubjectModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SchoolEntityModule {}
