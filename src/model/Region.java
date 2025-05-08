package model;

public enum Region
{
    NORTH("North"),
    SOUTH("South"),
    JERUSALEM("Jerusalem"),
    CENTER("Center");

    private final String displayName;

    Region(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public static Region fromString(String str)
    {
        for (Region region : Region.values())
        {
            if (region.displayName.equalsIgnoreCase(str) || region.name().equalsIgnoreCase(str))
            {
                return region;
            }
        }
        return null;
    }

    public String toString()
    {
        return displayName;
    }

    public static void printRegionOptions() {
        Region[] ordered = {
                Region.NORTH,
                Region.SOUTH,
                Region.JERUSALEM,
                Region.CENTER
        };

        for (int i = 0; i < ordered.length; i++) {
            System.out.println((i + 1) + ". " + ordered[i].getDisplayName());
        }
    }

    public static Region getRegionFromChoice(int choice) {
        switch (choice) {
            case 1: return Region.NORTH;
            case 2: return Region.SOUTH;
            case 3: return Region.JERUSALEM;
            case 4: return Region.CENTER;
            default: return null;
        }
    }
}