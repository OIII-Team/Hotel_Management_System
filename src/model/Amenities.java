package model;


public enum Amenities {
    ACCESSIBILITY("Accessibility"),
    KIDS_ACTIVITIES("Kids Activities"),
    RESTAURANTS("Restaurants"),
    CONVENIENT_STORES("Convenient Stores"),
    POOL("Pool"),
    GYM("Gym"),
    WIFI("WiFi"),
    PARKING("Parking");


    private final String displayName;

    Amenities(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void printOptions() {
        Amenities[] values = Amenities.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i].getDisplayName());
        }
    }

    public static Amenities fromChoice(int choice) {
        Amenities[] values = Amenities.values();
        if (choice >= 1 && choice <= values.length) {
            return values[choice - 1];
        }
        return null;
    }

    public String toString() {
        return displayName;
    }

    public static Amenities fromString(String str) {
        for (Amenities amenity : Amenities.values()) {
            if (amenity.displayName.equalsIgnoreCase(str)) {
                return amenity;
            }
        }
        return null;
    }
}