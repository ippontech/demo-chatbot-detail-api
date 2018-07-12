package tech.ippon.chatbotdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

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

    @OneToMany(mappedBy = "driver")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicle> vehicles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", userLogin='" + getUserLogin() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", licenseDate='" + getLicenseDate() + "'" +
            ", pastAccident=" + getPastAccident() +
            ", zipCode=" + getZipCode() +
            "}";
    }
}
