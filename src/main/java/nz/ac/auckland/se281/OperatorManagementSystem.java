package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperatorManagementSystem {
  private Map<String, Integer> locationCounts = new HashMap<>(); // locationFullname, operator count
  private Map<String, Integer> activityCounts = new HashMap<>(); // operatorID, activity count
  private Map<String, String> activityIDName = new HashMap<>(); // activityID, activity name
  private Map<String, String> operatorsNameMap =
      new HashMap<>(); // operator abbreviation, operator name
  private Map<String, Integer> reviewCount = new HashMap<>(); // activityID, review count
  private Map<String, String[]> reviewInformation = new HashMap<>(); // reviewID, review information
  private Map<String, String> reviewType = new HashMap<>(); // reviewID, type
  private Map<String, Boolean> reviewEndorse = new HashMap<>(); // reviewID, endorsement status
  private Map<String, String> reviewResponse = new HashMap<>(); // reviewID, Response
  private Map<String, String> reviewImage = new HashMap<>(); // reviewID, image name

  private ArrayList<String> operators =
      new ArrayList<>(); // operatorName operatorNumber locationFullname
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

    // If the keyword is "*", we need to print all operators
    if (keyword.equals("*")) {
      if (matchingCount == 1) {
        MessageCli.OPERATORS_FOUND.printMessage("is", "1", "", ":");
        MessageCli.OPERATOR_ENTRY.printMessage(
            operatorNameArray.get(0), operatorNumber.get(0), locationFullname.get(0));

        // If there are multiple operators, we need to print all of them
      } else if (matchingCount > 1) {
        MessageCli.OPERATORS_FOUND.printMessage("are", String.valueOf(matchingCount), "s", ":");
        for (int i = 0; i < operators.size(); i++) {
          MessageCli.OPERATOR_ENTRY.printMessage(
              operatorNameArray.get(i), operatorNumber.get(i), locationFullname.get(i));
        }
      }

      return;
    }
    // If the keyword is not "*", we need to print only the matching operators
    if (keywordCount == 1) {
      MessageCli.OPERATORS_FOUND.printMessage("is", "1", "", ":");
    } else if (keywordCount > 1) {
      MessageCli.OPERATORS_FOUND.printMessage("are", String.valueOf(keywordCount), "s", ":");
    }
    // Print the matching operators
    for (int i = 0; i < operators.size(); i++) {
      if (operators.get(i).toLowerCase().contains(keyword.toLowerCase())) {
        MessageCli.OPERATOR_ENTRY.printMessage(
            operatorNameArray.get(i), operatorNumber.get(i), locationFullname.get(i));
      }
    }

    // If no operators are found, print a message
    if (keywordCount == 0) {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
    }
  }

  public void createOperator(String operatorName, String location) {

    // Remove leading and trailing spaces
    if (operatorName.length() < 3) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_OPERATOR_NAME.printMessage(operatorName);
      return;
    }
    // Check if the location is valid
    int whetherHasLocation = 0;
    for (Types.Location loc : Types.Location.values()) {
      if (loc.getNameEnglish().equalsIgnoreCase(location)
          || loc.getNameTeReo().equalsIgnoreCase(location)
          || loc.getLocationAbbreviation().equalsIgnoreCase(location)) {
        whetherHasLocation++;
      }
    }
    // If the location is not valid, print an error message and return
    if (whetherHasLocation == 0) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_LOCATION.printMessage(location);
      return;
    }
    // Check if the operator name is already in use
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

    // Convert the abbreviation to uppercase
    String operatorNumber = new String();
    // Convert the abbreviation to uppercase
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

    // Add the operator to the location counts map
    operatorsNameMap.put(abbreviation.toString(), operatorName);
  }

  public void viewActivities(String operatorId) {
    // Check if the operator ID is valid
    // If not, print an error message and return
    int count = 0;
    operatorId = operatorId.trim(); // Remove leading and trailing spaces
    if (!(operatorNumber.contains(operatorId))) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
      return;
    }
    // Check if the operator ID has activities
    // If not, print an error message and return
    for (int i = 0; i < activityNumber.size(); i++) {
      if (activityNumber.get(i).contains(operatorId)) {
        count++;
      }
    }
    // If no activities are found, print a message
    if (count == 0) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      return;
    } else if (count == 1) {
      MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
    } else {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
    }
    // Print the activities for the operator
    for (int i = 0; i < activityNumber.size(); i++) {
      if (activityNumber.get(i).contains(operatorId)) {
        int operatorPosition = 0;
        // Find the position of the operator ID in the operatorNumber list
        for (int o = 0; o < operatorNumber.size(); o++) {
          if (operatorNumber.get(o).equals(operatorId)) {
            operatorPosition = o;
            break;
          }
        }
        // Split the activity number to get the activity ID and type
        // Print the activity entry
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activityName.get(i), activityId, activityType, operatorNameArray.get(operatorPosition));
      }
    }
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    activityName = activityName.trim(); // Remove leading and trailing spaces
    // Check if the activity name is valid
    // If not, print an error message and return
    if (activityName.length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
      return;
    }
    // Check if the activity type is valid
    // If not, print an error message and return
    if (!(operatorNumber.contains(operatorId))) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);
      return;
    }

    int operatorPosition = 0;
    // Find the position of the operator ID in the operatorNumber list
    for (int i = 0; i < operatorNumber.size(); i++) {
      if (operatorNumber.get(i).equals(operatorId)) {
        operatorPosition = i;
        break;
      }
    }
    // Check if the activity name is already in use
    int count = activityCounts.getOrDefault(operatorId, 0) + 1;
    activityCounts.put(operatorId, count);
    // Format the count to be 3 digits with leading zeros
    String activityCountResult = String.format("%03d", count);
    // Find the abbreviation of the activity name
    activityIDName.put(operatorId + "-" + activityCountResult, activityName);
    // Convert the abbreviation to uppercase
    this.activityName.add(activityName);
    activityNumber.add(operatorId + "-" + activityCountResult + ": " + activityType);
    // Print the Created Operator message(success message)
    MessageCli.ACTIVITY_CREATED.printMessage(
        activityName,
        operatorId + "-" + activityCountResult,
        activityType,
        operatorNameArray.get(operatorPosition));
  }

  public void searchActivities(String keyword) {
    keyword = keyword.trim(); // Remove leading and trailing spaces
    // Check if the keyword is empty
    if (keyword.isEmpty() || activityNumber.isEmpty()) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      return;
    }
    // Count matching keywords
    if (keyword.trim().equals("*")) {
      int count = 0;
      for (int i = 0; i < activityNumber.size(); i++) {
        count++;
      }
      // If the keyword is "*", we need to print all activities
      if (count == 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
      } else {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
      }
      // Print the activities for the operator
      for (int i = 0; i < activityNumber.size(); i++) {

        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];
        String[] operatorParts = activityId.split("-");
        String operatorId = operatorParts[0];

        String operatorFullName = operatorsNameMap.get(operatorId);
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activityName.get(i), activityId, activityType, operatorFullName);
      }
      return;
    }
    // If the keyword is not "*", we need to print only the matching activities
    // Count the number of matching activities
    if (!(keyword.trim().equals("*"))) {
      int count = 0;
      for (int i = 0; i < activityNumber.size(); i++) {
        // Split the activity number to get the activity ID and type
        // Print the activity entry
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];

        String[] operatorParts = activityId.split("-");
        String operatorId = operatorParts[0];
        String operatorFullName = operatorsNameMap.get(operatorId);

        // Split the activity number to get the location abbreviation
        // Print the activity entry
        String locationabbreviation = operatorParts[1];
        Types.Location locationEnum = Types.Location.fromString(locationabbreviation);
        // Check if the activity name, ID, type, operator name, or location contains the keyword
        // If so, print the activity entry
        if (activityName.get(i).toLowerCase().contains(keyword.toLowerCase())
            || activityId.toLowerCase().contains(keyword.toLowerCase())
            || activityType.toLowerCase().contains(keyword.toLowerCase())
            || operatorFullName.toLowerCase().contains(keyword.toLowerCase())
            || locationEnum.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
          count++;
        }
      }
      // If no activities are found, print a message
      if (count == 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
      } else if (count > 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
      } else {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
        return;
      }
      // Print the activities for the operator

      for (int i = 0; i < activityNumber.size(); i++) {
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        String activityType = parts[1];
        String[] operatorParts = activityId.split("-");
        String operatorId = operatorParts[0];
        String operatorFullName = operatorsNameMap.get(operatorId);
        // Split the activity number to get the location abbreviation
        // Print the activity entry
        String locationabbreviation = operatorParts[1];
        Types.Location locationEnum = Types.Location.fromString(locationabbreviation);
        if (activityName.get(i).toLowerCase().contains(keyword.toLowerCase())
            || activityId.toLowerCase().contains(keyword.toLowerCase())
            || activityType.toLowerCase().contains(keyword.toLowerCase())
            || operatorFullName.toLowerCase().contains(keyword.toLowerCase())
            || locationEnum.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
          MessageCli.ACTIVITY_ENTRY.printMessage(
              activityName.get(i), activityId, activityType, operatorFullName);
        }
      }
    }
  }

  public void addPublicReview(String activityId, String[] options) {
    // Check if the activity ID is valid
    // If not, print an error message and return
    int activityCount = 0;
    for (int i = 0; i < activityNumber.size(); i++) {
      String[] parts = activityNumber.get(i).split(": ");
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
      String[] parts = activityNumber.get(i).split(": ");
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
      String[] parts = activityNumber.get(i).split(": ");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        activityCount++;
      }
    }
    // Check if the activity ID is valid
    // If not, print an error message and return
    if (activityCount == 0) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    int count = reviewCount.getOrDefault(activityId, 0) + 1;
    reviewCount.put(activityId, count);

    this.reviewId.add(activityId + "-R" + count);
    String reviewId = activityId + "-R" + count;
    // Split the activity number to get the location abbreviation
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
      String[] parts = activityNumber.get(i).split(": ");
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
      String[] parts = this.reviewId.get(i).split("-R");
      String activityId1 = parts[0];
      if (activityId1.equals(activityId)) {
        reviewCount++;
      }
    }
    // If no reviews are found, print a message
    if (reviewCount == 0) {
      MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", activityIDName.get(activityId));
      return;
    } else if (reviewCount == 1) {
      MessageCli.REVIEWS_FOUND.printMessage("is", "1", "", activityIDName.get(activityId));
    } else {
      MessageCli.REVIEWS_FOUND.printMessage(
          "are", String.valueOf(reviewCount), "s", activityIDName.get(activityId));
    }
    // Print the reviews for the activity
    for (int i = 0; i < this.reviewId.size(); i++) {
      String[] parts = this.reviewId.get(i).split("-R");
      String activityId1 = parts[0];
      // Split the activity number to get the location abbreviation
      if (activityId1.equals(activityId)) {
        String reviewId = this.reviewId.get(i);
        String[] reviewInfo = reviewInformation.get(reviewId);
        String reviewType = this.reviewType.get(reviewId);
        if (reviewType.equals("Public")) {
          // Check if the review is public
          // If so, print the review entry
          if (reviewInfo[1].equals("n")) {
            MessageCli.REVIEW_ENTRY_HEADER.printMessage(
                reviewInfo[2], "5", reviewType, reviewId, reviewInfo[0]);
            MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[3]);
            // Check if the review is endorsed
            // If so, print the endorsed message
            if (reviewEndorse.get(reviewId)) {
              MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
            }
            return;
          }
          // Check if the review is anonymous
          // If so, print the review entry
          if (reviewInfo[1].equals("y")) {
            MessageCli.REVIEW_ENTRY_HEADER.printMessage(
                reviewInfo[2], "5", reviewType, reviewId, "Anonymous");
            MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[3]);
            if (reviewEndorse.get(reviewId)) {
              MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
            }
            return;
          }
          // Check if the review is not anonymous
          // If so, print the review entry
        } else if (reviewType.equals("Private")) {
          MessageCli.REVIEW_ENTRY_HEADER.printMessage(
              reviewInfo[2], "5", reviewType, reviewId, reviewInfo[0]);
          MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[3]);
          if (reviewResponse.get(reviewId) == null) {
            if (reviewInfo[4].equals("y")) {
              MessageCli.REVIEW_ENTRY_FOLLOW_UP.printMessage(reviewInfo[1]);
              return;
              // Check if the review is resolved
            } else if (reviewInfo[4].equals("n")) {
              MessageCli.REVIEW_ENTRY_RESOLVED.printMessage("-");
              return;
            }

          } else {
            MessageCli.REVIEW_ENTRY_RESOLVED.printMessage(reviewResponse.get(reviewId));
            return;
          }

        } else if (reviewType.equals("Expert")) {
          MessageCli.REVIEW_ENTRY_HEADER.printMessage(
              reviewInfo[1], "5", reviewType, reviewId, reviewInfo[0]);
          MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(reviewInfo[2]);
          // Check if the review is endorsed
          // If so, print the endorsed message
          if (reviewInfo[3].equals("y")) {
            MessageCli.REVIEW_ENTRY_RECOMMENDED.printMessage();
            if (reviewImage.get(reviewId) != null) {
              MessageCli.REVIEW_ENTRY_IMAGES.printMessage(reviewImage.get(reviewId));
            }
            return;
            // Check if the review is not endorsed
          } else if (reviewInfo[3].equals("n")) {
            if (reviewImage.get(reviewId) != null) {
              MessageCli.REVIEW_ENTRY_IMAGES.printMessage(reviewImage.get(reviewId));
            }
            return;
          }
        }
      }
    }
  }

  public void endorseReview(String reviewId) {
    // Check if the review ID is valid
    // If not, print an error message and return
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
    // Check if the review is public
    // If not, print an error message and return
    if (!(reviewType.get(reviewId).equals("Public"))) {
      MessageCli.REVIEW_NOT_ENDORSED.printMessage(reviewId);
      return;
    }

    reviewEndorse.put(reviewId, true);
    MessageCli.REVIEW_ENDORSED.printMessage(reviewId);
  }

  public void resolveReview(String reviewId, String response) {
    // Check if the review ID is valid
    // If not, print an error message and return
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
    // Check if the review is private
    // If not, print an error message and return
    if (!(reviewType.get(reviewId).equals("Private"))) {
      MessageCli.REVIEW_NOT_RESOLVED.printMessage(reviewId);
      return;
    }

    reviewResponse.put(reviewId, response);
    MessageCli.REVIEW_RESOLVED.printMessage(reviewId);
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    // Check if the review ID is valid
    // If not, print an error message and return
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
    // Check if the review is expert
    // If not, print an error message and return
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
    // Check if there are any activities
    // If not, print an error message and return
    for (Types.Location location : Types.Location.values()) {
      String locationFullName = location.getFullName();
      int count = 0;
      String topActivity = null;

      int topRating = 0;
      int topTotalReview = 0;
      int topTotalRating = 0;
      // Check if there are any activities in the location
      for (int i = 0; i < activityNumber.size(); i++) {
        String[] parts = activityNumber.get(i).split(": ");
        String activityId = parts[0];
        int numberOfReviews = reviewCount.getOrDefault(activityId, 0);
        // Check if the activity has reviews
        // If not, skip to the next activity
        if (numberOfReviews == 0) {
          continue;
        }

        if (reviewCount.get(activityId) == null) {
          continue;
        }
        String[] idParts = activityId.split("-");
        String locationAbbr = idParts[1];
        // Check if the activity is in the location
        // If not, skip to the next activity

        if (!location.getLocationAbbreviation().equals(locationAbbr)) {
          continue;
        }

        int totalRating = 0;
        int countRating = 0;
        // Calculate the total rating and count of ratings for the activity
        for (int j = 1; j <= numberOfReviews; j++) {
          String reviewId = activityId + "-R" + j;
          String[] reviewInfo = reviewInformation.get(reviewId);
          // Check if the review is public, private, or expert
          // If public, add the rating to the total rating and increment the count
          if (reviewType.get(reviewId).equals("Public")) {
            totalRating += Integer.parseInt(reviewInfo[2]);
            countRating++;
          } else if (reviewType.get(reviewId).equals("Private")) {
            continue;
          } else if (reviewType.get(reviewId).equals("Expert")) {
            totalRating += Integer.parseInt(reviewInfo[1]);
            countRating++;
          }
        }
        // Check if the activity has any ratings
        // If not, skip to the next activity
        if (countRating == 0) {
          continue;
        }
        int average = totalRating / countRating;

        int topAvgRating = 0;
        // Calculate the average rating for the top activity
        if (!(topTotalReview == 0)) {
          topAvgRating = topRating / topTotalReview;
        }
        // Check if the average rating is greater than the top average rating
        // If so, update the top activity and its rating
        if (average > topAvgRating) {
          topActivity = activityIDName.get(activityId);
          topTotalReview = countRating;
          topTotalRating = totalRating;
        }

        count++;
      }
      // Check if there are any activities in the location
      // If not, print an error message and return

      if (count == 0) {
        MessageCli.NO_REVIEWED_ACTIVITIES.printMessage(locationFullName);
      } else {
        // Print the top activity and its average rating
        // Check if the top activity is not null
        int averageRating = topTotalRating / topTotalReview;
        MessageCli.TOP_ACTIVITY.printMessage(
            locationFullName, topActivity, String.valueOf(averageRating));
      }
    }
  }
}
