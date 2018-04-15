package com.mycompany.myapp.client.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class Tour {
    private Long id;

    @NotNull
    @Column(name = "tour_name", nullable = false)
    private String tourName;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "duration")
    private String duration;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "to_date")
    private ZonedDateTime toDate;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public Tour setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTourName() {
        return tourName;
    }

    public Tour setTourName(String tourName) {
        this.tourName = tourName;
        return this;
    }

    public String getType() {
        return type;
    }

    public Tour setType(String type) {
        this.type = type;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public Tour setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public Tour setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public ZonedDateTime getToDate() {
        return toDate;
    }

    public Tour setToDate(ZonedDateTime toDate) {
        this.toDate = toDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tour setDescription(String description) {
        this.description = description;
        return this;
    }
}
