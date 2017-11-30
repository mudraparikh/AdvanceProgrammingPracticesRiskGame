
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import riskModels.player.AggressiveBot;
import test.cards.DiceTest;
import test.cards.HandTest;
import test.map.MapModelTest;
import test.player.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({MapModelTest.class,
        PlayerTest.class,
        DiceTest.class,
        HandTest.class,
        AggressiveBotTest.class,
        BenevolentBotTest.class,
        RandomBotTest.class,
        CheaterBotTest.class})
public class TestSuite {
    //nothing
}
