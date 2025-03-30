package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.Map;

public class OperatorManagementSystem {
  private Map<String, Integer> LocationCount = new HashMap<>();

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {

    System.out.println("There are no matching operators found.");
  }

  public void createOperator(String operatorName, String location) {

    // Count the number of operators in the given location
    int count = LocationCount.getOrDefault(location, 0) + 1;
    LocationCount.put(location, count);

    // Format the count to be 3 digits with leading zeros
    String locationCount;
    if (count <= 9) {
      locationCount = "00" + count;
    } else if (count <= 99) {
      locationCount = "0" + count;
    } else {
      locationCount = "" + count;
    }

    Types.Location locationEnum = Types.Location.fromString(location);
    // Find the abbreviation of the operator name
    String[] words = operatorName.split(" ");
    StringBuilder abbreviation = new StringBuilder();
    for (String word : words) {
      if (!word.isEmpty()) {
        abbreviation.append(word.charAt(0));
      }
    }

    // Print the Created Operator message(success message)
    System.out.println(
        "Successfully created operator '"
            + operatorName
            + "' ('"
            + abbreviation
            + "-"
            + locationEnum.getLocationAbbreviation()
            + "-"
            + locationCount
            + "') located in '"
            + locationEnum.getFullName()
            + "'.");
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
