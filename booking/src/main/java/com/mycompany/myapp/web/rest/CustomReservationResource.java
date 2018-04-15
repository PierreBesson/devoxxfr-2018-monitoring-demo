package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.client.CatalogClient;
import com.mycompany.myapp.client.dto.Tour;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.repository.ReservationRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.vm.ReservationVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reservation.
 */
@RestController
@RequestMapping("/api")
public class CustomReservationResource {

    private final Logger log = LoggerFactory.getLogger(CustomReservationResource.class);

    private static final String ENTITY_NAME = "reservation";

    private final ReservationRepository reservationRepository;

    private final CatalogClient catalogClient;


    public CustomReservationResource(ReservationRepository reservationRepository, CatalogClient catalogClient) {
        this.reservationRepository = reservationRepository;
        this.catalogClient = catalogClient;
    }

    /**
     * GET  /reservations/:id : get the "id" reservation.
     *
     * @param id the id of the reservation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservation, or with status 404 (Not Found)
     */
    @GetMapping("/reservations/withTourInfo/{id}")
    @Timed
    public ResponseEntity<ReservationVM> getReservationAndTourInformation(@PathVariable Long id) {
        log.debug("REST request to get Reservation With tour info : {}", id);
        Reservation reservation = reservationRepository.findOne(id);
        if (reservation != null) {
            ReservationVM reservationvm = new ReservationVM()
                .setId(reservation.getId())
                .setPersonName(reservation.getPersonName())
                .setNotes(reservation.getNotes())
                .setTourCode(reservation.getTourCode())
                .setPaid(reservation.isPaid());
            Tour tour = catalogClient.getTour(reservation.getTourCode());
            if (tour != null) {
                reservationvm.setTourInfo(tour);
            } else {
                log.warn("Tour not found in catalog");
            }
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reservationvm));
        }
        return null;
    }

    /**
     * GET  /reservations/triggerError : just throws an exception
     *
     * @return never
     */
    @GetMapping("/reservations/triggerError")
    @Timed
    public ResponseEntity<Reservation> triggerError() throws Exception {
        log.debug("DEMO: Launch an exception !");
        throw new Exception("demo");
    }

    /**
     * POST  /reservations/createRandom : create random reservatoin
     *
     * @return never
     */
    @PostMapping("/reservations/createRandom")
    @Timed
    public ResponseEntity<Reservation> creationRandom() throws Exception {
        log.debug("DEMO: Create random reservation");
        //Thread.sleep(1000);
        Reservation reservation = new Reservation()
            .tourCode(1L)
            .paid(false)
            .personName(RandomStringUtils.randomAlphabetic(10))
            .notes(RandomStringUtils.randomAlphabetic(100));
        Reservation result = reservationRepository.save(reservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reservation.getId().toString()))
            .body(result);
    }
}
