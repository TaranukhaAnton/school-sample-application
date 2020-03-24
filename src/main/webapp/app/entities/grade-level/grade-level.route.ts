import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGradeLevel, GradeLevel } from 'app/shared/model/grade-level.model';
import { GradeLevelService } from './grade-level.service';
import { GradeLevelComponent } from './grade-level.component';
import { GradeLevelDetailComponent } from './grade-level-detail.component';
import { GradeLevelUpdateComponent } from './grade-level-update.component';

@Injectable({ providedIn: 'root' })
export class GradeLevelResolve implements Resolve<IGradeLevel> {
  constructor(private service: GradeLevelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGradeLevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gradeLevel: HttpResponse<GradeLevel>) => {
          if (gradeLevel.body) {
            return of(gradeLevel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GradeLevel());
  }
}

export const gradeLevelRoute: Routes = [
  {
    path: '',
    component: GradeLevelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'GradeLevels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GradeLevelDetailComponent,
    resolve: {
      gradeLevel: GradeLevelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeLevels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GradeLevelUpdateComponent,
    resolve: {
      gradeLevel: GradeLevelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeLevels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GradeLevelUpdateComponent,
    resolve: {
      gradeLevel: GradeLevelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeLevels'
    },
    canActivate: [UserRouteAccessService]
  }
];
