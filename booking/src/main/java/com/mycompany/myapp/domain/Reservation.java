package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tour_code", nullable = false)
    private Long tourCode;

    @NotNull
    @Column(name = "person_name", nullable = false)
    private String personName;

    @NotNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "notes")
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTourCode() {
        return tourCode;
    }

    public Reservation tourCode(Long tourCode) {
        this.tourCode = tourCode;
        return this;
    }

    public void setTourCode(Long tourCode) {
        this.tourCode = tourCode;
    }

    public String getPersonName() {
        return personName;
    }

    public Reservation personName(String personName) {
        this.personName = personName;
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Boolean isPaid() {
        return paid;
    }

    public Reservation paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getNotes() {
        return notes;
    }

    public Reservation notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", tourCode=" + getTourCode() +
            ", personName='" + getPersonName() + "'" +
            ", paid='" + isPaid() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
