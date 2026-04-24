import java.util.*;

public class FLCSystem {

    static List<Member> members = new ArrayList<>();
    static List<Lesson> lessons = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        initMembers();
        initLessons();
        generateSampleReviews();

        while (true) {
            showMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1 -> bookLesson();
                case 2 -> changeBooking();
                case 3 -> cancelBooking();
                case 4 -> attendLesson();
                case 5 -> monthlyReport();
                case 6 -> championReport();
                case 7 -> {
                    System.out.println("Exiting system...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void showMenu() {
        System.out.println("\n====================================");
        System.out.println("        FLC BOOKING SYSTEM");
        System.out.println("====================================");
        System.out.println(" 1. Book Lesson");
        System.out.println(" 2. Change Booking");
        System.out.println(" 3. Cancel Booking");
        System.out.println(" 4. Attend Lesson");
        System.out.println(" 5. Monthly Report");
        System.out.println(" 6. Champion Report");
        System.out.println(" 7. Exit");
        System.out.println("====================================");
        System.out.print("Select option → ");
    }

    static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    static void initMembers() {
        String[] names = {
                "Ali Khan","Sara Ahmed","John Smith","Emily Davis",
                "Mohammed Hassan","Aisha Malik","David Brown",
                "Sophia Wilson","Daniel Taylor","Olivia Thomas",
                "James Anderson","Mia Clark","Noah White","Lily Walker"
        };

        List<String> list = Arrays.asList(names);
        Collections.shuffle(list);

        for (int i = 0; i < 10; i++) {
            
        }
    }

    static void initLessons() {
        String[] exercises = {"Yoga", "Zumba", "BoxFit", "BodyBlitz", "Aquacise"};
        String[] days = {"Saturday", "Sunday"};
        String[] times = {"Morning", "Afternoon", "Evening"};
        Map<String, Double> priceMap = Map.of(
                "Yoga", 10.0,
                "Zumba", 12.0,
                "BoxFit", 15.0,
                "BodyBlitz", 14.0,
                "Aquacise", 13.0
        );
        for (int week = 1; week <= 8; week++) {
            for (String d : days) {
                for (int i = 0; i < 3; i++) {

                    String exercise = exercises[(week + i) % exercises.length];

                    double price = priceMap.get(exercise);
                    lessons.add(new Lesson(
                            exercise,
                            d,
                            times[i],
                            week,
                            price
                    ));
                }
            }
        }
    }

    static void displayMembers() {
        System.out.println("\n----------- MEMBERS -----------");
        System.out.printf("%-5s %-20s\n", "ID", "NAME");
        System.out.println("--------------------------------");

        for (Member m : members) {
            System.out.printf("%-5d %-20s\n", m.getId(), m.getName());
        }
    }

    static void displayLessons(List<Lesson> list) {
        System.out.println("\n------------- LESSONS -------------");
        System.out.printf("%-5s %-12s %-6s %-10s %-10s %-6s\n",
                "No", "Exercise", "Week", "Day", "Time", "Price");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < list.size(); i++) {
            Lesson l = list.get(i);
            System.out.printf("%-5d %-12s %-6d %-10s %-10s %-6.2f\n",
                    i,
                    l.getExercise(),
                    l.getWeek(),
                    l.getDay(),
                    l.getTime(),
                    l.getPrice());
        }
    }

    static void displayBookings() {
        System.out.println("\n------------- BOOKINGS -------------");
        System.out.printf("%-5s %-15s %-10s %-6s %-10s %-10s %-10s\n",
                "ID", "Member", "Exercise", "Week", "Day", "Time", "Status");

        for (Booking b : bookings) {
            System.out.printf("%-5d %-15s %-10s %-6d %-10s %-10s %-10s\n",
                    b.getId(),
                    b.getMember().getName(),
                    b.getLesson().getExercise(),
                    b.getLesson().getWeek(),
                    b.getLesson().getDay(),
                    b.getLesson().getTime(),
                    b.getStatus());
        }
    }

    static List<Lesson> filterLessons() {
        System.out.println("\n=========== SEARCH LESSONS ===========");
        System.out.println("1. View by Day");
        System.out.println("2. View by Exercise");
        System.out.print("Choose option: ");

        int opt = getIntInput();
        List<Lesson> result = new ArrayList<>();

        if (opt != 1 && opt != 2) {
            System.out.println("Invalid option!");
            return new ArrayList<>();
        }

        if (opt == 1) {
            System.out.println("\n1. Saturday\n2. Sunday");
            System.out.print("Choose day: ");
            int d = getIntInput();
            String day = (d == 1) ? "Saturday" : "Sunday";

            for (Lesson l : lessons)
                if (l.getDay().equalsIgnoreCase(day)) result.add(l);

        } else if (opt == 2) {
            System.out.println("\n1. Yoga\n2. Zumba\n3. BoxFit\n4. BodyBlitz");
            System.out.print("Choose exercise: ");
            int e = getIntInput();

            String ex = switch (e) {
                case 1 -> "Yoga";
                case 2 -> "Zumba";
                case 3 -> "BoxFit";
                case 4 -> "BodyBlitz";
                default -> "";
            };

            for (Lesson l : lessons)
                if (l.getExercise().equalsIgnoreCase(ex)) result.add(l);
        }

        displayLessons(result);
        return result;
    }

    static void bookLesson() {
        displayMembers();
        System.out.print("\nEnter Member ID: ");
        Member m = members.get(getIntInput() - 1);

        List<Lesson> list = filterLessons();
        System.out.print("\nSelect lesson number: ");
        Lesson l = list.get(getIntInput());

        for (Booking b : bookings) {
            if (b.getMember() == m && b.getLesson() == l && b.getStatus() != Status.CANCELLED) {
                System.out.println(" Duplicate booking!");
                return;
            }

            if (b.getMember() == m &&
                    b.getLesson().getDay().equals(l.getDay()) &&
                    b.getLesson().getTime().equals(l.getTime()) &&
                    b.getStatus() != Status.CANCELLED) {
                System.out.println(" Time conflict!");
                return;
            }
        }

        if (l.hasSpace()) {
            Booking b = new Booking(m, l);
            bookings.add(b);
            l.addBooking(b);
            System.out.println("Booked successfully! ID: " + b.getId());
        } else {
            System.out.println("Lesson Full!");
        }
    }

    static void changeBooking() {
        displayBookings();
        System.out.print("\nEnter Booking ID: ");
        Booking b = find(getIntInput());

        if (b == null) {
            System.out.println("Invalid ID!");
            return;
        }

        if (b.getStatus() == Status.ATTENDED) {
            System.out.println("Cannot change an attended booking!");
            return;
        }

        if (b.getStatus() == Status.CANCELLED) {
            System.out.println("Cannot change a cancelled booking!");
            return;
        }

        List<Lesson> list = filterLessons();

        if (list.isEmpty()) {
            System.out.println("No lessons available to select!");
            return;
        }

        System.out.print("\nSelect new lesson: ");
        int index = getIntInput();

        if (index < 0 || index >= list.size()) {
            System.out.println("Invalid lesson number!");
            return;
        }

        Lesson newL = list.get(index);

        if (!newL.hasSpace()) {
            System.out.println("Lesson Full!");
            return;
        }

        b.getLesson().removeBooking(b);
        b.setLesson(newL);
        newL.addBooking(b);
        b.setStatus(Status.CHANGED);

        System.out.println("Booking changed!");
    }

    static void cancelBooking() {
        displayBookings();
        System.out.print("\nEnter Booking ID: ");
        Booking b = find(getIntInput());

        if (b == null) {
            System.out.println("Invalid ID!");
            return;
        }

        if (b.getStatus() == Status.ATTENDED) {
            System.out.println("Cannot cancel an attended lesson!");
            return;
        }

        if (b.getStatus() == Status.CANCELLED) {
            System.out.println("Already cancelled!");
            return;
        }

        b.setStatus(Status.CANCELLED);
        b.getLesson().removeBooking(b);

        System.out.println("Cancelled!");
    }

    static void attendLesson() {
        displayBookings();
        System.out.print("\nEnter Booking ID: ");
        Booking b = find(getIntInput());

        if (b == null) {
            System.out.println(" Invalid ID!");
            return;
        }

        if (b.getStatus() == Status.ATTENDED) {
            System.out.println(" Already attended!");
            return;
        }

        if (b.getStatus() == Status.CANCELLED) {
            System.out.println(" Cannot attend a cancelled booking!");
            return;
        }

        b.setStatus(Status.ATTENDED);

        sc.nextLine();
        System.out.print("Write review: ");
        String text = sc.nextLine();

        System.out.print("Rating (1-5): ");
        int rating = getIntInput();

        b.getLesson().addReview(new Review(text, rating));

        System.out.println(" Lesson attended & review saved!");
    }

    static void monthlyReport() {
        System.out.println("\nMonth Selection:");
        System.out.println("1 = Weeks 1–4");
        System.out.println("2 = Weeks 5–8");
        System.out.print("Enter Month: ");

        int month = getIntInput();

        int start = (month == 1) ? 1 : 5;
        int end = start + 3;

        System.out.println("\n----------- FULL MONTHLY REPORT -----------");

        for (int week = start; week <= end; week++) {

            System.out.println("\n===== WEEK " + week + " =====");

            for (Lesson l : lessons) {
                if (l.getWeek() == week) {

                    long total = l.getBookings().stream()
                            .filter(b -> b.getStatus() != Status.CANCELLED)
                            .count();

                    long attended = l.getBookings().stream()
                            .filter(b -> b.getStatus() == Status.ATTENDED)
                            .count();

                    double avg = (attended > 0) ? l.getAvgRating() : 0.0;

                    System.out.printf(
                            "%-10s %-10s %-10s Total:%-2d Attended:%-2d Avg:%.2f\n",
                            l.getExercise(),
                            l.getDay(),
                            l.getTime(),
                            total,
                            attended,
                            avg
                    );
                }
            }
        }
    }

    static void championReport() {
        System.out.println("\n Month Selection:");
        System.out.println("1 = Weeks 1–4");
        System.out.println("2 = Weeks 5–8");
        System.out.print("Enter Month: ");

        int month = getIntInput();

        int start = (month == 1) ? 1 : 5;
        int end = start + 3;

        Map<String, Double> income = new HashMap<>();

        for (Lesson l : lessons) {
            if (l.getWeek() >= start && l.getWeek() <= end) {

                long count = l.getBookings().stream()
                        .filter(b -> b.getStatus() != Status.CANCELLED)
                        .count();

                income.put(l.getExercise(),
                        income.getOrDefault(l.getExercise(), 0.0) + count * l.getPrice());
            }
        }

        System.out.println("\n----------- INCOME REPORT -----------");

        String best = "";
        double max = 0;

        for (String k : income.keySet()) {
            double value = income.get(k);
            System.out.println(k + " = " + value);

            if (value > max) {
                max = value;
                best = k;
            }
        }

        if (max == 0) {
            System.out.println(" No income generated in this month!");
        } else {
            System.out.println(" Champion Exercise: " + best);
        }
    }

    static void generateSampleReviews() {
        Random r = new Random();
        for (int i = 0; i < 25; i++) {
            Lesson l = lessons.get(r.nextInt(lessons.size()));
            l.addReview(new Review("Good session", r.nextInt(5) + 1));
        }
    }

    static Booking find(int id) {
        return bookings.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
