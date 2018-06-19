package at.qe.sepm.skeleton.model;

public class ScheduleEntry {

	private Child child;
	private Contact pickup;
	private boolean absent;
	private boolean lunch;
	private boolean birthday;
	
	public Child getChild() {
		return child;
	}

	public Contact getPickup() {
		return pickup;
	}

	public boolean isAbsent() {
		return absent;
	}

	public boolean isLunch() {
		return lunch;
	}

	public boolean isBirthday() {
		return birthday;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public void setPickup(Contact pickup) {
		this.pickup = pickup;
	}

	public void setAbsent(boolean absent) {
		this.absent = absent;
	}

	public void setLunch(boolean lunch) {
		this.lunch = lunch;
	}

	public void setBirthday(boolean birthday) {
		this.birthday = birthday;
	}

}
	

