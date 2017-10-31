package test;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import riskModels.gamedriver.GamePlayModelTest;
import riskModels.gamedriver.StartupPhaseTest;
import riskModels.map.MapModelTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({GamePlayModelTest.class, MapModelTest.class, StartupPhaseTest.class})
public class TestSuite {
    //nothing
}