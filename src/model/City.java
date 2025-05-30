package model;

public enum City
{
    //North
    HAIFA        ("Haifa",        Region.NORTH),
    TIBERIAS     ("Tiberias",     Region.NORTH),
    SAFED        ("Safed",        Region.NORTH),

    //South
    EILAT        ("Eilat",        Region.SOUTH),
    BEER_SHEVA   ("Beer Sheva",   Region.SOUTH),
    MITZPE_RAMON ("Mitzpe Ramon", Region.SOUTH),
    DEAD_SEA     ("Dead Sea",     Region.SOUTH),

    //Jerusalem
    JERUSALEM    ("Jerusalem",    Region.JERUSALEM),

    //Center
    TEL_AVIV     ("Tel Aviv",     Region.CENTER),
    HERZLIYA     ("Herzliya",     Region.CENTER),
    NETANYA      ("Netanya",      Region.CENTER);

    private String  displayName;
    private Region  region;

    City(String displayName, Region region) {
        this.displayName = displayName;
        this.region      = region;
    }

    public static void printCityOptions(Region selectedRegion)
    {
        City[] cities = citiesOf(selectedRegion);
        for (int i = 0; i < cities.length; i++)
            System.out.println((i + 1) + ". " + cities[i].getDisplayName());
    }

    public String getDisplayName() { return displayName; }
    public Region getRegion()      { return region; }

    public static City fromString(String str) {
        for (City c : City.values()) {
            if (c.displayName.equalsIgnoreCase(str) || c.name().equalsIgnoreCase(str))
                return c;
        }
        return null;
    }

    public static void printCityByRegion(Region region) {
        City[] cities = citiesOf(region);
        for (int i = 0; i < cities.length; i++)
            System.out.println((i + 1) + ". " + cities[i].getDisplayName());
    }

    public static City getCityFromChoice(Region region, int choice) {
        City[] cities = citiesOf(region);
        int idx = choice - 1;
        return (idx >= 0 && idx < cities.length) ? cities[idx] : null;
    }

    private static City[] citiesOf(Region region) {
        return java.util.Arrays.stream(City.values()).filter(c -> c.region == region)
                .toArray(City[]::new);
    }

    public String toString() { return displayName; }
}
