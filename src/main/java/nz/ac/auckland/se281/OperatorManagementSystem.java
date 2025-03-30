package nz.ac.auckland.se281;

public class OperatorManagementSystem {

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {
    // TODO implement
    System.out.println("There are no matching operators found.");
  }

  public void createOperator(String operatorName, String location) {
    // TODO implement
    Types.Location locationEnum = Types.Location.fromString(location);
    String[] words = operatorName.split(" ");
    StringBuilder abbreviation = new StringBuilder();
    for (String word : words) {
      if (!word.isEmpty()) {
        abbreviation.append(word.charAt(0));
      }
    }
    switch (locationEnum) {
      case AKL:
        System.out.println(
            "Successfully created operator '"
                + operatorName
                + "' ('"
                + abbreviation
                + "-"
                + locationEnum.getLocationAbbreviation()
                + "-"
                + "') located in '"
                + locationEnum.getFullName()
                + "'.");
        break;

      default:
        break;
    }
  }

  public void viewActivities(String operatorId) {
    // TODO implement
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    // TODO implement
  }

  public void searchActivities(String keyword) {
    // TODO implement
  }

  public void addPublicReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addPrivateReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addExpertReview(String activityId, String[] options) {
    // TODO implement
  }

  public void displayReviews(String activityId) {
    // TODO implement
  }

  public void endorseReview(String reviewId) {
    // TODO implement
  }

  public void resolveReview(String reviewId, String response) {
    // TODO implement
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    // TODO implement
  }

  public void displayTopActivities() {
    // TODO implement
  }
}
