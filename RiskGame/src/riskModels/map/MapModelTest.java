package riskModels.map;

import junit.framework.TestCase;

//import org.junit.Test;

public class MapModelTest extends TestCase {

    public void testReadMapFile() throws Exception {
        MapModel m = new MapModel();
        m.mapFileInputParse();
        assertEquals(1, 1);
    }

}