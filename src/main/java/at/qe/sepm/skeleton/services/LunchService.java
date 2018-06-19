package at.qe.sepm.skeleton.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Lunch;
import at.qe.sepm.skeleton.repositories.LunchRepository;
import at.qe.sepm.skeleton.utils.LunchReportEntry;

/**
 * @author Lukas Schmidinger, Thomas Perteneder (mods)
 *
 */
@Component
@Scope("application")
public class LunchService {
	@Autowired
	private LunchRepository lunchRepository;

	@Autowired
	private ChildService childService;

	public Lunch loadLunch(Long lunchId) {
		return this.lunchRepository.getById(lunchId);
	}

	public void saveLunch(Lunch lunch) {
		if (this.loadLunchByChildDate(lunch.getChild().getId(), lunch.getDate()) == null)
			this.lunchRepository.save(lunch);
	}

	public void saveLunch(Long childId, Date date) {
		Lunch lunch = this.getNewLunch(this.childService.loadChild(childId), date);
		this.saveLunch(lunch);
	}

	public void deleteLunch(Long childId, Date date) {
		Lunch lunch = this.loadLunchByChildDate(childId, date);
		if (lunch != null)
			this.lunchRepository.delete(lunch);
	}

	public void deleteLunch(Lunch lunch) {
		this.lunchRepository.delete(lunch);
	}

	public List<Lunch> loadLunches() {
		return this.lunchRepository.findAll();
	}

	public Lunch getNewLunch(Child child, Date date) {
		Lunch lunch = new Lunch();
		lunch.setChild(child);
		lunch.setDate(date);
		return lunch;
	}

	public List<Lunch> loadLunchesByDate(Date date) {
		return this.lunchRepository.getByDate(date);
	}

	public List<Lunch> loadLunchesByChild(Long childId) {
		return this.lunchRepository.getByChild(childId);
	}

	public Lunch loadLunchByChildDate(Long childId, Date date) {
		return lunchRepository.getByDateChild(date, childId);
	}

	public List<Lunch> loadLunchesByIntervalChild(Date start, Date end, Long childId) {
		return this.lunchRepository.getByIntervalChild(start, end, childId);
	}

	public LunchReportEntry getCostsByIntervalChild(Date start, Date end, Double price, Long childId) {
		List<Lunch> lunches = this.loadLunchesByIntervalChild(start, end, childId);
		Double costs = 0.0d;
		if (lunches != null)
			costs = lunches.size() * price;
		return new LunchReportEntry(this.childService.loadChild(childId), costs);
	}

	public List<LunchReportEntry> getLunchReport(Date start, Date end, Double price) {
		List<LunchReportEntry> lres = new ArrayList<LunchReportEntry>();
		for (Child c : this.childService.loadChildren()) {
			List<Lunch> lunches = this.lunchRepository.getByIntervalChild(start, end, c.getId());
			if (lunches != null)
				if (!lunches.isEmpty()) {
					Double costs = lunches.size() * price;
					lres.add(new LunchReportEntry(c, costs));
				}
		}
		return lres.isEmpty() ? null : lres;
	}

	public boolean hasLunch(Long childId, Date date) {
		return this.loadLunchByChildDate(childId, date) != null;
	}

}
