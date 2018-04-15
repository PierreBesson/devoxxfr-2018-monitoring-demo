import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Payment } from './payment.model';
import { PaymentService } from './payment.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-payment',
    templateUrl: './payment.component.html'
})
export class PaymentComponent implements OnInit, OnDestroy {
payments: Payment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentService: PaymentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.paymentService.query().subscribe(
            (res: HttpResponse<Payment[]>) => {
                this.payments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Payment) {
        return item.id;
    }
    registerChangeInPayments() {
        this.eventSubscriber = this.eventManager.subscribe('paymentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
