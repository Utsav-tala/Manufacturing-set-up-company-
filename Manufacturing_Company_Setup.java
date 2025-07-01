import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

//  main class start
public class Manufacturing_Company_Setup {

    private static final String USERS_FILE = "users.txt"; // user.txt file name store in USER_FILE named string
    private static final String PROFILES_FILE = "profiles.txt"; // profile.txt || || ||

    // main method start
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the LOGIN page!!");

        while (true) { ///////////// option for signup, login and exit.
            System.out.println("\nSelect an option:");
            System.out.println("1. Sign Up (Register)");
            System.out.println("2. Log In");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); /////// Consume newline

            switch (choice) {
                case 1:
                    SignUp.signup_user(); //////////// call signup class
                    break;
                case 2:
                    String loggedin_username = LogIn.login_user();
                    if (loggedin_username != null) {
                        System.out.println("Login successful. You are now logged in!");

                        // Ask questions for business advertisement
                        Business business_service = new Business();
                        business_service.start_business();
                    } else {
                        System.out.println("Wrong username or password. Please try again.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting the LOGIN page!!!!");
                    System.out.println("have a GOOD DAY!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. enter a valid option!!!");
            }
        }
    }

    ///////////// display profile from profile.txt file

    private static void displayUserProfile(String username) {
        System.out.println("\nUser Profile:");
        try (Scanner fileScanner = new Scanner(new File(PROFILES_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(username)) {
                    System.out.println("Username: " + parts[0]);
                    System.out.println("First Name: " + parts[1]);
                    System.out.println("Last Name: " + parts[2]);
                    System.out.println("Mobile Number: " + parts[3]);
                    System.out.println("Gmail: " + parts[4]);
                    return; // Exit after displaying profile
                }
            }
            System.out.println("User profile not found.");
        } catch (IOException e) {
            System.out.println("Error found while reading user profile: " + e.getMessage());
        }
    }
}

//////////////////////////////////////////// signup class start

class SignUp {

    private static final String USERS_FILE = "users.txt"; // user.txt file declare
    private static final String PROFILES_FILE = "profiles.txt"; // profile.txt declare

    public static String signup_user() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSign Up (Register)");

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        // Check if the username already exists
        if (isusername_exists(username)) {
            System.out.println("Username '" + username + "' is already registered. choose a different username.");
            return null; // Exit sign-up process
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your surname: ");
        String lastName = scanner.nextLine();

        String mobileNumber;
        do {
            System.out.print("Enter your mobile number (10 digits): ");
            mobileNumber = scanner.nextLine();
        } while (mobileNumber.length() != 10);

        System.out.print("Enter your Gmail : ");
        String gmail = scanner.nextLine();

        // Save user profile to files
        try (FileWriter usersWriter = new FileWriter(USERS_FILE, true); // here true is for append,not rewrite string
                FileWriter profilesWriter = new FileWriter(PROFILES_FILE, true)) {
            usersWriter.write(username + "," + password + "\n");
            profilesWriter.write(username + "," + firstName + "," + lastName + "," + mobileNumber + "," + gmail + "\n");
            System.out.println("User registration successful!");
            return username; // Return username for successful registration
        } catch (IOException e) {
            System.out.println("Error found while registering user: " + e.getMessage());
            return null;
        }
    }
    /////////////

    private static boolean isusername_exists(String username) {
        try (Scanner fileScanner = new Scanner(new File(USERS_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true; // Username already exists
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while checking username: " + e.getMessage());
        }
        return false; // Username does not exist
    }
}

/////////////////////////////////////////////////////////////
class LogIn {

    private static final String USERS_FILE = "users.txt";

    public static String login_user() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nLog In");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check if username exist in the file
        try (Scanner fileScanner = new Scanner(new File(USERS_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(","); // using split function to split all string and make array named parts
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return username; // Login successful, return the username
                }
            }
        } catch (IOException e) {
            System.out.println("Error found while reading user data: " + e.getMessage());
        }

        return null; // Login failed
    }
}

////////////////////////////////////////////////////////

class Business {
    Scanner scanner = new Scanner(System.in);

    public void start_business() {
        Advertisement advertisement = new Advertisement(); // create an object for advertisement class
        MachineryRepair machinery_repair = new MachineryRepair(); // create an object for machineryRepair class
        new_startup business = new new_startup(); // create an object for new startup class

        System.out.println("\nWelcome to the Business Services!!!!!!");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Advertisement for your business");
            System.out.println("2. Machinery & repairing");
            System.out.println("3. start a new business");
            System.out.println("4. Exit");

            int choice = getint_input();
            scanner.nextLine(); // Consume newline(clear all buffers in next line)

            switch (choice) {
                case 1:
                    System.out.println("Welcome to the advertisement section.");
                    advertisement.Company_info();
                    break;
                case 2:
                    System.out.println("Machinery & repairing section");
                    machinery_repair.repair_machinery();
                    break;
                case 3:
                    System.out.println("start a new business section");
                    business.info_Startup();
                    break;
                case 4:
                    System.out.println("Exiting business services. Goodbye!!!");
                    System.out.println("have a GOOD DAY!!!!1");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. enter a valid option.");
            }
        }
    }

    //////////////////////////

    private int getint_input() {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
        }
    }
}

//////////////////////////////////////////////////////////

class Advertisement {
    Scanner scanner = new Scanner(System.in);

    public void Company_info() {
        System.out.println("\nWelcome to the Advertisement Department.");
        System.out.println("Please provide information about your business.");

        System.out.print("Enter your company name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter your company type: ");
        String companyType = scanner.nextLine();

        System.out.print("Enter your city: ");
        String city = scanner.nextLine();

        String advertisement_goal = get_string_input("What are your advertisement goals? ");
        int years_in_operation = get_int_input("How long have you been in business(in year)(if you start a new bussiness than enter zero)? ");
        double company_valuation = get_double_input("Please enter your company's valuation(in lacs)(if you start a new bussiness than enter zero): ");

        // Handle advertisement options
        advertisement_options();

        // Display user collected information
        System.out.println("\nCompany Name: " + companyName);
        System.out.println("Company Type: " + companyType);
        System.out.println("City: " + city);
        System.out.println("Years in Operation: " + years_in_operation);
        System.out.println("Advertisement Goal: " + advertisement_goal);
        System.out.println("Company Valuation: " + company_valuation);
    }

    ////////////////

    private void advertisement_options() {
        while (true) {
            System.out.println("\nSelect an advertising option:");
            System.out.println("1. Newspaper and Magazine");
            System.out.println("2. TV");
            System.out.println("3. Radio");
            System.out.println("4. Social Media");
            System.out.println("5. Influencer Marketing");
            System.out.println("6. Billboards");
            System.out.println("7. Exit");

            int choice = get_int_input("Enter your choice: ");

            switch (choice) {
                case 1:
                    Newspaper_option();
                    break;
                case 2:
                    TV_option();
                    break;
                case 3:
                    Radio_option();
                    break;
                case 4:
                    socialmedia_option();
                    break;
                case 5:
                    InfluencerMarketing_option();
                    break;
                case 6:
                    Billboards_option();
                    break;
                case 7:
                    System.out.println("Exiting advertisement options.");
                    return;
                default:
                    System.out.println("Invalid choice. enter a valid option.");
            }
        }
    }

    ////////////////////

    private void Newspaper_option() {
        int numberOfNewspapers = get_int_input("Enter the number of newspapers for advertisement: ");
        String[] newspapers = new String[numberOfNewspapers];

        for (int i = 0; i < numberOfNewspapers; i++) {
            System.out.println("Select a newspaper for advertisement:");
            System.out.println("1. Sandesh");
            System.out.println("2. Gujarat Samachar");
            System.out.println("3. Times of India");
            System.out.println("4. Divya Bhaskar");
            System.out.println("5. The Hindu");
            System.out.println("6. Other");

            int newspaperChoice = get_int_input("Enter your choice: ");
            scanner.nextLine();

            switch (newspaperChoice) {
                case 1:
                    newspapers[i] = "Sandesh";
                    break;
                case 2:
                    newspapers[i] = "Gujarat Samachar";
                    break;
                case 3:
                    newspapers[i] = "Times of India";
                    break;
                case 4:
                    newspapers[i] = "Divya Bhaskar";
                    break;
                case 5:
                    newspapers[i] = "The Hindu";
                    break;
                case 6:
                    System.out.print("Enter the name of the newspaper: ");
                    newspapers[i] = scanner.nextLine();
                    // scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid choice. enter a valid option.");
                    i--; // Decrement i to repeat the current iteration
            }
        }

        System.out.println("Selected Newspapers for Advertisement:");
        for (int i = 0; i < numberOfNewspapers; i++) {
            System.out.println((i + 1) + ". " + newspapers[i]);
        }
    }

    ///////////////////////

    private void TV_option() {
        int numberOfTVChannels = get_int_input("Enter the number of TV channels for advertisement: ");
        scanner.nextLine();

        String[] tvChannels = new String[numberOfTVChannels];

        for (int i = 0; i < numberOfTVChannels; i++) {
            System.out.print("Enter TV channel " + (i + 1) + ": ");
            tvChannels[i] = scanner.nextLine();
            // scanner.nextLine(); // Consume newline
        }

        System.out.println("Selected TV Channels for Advertisement:");
        for (int i = 0; i < numberOfTVChannels; i++) {
            System.out.println((i + 1) + ". " + tvChannels[i]);
        }
    }

    ///////////////

    private void Radio_option() {
        System.out.println("Radio advertisement");
        int numberOfradioChannels = get_int_input("Enter the number of radio channels for advertisement: ");
        String[] radioChannels = new String[numberOfradioChannels];
        scanner.nextLine(); // Consume newline

        // Start with index 1 for TV channel naming
        for (int i = 0; i < numberOfradioChannels; i++) {
            System.out.print("Enter RADIO channel name " + (i + 1) + ": ");
            radioChannels[i] = scanner.nextLine();
            // scanner.nextLine(); // Consume newline
        }

        System.out.println("Selected RADIO Channels for Advertisement:");
        for (int i = 0; i < numberOfradioChannels; i++) {
            System.out.println((i + 1) + ". " + radioChannels[i]);
        }
        // Implement radio advertisement logic here
        System.out.println("Target audience reached through radio ads.");
    }

    ///////////////

    private void socialmedia_option() {
        System.out.println("Social Media advertisement");

        int numberOfsocialmediaApps = get_int_input("Enter the number of social media apps for advertisement: ");
        String[] socialApps = new String[numberOfsocialmediaApps];
        scanner.nextLine(); // Consume newline after reading the integer

        // Start with index 1 for TV channel naming
        for (int i = 0; i < numberOfsocialmediaApps; i++) {
            System.out.print("Enter SOCIAL MEDIA APPS name " + (i + 1) + ": ");
            socialApps[i] = scanner.nextLine();
        }

        System.out.println("Selected social media apps for Advertisement:");
        for (int i = 0; i < numberOfsocialmediaApps; i++) {
            System.out.println((i + 1) + ". " + socialApps[i]);
        }
        // Implement social media advertisement logic here
        System.out.println("Engage customers through social media platforms.");
    }

    ////////////

    private void InfluencerMarketing_option() {
        System.out.println("Influencer Marketing option");

        int numberOfinfluncers = get_int_input("Enter the number of influncers for advertisement: ");
        String[] influncers = new String[numberOfinfluncers];
        scanner.nextLine(); // Consume newline after reading the integer

        // Start with index 1 for TV channel naming
        for (int i = 0; i < numberOfinfluncers; i++) {
            System.out.print("Enter INFLUNCRES name " + (i + 1) + ": ");
            influncers[i] = scanner.nextLine();
        }

        System.out.println("Selected influncers for Advertisement:");
        for (int i = 0; i < numberOfinfluncers; i++) {
            System.out.println((i + 1) + ". " + influncers[i]);
        }
        // Implement influencer marketing logic here
        System.out.println("Collaborate with influencers to promote the business.");
    }

    ////////////////

    private void Billboards_option() {
        System.out.println("Billboards advertisement option");

        int totalnumberOfbillboards = get_int_input("Enter the  total number of billboards for advertisement: ");
        int totalnumberOfarea = get_int_input("Enter the  total number of area for advertisement: ");
        String[] billboards = new String[totalnumberOfarea];
        scanner.nextLine(); // Consume newline after reading the integer

        // Start with index 1 for TV channel naming
        for (int i = 0; i < totalnumberOfarea; i++) {
            System.out.print("Enter area name " + (i + 1) + ": ");
            billboards[i] = scanner.nextLine();
        }

        System.out.println("Selected area for Advertisement:");
        for (int i = 0; i < totalnumberOfarea; i++) {
            System.out.println((i + 1) + ". " + billboards[i]);
        }
        // Implement billboards advertisement logic here
        System.out.println("Display company ads on strategic billboard locations.");
    }

    /////////////////

    private int get_int_input(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    ////////////////

    private double get_double_input(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    ///////////////

    private String get_string_input(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
//////////////////////////////////////////////////////////

class MachineryRepair {
    Scanner scanner = new Scanner(System.in);

    public void repair_machinery() {
        System.out.println("\nWelcome to the machinery repair section.");
        System.out.println("Please give some information about your machinery problem.");

        String company_Name = get_string_input("Enter your company name: "); // input a company name
        String company_Type = get_string_input("Enter your company type: "); // input a company type
        String city = get_string_input("Enter your city: "); // input a city name

        int company_Year = get_iteger_input("How long have you owned and operated this business(in year)? ");
        double company_Valuation = get_double_input("Please enter the valuation of your company(in lacs): ");
        scanner.nextLine(); // to clear buffer

        String machinery_Type = get_string_input("Enter the type of machinery problem: ");
        String problem_Description = get_string_input("Describe the problem with your machinery: ");

        System.out.println("\nBased on the problem description, it seems that the issue might be related to the " + machinery_Type);
        System.out.println("Here are some possible parts that could be causing the problem:");
        System.out.println("-Unknown ");
        // Implement logic to suggest machinery parts based on problem description

        // contact information for mechanical worker
        System.out.println("\nFor further assistance, please contact a mechanical worker at:9812989870");

        // approximate budget for repairing
        double approximate_Budget = calculate_approximate_budget();
        System.out.println("Your Approximate budget for repairing: " + approximate_Budget + " rupees");
    }
    //////////////////

    private double calculate_approximate_budget() {
        // Implement logic to calculate approximate budget for repairing based on
        // problem description
        return 5000.0; // give a fix budget for all cases
    }
    ///////////////////

    private int get_iteger_input(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    /////////////////////

    private double get_double_input(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    ////////////////////////

    private String get_string_input(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}

////////////////////////////////////////////////////////

class new_startup {
    Scanner scanner = new Scanner(System.in);

    void info_Startup() {
        System.out.println("\nWelcome to the New Business Startup!!!!!");

        System.out.print("Do you have a any business idea for a new startup? (1 for yes, 0 for no): ");
        int response = scanner.nextInt();

        if (response == 0) {
            // give ideas for startup
            System.out.println("Here are 10 business ideas you can consider:");
            business_ideas();
            System.out.println("Enter a choice : ");
            int businessChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after reading intput

        } else if (response == 1) {
            // input a business idea from user
            String businessIdea = getstring_input("Enter your business idea: ");
            scanner.nextLine(); // Consume newline after reading intput

        } else {
            System.out.println("Invalid response. Please try again.");
            return;
        }

        String businessGoal = getstring_input("What is the goal behind starting this business? ");
        double approximateBudget = getdouble_input("Approximate budget for this business(in lacs): ");

        // Check if user has land for business
        System.out.print("Do you have land for this business? (1 for yes, 0 for no): ");
        if (scanner.nextInt() == 1) {
            String landAddress = getstring_input("Enter the address of the your land: "); // input a land address
            scanner.nextLine();
        } else {
            select_City_inGujarat(); ////// selection of city in gujarat
        }

        // Calculate budget and timeline
        double total_budget = calculate_total_budget(approximateBudget);
        int timeline_in_months = calculate_time_line();

        System.out.println("Approximate total budget for starting the business: " + total_budget + " lacs");
        System.out.println("Timeline to start the business: " + timeline_in_months + " months");

        // Check if user wants to advertise the business
        System.out.print("Do you want to advertise this business? (1 for yes, 2 for no): ");
        int advertise_response = scanner.nextInt();
        scanner.nextLine(); // Consume newline after reading int

        if (advertise_response == 1) {
            Advertisement advertisement = new Advertisement();
            advertisement.Company_info();
        } else {
            System.out.println("thank you for your responce!!!!!!!");
        }
    }

    /////////////////

    private void business_ideas() {
        System.out.println("1. Online Clothing Boutique");
        System.out.println("2. Fitness Coaching and Training");
        System.out.println("3. Mobile App Development");
        System.out.println("4. Healthy Meal Delivery Service");
        System.out.println("5. Digital Marketing Agency");
        System.out.println("6. Handmade Crafts and Artwork");
        System.out.println("7. Event Planning and Coordination");
        System.out.println("8. Home Cleaning and Organization");
        System.out.println("9. Pet Grooming and Daycare");
        System.out.println("10. Local Food Truck");
    }
    /////////////

    private void select_City_inGujarat() {
        System.out.println("\nSelect a city in Gujarat to start your business:");
        System.out.println("1. Ahmedabad");
        System.out.println("2. Surat");
        System.out.println("3. Vadodara");
        System.out.println("4. Rajkot");
        System.out.println("5. Bhavnagar");
        System.out.println("6. Jamnagar");
        System.out.println("7. Junagadh");
        System.out.println("8. Gandhinagar");
        System.out.println("9. Anand");
        System.out.println("10. Bharuch");

        System.out.print("\nEnter the number corresponding to the city in Gujarat for your business: ");
        int cityChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline 

        switch (cityChoice) {
            case 1:
                city_locations("Ahmedabad");
                break;
            case 2:
                city_locations("Surat");
                break;
            case 3:
                city_locations("Vadodara");
                break;
            case 4:
                city_locations("Rajkot");
                break;
            case 5:
                city_locations("Bhavnagar");
                break;
            case 6:
                city_locations("Jamnagar");
                break;
            case 7:
                city_locations("Junagadh");
                break;
            case 8:
                city_locations("Gandhinagar");
                break;
            case 9:
                city_locations("Anand");
                break;
            case 10:
                city_locations("Bharuch");
                break;
            default:
                System.out.println("Invalid city choice. Please try again.");
                select_City_inGujarat(); // Retry city selection
        }
    }
    ///////////////////

    private void city_locations(String city) {
        System.out.println("\nBest places in " + city + " for your business:");

        switch (city) {
            case "Ahmedabad":
                System.out.println("1. SG Highway");
                System.out.println("2. Vastrapur");
                System.out.println("3. Manek Chowk");
                System.out.println("4. Thaltej");
                System.out.println("5. Prahlad Nagar");
                break;
            case "Surat":
                System.out.println("1. Adajan");
                System.out.println("2. Ghod Dod Road");
                System.out.println("3. Dumas Road");
                System.out.println("4. Piplod");
                System.out.println("5. Vesu");
                break;
            case "Vadodara":
                System.out.println("1. Alkapuri");
                System.out.println("2. Sayajigunj");
                System.out.println("3. Fatehgunj");
                System.out.println("4. Gotri");
                System.out.println("5. Akota");
                break;
            case "Rajkot":
                System.out.println("1. Yagnik Road");
                System.out.println("2. Kalawad Road");
                System.out.println("3. Jubilee Garden");
                System.out.println("4. Race Course");
                System.out.println("5. Amin Marg");
                break;
            case "Bhavnagar":
                System.out.println("1. Ghogha Circle");
                System.out.println("2. Waghawadi Road");
                System.out.println("3. Talaja Road");
                System.out.println("4. Gandhi Nagar");
                System.out.println("5. Kaliabid");
                break;
            case "Jamnagar":
                System.out.println("1. Digjam Circle");
                System.out.println("2. Bedi Bandar Road");
                System.out.println("3. Park Colony");
                System.out.println("4. Dared");
                System.out.println("5. Shiv Colony");
                break;
            case "Junagadh":
                System.out.println("1. MG Road");
                System.out.println("2. Zanzarda Road");
                System.out.println("3. Timbavadi");
                System.out.println("4. Kalva Chowk");
                System.out.println("5. Moti Baug");
                break;
            case "Gandhinagar":
                System.out.println("1. Sector 16");
                System.out.println("2. Sector 22");
                System.out.println("3. Sector 29");
                System.out.println("4. Sector 11");
                System.out.println("5. Sector 6");
                break;
            case "Anand":
                System.out.println("1. Anand-Vidyanagar Road");
                System.out.println("2. Lambhvel Road");
                System.out.println("3. Vallabh Vidyanagar");
                System.out.println("4. Karamsad");
                System.out.println("5. Bakrol");
                break;
            case "Bharuch":
                System.out.println("1. Ankleshwar");
                System.out.println("2. Dahej");
                System.out.println("3. Jhagadia");
                System.out.println("4. Amod");
                System.out.println("5. Bharuch City");
                break;
            default:
                System.out.println("Invalid city. Please try again.");
                select_City_inGujarat(); // Retry city selection
        }
        String final_location = getstring_input("enter a final location name :"); // input a final location name from user
    }

    //////////////////

    //  methods for string input
    private String getstring_input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    ////////////// method for double input
    private double getdouble_input(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextDouble();
    }

    //////////////////

    private double calculate_total_budget(double approximateBudget) {
        // Implement logic to calculate total budget for startup
        return approximateBudget * 1.3; // here we give approximate budget which is 1.3 times of users approximate budjet
    }

    //////////////////

    private int calculate_time_line() {
        // Implement logic to calculate timeline for startup
        return 10; // fix timining 10 months
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////