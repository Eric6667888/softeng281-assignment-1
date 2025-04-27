package nz.ac.auckland.se281;

public class ExpertReview extends Review {
  private boolean isRecommended;

  public ExpertReview(
      String reviewId, String reviewerName, int rating, String reviewText, boolean isRecommended) {
    super(reviewId, reviewerName, rating, reviewText);
    this.isRecommended = isRecommended;
  }

  @Override
  public String gettype() {
    return "Expert";
  }

  public boolean isRecommended() {
    return isRecommended;
  }
}
