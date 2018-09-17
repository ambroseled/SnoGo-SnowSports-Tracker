package seng202.team5.Data;

import java.util.ArrayList;
import java.util.Date;

import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;

public class DataValidator {
	
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
	
	private boolean isDateTimeValid(Date dateTime) {
		if (dateTime == null) {
			return false;
		}
		else {
			//Other date checks performed here
			return true;			
		}
	}
	private boolean isHeartRateValid(int heartRate) {
		if (heartRate < 26 || heartRate > 480) {
			return false;
		}
		return true;
	}
	private boolean isLatitudeValid(double latitude) {
		if (latitude < -90 || latitude > 90) {
			return false;
		}
		return true;
	}
	private boolean isLongitudeValid(double longitude) {
		if (longitude < -180 || longitude > 180) {
			return false;
		}
		return true;
	}
	private boolean isElevationValid(double elevation) {
		if (elevation < -213 || elevation > 8850) {
			return false;
		}
		return true;
	}
	
	
	
	public void validateActivity(Activity activity) {
		
		ArrayList<DataPoint> dataPoints = activity.getDataSet().getDataPoints();
		
		validateFirstPoint(dataPoints);
		validateDataPoints(dataPoints);			
	}
	
	private boolean validateFirstPoint(ArrayList<DataPoint> dataPoints) {
		boolean firstPointValid = false;
		
		while (!firstPointValid) {
			int[] dataPointValidity = getDataPointValidity(dataPoints.get(0));
			
			if (dataPointValidity[5] == 0 ) {
				firstPointValid = true;
			}
			else if (dataPointValidity[1] == 0) {
//				
//				boolean validPointFound = false;
//				
//				for (int i = 1; i < dataPoints.size(); i++) {
//					DataPoint nextDataPoint = dataPoints.get(i);
//					int[] nextDataPointValidity = getDataPointValidity(nextDataPoint);
//					
//					if (nextDataPointValidity[1] == 1) {
//						dataPoints.get(0).setHeartRate(nextDataPoint.getHeartRate());
//						
//						validPointFound = true;
//						break;
//					}
//				}			
				
				firstPointValid = true;
			}
			else {
				dataPoints.remove(0);
			}
		}
		
		return firstPointValid;
	}
	
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

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				dataPoints.remove(dataPoint);
				return false;
			}
		}
		return true;
	}

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

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				int newHeartRate = previousDataPoint.getHeartRate();
				dataPoint.setHeartRate(newHeartRate);
			}
			

		}
	}

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

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				double newLatitude = previousDataPoint.getLatitude();
				dataPoint.setLatitude(newLatitude);
			}

		}

	}

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

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				double newLongitude = previousDataPoint.getLongitude();
				dataPoint.setLongitude(newLongitude);
			}

		}
	}

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

					nextValidPointFound = true;
					break;
				}
			}

			if (!nextValidPointFound) {
				double newElevation = previousDataPoint.getElevation();
				dataPoint.setElevation(newElevation);
			}

		}

	}

	//Takes a new activity compares it to all of the other users activities if date ranges intersect then not unique
	public boolean isActivityDuplicate(Activity activty, ArrayList<Activity> activities) {
	
		boolean isActivityUnique = true;
		
		for (Activity otherActivity : activities) {
			isActivityUnique = activities.equals(otherActivity);
		}
				
		return isActivityUnique;
	}
	

}

