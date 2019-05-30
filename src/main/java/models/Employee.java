package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Employee {
    private int id;
    private int ekNumber;
    private String name;
    private int phone;
    private LocalDateTime createdAt;

    public Employee(int ekNumber, String name, int phone){
        this.ekNumber=ekNumber;
        this.name=name;
        this.phone=phone;
        this.createdAt=LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEkNumber() {
        return ekNumber;
    }

    public void setEkNumber(int ekNumber) {
        this.ekNumber = ekNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getEkNumber() == employee.getEkNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEkNumber());
    }

}
