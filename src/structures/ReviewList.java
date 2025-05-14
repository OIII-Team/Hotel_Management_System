package structures;
import model.Review;
import java.util.List;
import java.util.ArrayList;

public class ReviewList
{
    private List<Review> reviews;

    public ReviewList() {
        this.reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
        }
    }
    public boolean removeReview(Review review) {
        return reviews.remove(review);
    }

    public int size() {
        return reviews.size();
    }

    public List<Review> asList() {
        return List.copyOf(reviews);
    }

    public boolean isEmpty()
    {
        return reviews.isEmpty();
    }
}
