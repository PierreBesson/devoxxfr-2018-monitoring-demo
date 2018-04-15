package com.mycompany.myapp.web.rest.vm;

import com.mycompany.myapp.client.dto.Tour;

import javax.validation.constraints.NotNull;

public class ReservationVM {
    private Long id;

    private Long tourCode;

    @NotNull
    private String personName;

    @NotNull
    private Boolean paid;

    private String notes;

    private Tour tourInfo;

    public Long getId() {
        return id;
    }

    public ReservationVM setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTourCode() {
        return tourCode;
    }

    public ReservationVM setTourCode(Long tourCode) {
        this.tourCode = tourCode;
        return this;
    }

    public String getPersonName() {
        return personName;
    }

    public ReservationVM setPersonName(String personName) {
        this.personName = personName;
        return this;
    }

    public Boolean getPaid() {
        return paid;
    }

    public ReservationVM setPaid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public ReservationVM setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Tour getTourInfo() {
        return tourInfo;
    }

    public ReservationVM setTourInfo(Tour tourInfo) {
        this.tourInfo = tourInfo;
        return this;
    }
}
