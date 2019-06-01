package models;

import java.util.Objects;

public class Site {
    private int id;
    private String siteName;
    private String siteLocation;
    private int engineerId;

    public Site(String name, String location, int engineerId){
        this.siteName=name;
        this.siteLocation=location;
        this.engineerId=engineerId;
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

    public int getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(int engineerId) {
        this.engineerId = engineerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return getId() == site.getId() &&
                Objects.equals(siteName, site.siteName) &&
                Objects.equals(siteLocation, site.siteLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), siteName, siteLocation);
    }
}
