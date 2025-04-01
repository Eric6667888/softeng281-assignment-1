package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperatorManagementSystem {
  private Map<String, Integer> locationCounts = new HashMap<>();
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
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
      return;
    }

    // Count matching keywords
    int matchingCount = 0;

    for (String operator : this.operators) {
      if (operator.toLowerCase().contains(keyword.toLowerCase()) || keyword.equals("*")) {
        matchingCount++;
        keywordCount++;
      }
    }

    if (keyword.equals("*")) {
      if (matchingCount == 1) {
        MessageCli.OPERATORS_FOUND.printMessage("is", "1", "", ":");
        MessageCli.OPERATOR_ENTRY.printMessage(
            operatorNameArray.get(0), operatorNumber.get(0), locationFullname.get(0));

      } else if (matchingCount > 1) {
        MessageCli.OPERATORS_FOUND.printMessage("are", String.valueOf(matchingCount), "s", ":");
        for (int i = 0; i < operators.size(); i++) {
          MessageCli.OPERATOR_ENTRY.printMessage(
              operatorNameArray.get(i), operatorNumber.get(i), locationFullname.get(i));
        }
      }

      return;
    }

    if (keywordCount == 1) {
      MessageCli.OPERATORS_FOUND.printMessage("is", "1", "", ":");
    } else if (keywordCount > 1) {
      MessageCli.OPERATORS_FOUND.printMessage("are", String.valueOf(keywordCount), "s", ":");
    }

    for (int i = 0; i < operators.size(); i++) {
      if (operators.get(i).toLowerCase().contains(keyword.toLowerCase())) {
        MessageCli.OPERATOR_ENTRY.printMessage(
            operatorNameArray.get(i), operatorNumber.get(i), locationFullname.get(i));
      }
    }

    if (keywordCount == 0) {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
    }
  }

  public void createOperator(String operatorName, String location) {

    int whetherHasLocation = 0;
    for (Types.Location loc : Types.Location.values()) {
      if (loc.getNameEnglish().equalsIgnoreCase(location)
          || loc.getNameTeReo().equalsIgnoreCase(location)
          || loc.getLocationAbbreviation().equalsIgnoreCase(location)) {
        whetherHasLocation++;
      }
    }
    if (whetherHasLocation == 0) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_LOCATION.printMessage(location);
      return;
    }

    Types.Location locationEnum = Types.Location.fromString(location);
    String locationFull = locationEnum.getFullName();

    // Operator already Exist same location
    for (int i = 0; i < operators.size(); i++) {
      if (this.operatorNameArray.get(i).equals(operatorName)
          && this.locationFullname.get(i).equals(locationEnum.getFullName())) {
        MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
            operatorName, locationEnum.getFullName());
        return;
      }
    }

    // Count the number of operators in the given location
    int count = locationCounts.getOrDefault(locationFull, 0) + 1;
    locationCounts.put(locationFull, count);

    // Format the count to be 3 digits with leading zeros
    String locationCountResult = String.format("%03d", count);

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
    MessageCli.OPERATOR_CREATED.printMessage(
        operatorName, operatorNumber, locationEnum.getFullName());

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
