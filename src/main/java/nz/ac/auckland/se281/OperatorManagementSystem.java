package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperatorManagementSystem {
  private Map<String, Integer> locationCounts = new HashMap<>();
  private Map<String, Integer> activityCounts = new HashMap<>();
  private Map<String, String> operatorsNameMap = new HashMap<>();
  private ArrayList<String> operators = new ArrayList<>();
  private ArrayList<String> operatorNameArray = new ArrayList<>();
  private ArrayList<String> operatorNumber = new ArrayList<>();
  private ArrayList<String> locationFullname = new ArrayList<>();
  private ArrayList<String> activityNumber = new ArrayList<>();
  private ArrayList<String> activityName = new ArrayList<>();

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

    if (operatorName.length() < 3) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_OPERATOR_NAME.printMessage(operatorName);
      return;
    }

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

    operatorsNameMap.put(abbreviation.toString(), operatorName);
  }

  public void viewActivities(String operatorId) {
    int count = 0;
    operatorId = operatorId.trim(); // Remove leading and trailing spaces
    if (!(operatorNumber.contains(operatorId))) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
      return;
    }

    for (int i = 0; i < activityNumber.size(); i++) {
      if (activityNumber.get(i).contains(operatorId)) {
        count++;
        
      }
    }
    if (count == 0) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      return;
    } else if (count == 1) {
      MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
    } else {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
    }
    for (int i = 0; i < activityNumber.size(); i++) {
      if (activityNumber.get(i).contains(operatorId)) {
        int operatorPosition = 0;

        for (int o = 0; o < operatorNumber.size(); o++) {
          if (operatorNumber.get(o).equals(operatorId)) {
            operatorPosition = o;
            break;

          }

        }
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];
        MessageCli.ACTIVITY_ENTRY.printMessage(activityName.get(i), activityId, activityType, operatorNameArray.get(operatorPosition));
      }
    }


    
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    activityName = activityName.trim(); // Remove leading and trailing spaces
    if (activityName.length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
      return;
    }

    if (!(operatorNumber.contains(operatorId))) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);
      return;
    }

    int operatorPosition = 0;

    for (int i = 0; i < operatorNumber.size(); i++) {
      if (operatorNumber.get(i).equals(operatorId)) {
        operatorPosition = i;
        break;

      }

    }

    int count = activityCounts.getOrDefault(operatorId, 0) + 1;
    activityCounts.put(operatorId, count);
    String activityCountResult = String.format("%03d", count);

    this.activityName.add(activityName);
    activityNumber.add(operatorId + "-" + activityCountResult + ": " + activityType);
    MessageCli.ACTIVITY_CREATED.printMessage(activityName, operatorId + "-" + activityCountResult, activityType, operatorNameArray.get(operatorPosition));


  }

  public void searchActivities(String keyword) {
    keyword = keyword.trim(); // Remove leading and trailing spaces
    // Check if the keyword is empty
    if (keyword.isEmpty() || activityNumber.isEmpty()) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      return;
    }

    if (keyword.trim().equals("*")) {
      int count = 0;
      for (int i = 0; i < activityNumber.size(); i++) {
        count++;
      }
      if (count == 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
      } else {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
      }

      for (int i = 0; i < activityNumber.size(); i++) {
        
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];
        String[] operatorParts = activityId.split("-");
        String operatorId = operatorParts[0];
        
        String operatorFullName = operatorsNameMap.get(operatorId);
        MessageCli.ACTIVITY_ENTRY.printMessage(activityName.get(i), activityId, activityType, operatorFullName);
      }
      return;
    }

    if (!(keyword.trim().equals("*"))) {
      int count = 0;
      for (int i = 0; i < activityNumber.size(); i++) {

        String [] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];

        String [] operatorParts = activityId.split("-");
        String operatorId = operatorParts[0];
        String operatorFullName = operatorsNameMap.get(operatorId);

        String locationabbreviation = operatorParts[1];
        Types.Location locationEnum = Types.Location.fromString(locationabbreviation);

        
        


        if (activityName.get(i).toLowerCase().contains(keyword.toLowerCase()) || activityId.toLowerCase().contains(keyword.toLowerCase()) || activityType.toLowerCase().contains(keyword.toLowerCase()) || operatorFullName.toLowerCase().contains(keyword.toLowerCase())  || locationEnum.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
          count++;
        }
      }
      if (count == 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
      } else if (count > 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
      } else {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
        return;
      }

      for (int i = 0; i < activityNumber.size(); i++) {
        String [] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];
        String[] operatorParts = activityId.split("-");
        String operatorId = operatorParts[0];
        String operatorFullName = operatorsNameMap.get(operatorId);

        String locationabbreviation = operatorParts[1];
        Types.Location locationEnum = Types.Location.fromString(locationabbreviation);
        if (activityName.get(i).toLowerCase().contains(keyword.toLowerCase()) || activityId.toLowerCase().contains(keyword.toLowerCase()) || activityType.toLowerCase().contains(keyword.toLowerCase()) || operatorFullName.toLowerCase().contains(keyword.toLowerCase())  || locationEnum.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
          MessageCli.ACTIVITY_ENTRY.printMessage(activityName.get(i), activityId, activityType, operatorFullName);
        }
          
        }
      }
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
