package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.client.BookingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoResource {
    private final Logger log = LoggerFactory.getLogger(DemoResource.class);


    private final BookingClient bookingClient;

    @Autowired
    public DemoResource(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    /**
     * GET  /routes : get the active routes.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of routes
     */
    @GetMapping("/testCreateRandom")
    @Timed
    public String testCreateRandom() {
        return bookingClient.createRandom();
    }

    /**
     * GET  /routes : get the active routes.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of routes
     */
    @GetMapping("/testError")
    @Timed
    public String testError() {
        return bookingClient.reservationTriggerError();
    }
}
