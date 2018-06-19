package at.qe.sepm.skeleton.utils;

import at.qe.sepm.skeleton.model.Child;

public class LunchReportEntry {
	private Child child;
	private Double costs;
	
	public LunchReportEntry(Child child, Double costs) {
		this.child = child;
		this.costs = costs;
	}

	public Child getChild() {
		return child;
	}

	public Double getCosts() {
		return costs;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public void setCosts(Double costs) {
		this.costs = costs;
	}
	

}
