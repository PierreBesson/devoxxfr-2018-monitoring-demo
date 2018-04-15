package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @Column(name = "notes")
    private String notes;

    @OneToOne
    @JoinColumn(unique = true)
    private Reservation reservationCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public Payment price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public Payment buyerName(String buyerName) {
        this.buyerName = buyerName;
        return this;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getNotes() {
        return notes;
    }

    public Payment notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Reservation getReservationCode() {
        return reservationCode;
    }

    public Payment reservationCode(Reservation reservation) {
        this.reservationCode = reservation;
        return this;
    }

    public void setReservationCode(Reservation reservation) {
        this.reservationCode = reservation;
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
        Payment payment = (Payment) o;
        if (payment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", buyerName='" + getBuyerName() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
