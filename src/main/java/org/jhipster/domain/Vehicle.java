package org.jhipster.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "mileage")
    private Long mileage;

    @Column(name = "vin")
    private String vin;

    @Column(name = "has_policy")
    private Boolean hasPolicy;

    @Column(name = "policy_number")
    private Long policyNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private InsuranceDetails insurancedetails;

    @ManyToOne
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public Vehicle make(String make) {
        this.make = make;
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public Vehicle model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public Vehicle year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getMileage() {
        return mileage;
    }

    public Vehicle mileage(Long mileage) {
        this.mileage = mileage;
        return this;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public String getVin() {
        return vin;
    }

    public Vehicle vin(String vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Boolean isHasPolicy() {
        return hasPolicy;
    }

    public Vehicle hasPolicy(Boolean hasPolicy) {
        this.hasPolicy = hasPolicy;
        return this;
    }

    public void setHasPolicy(Boolean hasPolicy) {
        this.hasPolicy = hasPolicy;
    }

    public Long getPolicyNumber() {
        return policyNumber;
    }

    public Vehicle policyNumber(Long policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public void setPolicyNumber(Long policyNumber) {
        this.policyNumber = policyNumber;
    }

    public InsuranceDetails getInsurancedetails() {
        return insurancedetails;
    }

    public Vehicle insurancedetails(InsuranceDetails insuranceDetails) {
        this.insurancedetails = insuranceDetails;
        return this;
    }

    public void setInsurancedetails(InsuranceDetails insuranceDetails) {
        this.insurancedetails = insuranceDetails;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            ", year=" + getYear() +
            ", mileage=" + getMileage() +
            ", vin='" + getVin() + "'" +
            ", hasPolicy='" + isHasPolicy() + "'" +
            ", policyNumber=" + getPolicyNumber() +
            "}";
    }
}
