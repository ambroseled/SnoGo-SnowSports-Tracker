package seng202.team5.DataManipulation;

import seng202.team5.Model.Activity;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataValidationTest {


    /**
     * Tests for when random values throughout the file are missing
     */
    @Test
    public void testMissingData() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/missingDataValues.csv").get(0);
        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(16, validator.getDataValidated());
            assertEquals(0, validator.getPointsDeleted());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests first missing values, as validation is based on valid predecessor
     */
    @Test
    public void testMissingFirstPoint() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/missingFirstValues.csv").get(0);


        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);
            assertEquals(0, validator.getDataValidated());
            assertEquals(2, validator.getPointsDeleted());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests when the entire heart rate column is missing
     */
    @Test
    public void testMissingHeartRateColumn() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/missingHeartRateColumn.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(0, validator.getPointsDeleted());
            assertEquals(16, validator.getDataValidated());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests when an entire important column is missing, in this case longitude
     */
    @Test
    public void testMissingPositionalColumn() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/missingPositionalColumn.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(17, validator.getPointsDeleted());
            assertEquals(0, validator.getDataValidated());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    //Test missing values of the last datapoint

    /**
     * Tests when last values are missing
     */
    @Test
    public void testMissingLastValues() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/missingLastValues.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(0, validator.getPointsDeleted());
            assertEquals(2, validator.getDataValidated());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests When the last date is missing
     */
    @Test
    public void testMissingLastDate() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/missingLastDate.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(1, validator.getPointsDeleted());
            assertEquals(0, validator.getDataValidated());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests when there are no data points in the data set
     */
    @Test
    public void testEmptyDataSet() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/emptyDataSet.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(0, validator.getPointsDeleted());
            assertEquals(0, validator.getDataValidated());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests when there is only one value for heart rate in the entire heart rate column
     */
    @Test
    public void testOneHeartRateValue() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/oneHeartRateValue.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(0, validator.getPointsDeleted());
            assertEquals(15, validator.getDataValidated());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Tests when there is only one value for latitude in the entire column
     */
    @Test
    public void testOneLatitudeValue() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/oneLatitudeValue.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(11, validator.getPointsDeleted()); //Deleted all points before valid point
            assertEquals(5, validator.getDataValidated()); //Change all point after valid point
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test with outrageous / outlying values instead of just missing data (should act the same)
     */
    @Test
    public void testOutrageousValues() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/validationTestFiles/outrageousValues.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(0, validator.getPointsDeleted()); //Deleted all points before valid point
            assertEquals(6, validator.getDataValidated()); //Change all point after valid point
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test with a file with very unusable data
     */
    @Test
    public void testCorruptFile() {
        InputDataParser parser = new InputDataParser();
        Activity activity = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/corruptFile.csv").get(0);

        DataValidator validator = new DataValidator();

        try {
            validator.validateActivity(activity);

            assertEquals(4, validator.getPointsDeleted());
            assertEquals(36, validator.getDataValidated());

        }
        catch (Exception e) {
            assert(false);
        }
    }


}
