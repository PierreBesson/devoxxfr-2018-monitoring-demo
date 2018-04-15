package com.mycompany.myapp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("booking")
public interface BookingClient {
    @RequestMapping(path = "/api/reservations/triggerError", method = RequestMethod.GET)
    public String reservationTriggerError();

    @RequestMapping(path = "/api/reservations/createRandom", method = RequestMethod.POST)
    public String createRandom();

}
