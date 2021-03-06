package models;

import java.util.Objects;

public class Engineer {
    private int id;
    private int ekNumber;
    private String name;
    private String phone;

    public Engineer(int ekNumber, String name, String phone){
        this.ekNumber=ekNumber;
        this.name=name;
        this.phone=phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return getId() == engineer.getId() &&
                getEkNumber() == engineer.getEkNumber() &&
                Objects.equals(getName(), engineer.getName()) &&
                Objects.equals(getPhone(), engineer.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEkNumber(), getName(), getPhone());
    }
}
