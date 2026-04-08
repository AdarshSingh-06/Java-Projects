import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Item class
class Item {
    String name;
    int price;
    int quantity;

    Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    int getTotal() {
        return price * quantity;
    }
}

public class RestaurantBilling {

    static ArrayList<Item> cart = new ArrayList<>();

    // Menu
    public static void showMenu() {
        System.out.println("\n====== MENU ======");
        System.out.println("1. Pizza      - 200");
        System.out.println("2. Burger     - 100");
        System.out.println("3. Pasta      - 150");
        System.out.println("4. Sandwich   - 80");
        System.out.println("5. Remove Item");
        System.out.println("6. Generate Bill & Exit");
    }

    // Add item
    public static void addItem(int choice, int quantity) {
        switch (choice) {
            case 1:
                cart.add(new Item("Pizza", 200, quantity));
                break;
            case 2:
                cart.add(new Item("Burger", 100, quantity));
                break;
            case 3:
                cart.add(new Item("Pasta", 150, quantity));
                break;
            case 4:
                cart.add(new Item("Sandwich", 80, quantity));
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Remove item
    public static void removeItem(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        System.out.println("\nItems in Cart:");
        for (int i = 0; i < cart.size(); i++) {
            System.out.println((i + 1) + ". " + cart.get(i).name);
        }

        System.out.print("Enter item number to remove: ");
        int index = sc.nextInt();

        if (index > 0 && index <= cart.size()) {
            cart.remove(index - 1);
            System.out.println("Item removed!");
        } else {
            System.out.println("Invalid selection!");
        }
    }

    // Generate Bill
    public static void generateBill(String customerName) {
        int total = 0;

        System.out.println("\n========= BILL =========");

        // Date & Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("Customer: " + customerName);
        System.out.println("Date: " + dtf.format(now));

        System.out.println("\nItem\tPrice\tQty\tTotal");

        for (Item item : cart) {
            System.out.println(item.name + "\t" + item.price + "\t" + item.quantity + "\t" + item.getTotal());
            total += item.getTotal();
        }

        double gst = total * 0.05;
        double finalAmount = total + gst;

        System.out.println("--------------------------------");
        System.out.println("Subtotal: " + total);
        System.out.println("GST (5%): " + gst);
        System.out.println("Final Amount: " + finalAmount);
        System.out.println("================================");
        System.out.println("🙏 Thank You! Visit Again");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        int choice;

        while (true) {
            showMenu();
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();

                if (choice >= 1 && choice <= 4) {
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();

                    if (quantity <= 0) {
                        System.out.println("Invalid quantity!");
                        continue;
                    }

                    addItem(choice, quantity);

                } else if (choice == 5) {
                    removeItem(sc);

                } else if (choice == 6) {
                    break;

                } else {
                    System.out.println("Invalid choice!");
                }

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter numbers only.");
                sc.nextLine(); // clear buffer
            }
        }

        generateBill(name);
    }
}