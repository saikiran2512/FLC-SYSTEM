import java.util.*;

public class Lesson {
    private String exercise;
    private String day;
    private String time;
    private int week;
    private double price;

    private List<Booking> bookings = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public Lesson(String ex, String day, String time, int week, double price) {
        this.exercise = ex;
        this.day = day;
        this.time = time;
        this.week = week;
        this.price = price;
    }

    public boolean hasSpace() {
        return bookings.size() < 4;
    }

    public void addBooking(Booking b) { bookings.add(b); }
    public void removeBooking(Booking b) { bookings.remove(b); }

    public void addReview(Review r) { reviews.add(r); }

    public double getAvgRating() {
        if (reviews.isEmpty()) return 0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0);
    }

    public List<Booking> getBookings() { return bookings; }
    public List<Review> getReviews() { return reviews; }

    public String getExercise() { return exercise; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public int getWeek() { return week; }
    public double getPrice() { return price; }
}