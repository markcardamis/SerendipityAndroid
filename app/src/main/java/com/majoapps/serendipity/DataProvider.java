package com.majoapps.serendipity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Mark on 13/02/2016.
 */
public class DataProvider {

    public static LinkedHashMap<String, List<String>> getInfo()
    {


        LinkedHashMap<String, List<String>> LevelsDetails = new LinkedHashMap<String, List<String>>();

        List<String> Level_1 = new ArrayList<String>();
        Level_1.add("When was your last embarrassing moment?");
        Level_1.add("In what way would you like to be famous?");
        Level_1.add("What is an interesting fact about yourself?");
        Level_1.add("What is a perfect day for you?");
        Level_1.add("Do a shot together");
        Level_1.add("What is your nickname?");

        List<String> Level_2 = new ArrayList<String>();
        Level_2.add("When did you last laugh uncontrollably");
        Level_2.add("Show off 1 dance move");
        Level_2.add("What does friendship mean to you?");
        Level_2.add("What is your favourite part of your body?");
        Level_2.add("What colour underwear are you both wearing?");
        Level_2.add("Tell your date something you like about them");

        List<String> Level_3 = new ArrayList<String>();
        Level_3.add("Get as close to each other without kissing");
        Level_3.add("Hold hands for 30 seconds");
        Level_3.add("Walk to another area holding hands");
        Level_3.add("Kiss on the neck/ear");
        Level_3.add("When did you last masturbate? (don't lie)");
        Level_3.add("Introduce yourself to a stranger as a couple");

        List<String> Level_4 = new ArrayList<String>();
        Level_4.add("What is your favourite sex position?");
        Level_4.add("Do a tequila shot");
        Level_4.add("Flash your underwear to each other");
        Level_4.add("Kiss gently for 10 seconds");
        Level_4.add("Ask a naughty question");
        Level_4.add("Run fingers across partner's lips");

        List<String> Level_5 = new ArrayList<String>();
        Level_5.add("Organise date/time for another date");
        Level_5.add("Go to the dance floor and move seductively");
        Level_5.add("Kiss passionately for 10 seconds");
        Level_5.add("Buy your date flowers and chocolate");
        Level_5.add("It's time to go.... alone");
        Level_5.add("Go back to your place and make your own list");




        LevelsDetails.put("Lets play", Level_1);
        LevelsDetails.put("Two left feet", Level_2);
        LevelsDetails.put("Take a chance", Level_3);
        LevelsDetails.put("You did what!", Level_4);
        LevelsDetails.put("It better be love", Level_5);


        return LevelsDetails;

    }


}
