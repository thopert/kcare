package at.qe.sepm.skeleton.model;

public class MonthScheduleEntry {

	private Child child;
	private int daysCount;

	public MonthScheduleEntry(Child child, int daysCount) {
		this.child = child;
		this.daysCount = daysCount;
	}

	public Child getChild() {
		return child;
	}
	public void setChild(Child child) {
		this.child = child;
	}

	public int getDaysCount() {
		return daysCount;
	}

	public void setDaysCount(int daysCount) {
		this.daysCount = daysCount;
	}

}
