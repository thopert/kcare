package at.qe.sepm.skeleton.utils;

import java.sql.Time;

public class TimeUtils {
	public static Time toTime(String time){
		if (time == null)
			return null;
		
		String nums[] = time.split(":");
		if (nums.length != 3) {
			System.err.println("Couldn't parse Time-String");
			return null;
		}

		int parsing[] = new int[3];
		for (int i = 0; i < 3; i++) {
			try {
				parsing[i] = Integer.parseInt(nums[i]);
			} catch (NumberFormatException e) {
				System.err.println("Couldn't parse Time-String");
				return null;
			}
		}
		return new Time(parsing[0], parsing[1], parsing[2]);
	}
}
