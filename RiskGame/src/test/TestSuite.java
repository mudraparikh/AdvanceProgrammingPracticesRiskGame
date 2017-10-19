package test;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import riskModels.gamedriver.GamePlayAPITest;
import riskModels.gamedriver.StartupPhaseTest;
import riskModels.map.MapModelTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({GamePlayAPITest.class, MapModelTest.class, StartupPhaseTest.class})
public class TestSuite {
    //nothing
}