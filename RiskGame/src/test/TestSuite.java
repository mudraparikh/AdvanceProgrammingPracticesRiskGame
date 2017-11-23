
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.cards.DiceTest;
import test.cards.HandTest;
import test.map.MapModelTest;
import test.player.PlayerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MapModelTest.class, PlayerTest.class, DiceTest.class,HandTest.class })

/**
 * This Test suite class all the test cases are collected to test as a single batch.
 * @author hnath
 *
 */
public class TestSuite {
    //nothing
}
