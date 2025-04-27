package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperatorManagementSystem {
  private Map<String, Integer> locationCounts = new HashMap<>(); // locationFullname, operator count
  private Map<String, Integer> activityCounts = new HashMap<>(); // operatorID, activity count
  private Map<String, String> activityIDName = new HashMap<>(); // activityID, activity name
  private Map<String, String> operatorsNameMap = new HashMap<>(); // operator abbreviation, operator name
  private Map<String, Integer> reviewCount = new HashMap<>(); // activityID, review count
  private Map<String, String[]> reviewInformation = new HashMap<>(); // reviewID, review information
  private Map<String, String> reviewType = new HashMap<>(); // reviewID, type
  private Map<String, Boolean> reviewEndorse = new HashMap<>(); // reviewID, endorsement status
  private Map<String, String> reviewResponse = new HashMap<>(); // reviewID, Response
  private Map<String, String> reviewImage = new HashMap<>(); // reviewID, image name


  private ArrayList<String> operators = new ArrayList<>(); // operatorName operatorNumber locationFullname
  private ArrayList<String> operatorNameArray = new ArrayList<>(); // operatorName
  private ArrayList<String> operatorNumber = new ArrayList<>(); // operatorNumber
  private ArrayList<String> locationFullname = new ArrayList<>(); // locationFullname
  private ArrayList<String> activityNumber = new ArrayList<>(); //
  private ArrayList<String> activityName = new ArrayList<>();
  private ArrayList<String> reviewId = new ArrayList<>();

  

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

    activityIDName.put(operatorId + "-" + activityCountResult, activityName);

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
    // Check if the activity ID is valid
    // If not, print an error message and return
    int activityCount = 0;
    for (int i = 0; i < activityNumber.size(); i++) {
      String [] parts = activityNumber.get(i).split(": ");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        activityCount++;
      }
    }
    if (activityCount == 0) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }
    
    // Check if the rating is between 1 and 5
    // If not, set it to 1 or 5 accordingly
    
    int count = reviewCount.getOrDefault(activityId, 0) + 1;
    reviewCount.put(activityId, count);
    
    this.reviewId.add(activityId + "-R" + count);
    String reviewId = activityId + "-R" + count;

    reviewInformation.put(reviewId, options);

    reviewType.put(reviewId, "Public");

    reviewEndorse.put(reviewId, false);


      
      
    


    // Print the Created Operator message(success message)
    MessageCli.REVIEW_ADDED.printMessage("Public", reviewId, activityIDName.get(activityId));
    
    
  }

  public void addPrivateReview(String activityId, String[] options) {
    // Check if the activity ID is valid
    // If not, print an error message and return
    int activityCount = 0;
    for (int i = 0; i < activityNumber.size(); i++) {
      String [] parts = activityNumber.get(i).split(": ");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        activityCount++;
      }
    }
    if (activityCount == 0) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }
    // Check if the rating is between 1 and 5
    // If not, set it to 1 or 5 accordingly
    
    int count = reviewCount.getOrDefault(activityId, 0) + 1;
    reviewCount.put(activityId, count);
    
    this.reviewId.add(activityId + "-R" + count);
    String reviewId = activityId + "-R" + count;

    reviewInformation.put(reviewId, options);

    reviewType.put(reviewId, "Private");

    

    // Print the Created Operator message(success message)
    MessageCli.REVIEW_ADDED.printMessage("Private", reviewId, activityIDName.get(activityId));
  }

  public void addExpertReview(String activityId, String[] options) {
    // Check if the activity ID is valid
    // If not, print an error message and return
    int activityCount = 0;
    for (int i = 0; i < activityNumber.size(); i++) {
      String [] parts = activityNumber.get(i).split(": ");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        activityCount++;
      }
    }
    if (activityCount == 0) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }
    
    int count = reviewCount.getOrDefault(activityId, 0) + 1;
    reviewCount.put(activityId, count);
    
    this.reviewId.add(activityId + "-R" + count);
    String reviewId = activityId + "-R" + count;

    reviewInformation.put(reviewId, options);

    reviewType.put(reviewId, "Expert");

    

    // Print the Created Operator message(success message)
    MessageCli.REVIEW_ADDED.printMessage("Expert", reviewId, activityIDName.get(activityId));
  }

  public void displayReviews(String activityId) {
    // Check if the activity ID is valid
    // If not, print an error message and return
    int activityCount = 0;
    for (int i = 0; i < activityNumber.size(); i++) {
      String [] parts = activityNumber.get(i).split(": ");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        activityCount++;
      }
    }
    if (activityCount == 0) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // Check if the activity ID has reviews
    // If not, print an error message and return
    int reviewCount = 0;
    for (int i = 0; i < this.reviewId.size(); i++) {
      String [] parts = this.reviewId.get(i).split("-R");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        reviewCount++;
      }
    }
    if (reviewCount == 0) {
      MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", activityIDName.get(activityId));
      return;
    } else if (reviewCount == 1) {
      MessageCli.REVIEWS_FOUND.printMessage("is", "1", "", activityIDName.get(activityId));
    } else {
      MessageCli.REVIEWS_FOUND.printMessage("are", String.valueOf(reviewCount), "s", activityIDName.get(activityId));
    }

    for (int i = 0; i < this.reviewId.size(); i++) {
      String [] parts = this.reviewId.get(i).split("-R");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        String reviewId = this.reviewId.get(i);
        String[] reviewInfo = reviewInformation.get(reviewId);
        String reviewType = this.reviewType.get(reviewId);
        if (reviewType.equals("Public")) {
          if (reviewInfo[1].equals("n")) {
            MessageCli.REVIEW_ENTRY_HEADER.printMessage(reviewInfo[2], "5", reviewType, reviewId, reviewInfo[0]);
            MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[3]);
            if (reviewEndorse.get(reviewId)) {
              MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
            }
            return;
          }
          if (reviewInfo[1].equals("y")) {
            MessageCli.REVIEW_ENTRY_HEADER.printMessage(reviewInfo[2], "5", reviewType, reviewId, "Anonymous");
            MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[3]);
            if (reviewEndorse.get(reviewId)) {
              MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
            }
            return;
          }
        } else if (reviewType.equals("Private")) {
          MessageCli.REVIEW_ENTRY_HEADER.printMessage(reviewInfo[2], "5", reviewType, reviewId, reviewInfo[0]);
          MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[3]);
          if (reviewResponse.get(reviewId) == null) {
            if (reviewInfo[4].equals("y")) {
              MessageCli.REVIEW_ENTRY_FOLLOW_UP.printMessage(reviewInfo[1]);
              return;
            } else if (reviewInfo[4].equals("n")) {
              MessageCli.REVIEW_ENTRY_RESOLVED.printMessage("-");
              return;
            }

          } else {
            MessageCli.REVIEW_ENTRY_RESOLVED.printMessage(reviewResponse.get(reviewId));
            return;
          }
          
            
        } else if (reviewType.equals("Expert")) {
          MessageCli.REVIEW_ENTRY_HEADER.printMessage(reviewInfo[1], "5", reviewType, reviewId, reviewInfo[0]);
          MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[2]);
          if (reviewInfo[3].equals("y")) {
            MessageCli.REVIEW_ENTRY_RECOMMENDED.printMessage();
            if (reviewImage.get(reviewId) != null) {
              MessageCli.REVIEW_ENTRY_IMAGES.printMessage(reviewImage.get(reviewId));
            }
            return;
          } else if (reviewInfo[3].equals("n")) {
            return;
          }
          
        }
      }
    }
  }

  public void endorseReview(String reviewId) {
    int reviewCount = 0;
    for (int i = 0; i < this.reviewId.size(); i++) {
      if (this.reviewId.get(i).equals(reviewId)) {
        reviewCount++;
      }
    }
    if (reviewCount == 0) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }

    if (!(reviewType.get(reviewId).equals("Public"))) {
      MessageCli.REVIEW_NOT_ENDORSED.printMessage(reviewId);
      return;
    }

    reviewEndorse.put(reviewId, true);
    MessageCli.REVIEW_ENDORSED.printMessage(reviewId);      
  }

  public void resolveReview(String reviewId, String response) {
    int reviewCount = 0;
    for (int i = 0; i < this.reviewId.size(); i++) {
      if (this.reviewId.get(i).equals(reviewId)) {
        reviewCount++;
      }
    }
    if (reviewCount == 0) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }

    if (!(reviewType.get(reviewId).equals("Private"))) {
      MessageCli.REVIEW_NOT_RESOLVED.printMessage(reviewId);
      return;
    }

    reviewResponse.put(reviewId, response);
    MessageCli.REVIEW_RESOLVED.printMessage(reviewId);
    
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    int reviewCount = 0;
    for (int i = 0; i < this.reviewId.size(); i++) {
      if (this.reviewId.get(i).equals(reviewId)) {
        reviewCount++;
      }
    }
    if (reviewCount == 0) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }

    if (!(reviewType.get(reviewId).equals("Expert"))) {
      MessageCli.REVIEW_IMAGE_NOT_ADDED_NOT_EXPERT.printMessage(reviewId);
      return;
    }

    if (reviewImage.get(reviewId) == null) {
      reviewImage.put(reviewId, imageName);
    } else {
      String existingImage = reviewImage.get(reviewId);
      reviewImage.put(reviewId, existingImage + "," + imageName);

    }


    MessageCli.REVIEW_IMAGE_ADDED.printMessage(imageName, reviewId);

    
  }

  public void displayTopActivities() {
    for (Types.Location location : Types.Location.values()) {
      String locationFullName = location.getFullName();
      int count = 0;
      String topActivity = null;
      int averageRating = 0;
      int topRating = 0;

      for (int i = 0; i < activityNumber.size(); i++) {
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];

        if (reviewCount.get(activityId) == null) {
          continue;
        }
        String reviewID = activityId + "-R" + reviewCount.get(activityId);
        String[] reviewInfo = reviewInformation.get(reviewID);
        if (reviewInfo == null) {
          continue;
        }
        if (reviewType.get(reviewID) == "Public") {
          int rating = Integer.parseInt(reviewInfo[2]);
          if (rating > topRating) {
            topRating = rating;
            averageRating += rating;
            topActivity = activityIDName.get(activityId);
          }
        } else if (reviewType.get(reviewID) == "Private") {
          continue;
        } else if (reviewType.get(reviewID) == "Expert") {
          int rating = Integer.parseInt(reviewInfo[1]);
          if (rating > topRating) {
            topRating = rating;
            averageRating += rating;
            topActivity = activityIDName.get(activityId);
          }
        }
        

        count++;
      }
      averageRating /= count;

      if (count == 0) {
        MessageCli.NO_REVIEWED_ACTIVITIES.printMessage(locationFullName);
      } else {
        MessageCli.TOP_ACTIVITY.printMessage(topActivity, String.valueOf(averageRating));
      }
    }
  }
}
