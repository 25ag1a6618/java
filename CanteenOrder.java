
import java.util.*;
import java.sql.*;

class Canteen {

    ArrayList<String> food = new ArrayList<>();
    ArrayList<Integer> price = new ArrayList<>();

    void loadMenu() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/canteen_db",
                "root",
                "hasini1902"
            );

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT * FROM food_items"
            );

            while (rs.next()) {
                food.add(rs.getString("food_name"));
                price.add(rs.getInt("price"));
            }

            con.close();

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {
            System.out.println("Database Error: " + e);
        }
    }

    void showMenu() {

        System.out.println("\n--- CANTEEN MENU ---");

        for (int i = 0; i < food.size(); i++) {
            System.out.println((i + 1) + ". " +
                food.get(i) + " - Rs." + price.get(i));
        }
    }

    void order() {

        Scanner sc = new Scanner(System.in);

        loadMenu();

        if (food.isEmpty()) {
            System.out.println("No food items available!");
            return;
        }

        int choice, quantity, total = 0;
        char again;

        do {
            showMenu();

            System.out.print("Enter item number: ");
            choice = sc.nextInt();

            if (choice >= 1 && choice <= food.size()) {

                System.out.print("Enter quantity: ");
                quantity = sc.nextInt();

                if (quantity <= 0) {
                    System.out.println("Invalid quantity!");
                } else {
                    total = total +
                        price.get(choice - 1) * quantity;

                    System.out.println(quantity + " " +
                        food.get(choice - 1) +
                        " added to order.");
                }

            } else {
                System.out.println("Invalid choice!");
            }

            System.out.print("Order another item? (y/n): ");
            again = sc.next().charAt(0);

        } while (again == 'y' || again == 'Y');

        System.out.println("\n--- FINAL BILL ---");
        System.out.println("Total Amount = Rs." + total);
        System.out.println("Thank you! Visit again.");
    }
}

public class CanteenOrder {

    public static void main(String[] args) {

        Canteen c = new Canteen();
        c.order();
    }
}
