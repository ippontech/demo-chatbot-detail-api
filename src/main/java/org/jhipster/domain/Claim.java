package org.jhipster.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Claim.
 */
@Entity
@Table(name = "claim")
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "injury_involved")
    private Boolean injuryInvolved;

    @Column(name = "accident_date")
    private LocalDate accidentDate;

    @Column(name = "accident_time")
    private Instant accidentTime;

    @Column(name = "accident_city")
    private String accidentCity;

    @Column(name = "accident_state")
    private String accidentState;

    @Column(name = "passengers_in_cars")
    private Boolean passengersInCars;

    @Column(name = "damage_location")
    private String damageLocation;

    @ManyToOne
    private Driver driver;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isInjuryInvolved() {
        return injuryInvolved;
    }

    public Claim injuryInvolved(Boolean injuryInvolved) {
        this.injuryInvolved = injuryInvolved;
        return this;
    }

    public void setInjuryInvolved(Boolean injuryInvolved) {
        this.injuryInvolved = injuryInvolved;
    }

    public LocalDate getAccidentDate() {
        return accidentDate;
    }

    public Claim accidentDate(LocalDate accidentDate) {
        this.accidentDate = accidentDate;
        return this;
    }

    public void setAccidentDate(LocalDate accidentDate) {
        this.accidentDate = accidentDate;
    }

    public Instant getAccidentTime() {
        return accidentTime;
    }

    public Claim accidentTime(Instant accidentTime) {
        this.accidentTime = accidentTime;
        return this;
    }

    public void setAccidentTime(Instant accidentTime) {
        this.accidentTime = accidentTime;
    }

    public String getAccidentCity() {
        return accidentCity;
    }

    public Claim accidentCity(String accidentCity) {
        this.accidentCity = accidentCity;
        return this;
    }

    public void setAccidentCity(String accidentCity) {
        this.accidentCity = accidentCity;
    }

    public String getAccidentState() {
        return accidentState;
    }

    public Claim accidentState(String accidentState) {
        this.accidentState = accidentState;
        return this;
    }

    public void setAccidentState(String accidentState) {
        this.accidentState = accidentState;
    }

    public Boolean isPassengersInCars() {
        return passengersInCars;
    }

    public Claim passengersInCars(Boolean passengersInCars) {
        this.passengersInCars = passengersInCars;
        return this;
    }

    public void setPassengersInCars(Boolean passengersInCars) {
        this.passengersInCars = passengersInCars;
    }

    public String getDamageLocation() {
        return damageLocation;
    }

    public Claim damageLocation(String damageLocation) {
        this.damageLocation = damageLocation;
        return this;
    }

    public void setDamageLocation(String damageLocation) {
        this.damageLocation = damageLocation;
    }

    public Driver getDriver() {
        return driver;
    }

    public Claim driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Claim vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
        Claim claim = (Claim) o;
        if (claim.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), claim.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Claim{" +
            "id=" + getId() +
            ", injuryInvolved='" + isInjuryInvolved() + "'" +
            ", accidentDate='" + getAccidentDate() + "'" +
            ", accidentTime='" + getAccidentTime() + "'" +
            ", accidentCity='" + getAccidentCity() + "'" +
            ", accidentState='" + getAccidentState() + "'" +
            ", passengersInCars='" + isPassengersInCars() + "'" +
            ", damageLocation='" + getDamageLocation() + "'" +
            "}";
    }
}
