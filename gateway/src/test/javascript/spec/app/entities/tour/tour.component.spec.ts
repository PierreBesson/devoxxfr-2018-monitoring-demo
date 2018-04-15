/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HipsterTravelTestModule } from '../../../test.module';
import { TourComponent } from '../../../../../../main/webapp/app/entities/tour/tour.component';
import { TourService } from '../../../../../../main/webapp/app/entities/tour/tour.service';
import { Tour } from '../../../../../../main/webapp/app/entities/tour/tour.model';

describe('Component Tests', () => {

    describe('Tour Management Component', () => {
        let comp: TourComponent;
        let fixture: ComponentFixture<TourComponent>;
        let service: TourService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTravelTestModule],
                declarations: [TourComponent],
                providers: [
                    TourService
                ]
            })
            .overrideTemplate(TourComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TourComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TourService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Tour(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tours[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
