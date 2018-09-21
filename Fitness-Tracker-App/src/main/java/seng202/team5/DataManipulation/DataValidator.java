package seng202.team5.DataManipulation;

import java.util.ArrayList;
import java.util.Date;

import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;

/**
 * This class performs various validation procedures on one activity class.
 * The InputDataParser utilises this class.
 */

public class DataValidator {


	//TODO: are these used?????? YESS THEY ARE! :)

	// This attribute is used to keep track of how many points have been deleted
	private int pointsDeleted = 0;
	/**
	 * This attribute keeps track of how many data values have been altered
	 */
	private int dataValidated = 0;
	/**
	 * This attribute is used for determining the changes made to the activity object
	 */
	private int initialDataSetSize;

	public int getPointsDeleted() { return pointsDeleted; }
	public int getDataValidated() { return dataValidated; }
	public int getInitialDataSetSize() { return initialDataSetSize; }

	/**
	 * The method uses several helper methods to check the validity of each piece of data, i.e.
	 * longitude, elevation, datetime...
	 * The information returned from this method is used in determining if and howthe data point
	 * needs to be fixed
	 * @param dataPoint a given data point used to calculate the validity of this point
	 * @return dataPointValidity an array of 6 elements the first 5 corresponding to 1 for
	 * valid data and 0 for invalid data, the 6th element is a count for how many
	 */
	private int[] getDataPointValidity(DataPoint dataPoint) {

		int[] dataPointValidity = {1, 1, 1, 1, 1, 0};

		if (!isDateTimeValid(dataPoint.getDateTime())) {
			dataPointValidity[0] = 0;
			dataPointValidity[5]++;
		}
		if (!isHeartRateValid(dataPoint.getHeartRate())) {
			dataPointValidity[1] = 0;
			dataPointValidity[5]++;
		}
		if (!isLatitudeValid(dataPoint.getLatitude())) {
			dataPointValidity[2] = 0;
			dataPointValidity[5]++;
		}
		if (!isLongitudeValid(dataPoint.getLongitude())) {
			dataPointValidity[3] = 0;
			dataPointValidity[5]++;
		}
		if(!isElevationValid(dataPoint.getElevation())) {
			dataPointValidity[4] = 0;
			dataPointValidity[5]++;
		}

		return dataPointValidity;
	}

	/**
	 * Checks if the date time is null
	 * @param dateTime the given date time
	 * @return true if date is valid, false if not
	 */
	private boolean isDateTimeValid(Date dateTime) {
		if (dateTime == null) {
			return false;
		}
		else {
			//Other date checks performed here
			return true;
		}
	}
	/**
	 * Checks if the heart rate is between 26 (the lowest heart rate ever recorded)
	 * and 480 (the highest heart rate ever recorded)
	 * @param heartRate the given heart rate
	 * @return true if heart rate is valid, false if not.
	 */
	private boolean isHeartRateValid(int heartRate) {
		if (heartRate < 26 || heartRate > 480) {
			return false;
		}
		return true;
	}
	/**
	 * Checks if the latitude is between -90 and 90, as the range of latitude is [-90, 90]
	 * @param latitude the given latitude value
	 * @return true if the latitude is valid, flase if not
	 */
	private boolean isLatitudeValid(double latitude) {
		if (latitude < -90 || latitude > 90) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the longitude is between -180 and 180 as the range of longitude is [-180, 180]
	 * @param longitude the given longitude value
	 * @return true if the value is valid, flase if not
	 */
	private boolean isLongitudeValid(double longitude) {
		if (longitude < -180 || longitude > 180) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the elevation is between -213m, as this is the lowest point on the surface of earth,
	 * and 8850m as this is the highest point on earth +2m as the user may have been holding the device up high.
	 * @param elevation the given elevation value
	 * @return true if the value is valid, false if not
	 */
	private boolean isElevationValid(double elevation) {
		if (elevation < -213 || elevation > 8850) {
			return false;
		}
		return true;
	}

	/**
	 * Control method for the class, the method is called from within the InputDataParser class.
	 * The method performs validation on all of the data points of the activity
	 * @param activity the given activity on which to perform validation
	 */
	public void validateActivity(Activity activity) {

		ArrayList<DataPoint> dataPoints = activity.getDataSet().getDataPoints();
		initialDataSetSize = dataPoints.size();

		validateFirstPoint(dataPoints);
		validateDataPoints(dataPoints);
	}

	/**
	 * This method loops through the dataPoints until it finds a valid dataPoint
	 * For each of the position elements i.e. not heart rate, it will delete the
	 * dataPoint.
	 * @param dataPoints the data points which to make the first point valid
	 * @return true if there is a valid first point, false when the every point has been deleted
	 */
	private boolean validateFirstPoint(ArrayList<DataPoint> dataPoints) {
		boolean firstPointValid = false;

		while (!firstPointValid && dataPoints.size() > 0) {
			int[] dataPointValidity = getDataPointValidity(dataPoints.get(0));

			if (dataPointValidity[5] == 0 ) {
				firstPointValid = true;
			}
			else if (dataPointValidity[1] == 0) {
				firstPointValid = true;
			}
			else {
				dataPoints.remove(0);
				pointsDeleted++;
			}
		}

		return firstPointValid;
	}

	/**
	 * This method loops through each data point and validates it, if the datetime cannot be
	 * validated then the point would have been deleted
	 * @param dataPoints the data points to be validated, the first point is valid
	 */
	private void validateDataPoints(ArrayList<DataPoint> dataPoints) {

		for (int i = 1; i < dataPoints.size(); i++) {

			boolean validDateTime = validateDateTime(dataPoints, i);

			if (validDateTime) {
				validateHeartRate(dataPoints, i);
				validateLatitude(dataPoints, i);
				validateLongitude(dataPoints, i);
				validateElvation(dataPoints, i);
			}
		}

	}

	/**
	 * This method checks the validity of the date time, uses the preceding and the next valid succeeding data
	 * point to estimate the value, if the value cannot be estimated then the point is deleted.
	 * @param dataPoints used for selecting preceding and succeeding data points to estimate the value
	 * @param i where the datapoint is in relation to the others
	 * @return true, if the date was valid or if it could be estimated, false if not
	 */
	private boolean validateDateTime(ArrayList<DataPoint> dataPoints, int i) {

		DataPoint dataPoint = dataPoints.get(i);
		int[] dataPointValidity = getDataPointValidity(dataPoint);

		if (dataPointValidity[0] == 0) {//If date time error

			DataPoint previousDataPoint = dataPoints.get(i-1);

			boolean nextValidPointFound = false;
			for (int j = 1; i + j < dataPoints.size(); j++) {

				DataPoint nextDataPoint = dataPoints.get(i+j);
				int[] nextDataPointValidity = getDataPointValidity(nextDataPoint);

				if (nextDataPointValidity[0] == 1) {//Next datapoint is valid

					long dateTimeInitial = previousDataPoint.getDateTime().getTime();
					long dateTimeFinal = nextDataPoint.getDateTime().getTime();

					long newDateTime = (dateTimeInitial + dateTimeFinal)/2;

					dataPoint.setDateTime(new Date(newDateTime));
					dataValidated++;

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				dataPoints.remove(dataPoint);
				pointsDeleted++;
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks the validity of the heart rate of the data point, uses the preceding and the next
	 * valid succeeding data point to estimate the value, if there is no valid succeeding heart rate then
	 * the preceding value is used.
	 * @param dataPoints used for selecting preceding and succeeding data points to estimate the value.
	 * @param i where the datapoint is in relation to the others
	 */
	private void validateHeartRate(ArrayList<DataPoint> dataPoints, int i) {

		DataPoint dataPoint = dataPoints.get(i);
		int[] dataPointValidity = getDataPointValidity(dataPoint);

		if (dataPointValidity[1] == 0) {//If heart rate error

			DataPoint previousDataPoint = dataPoints.get(i-1);

			boolean nextValidPointFound = false;
			for (int j = 1; i + j < dataPoints.size(); j++) {

				DataPoint nextDataPoint = dataPoints.get(i+j);
				int[] nextDataPointValidity = getDataPointValidity(nextDataPoint);

				if (nextDataPointValidity[1] == 1) {//Next datapoint is valid

					int heartRateInitial = previousDataPoint.getHeartRate();
					int heartRateFinal = nextDataPoint.getHeartRate();

					int newHeartRate = (heartRateInitial + heartRateFinal)/2;

					dataPoint.setHeartRate(newHeartRate);
					dataValidated++;

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				int newHeartRate = previousDataPoint.getHeartRate();
				dataPoint.setHeartRate(newHeartRate);
				dataValidated++;
			}


		}
	}

	/**
	 * This method checks the validity of the latitude of the data point, uses the preceding and the next
	 * valid succeeding data point to estimate the value, if there is no valid succeeding latitude then
	 * the preceding value is used.
	 * @param dataPoints used for selecting preceding and succeeding data points to estimate the value.
	 * @param i where the datapoint is in relation to the others
	 */
	private void validateLatitude(ArrayList<DataPoint> dataPoints, int i) {

		DataPoint dataPoint = dataPoints.get(i);
		int[] dataPointValidity = getDataPointValidity(dataPoint);

		if (dataPointValidity[2] == 0) {//If latitude error

			DataPoint previousDataPoint = dataPoints.get(i-1);

			boolean nextValidPointFound = false;

			for (int j = 1; i + j < dataPoints.size(); j++) {

				DataPoint nextDataPoint = dataPoints.get(i+j);
				int[] nextDataPointValidity = getDataPointValidity(nextDataPoint);

				if (nextDataPointValidity[2] == 1) {//Next datapoint is valid

					double latitudeInitial = previousDataPoint.getLatitude();
					double latitudeFinal = nextDataPoint.getLatitude();

					double newLatitude = (latitudeInitial + latitudeFinal)/2;

					dataPoint.setLatitude(newLatitude);
					dataValidated++;

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				double newLatitude = previousDataPoint.getLatitude();
				dataPoint.setLatitude(newLatitude);
				dataValidated++;
			}

		}

	}

	/**
	 * This method checks the validity of the longitude of the data point, uses the preceding and the next
	 * valid succeeding data point to estimate the value, if there is no valid succeeding longitude then
	 * the preceding value is used.
	 * @param dataPoints used for selecting preceding and succeeding data points to estimate the value.
	 * @param i where the datapoint is in relation to the others
	 */
	private void validateLongitude(ArrayList<DataPoint> dataPoints, int i) {

		DataPoint dataPoint = dataPoints.get(i);
		int[] dataPointValidity = getDataPointValidity(dataPoint);

		if (dataPointValidity[3] == 0) {//If longitude error

			DataPoint previousDataPoint = dataPoints.get(i-1);

			boolean nextValidPointFound = false;
			for (int j = 1; i + j < dataPoints.size(); j++) {

				DataPoint nextDataPoint = dataPoints.get(i+j);
				int[] nextDataPointValidity = getDataPointValidity(nextDataPoint);

				if (nextDataPointValidity[3] == 1) {//Next datapoint is valid

					double longitudeInitial = previousDataPoint.getLongitude();
					double longitudeFinal = nextDataPoint.getLongitude();

					double newLongitude = (longitudeInitial + longitudeFinal)/2;

					dataPoint.setLongitude(newLongitude);
					dataValidated++;

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				double newLongitude = previousDataPoint.getLongitude();
				dataPoint.setLongitude(newLongitude);
				dataValidated++;
			}

		}
	}

	/**
	 * This method checks the validity of the elevation of the data point, uses the preceding and the next
	 * valid succeeding data point to estimate the value, if there is no valid succeeding elevation then
	 * the preceding value is used.
	 * @param dataPoints used for selecting preceding and succeeding data points to estimate the value.
	 * @param i where the datapoint is in relation to the others
	 */
	private void validateElvation(ArrayList<DataPoint> dataPoints, int i) {

		DataPoint dataPoint = dataPoints.get(i);
		int[] dataPointValidity = getDataPointValidity(dataPoint);

		if (dataPointValidity[4] == 0) {//If elevation error

			DataPoint previousDataPoint = dataPoints.get(i-1);

			boolean nextValidPointFound = false;
			for (int j = 1; i + j < dataPoints.size(); j++) {

				DataPoint nextDataPoint = dataPoints.get(i+j);
				int[] nextDataPointValidity = getDataPointValidity(nextDataPoint);

				if (nextDataPointValidity[4] == 1) {//Next datapoint is valid

					double elevationInitial = previousDataPoint.getElevation();
					double elevationFinal = nextDataPoint.getElevation();

					double newElevation = (elevationInitial + elevationFinal)/2;

					dataPoint.setElevation(newElevation);
					dataValidated++;

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				double newElevation = previousDataPoint.getElevation();
				dataPoint.setElevation(newElevation);
				dataValidated++;
			}

		}

	}

	/**
	 * This method checks the uniqueness of an activity against other activities, by testing if
	 * their datetime ranges intersect.
	 * @param activty the subject activity on which to test the uniqueness
	 * @param activities the activities on which to test against
	 * @return boolean value of whether the activity is unique or not
	 */

/*	public boolean isActivityDuplicate(Activity activty, ArrayList<Activity> activities) {
		boolean isActivityUnique = true;

		for (Activity otherActivity : activities) {
			isActivityUnique = activities.equals(otherActivity);
		}

		return isActivityUnique;
	}
*/
}

