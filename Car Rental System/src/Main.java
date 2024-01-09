import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;
    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }
}

class Customer {
    private String customerId;
    private String name;
    private String mobileNumber;

    public Customer(String customerId, String name, String mobileNumber) {
        this.customerId = customerId;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}

class Rental {
    private Car car;
    private Customer customer;

    // Days for rent of car
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomers(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("==*****=== CAR RENTAL SYSTEM ===*****==");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1) {
                System.out.println("\n==**== RENT A CAR ==**==\n");
                boolean allCarsRented = true;
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        allCarsRented = false;
                        break;
                    }
                }
                if (allCarsRented) {
                    System.out.println("Soory, No cars are available for rent at the moment. Please try again later.\n");
                    continue;
                }
                System.out.print("Enter your name : ");
                String cutomerName = sc.nextLine();

                System.out.print("Enter your mobile number : ");
                String mobileNumber = sc.nextLine();

                System.out.println("\nAvailable Cars");
                System.out.println("------------------");
                for(Car car : cars) {
                    if(car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }

                System.out.print("\nEnter the Car ID which you want to take on rent : ");
                String carId = sc.nextLine();

                System.out.print("Enter the number of days for rental : ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), cutomerName, mobileNumber);
                addCustomers(newCustomer);

                Car selectedCar = null;
                for(Car car : cars) {
                    if(car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==**== Rental Information ==**==\n");
                    System.out.println("Customer ID : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name : " + newCustomer.getName());
                    System.out.println("Customer Mobile Number : " + newCustomer.getMobileNumber());
                    System.out.println("Car ID : " + selectedCar.getCarId());
                    System.out.println("Car : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days : " + rentalDays);
                    System.out.printf("Total Price : $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n Car rented Successfully !!!\n");
                    }
                    else {
                        System.out.println("Rental Cancelled");
                    }
                }
                else {
                    System.out.println("\n Sorry, selected car is not available for rent\n");
                }
            }

            else if(choice == 2) {
                System.out.println("\n===***== Return a Car ==**===\n");
                System.out.print("Enter the car ID which you want to return : ");
                String carID = sc.nextLine();

                Car carToReturn = null;
                for(Car car : cars) {
                    if(car.getCarId().equals(carID) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null) {
                    Customer customer = null;
                    for(Rental rental : rentals) {
                        if(rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    }
                    else {
                        System.out.println("\nCar is not returned or rental information is incomplete or wrong !!!\n");
                    }
                }

                else {
                    System.out.println("Invalid car ID or car is not rented !!!");
                }
            }

            else if(choice == 3) {
                System.out.println("\nThank you for using the Car Rental System !!!");
                System.exit(0);
            }

            else {
                System.out.println("Invalid Choice !! Please enter a valid option...\n");
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {

        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Fortuner", 60.0);
        Car car2 = new Car("C002", "Mahindra", "Scorpio", 80.0);
        Car car3 = new Car("C003", "Hyundai", "Creta", 50.0);
        Car car4 = new Car("C004", "Mahindra", "Thar", 250.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);

        rentalSystem.menu();
    }
}