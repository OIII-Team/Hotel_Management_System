package model;

public class Location
{
    private Region region;
    private String city;
    private String address;

    public Location(Region region, String city, String address) {
        this.region = region;
        this.city = city;
        this.address = address;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
