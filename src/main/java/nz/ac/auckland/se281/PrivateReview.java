package nz.ac.auckland.se281;

public class PrivateReview extends Review {

  private String email;
  private boolean followUp;

  public PrivateReview(
      String reviewId,
      String reviewerName,
      int rating,
      String reviewText,
      String email,
      boolean followUp) {

    super(reviewId, reviewerName, rating, reviewText);
    this.email = email;
    this.followUp = followUp;
  }

  @Override
  public String gettype() {
    return "Private";
  }

  public String getEmail() {
    return email;
  }

  public boolean isFollowUp() {
    return followUp;
  }
}
