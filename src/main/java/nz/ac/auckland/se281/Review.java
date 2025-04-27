package nz.ac.auckland.se281;

public abstract class Review {
  // Fields
  protected String reviewId;
  protected String reviewerName;
  protected int rating;
  protected String reviewText;

  // Constructor

  public Review(String reviewId, String reviewerName, int rating, String reviewText) {
    this.reviewId = reviewId;
    this.reviewerName = reviewerName;
    this.rating = rating;
    this.reviewText = reviewText;
  }

  public String getReviewId() {
    return reviewId;
  }

  public int getRating() {
    return rating;
  }

  public abstract String gettype();
}
