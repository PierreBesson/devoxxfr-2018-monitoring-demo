import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ReservationComponent } from './reservation.component';
import { ReservationDetailComponent } from './reservation-detail.component';
import { ReservationPopupComponent } from './reservation-dialog.component';
import { ReservationDeletePopupComponent } from './reservation-delete-dialog.component';

export const reservationRoute: Routes = [
    {
        path: 'reservation',
        component: ReservationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reservations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reservation/:id',
        component: ReservationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reservations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reservationPopupRoute: Routes = [
    {
        path: 'reservation-new',
        component: ReservationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reservations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reservation/:id/edit',
        component: ReservationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reservations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reservation/:id/delete',
        component: ReservationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reservations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
