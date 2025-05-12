import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import structures.HotelTree;
import structures.UsersList;

import java.io.IOException;
import java.util.Arrays;

public class Menu {
    private static UsersList users = new UsersList();
    private static HotelTree hotelTree = new HotelTree();
    private static User currentUser;

    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();

            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
            Window mainWindow = new BasicWindow("Hotel Management System");

            Panel panel = new Panel();
            panel.setLayoutManager(new GridLayout(1));

            panel.addComponent(new Label("Welcome to Hotel Management System!"));
            panel.addComponent(new Button("User", () -> {
                currentUser = User.loginOrRegisterLanterna(gui, users);
                userMenu(gui);
            }));
            panel.addComponent(new Button("Admin", () -> adminMenu(gui)));
            panel.addComponent(new Button("Exit", () -> {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            mainWindow.setComponent(panel);
            gui.addWindowAndWait(mainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void userMenu(MultiWindowTextGUI gui) {
        Window userWindow = new BasicWindow("User Menu");
        Panel panel = new Panel(new GridLayout(1));

        panel.addComponent(new Button("View hotels", () -> {
            HotelsView hotelsView = new HotelsView(hotelTree);
            hotelsView.runLanterna(gui, currentUser);
        }));
        panel.addComponent(new Button("Cancel last booking", () -> currentUser.cancelLastBookingLanterna(gui)));
        panel.addComponent(new Button("View my upcoming bookings", () -> currentUser.viewUpcomingBookingsLanterna(gui)));
        panel.addComponent(new Button("Back to main menu", userWindow::close));

        userWindow.setComponent(panel);
        gui.addWindowAndWait(userWindow);
    }

    private static void adminMenu(MultiWindowTextGUI gui) {
        Window adminWindow = new BasicWindow("Admin Menu");
        Panel panel = new Panel(new GridLayout(1));

        panel.addComponent(new Button("Add hotel", () -> Admin.addHotelInteractiveLanterna(gui, hotelTree)));
        panel.addComponent(new Button("Remove hotel", () -> Admin.removeHotelInteractiveLanterna(gui, hotelTree)));
        panel.addComponent(new Button("View Hotels by region", () -> Admin.viewHotelsByRegionLanterna(gui, hotelTree)));
        panel.addComponent(new Button("Back to main menu", adminWindow::close));

        adminWindow.setComponent(panel);
        gui.addWindowAndWait(adminWindow);
    }
}
