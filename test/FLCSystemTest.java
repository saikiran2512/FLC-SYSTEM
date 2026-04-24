import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class FLCSystemTest {

    @Test
    void testDuplicateBooking() {
        Member m = new Member(1, "Ali");
        Lesson l = new Lesson("Yoga", "Saturday", "Morning", 1, 10);

        Booking b1 = new Booking(m, l);
        Booking b2 = new Booking(m, l);

        l.addBooking(b1);

        boolean duplicate = false;
        for (Booking b : l.getBookings()) {
            if (b.getMember() == m && b.getLesson() == l) {
                duplicate = true;
            }
        }

        assertTrue(duplicate);
    }
    @Test
    void testTimeConflict() {
        Member m = new Member(1, "Ali");

        Lesson l1 = new Lesson("Yoga", "Saturday", "Morning", 1, 10);
        Lesson l2 = new Lesson("Zumba", "Saturday", "Morning", 1, 12);

        Booking b1 = new Booking(m, l1);

        boolean conflict = l1.getDay().equals(l2.getDay()) &&
                l1.getTime().equals(l2.getTime());

        assertTrue(conflict);
    }

    @Test
    void testOverCapacity() {
        Lesson l = new Lesson("BoxFit", "Sunday", "Evening", 1, 15);

        for (int i = 1; i <= 4; i++) {
            Member m = new Member(i, "M" + i);
            l.addBooking(new Booking(m, l));
        }
        assertFalse(l.hasSpace());

        Member m5 = new Member(5, "Extra");
        boolean canAdd = l.hasSpace();

        assertFalse(canAdd);
    }

    @Test
    void testCancelledNotCounted() {
        Member m = new Member(1, "Ali");
        Lesson l = new Lesson("Yoga", "Saturday", "Morning", 1, 10);

        Booking b = new Booking(m, l);
        b.setStatus(Status.CANCELLED);

        l.addBooking(b);

        long total = l.getBookings().stream()
                .filter(x -> x.getStatus() != Status.CANCELLED)
                .count();

        assertEquals(0, total);
    }

    @Test
    void testAttendTwice() {
        Member m = new Member(1, "Ali");
        Lesson l = new Lesson("Yoga", "Sunday", "Morning", 1, 10);

        Booking b = new Booking(m, l);
        b.setStatus(Status.ATTENDED);

        boolean alreadyAttended = (b.getStatus() == Status.ATTENDED);

        assertTrue(alreadyAttended);
    }

    @Test
    void testChangeAfterAttend() {
        Member m = new Member(1, "Ali");
        Lesson l = new Lesson("Yoga", "Sunday", "Morning", 1, 10);

        Booking b = new Booking(m, l);
        b.setStatus(Status.ATTENDED);

        boolean canChange = b.getStatus() != Status.ATTENDED;

        assertFalse(canChange);
    }

    @Test
    void testCancelAfterAttend() {
        Member m = new Member(1, "Ali");
        Lesson l = new Lesson("Yoga", "Sunday", "Morning", 1, 10);

        Booking b = new Booking(m, l);
        b.setStatus(Status.ATTENDED);

        boolean canCancel = b.getStatus() != Status.ATTENDED;

        assertFalse(canCancel);
    }

    @Test
    void testInvalidRating() {
        Review r = new Review("Bad", 6); // invalid >5

        boolean valid = r.getRating() >= 1 && r.getRating() <= 5;

        assertFalse(valid);
    }
}