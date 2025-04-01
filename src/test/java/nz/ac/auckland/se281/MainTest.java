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
@SuiteClasses({MainTest.Task1.class})
public class MainTest {

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_xx_zero_operators() throws Exception {
      runCommands(SEARCH_OPERATORS, "*", EXIT);

      assertContains("There are no matching operators found.");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_operator_name() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains("Successfully created operator 'West Auckland Camel Treks'");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_location() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains("Successfully created operator 'West Auckland Camel Treks'");
      assertContains("located in 'Auckland | Tāmaki Makaurau'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_id_part_name() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains("Successfully created operator 'West Auckland Camel Treks'");
      assertContains("located in 'Auckland | Tāmaki Makaurau'.");
      assertContains("WACT");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_id_full() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'AKL'", EXIT);

      assertContains(
          "Successfully created operator 'West Auckland Camel Treks' ('WACT-AKL-001') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_name_too_short() throws Exception {
      runCommands(CREATE_OPERATOR, "'Yo'", "'AKL'", EXIT);

      assertContains("Operator not created: 'Yo' is not a valid operator name.");
      assertDoesNotContain("Successfully", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_invalid_location() throws Exception {
      runCommands(CREATE_OPERATOR, "'West Auckland Camel Treks'", "'XYZ'", EXIT);

      assertContains("Operator not created: 'XYZ' is an invalid location.");
      assertDoesNotContain("Successfully", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    // specify location in full name
    @Test
    public void T1_xx_create_operator_valid_location_full_name_english() throws Exception {
      runCommands(CREATE_OPERATOR, "'Parliament Bungee Jump'", "'Wellington'", EXIT);

      assertContains(
          "Successfully created operator 'Parliament Bungee Jump' ('PBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_valid_location_full_name_teo_reo() throws Exception {
      runCommands(CREATE_OPERATOR, "'Parliament Bungee Jump'", "'Te Whanganui-a-Tara'", EXIT);

      assertContains(
          "Successfully created operator 'Parliament Bungee Jump' ('PBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    // SHOW used in handout
    @Test
    public void T1_xx_create_operator_saved() throws Exception {
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

    // operator in Tauranga 'Shark Snorkel Bay'
    @Test
    public void T1_xx_create_operator_saved_english_tereo_match() throws Exception {
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
    public void T1_xx_create_operator_saved_two() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'West Auckland Camel Treks'",
          "'AKL'", //
          CREATE_OPERATOR,
          "'Parliament Bungee Jump'",
          "'WLG'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki"
              + " Makaurau')");
      assertContains(
          "* Parliament Bungee Jump ('PBJ-WLG-001' located in 'Wellington | Te Whanganui-a-Tara')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_operator_same_name_same_location() throws Exception {
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
    public void T1_xx_create_operator_same_location() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Parliament Bungee Jump'",
          "'Wellington'", //
          CREATE_OPERATOR,
          "'Parliament Bungee Jump'",
          "'Te Whanganui-a-Tara'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'Parliament Bungee Jump' ('PBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertContains(
          "Operator not created: the operator name 'Parliament Bungee Jump' already exists same"
              + " location for 'Wellington | Te Whanganui-a-Tara'.");
      assertContains("There is 1 matching operator found:");
      assertContains(
          "* Parliament Bungee Jump ('PBJ-WLG-001' located in 'Wellington | Te Whanganui-a-Tara')");
      assertDoesNotContain("002", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_operator_same_name_different_location() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'WLG'", //
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'AKL'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-WLG-001') located in"
              + " 'Wellington | Te Whanganui-a-Tara'.");
      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-AKL-001') located in"
              + " 'Auckland | Tāmaki Makaurau'.");
      assertContains("There are 2 matching operators found:");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-WLG-001' located in 'Wellington | Te Whanganui-a-Tara')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-001' located in 'Auckland | Tāmaki" + " Makaurau')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_operator_same_name_different_full_location() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'Taupo'", //
          CREATE_OPERATOR,
          "'Volcano Bungee Jump'",
          "'Tauranga'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-TUO-001') located in"
              + " 'Taupo | Taupō-nui-a-Tia'.");
      assertContains(
          "Successfully created operator 'Volcano Bungee Jump' ('VBJ-TRG-001') located in"
              + " 'Tauranga'.");
      assertContains("There are 2 matching operators found:");
      assertContains("* Volcano Bungee Jump ('VBJ-TUO-001' located in 'Taupo | Taupō-nui-a-Tia')");
      assertContains("* Volcano Bungee Jump ('VBJ-TRG-001' located in 'Tauranga')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_operator_three_locations() throws Exception {
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
    public void T1_xx_create_operator_same_location_three() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Mystical Waikato Whale Watching'",
          "'HLZ'", //
          CREATE_OPERATOR,
          "'Glowworm Fireworks Adventures'",
          "'Kirikiriroa'", //
          CREATE_OPERATOR,
          "'Hobbiton Skydiving Tours'",
          "'Hamilton'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'Mystical Waikato Whale Watching' ('MWWW-HLZ-001') located"
              + " in 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Glowworm Fireworks Adventures' ('GFA-HLZ-002') located in"
              + " 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Hobbiton Skydiving Tours' ('HST-HLZ-003') located in"
              + " 'Hamilton | Kirikiriroa'.");
      assertContains("There are 3 matching operators found:");
      assertContains(
          "* Mystical Waikato Whale Watching ('MWWW-HLZ-001' located in 'Hamilton | Kirikiriroa')");
      assertContains(
          "* Glowworm Fireworks Adventures ('GFA-HLZ-002' located in 'Hamilton | Kirikiriroa')");
      assertContains(
          "* Hobbiton Skydiving Tours ('HST-HLZ-003' located in 'Hamilton | Kirikiriroa')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    // 5 operators in same and mixed locations
    // Mystical Waikato Whale Watching – Says you can see whales in the Waikato River.
    // Glowworm Fireworks Adventures – Promises glowworms that shoot fireworks (they don’t).
    // Hobbiton Skydiving Tours – Parachute directly into Hobbiton (this does not exist).

    // Tauranga (Tauranga, TRG)
    // Mount Maunganui Ski Resort
    // Shark Snorkel Bay

    @Test
    public void T1_xx_create_operators_mixed_and_same_locations() throws Exception {
      runCommands(
          CREATE_OPERATOR,
          "'Mystical Waikato Whale Watching'",
          "'HLZ'", //
          CREATE_OPERATOR,
          "'Glowworm Fireworks Adventures'",
          "'Kirikiriroa'", //
          CREATE_OPERATOR,
          "'Hobbiton Skydiving Tours'",
          "'Hamilton'", //
          CREATE_OPERATOR,
          "'Mount Maunganui Ski Resort'",
          "'Tauranga'", //
          CREATE_OPERATOR,
          "'Shark Snorkel Bay'",
          "'Tauranga'", //
          SEARCH_OPERATORS,
          "*", //
          EXIT);

      assertContains(
          "Successfully created operator 'Mystical Waikato Whale Watching' ('MWWW-HLZ-001') located"
              + " in 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Glowworm Fireworks Adventures' ('GFA-HLZ-002') located in"
              + " 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Hobbiton Skydiving Tours' ('HST-HLZ-003') located in"
              + " 'Hamilton | Kirikiriroa'.");
      assertContains(
          "Successfully created operator 'Mount Maunganui Ski Resort' ('MMSR-TRG-001') located in"
              + " 'Tauranga'.");
      assertContains(
          "Successfully created operator 'Shark Snorkel Bay' ('SSB-TRG-002') located in"
              + " 'Tauranga'.");
      assertContains("There are 5 matching operators found:");
      assertContains(
          "* Mystical Waikato Whale Watching ('MWWW-HLZ-001' located in 'Hamilton | Kirikiriroa')");
      assertContains(
          "* Glowworm Fireworks Adventures ('GFA-HLZ-002' located in 'Hamilton | Kirikiriroa')");
      assertContains(
          "* Hobbiton Skydiving Tours ('HST-HLZ-003' located in 'Hamilton | Kirikiriroa')");
      assertContains("* Mount Maunganui Ski Resort ('MMSR-TRG-001' located in 'Tauranga')");
      assertContains("* Shark Snorkel Bay ('SSB-TRG-002' located in 'Tauranga')");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_14_operators() throws Exception {
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

    // HIDE
    @Test
    public void T1_xx_create_14_operators_saved() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "*", EXIT));

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
      assertContains("There are 14 matching operators found:");
      assertDoesNotContain("Operator not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_search_operators_specific_location_te_reo() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "'Tāmaki   '", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki Makaurau')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-002' located in 'Auckland | Tāmaki Makaurau')");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 14", true);
    }

    @Test
    public void T1_xx_search_operators_specific_location_english() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "'Auckland'", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki Makaurau')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-002' located in 'Auckland | Tāmaki Makaurau')");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_search_operators_specific_location_abbreviation() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "akl", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* West Auckland Camel Treks ('WACT-AKL-001' located in 'Auckland | Tāmaki Makaurau')");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-002' located in 'Auckland | Tāmaki Makaurau')");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_search_operators_keyword_in_location() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "ranga", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains("* Mount Maunganui Ski Resort ('MMSR-TRG-001' located in 'Tauranga')");
      assertContains("* Shark Snorkel Bay ('SSB-TRG-002' located in 'Tauranga')");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 14", true);
    }

    // HIDE
    @Test
    public void T1_xx_search_operators_keyword_across_multiple_locations() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "tau", EXIT));

      assertContains("There are 6 matching operators found:");
      assertContains("* Mount Maunganui Ski Resort ('MMSR-TRG-001' located in 'Tauranga')");
      assertContains("* Shark Snorkel Bay ('SSB-TRG-002' located in 'Tauranga')");
      assertContains(
          "* Huka Falls Barrel Rides ('HFBR-TUO-001' located in 'Taupo | Taupō-nui-a-Tia')");
      assertContains("* Taupo UFO Watching ('TUW-TUO-002' located in 'Taupo | Taupō-nui-a-Tia')");
      assertContains(
          "* Christchurch Camel Treks ('CCT-CHC-001' located in 'Christchurch | Ōtautahi')");
      assertContains(
          "* Avon River Whitewater Rafting ('ARWR-CHC-002' located in 'Christchurch | Ōtautahi')");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 14", true);
    }

    // HIDE
    @Test
    public void T1_xx_search_operators_keyword_no_match() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "xyz", EXIT));

      assertContains("There are no matching operators found.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("matching operators found:", true);
    }

    @Test
    public void T1_xx_search_operators_keyword_in_name() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "Avon", EXIT));

      assertContains("There is 1 matching operator found:");
      assertContains(
          "* Avon River Whitewater Rafting ('ARWR-CHC-002' located in 'Christchurch | Ōtautahi')");
      assertDoesNotContain("There are 14", true);
      assertDoesNotContain("no matching operators found", true);
    }

    // HIDE
    @Test
    public void T1_xx_search_operators_keyword_in_name_multiple() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "bungee", EXIT));

      assertContains("There are 2 matching operators found:");
      assertContains(
          "* Volcano Bungee Jump ('VBJ-AKL-002' located in 'Auckland | Tāmaki Makaurau')");
      assertContains(
          "* Parliament Bungee Jump ('PBJ-WLG-001' located in 'Wellington | Te Whanganui-a-Tara')");
      assertDoesNotContain("There are 14", true);
      assertDoesNotContain("no matching operators found", true);
    }

    @Test
    public void T1_xx_search_operators_keyword_in_name_no_match() throws Exception {
      runCommands(unpack(CREATE_14_OPERATORS, SEARCH_OPERATORS, "climbing", EXIT));

      assertContains("There are no matching operators found.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("matching operators found:", true);
    }

    public static class YourTests extends CliTest {

      public YourTests() {
        super(Main.class);
      }
    }
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
