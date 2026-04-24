public class Booking {
    private static int counter = 1;

    private int id;
    private Member member;
    private Lesson lesson;
    private Status status;

    public Booking(Member m, Lesson l) {
        this.id = counter++;
        this.member = m;
        this.lesson = l;
        this.status = Status.BOOKED;
    }

    
    public Member getMember() { return member; }
    public Lesson getLesson() { return lesson; }

    public void setLesson(Lesson l) { this.lesson = l; }

    public Status getStatus() { return status; }
    public void setStatus(Status s) { this.status = s; }
}