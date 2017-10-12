package riskModels.map;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapModelTest extends TestCase {

    public void testReadMapFile() throws Exception {
        MapModel m = new MapModel();
        m.mapFileInputParse();
        assertEquals(1,1);
    }

}