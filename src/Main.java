import structures.*;
import model.*;


public class Main
{
    public static void main(String[] args)
    {
        //Hotels and Tsimmers objects are created here
        HotelTree hotelTree = new HotelTree();
        //North
        hotelTree.addHotel(new Hotel("The Scots Hotel",Region.NORTH,new Location(Region.NORTH,City.TIBERIAS,"Sea Road 1"),400.0,new Amenities[]{Amenities.POOL,Amenities.WIFI},20,2,4.5,null,null,null));
        hotelTree.addHotel(new Hotel("Leonardo Plaza Hotel Tiberias",Region.NORTH,new Location(Region.NORTH,City.TIBERIAS,"Kinneret St. 5"),450.0,new Amenities[]{Amenities.POOL,Amenities.RESTAURANTS},25,3,4.3,null,null,null));
       //Jerusalem
        hotelTree.addHotel(new Hotel("King David Hotel",Region.JERUSALEM,new Location(Region.JERUSALEM,City.JERUSALEM,"King David 23"),500.0,new Amenities[]{Amenities.PARKING,Amenities.RESTAURANTS},15,2,4.8,null,null,null));
        hotelTree.addHotel(new Hotel("Mamilla Hotel",Region.JERUSALEM,new Location(Region.JERUSALEM,City.JERUSALEM,"Mamilla Mall"),350.0,new Amenities[]{Amenities.WIFI,Amenities.RESTAURANTS},30,3,4.6,null,null,null));
        //Center
        hotelTree.addHotel(new Hotel("The Norman Tel Aviv",Region.CENTER,new Location(Region.CENTER,City.TEL_AVIV,"Herbert Samuel 2"),600.0,new Amenities[]{Amenities.GYM,Amenities.WIFI},18,2,4.7,null,null,null));
        hotelTree.addHotel(new Hotel("Dan Panorama Tel Aviv",Region.CENTER,new Location(Region.CENTER,City.TEL_AVIV,"Bialik 23"),480.0,new Amenities[]{Amenities.POOL,Amenities.RESTAURANTS},25,3,4.4,null,null,null));
        //South
        hotelTree.addHotel(new Hotel("Dan Eilat",Region.SOUTH,new Location(Region.SOUTH,City.EILAT,"North Beach"),450.0,new Amenities[]{Amenities.POOL,Amenities.WIFI},40,3,4.2,null,null,null));
        hotelTree.addHotel(new Hotel("Kempinski Hotel Ishtar Dead Sea",Region.SOUTH,new Location(Region.SOUTH,City.DEAD_SEA,"Ishtar Gate"),700.0,new Amenities[]{Amenities.SPA,Amenities.POOL},50,4,4.6,null,null,null));
        //Tsimmers
        hotelTree.addHotel(new Tsimmer("Galim Tsimmer", Region.NORTH, new Location(Region.NORTH, City.TIBERIAS, "Lake View Rd. 5"), 300.0, new Amenities[]{Amenities.WIFI}, 5, 4, 3.9, null, null, null, true, false, "Lake"));
        hotelTree.addHotel(new Tsimmer("Mitzpe Ramon Tsimmer", Region.SOUTH, new Location(Region.SOUTH, City.MITZPE_RAMON, "Desert Path 1"), 250.0, new Amenities[]{Amenities.PARKING}, 3, 2, 4.1, null, null, null, false, true, "Desert"));

        Menu.setHotelTree(hotelTree); // set the hotel tree in Menu
        Menu.main(args); // call Menu

    }
}
