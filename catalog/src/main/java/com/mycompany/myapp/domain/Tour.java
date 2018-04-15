package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public Tour tourName(String tourName) {
        this.tourName = tourName;
        return this;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getType() {
        return type;
    }

    public Tour type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public Tour duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public Tour fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getToDate() {
        return toDate;
    }

    public Tour toDate(ZonedDateTime toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(ZonedDateTime toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public Tour description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tour tour = (Tour) o;
        if (tour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tour{" +
            "id=" + getId() +
            ", tourName='" + getTourName() + "'" +
            ", type='" + getType() + "'" +
            ", duration='" + getDuration() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
