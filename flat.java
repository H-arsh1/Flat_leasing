import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Flat {
    private String place;
    private int Bhk;
    private int year;
    private int price;
    private boolean Available;

    public Flat(String place, int Bhk, int year, int price) {
        this.place = place;
        this.Bhk = Bhk;
        this.year = year;
        this.price = price;
        this.Available = true;
    }

    public String getPlace() {
        return place;
    }

    public int getBhk() {
        return Bhk;
    }

    public int getYear() {
        return year;
    }

    public int calculatePrice(int rentalYears) {
        return Bhk * rentalYears * price; // Adjusted price calculation
    }

    public boolean isAvailable() {
        return Available;
    }

    public void lease() {
        Available = false;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Lease {
    private Flat flat;
    private Customer customer;
    private int years;

    public Lease(Flat flat, Customer customer, int years) {
        this.flat = flat;
        this.customer = customer;
        this.years = years;
    }

    public Flat getFlat() {
        return flat;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getYears() {
        return years;
    }
}

class LeaseSystem {
    private List<Flat> flats;
    private List<Customer> customers;
    private List<Lease> leases;

    public LeaseSystem() {
        flats = new ArrayList<>();
        customers = new ArrayList<>();
        leases = new ArrayList<>();
    }

    public void addFlat(Flat flat) {
        flats.add(flat);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void leaseFlat(Flat flat, Customer customer, int years) {
        if (flat.isAvailable()) {
            flat.lease();
            leases.add(new Lease(flat, customer, years));
            System.out.println("Congratulations! Flat leased successfully.");
        } else {
            System.out.println("Flat is not available for lease.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Flat Lease System =====");
            System.out.println("1. Lease a Flat");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Lease a flat ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable flats:");
                for (Flat flat : flats) {
                    if (flat.isAvailable()) {
                        System.out.println(flat.getPlace() + " - " + flat.getBhk() + " BHK, Year " + flat.getYear());
                    }
                }

                System.out.print("\nEnter the flat place you want to lease: ");
                String place = scanner.nextLine();

                System.out.print("Enter the number of years for lease: ");
                int rentalYears = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Flat selectedFlat = null;
                for (Flat flat : flats) {
                    if (flat.getPlace().equalsIgnoreCase(place) && flat.isAvailable()) {
                        selectedFlat = flat;
                        break;
                    }
                }

                if (selectedFlat != null) {
                    double totalPrice = selectedFlat.calculatePrice(rentalYears);
                    System.out.println("\n== Lease Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Flat: " + selectedFlat.getPlace() + " - " + selectedFlat.getBhk() + " BHK");
                    System.out.println("Year: " + selectedFlat.getYear());
                    System.out.println("Rental years: " + rentalYears);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirmation (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        leaseFlat(selectedFlat, newCustomer, rentalYears);
                    } else {
                        System.out.println("\nLease canceled.");
                    }
                } else {
                    System.out.println("\nInvalid flat selection or flat not available for lease.");
                }
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Flat Lease System!");
    }
}

public class Main {
    public static void main(String[] args) {
        LeaseSystem leaseSys = new LeaseSystem();

        Flat flat1 = new Flat("noida", 3, 2, 1160);
        Flat flat2 = new Flat("delhi", 1, 4, 2170);
        Flat flat3 = new Flat("mumbai", 5, 1, 3150);
        leaseSys.addFlat(flat1);
        leaseSys.addFlat(flat2);
        leaseSys.addFlat(flat3);

        leaseSys.menu();
    }
}
