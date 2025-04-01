package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  // MainTest.Task2.class,
  // MainTest.Task3.class,
  // MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_zero_operators() throws Exception {
      runCommands(SEARCH_OPERATORS, "*", EXIT);

      assertContains("There are no matching operators found.");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_02_create_operator_name() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains("Successfully created operator 'West Auckland Camel Treks'");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_03_create_operator_location() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains("Successfully created operator 'West Auckland Camel Treks'");
      assertContains("located in 'Auckland | Tāmaki Makaurau'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_04_create_operator_id_part_name() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains("Successfully created operator 'West Auckland Camel Treks'");
      assertContains("located in 'Auckland | Tāmaki Makaurau'.");
      assertContains("WACT");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_05_create_operator_id_full() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains(
          "Successfully created operator 'West Auckland Camel Treks' ('WACT-AKL-001') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_06_create_operator_valid_location_full_name_english() throws Exception {
      runCommands(CREATE_OPERATOR, "'Parliament Bungee Jump'", "'Wellington'", EXIT);

      assertContains(
          "Successfully created operator 'Parliament Bungee Jump' ('PBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_07_create_operator_valid_location_full_name_teo_reo() throws Exception {
      runCommands(CREATE_OPERATOR, "'Parliament Bungee Jump'", "'Te Whanganui-a-Tara'", EXIT);

      assertContains(
          "Successfully created operator 'Parliament Bungee Jump' ('PBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_08_create_operator_saved() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'West Auckland Camel Treks'",
          "'AKL'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains("There is 1 matching operator found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki"
              + " Makaurau')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_09_create_operator_saved_english_tereo_match() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Shark Snorkel Bay'",
          "'Tauranga'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains("There is 1 matching operator found:");
      assertContains("* Shark Snorkel Bay ('SSB-TRG-001' located in 'Tauranga')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There are", true);
      assertDoesNotContain("'Tauranga | Tauranga'", true);
    }

    @Test
    public void T1_10_create_operator_same_name_same_location() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'West Auckland Camel Treks'",
          "'AKL'", //
          CREATE_OPERATOR,
          "'West Auckland Camel Treks'",
          "'AKL'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'West Auckland Camel Treks' ('WACT-AKL-001') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertContains(
          "Operator not created: the operator name 'West Auckland Camel Treks' already exists same"
              + " location for 'Auckland | Tāmaki Makaurau'.");
      assertContains("There is 1 matching operator found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki"
              + " Makaurau')");
      assertDoesNotContain("002", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_11_create_operator_three_locations() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'Taupo'", //
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'Tauranga'", //
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'Auckland'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-TUO-001') located in"
              + " 'Taupo | Taupō-nui-a-Tia'.");
      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-TRG-001') located in"
              + " 'Tauranga'.");
      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-AKL-001') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertContains("There are 3 matching operators found:");
      assertContains("* Volcano Bungee Jump ('VBJ-TUO-001' located in 'Taupo | Taupō-nui-a-Tia')");
      assertContains("* Volcano Bungee Jump ('VBJ-TRG-001' located in 'Tauranga')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-001' located in 'Auckland | Tāmaki Makaurau')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_12_create_14_operators() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, EXIT));

      assertContains(
          "Successfully created operator 'West Auckland Camel Treks' ('WACT-AKL-001') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-AKL-002') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertContains(
          "Successfully created operator 'Mystical Waikato Whale Watching' ('MWWW-HLZ-001') located"
              + " in 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Hobbiton Skydiving Tours' ('HST-HLZ-002') located in"
              + " 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Mount Maunganui Ski Resort' ('MMSR-TRG-001') located in"
              + " 'Tauranga'.");
      assertContains(
          "Successfully created operator 'Shark Snorkel Bay' ('SSB-TRG-002') located in"
              + " 'Tauranga'.");
      assertContains(
          "Successfully created operator 'Huka Falls Barrel Rides' ('HFBR-TUO-001') located in"
              + " 'Taupo | Taupō-nui-a-Tia'.");
      assertContains(
          "Successfully created operator 'Taupo UFO Watching' ('TUW-TUO-002') located in"
              + " 'Taupo | Taupō-nui-a-Tia'.");
      assertContains(
          "Successfully created operator 'Parliament Bungee Jump' ('PBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertContains(
          "Successfully created operator 'Nelson UFO Watching' ('NUW-NSN-001') located in"
              + " 'Nelson | Whakatu'.");
      assertContains(
          "Successfully created operator 'Christchurch Camel Treks' ('CCT-CHC-001') located in"
              + " 'Christchurch | Ōtautahi'.");
      assertContains(
          "Successfully created operator 'Avon River Whitewater Rafting' ('ARWR-CHC-002') located"
              + " in 'Christchurch | Ōtautahi'.");
      assertContains(
          "Successfully created operator 'Dunedin Penguin Parade' ('DPP-DUD-001') located in"
              + " 'Dunedin | Ōtepoti'.");
      assertContains(
          "Successfully created operator 'Baldwin Street Ski Jumping' ('BSSJ-DUD-002') located in"
              + " 'Dunedin | Ōtepoti'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_13_search_operators_specific_location_te_reo() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "'Tāmaki Makaurau'", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki Makaurau')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-002' located in 'Auckland | Tāmaki Makaurau')");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 14", true);
    }

    @Test
    public void T1_14_search_operators_specific_location_english() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "'Auckland'", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki Makaurau')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-002' located in 'Auckland | Tāmaki Makaurau')");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_15_search_operators_keyword_in_location() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "ranga", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains("* Mount Maunganui Ski Resort ('MMSR-TRG-001' located in 'Tauranga')");
      assertContains("* Shark Snorkel Bay ('SSB-TRG-002' located in 'Tauranga')");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 14", true);
    }

    public static class YourTests extends CliTest {

      public YourTests() {
        super(Main.class);
      }
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task2 extends CliTest {

    public Task2() {
      super(Main.class);
    }

    @Override
    public void reset() {}

    @Test
    public void T2_01_view_activities_invalid_operator() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, VIEW_ACTIVITIES, "UHOH-AKL-789", EXIT));

      assertContains("Operator not found: 'UHOH-AKL-789' is an invalid operator ID.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T2_02_view_activities_no_activities() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, VIEW_ACTIVITIES, "WACT-AKL-001", EXIT));

      assertContains("There are no matching activities found.");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T2_03_create_activity_name_too_short() throws Exception {
      runCommands(
          unpack(CREATE_14_OPERATORS, CREATE_ACTIVITY, "Hi", "ADVENTURE", "'WACT-AKL-001'", EXIT));

      assertContains("Activity not created: 'Hi' is not a valid activity name.");
      assertDoesNotContain("Successfully created activity", true);
    }

    @Test
    public void T2_04_create_activity_invalid_operator() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_ACTIVITY,
              "'Bethells Beach Camel Trek'",
              "ADVENTURE",
              "'WACT-AKL-789'",
              EXIT));

      assertContains("Activity not created: 'WACT-AKL-789' is an invalid operator ID.");
      assertDoesNotContain("Successfully created activity", true);
    }

    @Test
    public void T2_05_create_activity_success_activity_id() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_ACTIVITY,
              "'Bethells Beach Camel Trek'",
              "Adventure",
              "'WACT-AKL-001'",
              EXIT));

      assertContains("Successfully created activity");
      assertContains("WACT-AKL-001-001");
      assertDoesNotContain("Activity not created", true);
    }

    @Test
    public void T2_06_create_activity_success_full_details() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_ACTIVITY,
              "'Bethells Beach Camel Trek'",
              "Adventure",
              "'WACT-AKL-001'",
              EXIT));

      assertContains(
          "Successfully created activity 'Bethells Beach Camel Trek' ('WACT-AKL-001-001':"
              + " 'Adventure') for 'West Auckland Camel Treks'.");
      assertDoesNotContain("Activity not created", true);
    }

    @Test
    public void T2_07_saved_activity_details_single_operator() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_ACTIVITY,
              "'Bethells Beach Camel Trek'",
              "Adventure",
              "'WACT-AKL-001'",
              CREATE_ACTIVITY,
              "'Sky Tower Base Jumping'",
              "Adventure",
              "'WACT-AKL-001'",
              VIEW_ACTIVITIES,
              "'WACT-AKL-001'",
              EXIT));

      assertContains("There are 2 matching activities found:");
      assertContains(
          "* Bethells Beach Camel Trek: [WACT-AKL-001-001/Adventure] offered by West Auckland Camel"
              + " Treks");
      assertContains(
          "* Sky Tower Base Jumping: [WACT-AKL-001-002/Adventure] offered by West Auckland Camel"
              + " Treks");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T2_08_create_27_activities() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, EXIT));

      assertContains("Successfully created activity");
      assertDoesNotContain("Activity not created", true);
    }

    @Test
    public void T2_09_search_activities_all_no_activities_exist() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_ACTIVITIES, "*", EXIT));

      assertContains("There are no matching activities found.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 27", true);
    }

    @Test
    public void T2_10_search_activities_none_found() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_ACTIVITIES, "Swimming", EXIT));

      assertContains("There are no matching activities found.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 27", true);
    }

    @Test
    public void T2_11_search_activities_found_all() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, SEARCH_ACTIVITIES, "*", EXIT));

      assertContains("There are 27 matching activities found:");
      assertDoesNotContain("There are 28", true);
    }

    @Test
    public void T2_12_search_activities_found_keyword_activity_name() throws Exception {
      runCommands(
          unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, SEARCH_ACTIVITIES, "Spaceships", EXIT));

      assertContains("There is 1 matching activity found:");
      assertContains(
          "* Stars or Spaceships?: [NUW-NSN-001-001/Scenic] offered by Nelson UFO Watching");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T2_13_search_activities_found_keyword_activity_type() throws Exception {
      runCommands(
          unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, SEARCH_ACTIVITIES, "Culture", EXIT));

      assertContains("There are 2 matching activities found:");
      assertContains(
          "* Jumping Through Political Loopholes: [PBJ-WLG-001-001/Culture] offered by Parliament"
              + " Bungee Jump");
      assertContains(
          "* Legends of the Lost Snow: [MMSR-TRG-001-001/Culture] offered by Mount Maunganui Ski"
              + " Resort");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T2_14_search_activities_found_keyword_location_te_reo() throws Exception {
      runCommands(
          unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, SEARCH_ACTIVITIES, "Whakatu", EXIT));

      assertContains("There are 2 matching activities found:");
      assertContains(
          "* Stars or Spaceships?: [NUW-NSN-001-001/Scenic] offered by Nelson UFO Watching");
      assertContains(
          "* Meteorites & Meat Pies: [NUW-NSN-001-002/Food] offered by Nelson UFO Watching");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T2_15_search_activities_found_keyword_partial_various() throws Exception {
      runCommands(
          unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, SEARCH_ACTIVITIES, "the", EXIT));

      assertContains("There are 5 matching activities found:");
      assertContains(
          "* The Frodo Jump: [HST-HLZ-002-001/Adventure] offered by Hobbiton Skydiving Tours");
      assertContains(
          "* The Gandalf Picnic: [HST-HLZ-002-002/Food] offered by Hobbiton Skydiving Tours");
      assertContains(
          "* The Frodo Jump: [HST-HLZ-002-001/Adventure] offered by Hobbiton Skydiving Tours");
      assertContains(
          "* The Gandalf Picnic: [HST-HLZ-002-002/Food] offered by Hobbiton Skydiving Tours");
      assertContains(
          "* Close Encounters of the Lake: [TUW-TUO-002-002/Wildlife] offered by Taupo UFO"
              + " Watching");

      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
      assertDoesNotContain("There are 4", true);
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task3 extends CliTest {

    public Task3() {
      super(Main.class);
    }

    @Override
    public void reset() {}

    @Test
    public void T3_01_display_reviews_no_reviews() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              DISPLAY_REVIEWS,
              "WACT-AKL-001-001",
              DISPLAY_REVIEWS,
              "TUW-TUO-002-002",
              DISPLAY_REVIEWS,
              "ARWR-CHC-002-003",
              EXIT));

      assertContains("There are no reviews for activity 'Bethells Beach Camel Trek'.");
      assertContains("There are no reviews for activity 'Close Encounters of the Lake'.");
      assertContains("There are no reviews for activity 'River Rush'.");
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T3_02_add_public_review_invalid_activity() throws Exception {
      runCommands(ADD_PUBLIC_REVIEW, "WACT-AKL-001-999", options("Alice", "n", "5", "Nice"), EXIT);

      assertContains("Review not added: 'WACT-AKL-001-999' is an invalid activity ID.");
      assertDoesNotContain("added successfully for activity", true);
    }

    @Test
    public void T3_03_add_public_review_everything_ok() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "WACT-AKL-001-001",
              options("Alice", "n", "5", "Nice"),
              EXIT));

      assertContains("added successfully for activity ");
      assertDoesNotContain("Review not added", true);
    }

    @Test
    public void T3_04_add_other_reviews_everything_ok() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PRIVATE_REVIEW,
              "WACT-AKL-001-001",
              options("Felicia", "felicia@email.com", "5", "Great", "n"),
              ADD_EXPERT_REVIEW,
              "WACT-AKL-001-001",
              options("Eve", "4", "Good", "y"),
              EXIT));

      assertContains("Private review '");
      assertContains("Expert review '");
      assertContains("added successfully for activity");
      assertDoesNotContain("Review not added", true);
    }

    @Test
    public void T3_05_add_other_reviews_everything_ok_full_details() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PRIVATE_REVIEW,
              "WACT-AKL-001-001",
              options("Felicia", "felicia@email.com", "5", "Great", "n"),
              ADD_EXPERT_REVIEW,
              "WACT-AKL-001-001",
              options("Eve", "4", "Good", "y"),
              EXIT));

      assertContains(
          "Private review 'WACT-AKL-001-001-R1' added successfully for activity"
              + " 'Bethells Beach Camel Trek'.");
      assertContains(
          "Expert review 'WACT-AKL-001-001-R2' added successfully for activity"
              + " 'Bethells Beach Camel Trek'.");
      assertDoesNotContain("Review not added", true);
    }

    @Test
    public void T3_xx_add_reviews_different_activities_correct_ids() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              ADD_PRIVATE_REVIEW,
              "TUW-TUO-002-002",
              options(
                  "Felicia", "felicia@email.com", "5", "Thank you for the great experience!", "n"),
              ADD_EXPERT_REVIEW,
              "ARWR-CHC-002-003",
              options("Eve", "4", "Lots of rapids, very scary, watch out!", "y"),
              EXIT));

      assertContains("'SSB-TRG-002-001-R1'");
      assertContains("'TUW-TUO-002-002-R1'");
      assertContains("'ARWR-CHC-002-003-R1'");
      assertDoesNotContain("Review not added", true);
    }

    @Test
    public void T3_07_add_reviews_different_activities_correct_activities() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              ADD_PRIVATE_REVIEW,
              "TUW-TUO-002-002",
              options(
                  "Felicia", "felicia@email.com", "5", "Thank you for the great experience!", "n"),
              ADD_EXPERT_REVIEW,
              "ARWR-CHC-002-003",
              options("Eve", "4", "Lots of rapids, very scary, watch out!", "y"),
              EXIT));

      assertContains("'Nemos Playground'.");
      assertContains("'Close Encounters of the Lake'.");
      assertContains("'River Rush'.");
      assertDoesNotContain("Review not added", true);
    }

    @Test
    public void T3_08_public_review_saved() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              DISPLAY_REVIEWS,
              "SSB-TRG-002-001",
              EXIT));

      assertContains("There is 1 review for activity 'Nemos Playground'.");
      assertContains("* [3/5] Public review (SSB-TRG-002-001-R1) by 'Alice'");
      assertContains("\"Could be better\"");
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T3_09_private_review_saved() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PRIVATE_REVIEW,
              "TUW-TUO-002-002",
              options(
                  "Felicia", "felicia@email.com", "5", "Thank you for the great experience!", "n"),
              DISPLAY_REVIEWS,
              "TUW-TUO-002-002",
              EXIT));

      assertContains("There is 1 review for activity 'Close Encounters of the Lake'.");
      assertContains("* [5/5] Private review (TUW-TUO-002-002-R1) by 'Felicia'");
      assertContains("Resolved: \"-\"");
      assertContains("\"Thank you for the great experience!\"");
      assertDoesNotContain("There are", true);
      assertDoesNotContain("Need to email", true);
    }

    @Test
    public void T3_10_display_reviews_multiple_reviews() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              ADD_PRIVATE_REVIEW,
              "TUW-TUO-002-002",
              options(
                  "Felicia", "felicia@email.com", "5", "Thank you for the great experience!", "n"),
              ADD_EXPERT_REVIEW,
              "ARWR-CHC-002-003",
              options("Eve", "4", "Lots of rapids, very scary, watch out!", "y"),
              DISPLAY_REVIEWS,
              "SSB-TRG-002-001",
              DISPLAY_REVIEWS,
              "TUW-TUO-002-002",
              DISPLAY_REVIEWS,
              "ARWR-CHC-002-003",
              EXIT));

      assertContains("There is 1 review for activity 'Nemos Playground'.");
      assertContains("* [3/5] Public review (SSB-TRG-002-001-R1) by 'Alice'");
      assertContains("\"Could be better\"");
      assertContains("There is 1 review for activity 'Close Encounters of the Lake'.");
      assertContains("* [5/5] Private review (TUW-TUO-002-002-R1) by 'Felicia'");
      assertContains("Resolved: \"-\"");
      assertContains("\"Thank you for the great experience!\"");
      assertContains("There is 1 review for activity 'River Rush'.");
      assertContains("* [4/5] Expert review (ARWR-CHC-002-003-R1) by 'Eve'");
      assertContains("\"Lots of rapids, very scary, watch out!\"");
      assertContains("Recommended by experts.");
      assertDoesNotContain("There are", true);
      assertDoesNotContain("Need to email", true);
    }

    @Test
    public void T3_11_public_review_endorsed_afterwards() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              ENDORSE_REVIEW,
              "SSB-TRG-002-001-R1",
              DISPLAY_REVIEWS,
              "SSB-TRG-002-001",
              EXIT));

      assertContains("Review 'SSB-TRG-002-001-R1' endorsed successfully.");
      assertContains("There is 1 review for activity 'Nemos Playground'.");
      assertContains("* [3/5] Public review (SSB-TRG-002-001-R1) by 'Alice'");
      assertContains("\"Could be better\"");
      assertContains("Endorsed by admin.");
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T3_12_resolve_private_review_not_found() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PRIVATE_REVIEW,
              "TUW-TUO-002-002",
              options("Felicia", "felicia@email.com", "1", "I'm so disappointed!", "y"),
              RESOLVE_REVIEW,
              "TUW-TUO-002-002-R2",
              "'So sorry to hear that!'",
              DISPLAY_REVIEWS,
              "TUW-TUO-002-002",
              EXIT));
      assertContains("Review not found: 'TUW-TUO-002-002-R2' is an invalid review ID.");
      assertDoesNotContain("resolved successfully", true);
    }

    @Test
    public void T3_13_resolve_private_review() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PRIVATE_REVIEW,
              "TUW-TUO-002-002",
              options("Felicia", "felicia@email.com", "1", "I'm so disappointed!", "y"),
              RESOLVE_REVIEW,
              "TUW-TUO-002-002-R1",
              "'So sorry to hear that!'",
              DISPLAY_REVIEWS,
              "TUW-TUO-002-002",
              EXIT)); // HIDE

      assertContains("Review 'TUW-TUO-002-002-R1' resolved successfully.");
      assertContains("There is 1 review for activity 'Close Encounters of the Lake'.");
      assertContains("* [1/5] Private review (TUW-TUO-002-002-R1) by 'Felicia'");
      assertDoesNotContain("Need to email", true);
    }

    @Test
    public void T3_14_upload_image_expert_review_not_found() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              UPLOAD_REVIEW_IMAGE,
              "ARWR-CHC-002-003-R2",
              "image.jpg",
              EXIT));

      assertContains("Review not found: 'ARWR-CHC-002-003-R2' is an invalid review ID.");
      assertDoesNotContain("uploaded successfully for review", true);
      assertDoesNotContain("Images: ", true);
    }

    @Test
    public void T3_15_upload_image_expert_review() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_EXPERT_REVIEW,
              "ARWR-CHC-002-003",
              options("Eve", "4", "Lots of rapids, very scary, watch out!", "y"),
              UPLOAD_REVIEW_IMAGE,
              "ARWR-CHC-002-003-R1",
              "image.jpg",
              DISPLAY_REVIEWS,
              "ARWR-CHC-002-003",
              EXIT));

      assertContains("Image 'image.jpg' uploaded successfully for review 'ARWR-CHC-002-003-R1'.");
      assertContains("There is 1 review for activity 'River Rush'.");
      assertContains("* [4/5] Expert review (ARWR-CHC-002-003-R1) by 'Eve'");
      assertContains("Images: [image.jpg]");
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T3_16_upload_multiple_images_expert_review() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_EXPERT_REVIEW,
              "ARWR-CHC-002-003",
              options("Eve", "4", "Lots of rapids, very scary, watch out!", "y"),
              UPLOAD_REVIEW_IMAGE,
              "ARWR-CHC-002-003-R1",
              "image1.jpg",
              UPLOAD_REVIEW_IMAGE,
              "ARWR-CHC-002-003-R1",
              "image2.jpg",
              DISPLAY_REVIEWS,
              "ARWR-CHC-002-003",
              EXIT));

      assertContains("Image 'image1.jpg' uploaded successfully for review 'ARWR-CHC-002-003-R1'.");
      assertContains("Image 'image2.jpg' uploaded successfully for review 'ARWR-CHC-002-003-R1'.");

      assertContains("There is 1 review for activity 'River Rush'.");
      assertContains("* [4/5] Expert review (ARWR-CHC-002-003-R1) by 'Eve'");
      assertContains("Images: [image1.jpg,image2.jpg]");
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T3_17_rank_activities_no_reviews() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, CREATE_27_ACTIVITIES, DISPLAY_TOP_ACTIVITIES, EXIT));

      assertContains("No reviewed activities found in Auckland | Tāmaki Makaurau.");
      assertContains("No reviewed activities found in Hamilton | Kirikiriroa.");
      assertContains("No reviewed activities found in Tauranga.");
      assertContains("No reviewed activities found in Taupo | Taupō-nui-a-Tia.");
      assertContains("No reviewed activities found in Wellington | Te Whanganui-a-Tara.");
      assertContains("No reviewed activities found in Nelson | Whakatu.");
      assertContains("No reviewed activities found in Christchurch | Ōtautahi.");
      assertContains("No reviewed activities found in Dunedin | Ōtepoti.");
      assertDoesNotContain("Top reviewed activity in ", true);
    }

    @Test
    public void T3_18_rank_activities_one_review() throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              DISPLAY_TOP_ACTIVITIES,
              EXIT));

      assertContains(
          "Top reviewed activity in Tauranga is 'Nemos Playground',"
              + " with an average"
              + " rating"
              + " of 3"); // not caring about decimals
    }

    @Test
    public void T3_19_rank_activities_two_reviews_different_activities_same_location()
        throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "3", "Could be better"),
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-002",
              options("Bob", "n", "4", "Good"),
              DISPLAY_TOP_ACTIVITIES,
              EXIT));

      assertContains(
          "Top reviewed activity in Tauranga is 'Seaside Mussel Munch', with an average"
              + " rating"
              + " of 4");

      assertDoesNotContain("in Tauranga is 'Nemos Playground'", true);
    }

    @Test
    public void T3_20_rank_activities_multiple_reviews_per_activity_same_location()
        throws Exception {
      runCommands(
          unpack(
              CREATE_14_OPERATORS,
              CREATE_27_ACTIVITIES,
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Alice", "n", "1", "OK"),
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-001",
              options("Bob", "n", "2", "OK"),
              ADD_EXPERT_REVIEW,
              "SSB-TRG-002-001",
              options("Charlie", "3", "OK", "y"),
              ADD_PUBLIC_REVIEW,
              "SSB-TRG-002-002",
              options("Alice", "n", "3", "Nice"),
              ADD_EXPERT_REVIEW,
              "SSB-TRG-002-002",
              options("Charlie", "5", "Nice", "y"),
              DISPLAY_REVIEWS,
              "SSB-TRG-002-001",
              DISPLAY_REVIEWS,
              "SSB-TRG-002-002",
              DISPLAY_TOP_ACTIVITIES,
              EXIT));

      assertContains(
          "Top reviewed activity in Tauranga is 'Seaside Mussel Munch', with an average rating of"
              + " 4");
      assertDoesNotContain("in Tauranga is 'Nemos Playground'", true);
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class YourTests extends CliTest {

    public YourTests() {
      super(Main.class);
    }

    @Override
    public void reset() {}

    @Test
    public void T4_01_add_your_own_tests_as_needed() throws Exception {}
  }

  private static final Object[] CREATE_14_OPERATORS =
      new Object[] {
        // *** Tāmaki Makaurau | Auckland ***
        CREATE_OPERATOR,
        "'West Auckland Camel Treks'", // WACT-AKL-001
        "'AKL'",
        CREATE_OPERATOR,
        "'Volcano Bungee Jump'", // VBJ-AKL-002
        "'AKL'",
        // *** Kirikiriroa | Hamilton ***
        CREATE_OPERATOR,
        "'Mystical Waikato Whale Watching'", // MWWW-HLZ-001
        "'HLZ'",
        CREATE_OPERATOR,
        "'Hobbiton Skydiving Tours'", // HST-HLZ-002
        "'HLZ'",
        // *** Tauranga ***
        CREATE_OPERATOR,
        "'Mount Maunganui Ski Resort'", // MMSR-TRG-001
        "'TRG'",
        CREATE_OPERATOR,
        "'Shark Snorkel Bay'", // SSB-TRG-002
        "'TRG'",
        // *** Taupō-nui-a-Tia | Taupo ***
        CREATE_OPERATOR,
        "'Huka Falls Barrel Rides'", // HFBR-TUO-001
        "'TUO'",
        CREATE_OPERATOR,
        "'Taupo UFO Watching'", // TUW-TUO-002
        "'TUO'",
        // *** Te Whanganui-a-Tara | Wellington ***
        CREATE_OPERATOR,
        "'Parliament Bungee Jump'", // PBJ-WLG-001
        "'WLG'",
        // *** Nelson | Whakatu ***
        CREATE_OPERATOR,
        "'Nelson UFO Watching'", // NUW-NSN-001
        "'NSN'",
        // *** Ōtautahi | Christchurch ***
        CREATE_OPERATOR,
        "'Christchurch Camel Treks'", // CCT-CHC-001
        "'CHC'",
        CREATE_OPERATOR,
        "'Avon River Whitewater Rafting'", // ARWR-CHC-002
        "'CHC'",
        // *** Ōtepoti | Dunedin ***
        CREATE_OPERATOR,
        "'Dunedin Penguin Parade'", // DPP-DUD-001
        "'DUD'",
        CREATE_OPERATOR,
        "'Baldwin Street Ski Jumping'", // BSSJ-DUD-002
        "'DUD'",
      };

  private static final Object[] CREATE_27_ACTIVITIES =
      new Object[] {
        // *** West Aucklad Camel Treks | Tāmaki Makaurau | Auckland ***
        CREATE_ACTIVITY, // 1
        "'Bethells Beach Camel Trek'",
        "Adventure",
        "'WACT-AKL-001'",
        CREATE_ACTIVITY, // 2
        "'Sky Tower Base Jumping'",
        "Adventure",
        "'WACT-AKL-001'",
        // *** Volcano Bungee Jump | Tāmaki Makaurau | Auckland ***
        CREATE_ACTIVITY, // 3
        "'Flaming Feast'",
        "Food",
        "'VBJ-AKL-002'",
        CREATE_ACTIVITY, // 4
        "'Lava Lookout Walk'",
        "SCENIC",
        "'VBJ-AKL-002'",
        // *** Mystical Waikato Whale Watching | Kirikiriroa | Hamilton ***
        CREATE_ACTIVITY, // 5
        "'Whale and Dolphin Safari'",
        "Wildlife",
        "'MWWW-HLZ-001'",
        CREATE_ACTIVITY, // 6
        "'Whale and Chips'",
        "Food",
        "'MWWW-HLZ-001'",
        // *** Hobbiton Skydiving Tours | Kirikiriroa | Hamilton ***
        CREATE_ACTIVITY, // 7
        "'The Frodo Jump'",
        "Adventure",
        "'HST-HLZ-002'",
        CREATE_ACTIVITY, // 8
        "'The Gandalf Picnic'",
        "Food",
        "'HST-HLZ-002'",
        CREATE_ACTIVITY, // 9
        "'Flying Orcs'",
        "Wildlife",
        "'HST-HLZ-002'",
        // *** Mount Maunganui Ski Resort | Tauranga ***
        CREATE_ACTIVITY, // 10
        "'Legends of the Lost Snow'",
        "Culture",
        "'MMSR-TRG-001'",
        // *** Shark Snorkel Bay | Tauranga ***
        CREATE_ACTIVITY, // 11
        "'Nemos Playground'",
        "Wildlife",
        "'SSB-TRG-002'",
        CREATE_ACTIVITY, // 12
        "'Seaside Mussel Munch'",
        "Food",
        "'SSB-TRG-002'",
        // *** Huka Falls Barrel Rides | Taupō-nui-a-Tia | Taupo ***
        CREATE_ACTIVITY, // 13
        "'Waterfall Wine Tasting'",
        "Food",
        "'HFBR-TUO-001'",
        CREATE_ACTIVITY, // 14
        "'Huka Eel Submarine'",
        "Wildlife",
        "'HFBR-TUO-001'",
        // *** Taupo UFO Watching | Taupō-nui-a-Tia | Taupo ***
        CREATE_ACTIVITY, // 15
        "'Unidentified Frying Objects'",
        "Food",
        "'TUW-TUO-002'",
        CREATE_ACTIVITY, // 16
        "'Close Encounters of the Lake'",
        "Wildlife",
        "'TUW-TUO-002'",
        // *** Parliament Bungee Jump | Te Whanganui-a-Tara | Wellington ***
        CREATE_ACTIVITY, // 17
        "'Jumping Through Political Loopholes'",
        "Culture",
        "'PBJ-WLG-001'",
        CREATE_ACTIVITY, // 18
        "'Politics with a View'",
        "SCENIC",
        "'PBJ-WLG-001'",
        // *** Nelson UFO Watching | Nelson ***
        CREATE_ACTIVITY, // 19
        "'Stars or Spaceships?'",
        "Scenic",
        "'NUW-NSN-001'",
        CREATE_ACTIVITY, // 20
        "'Meteorites & Meat Pies'",
        "Food",
        "'NUW-NSN-001'",
        // *** Christchurch Camel Treks | Ōtautahi | Christchurch ***
        CREATE_ACTIVITY, // 21
        "'Wild Desert Desserts'",
        "Food",
        "'CCT-CHC-001'",
        // *** Avon River Whitewater Rafting | Ōtautahi | Christchurch ***
        CREATE_ACTIVITY, // 22
        "'Rapid Riverside Ramen'",
        "Food",
        "'ARWR-CHC-002'",
        CREATE_ACTIVITY, // 23
        "'Duck Dodging'",
        "Wildlife",
        "'ARWR-CHC-002'",
        CREATE_ACTIVITY, // 24
        "'River Rush'",
        "Adventure",
        "'ARWR-CHC-002'",
        // *** Dunedin Penguin Parade | Ōtepoti | Dunedin ***
        CREATE_ACTIVITY, // 25
        "'Penguin Pies'",
        "Food",
        "'DPP-DUD-001'",
        CREATE_ACTIVITY, // 26
        "'Waddling Wonders'",
        "Wildlife",
        "'DPP-DUD-001'",
        CREATE_ACTIVITY, // 27
        "'Snowy Slide'",
        "Adventure",
        "'DPP-DUD-001'",
        // *** Baldwin Street Ski Jumping | Ōtepoti | Dunedin ***
        // none
      };

  private static Object[] unpack(Object[] commands, Object... more) {
    List<Object> all = new ArrayList<Object>();
    all.addAll(List.of(commands));

    for (Object item : more) {
      // String[] are options for certain commands, so treat as a single item
      if (item instanceof Object[] && !(item instanceof String[])) {
        all.addAll(Arrays.asList((Object[]) item));
      } else {
        all.add(item);
      }
    }
    return all.toArray(new Object[0]);
  }

  private static String[] options(String... options) {
    List<String> all = new ArrayList<String>();
    all.addAll(List.of(options));
    return all.toArray(new String[all.size()]);
  }
}
