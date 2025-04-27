package nz.ac.auckland.se281;

public class PublicReview extends Review {
    private boolean isAnonymous;

    public PublicReview(String reviewId, String reviewerName, int rating, String reviewText, boolean isAnonymous) {
        super(reviewId, reviewerName, rating, reviewText);
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String gettype() {
        return "Public";
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }
}