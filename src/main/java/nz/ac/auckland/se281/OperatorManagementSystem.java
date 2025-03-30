package nz.ac.auckland.se281;

import java.util.ArrayList;

public class OperatorManagementSystem {
  private int locationCount = 0;
  private ArrayList<String> operators = new ArrayList<>();
  private ArrayList<String> operatorNameArray = new ArrayList<>();
  private ArrayList<String> operatorNumber = new ArrayList<>();
  private ArrayList<String> locationFullname = new ArrayList<>();

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {
    keyword = keyword.trim(); // Remove leading and trailing spaces
    // Check if the keyword is empty
    int keywordCount = 0;

    if (keyword.isEmpty() || operators.isEmpty()) {
      System.out.println("There are no matching operators found.");
      return;
    }

    if (keyword.equals("*")) {
      if (this.locationCount == 1) {
        System.out.println("There is 1 matching operator found:");
        System.out.println(
            "  * "
                + operatorNameArray.get(0)
                + " ('"
                + operatorNumber.get(0)
                + "' located in '"
                + locationFullname.get(0)
                + "')");

      } else if (this.locationCount > 1) {
        System.out.println("There are " + locationCount + " matching operators found:");
        for (int i = 0; i < operators.size(); i++) {
          System.out.println(
              "  * "
                  + operatorNameArray.get(i)
                  + " ('"
                  + operatorNumber.get(i)
                  + "' located in '"
                  + locationFullname.get(i)
                  + "')");
        }
      }

      return;
    }

    for (String operator : this.operators) {
      if (operator.toLowerCase().contains(keyword.toLowerCase())) {
        keywordCount++;
      }
    }

    if (keywordCount == 1) {
      System.out.println("There is 1 matching operator found:");
    } else if (keywordCount > 1) {
      System.out.println("There are " + keywordCount + " matching operators found:");
    }
  }

  public void createOperator(String operatorName, String location) {
    Types.Location locationEnum = Types.Location.fromString(location);

    // Operator already Exist same location
    for (int i = 0; i < operators.size(); i++) {
      if (this.operatorNameArray.get(i).equals(operatorName)
          && this.locationFullname.get(i).contains(location)) {
        MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
            operatorName, locationEnum.getFullName());
        return;
      }
    }

    // Count the number of operators in the given location
    int count = 1;
    for (int i = 0; i < operators.size(); i++) {
      if (this.locationFullname.get(i).contains(location)) {
        count++;
      }
    }
    this.locationCount++;

    // Format the count to be 3 digits with leading zeros
    String locationCountResult;
    if (count <= 9) {
      locationCountResult = "00" + count;
    } else if (count <= 99) {
      locationCountResult = "0" + count;
    } else {
      locationCountResult = "" + count;
    }

    // Find the abbreviation of the operator name
    String[] words = operatorName.split(" ");
    StringBuilder abbreviation = new StringBuilder();
    for (String word : words) {
      if (!word.isEmpty()) {
        abbreviation.append(word.charAt(0));
      }
    }

    String operatorNumber = new String();

    operatorNumber =
        abbreviation + "-" + locationEnum.getLocationAbbreviation() + "-" + locationCountResult;

    // Print the Created Operator message(success message)
    System.out.println(
        "Successfully created operator '"
            + operatorName
            + "' ('"
            + operatorNumber
            + "') located in '"
            + locationEnum.getFullName()
            + "'.");

    // Create the operator in the system
    // Add the operator to the list of operators
    this.operators.add(operatorName + " " + operatorNumber + " " + locationEnum.getFullName());
    this.operatorNameArray.add(operatorName);
    this.operatorNumber.add(operatorNumber);
    this.locationFullname.add(locationEnum.getFullName());
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
