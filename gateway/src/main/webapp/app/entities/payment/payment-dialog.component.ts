import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Payment } from './payment.model';
import { PaymentPopupService } from './payment-popup.service';
import { PaymentService } from './payment.service';
import { Reservation, ReservationService } from '../reservation';

@Component({
    selector: 'jhi-payment-dialog',
    templateUrl: './payment-dialog.component.html'
})
export class PaymentDialogComponent implements OnInit {

    payment: Payment;
    isSaving: boolean;

    reservationcodes: Reservation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private paymentService: PaymentService,
        private reservationService: ReservationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.reservationService
            .query({filter: 'payment-is-null'})
            .subscribe((res: HttpResponse<Reservation[]>) => {
                if (!this.payment.reservationCode || !this.payment.reservationCode.id) {
                    this.reservationcodes = res.body;
                } else {
                    this.reservationService
                        .find(this.payment.reservationCode.id)
                        .subscribe((subRes: HttpResponse<Reservation>) => {
                            this.reservationcodes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.payment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentService.update(this.payment));
        } else {
            this.subscribeToSaveResponse(
                this.paymentService.create(this.payment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Payment>>) {
        result.subscribe((res: HttpResponse<Payment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Payment) {
        this.eventManager.broadcast({ name: 'paymentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackReservationById(index: number, item: Reservation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-payment-popup',
    template: ''
})
export class PaymentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentPopupService: PaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paymentPopupService
                    .open(PaymentDialogComponent as Component, params['id']);
            } else {
                this.paymentPopupService
                    .open(PaymentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
