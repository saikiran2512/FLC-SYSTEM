public class Review {
    private String comment;
    private int rating;

    public Review(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
    public String getComment() {
        return comment;
    }
}