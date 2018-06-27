package org.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "license_date")
    private LocalDate licenseDate;

    @Column(name = "past_accident")
    private Integer pastAccident;

    @Column(name = "zip_code")
    private Long zipCode;

    @Column(name = "jhi_disable")
    private Boolean disable;

    @Column(name = "marital_status")
    private Boolean maritalStatus;

    @Column(name = "owns_home")
    private Boolean ownsHome;

    @Column(name = "military_service")
    private Boolean militaryService;

    @Column(name = "user_login")
    private String userLogin;

    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private Set<Claim> claims = new HashSet<>();

    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private Set<Vehicle> vehicles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Driver birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getLicenseDate() {
        return licenseDate;
    }

    public Driver licenseDate(LocalDate licenseDate) {
        this.licenseDate = licenseDate;
        return this;
    }

    public void setLicenseDate(LocalDate licenseDate) {
        this.licenseDate = licenseDate;
    }

    public Integer getPastAccident() {
        return pastAccident;
    }

    public Driver pastAccident(Integer pastAccident) {
        this.pastAccident = pastAccident;
        return this;
    }

    public void setPastAccident(Integer pastAccident) {
        this.pastAccident = pastAccident;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public Driver zipCode(Long zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean isDisable() {
        return disable;
    }

    public Driver disable(Boolean disable) {
        this.disable = disable;
        return this;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean isMaritalStatus() {
        return maritalStatus;
    }

    public Driver maritalStatus(Boolean maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(Boolean maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Boolean isOwnsHome() {
        return ownsHome;
    }

    public Driver ownsHome(Boolean ownsHome) {
        this.ownsHome = ownsHome;
        return this;
    }

    public void setOwnsHome(Boolean ownsHome) {
        this.ownsHome = ownsHome;
    }

    public Boolean isMilitaryService() {
        return militaryService;
    }

    public Driver militaryService(Boolean militaryService) {
        this.militaryService = militaryService;
        return this;
    }

    public void setMilitaryService(Boolean militaryService) {
        this.militaryService = militaryService;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public Driver userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<Claim> getClaims() {
        return claims;
    }

    public Driver claims(Set<Claim> claims) {
        this.claims = claims;
        return this;
    }

    public Driver addClaim(Claim claim) {
        this.claims.add(claim);
        claim.setDriver(this);
        return this;
    }

    public Driver removeClaim(Claim claim) {
        this.claims.remove(claim);
        claim.setDriver(null);
        return this;
    }

    public void setClaims(Set<Claim> claims) {
        this.claims = claims;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public Driver vehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public Driver addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setDriver(this);
        return this;
    }

    public Driver removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setDriver(null);
        return this;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
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
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", licenseDate='" + getLicenseDate() + "'" +
            ", pastAccident=" + getPastAccident() +
            ", zipCode=" + getZipCode() +
            ", disable='" + isDisable() + "'" +
            ", maritalStatus='" + isMaritalStatus() + "'" +
            ", ownsHome='" + isOwnsHome() + "'" +
            ", militaryService='" + isMilitaryService() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
