/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HipsterTravelTestModule } from '../../../test.module';
import { ReservationComponent } from '../../../../../../main/webapp/app/entities/reservation/reservation.component';
import { ReservationService } from '../../../../../../main/webapp/app/entities/reservation/reservation.service';
import { Reservation } from '../../../../../../main/webapp/app/entities/reservation/reservation.model';

describe('Component Tests', () => {

    describe('Reservation Management Component', () => {
        let comp: ReservationComponent;
        let fixture: ComponentFixture<ReservationComponent>;
        let service: ReservationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTravelTestModule],
                declarations: [ReservationComponent],
                providers: [
                    ReservationService
                ]
            })
            .overrideTemplate(ReservationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReservationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReservationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Reservation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.reservations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
