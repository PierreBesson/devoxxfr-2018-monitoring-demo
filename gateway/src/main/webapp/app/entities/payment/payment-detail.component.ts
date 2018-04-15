import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Payment } from './payment.model';
import { PaymentService } from './payment.service';

@Component({
    selector: 'jhi-payment-detail',
    templateUrl: './payment-detail.component.html'
})
export class PaymentDetailComponent implements OnInit, OnDestroy {

    payment: Payment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private paymentService: PaymentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayments();
    }

    load(id) {
        this.paymentService.find(id)
            .subscribe((paymentResponse: HttpResponse<Payment>) => {
                this.payment = paymentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paymentListModification',
            (response) => this.load(this.payment.id)
        );
    }
}
