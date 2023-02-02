package com.allstate.quickclaimsserver.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class GeneratedNumber {
    @Id
    private String number;

    public GeneratedNumber(String number) {
        this.number = number;
    }

    public GeneratedNumber() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedNumber that = (GeneratedNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "GeneratedNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
