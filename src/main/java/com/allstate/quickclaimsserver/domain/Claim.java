package com.allstate.quickclaimsserver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity //to store class in database. You can also specify what it should be called
public class Claim {

    @Id //tells spring this is the unique field / primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tells spring it is responsible for coming up with ids
    private Integer id;

    private String claimStatus;
    private String claimNumber;
    private String policyNumber;
    private String policyType;
    private String propertyAddress;
    private String vehicleMake;
    private String vehicleModel;
    private String manufactureYear;
    private String typeOfAnimal;
    private String breedOfAnimal;
    private String firstName;
    private String lastName;
    private Date claimStartDate;
    private Double estimatedAmount;
    private String claimReason;
    private String incidentDescription;
    private LocalDate incidentDate;
    private String furtherDetails;

    public Integer getId() {
        return id;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getTypeOfAnimal() {
        return typeOfAnimal;
    }

    public void setTypeOfAnimal(String typeOfAnimal) {
        this.typeOfAnimal = typeOfAnimal;
    }

    public String getBreedOfAnimal() {
        return breedOfAnimal;
    }

    public void setBreedOfAnimal(String breedOfAnimal) {
        this.breedOfAnimal = breedOfAnimal;
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

    public Date getClaimStartDate() {
        return claimStartDate;
    }

    public void setClaimStartDate(Date claimStartDate) {
        this.claimStartDate = claimStartDate;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getFurtherDetails() {
        return furtherDetails;
    }

    public void setFurtherDetails(String furtherDetails) {
        this.furtherDetails = furtherDetails;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "claimStatus='" + claimStatus + '\'' +
                ", claimNumber='" + claimNumber + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", policyType='" + policyType + '\'' +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", manufactureYear='" + manufactureYear + '\'' +
                ", typeOfAnimal='" + typeOfAnimal + '\'' +
                ", breedOfAnimal='" + breedOfAnimal + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", claimStartDate='" + claimStartDate + '\'' +
                ", estimatedAmount='" + estimatedAmount + '\'' +
                ", claimReason='" + claimReason + '\'' +
                ", incidentDescription='" + incidentDescription + '\'' +
                ", incidentDate='" + incidentDate + '\'' +
                ", furtherDetails='" + furtherDetails + '\'' +
                '}';
    }

    public Claim(String claimStatus, String claimNumber, String policyNumber, String policyType, String propertyAddress, String vehicleMake, String vehicleModel, String manufactureYear, String typeOfAnimal, String breedOfAnimal, String firstName, String lastName, Date claimStartDate, Double estimatedAmount, String claimReason, String incidentDescription, LocalDate incidentDate, String furtherDetails) {
        this.claimStatus = claimStatus;
        this.claimNumber = claimNumber;
        this.policyNumber = policyNumber;
        this.policyType = policyType;
        this.propertyAddress = propertyAddress;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.manufactureYear = manufactureYear;
        this.typeOfAnimal = typeOfAnimal;
        this.breedOfAnimal = breedOfAnimal;
        this.firstName = firstName;
        this.lastName = lastName;
        this.claimStartDate = claimStartDate;
        this.estimatedAmount = estimatedAmount;
        this.claimReason = claimReason;
        this.incidentDescription = incidentDescription;
        this.incidentDate = incidentDate;
        this.furtherDetails = furtherDetails;
    }

    public Claim() {
    }

}
