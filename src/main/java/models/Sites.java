package models;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sites {
    private int id;
    private String siteName;
    private String siteLocation;
    private LocalDateTime createdAt;

    public Sites(String name, String location){
        this.siteName=name;
        this.siteLocation=location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return siteName;
    }

    public void setName(String siteName) {
        this.siteName = siteName;
    }

    public String getLocation() {
        return siteLocation;
    }

    public void setLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public LocalDateTime getCreated() {
        return createdAt;
    }

    public void setCreated(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sites sites = (Sites) o;
        return Objects.equals(siteName, sites.siteName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteName);
    }
}
