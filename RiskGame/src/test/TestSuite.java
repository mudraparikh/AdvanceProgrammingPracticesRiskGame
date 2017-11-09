
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import riskModels.dice.DiceTest;
import riskModels.map.MapModelTest;
import riskModels.player.PlayerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MapModelTest.class, PlayerTest.class, DiceTest.class, })
public class TestSuite {
    //nothing
}
