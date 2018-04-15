package com.mycompany.myapp.client;

import com.mycompany.myapp.client.dto.Tour;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("catalog")
public interface CatalogClient {
    @RequestMapping(path = "/api/tours/{id}", method = RequestMethod.GET)
    public Tour getTour(@PathVariable("id") Long id);
}
