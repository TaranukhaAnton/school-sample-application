import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchollClass, SchollClass } from 'app/shared/model/scholl-class.model';
import { SchollClassService } from './scholl-class.service';
import { SchollClassComponent } from './scholl-class.component';
import { SchollClassDetailComponent } from './scholl-class-detail.component';
import { SchollClassUpdateComponent } from './scholl-class-update.component';

@Injectable({ providedIn: 'root' })
export class SchollClassResolve implements Resolve<ISchollClass> {
  constructor(private service: SchollClassService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchollClass> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((schollClass: HttpResponse<SchollClass>) => {
          if (schollClass.body) {
            return of(schollClass.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchollClass());
  }
}

export const schollClassRoute: Routes = [
  {
    path: '',
    component: SchollClassComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SchollClasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SchollClassDetailComponent,
    resolve: {
      schollClass: SchollClassResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SchollClasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SchollClassUpdateComponent,
    resolve: {
      schollClass: SchollClassResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SchollClasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SchollClassUpdateComponent,
    resolve: {
      schollClass: SchollClassResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SchollClasses'
    },
    canActivate: [UserRouteAccessService]
  }
];
