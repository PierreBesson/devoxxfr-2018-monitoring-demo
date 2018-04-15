import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaymentComponent } from './payment.component';
import { PaymentDetailComponent } from './payment-detail.component';
import { PaymentPopupComponent } from './payment-dialog.component';
import { PaymentDeletePopupComponent } from './payment-delete-dialog.component';

export const paymentRoute: Routes = [
    {
        path: 'payment',
        component: PaymentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payment/:id',
        component: PaymentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentPopupRoute: Routes = [
    {
        path: 'payment-new',
        component: PaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment/:id/edit',
        component: PaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment/:id/delete',
        component: PaymentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
