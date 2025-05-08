package model;

public class Location
{
    private Region region;
    private City city;
    private String address;

    public Location(Region region, City city, String address) {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
