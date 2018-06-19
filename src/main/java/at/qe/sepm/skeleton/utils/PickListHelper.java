package at.qe.sepm.skeleton.utils;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DualListModel;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Supervisor;

public class PickListHelper {
	public static DualListModel<String> getSupervisorPickList(List<Supervisor> source, List<Supervisor> target) {
		ArrayList<String> sourceStr = new ArrayList<>();
		ArrayList<String> targetStr = new ArrayList<>();
		
		for (Supervisor s: source)
			sourceStr.add(s.getPerson().getFirstName() + " " + s.getPerson().getLastName());
		
		for (Supervisor s: target)
			targetStr.add(s.getPerson().getFirstName() + " " + s.getPerson().getLastName());
		
		return new DualListModel<String>(sourceStr, targetStr);
	}
	
	public static DualListModel<String> getChildPickList(List<Child> source, List<Child> target) {
		ArrayList<String> sourceStr = new ArrayList<>();
		ArrayList<String> targetStr = new ArrayList<>();
		
		for (Child c: source)
			sourceStr.add(c.getPerson().getFirstName() + " " + c.getPerson().getLastName());
		
		for (Child c: target)
			targetStr.add(c.getPerson().getFirstName() + " " + c.getPerson().getLastName());
		
		return new DualListModel<String>(sourceStr, targetStr);
	}
}
