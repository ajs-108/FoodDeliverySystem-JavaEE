package com.app.model.jpa;

import com.app.common.enums.AccountStatus;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "user")
@Table(name = "user_")
public class JPAUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_", nullable = false, length = 41)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 14)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "created_on", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn;

    @Column(name = "update_on",
            columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedOn;

    @ManyToOne(targetEntity = UserRole.class, cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    private UserRole role;

    @Column(name = "account_status", nullable = false,
            columnDefinition = "ENUM('ACTIVATED', 'DEACTIVATED') DEFAULT 'ACTIVATED'")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "JPAUser{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", role=" + role +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
