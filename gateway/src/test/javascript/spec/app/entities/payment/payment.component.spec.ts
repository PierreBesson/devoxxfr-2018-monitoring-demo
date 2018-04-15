/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HipsterTravelTestModule } from '../../../test.module';
import { PaymentComponent } from '../../../../../../main/webapp/app/entities/payment/payment.component';
import { PaymentService } from '../../../../../../main/webapp/app/entities/payment/payment.service';
import { Payment } from '../../../../../../main/webapp/app/entities/payment/payment.model';

describe('Component Tests', () => {

    describe('Payment Management Component', () => {
        let comp: PaymentComponent;
        let fixture: ComponentFixture<PaymentComponent>;
        let service: PaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTravelTestModule],
                declarations: [PaymentComponent],
                providers: [
                    PaymentService
                ]
            })
            .overrideTemplate(PaymentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Payment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
