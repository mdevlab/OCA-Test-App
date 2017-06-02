package io.mdevlab.ocatraining.util;

import static io.mdevlab.ocatraining.model.Test.CUSTOM_TEST_MODE_NAME;
import static io.mdevlab.ocatraining.model.Test.FINAL_TEST_MODE_NAME;
import static io.mdevlab.ocatraining.model.Test.RANDOM_TEST_MODE_NAME;

/**
 * Created by bachiri on 6/1/17.
 */

public class OcaStringUtils {

    public static String getStringTestMode(int testMode) {
        String testModeStr = "";
        switch (testMode) {
            case 1:
                testModeStr = FINAL_TEST_MODE_NAME;
                break;
            case 2:
                testModeStr = CUSTOM_TEST_MODE_NAME;
                break;
            case 3:
                testModeStr = RANDOM_TEST_MODE_NAME;
                break;

        }
        return testModeStr;
    }
}
