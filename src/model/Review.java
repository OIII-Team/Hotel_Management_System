package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import structures.ReviewList;


public class Review
{
    private User authorName;
    private Hotel hotel;
    private int rating;
    private String comment;
    private LocalDateTime commentDate;
    private ReviewList reviewList;

    public Review(User authorName, Hotel hotel, int rating, String comment, LocalDateTime dateTime)
    {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }

        this.authorName = authorName;
        this.hotel = hotel;
        this.rating = rating;
        this.comment = comment;
        this.commentDate = dateTime;
        ReviewList list = hotel.getReview();
        if (list == null) {
            list = new ReviewList();
            hotel.setReview(list);
        }
    }
    public String getAuthorName()           { return authorName.getName();}
    public String getHotelName()      { return hotel.getName(); }
    public double getRating()            { return hotel.getRating(); }
    public String getComment()        { return comment; }
    public LocalDateTime getDateTime(){ return commentDate; }

    public void printReview()
    {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm");
        System.out.println("\nReview by " + authorName.getName() + " for " + getHotelName());
        System.out.println("Rating: " + rating);
        System.out.println("Comment: " + comment);
        System.out.println("Date: " + commentDate.format(fmt));
        System.out.print("\n");
    }

    public ReviewList addReviewToList(Review review)
    {
        if (reviewList == null)
        {
            reviewList = new ReviewList();
        }
        reviewList.addReview(review);
        return reviewList;
    }

    public static Review create(User userName, Hotel hotel, int rating, String comment, LocalDateTime when){
        Review r = new Review(userName, hotel, rating, comment, when);
        hotel.addReview(r);
        return r;
    }


}
