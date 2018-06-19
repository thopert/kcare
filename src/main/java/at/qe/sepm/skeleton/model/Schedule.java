package at.qe.sepm.skeleton.model;

import java.util.Objects;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Schedule {

    @Temporal(TemporalType.DATE)
    private Date date;
    private Set<Supervisor> supervisors;
    private Set<Child> childs;
    private Set<Child> absentchilds;
    private Long maxchilds;

    public Date getDate() {
        return date;
    }

    public Set<Supervisor> getSupervisors() {
        return supervisors;
    }

    public Set<Child> getChilds() {
        return childs;
    }

    public Set<Child> getAbsentChilds() {
        return absentchilds;
    }

    public Long getMaxChilds() {
        return maxchilds;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            this.date = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    public void setSupervisors(Set<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public void setChilds(Set<Child> childs) {
        this.childs = childs;
    }

    public void setAbsentChilds(Set<Child> absentchilds) {
        this.absentchilds = absentchilds;
    }

    public void setMaxChilds(Long maxchilds) {
        this.maxchilds = maxchilds;
    }

    public void setMaxChilds(int maxchilds) {
        setMaxChilds(new Long(maxchilds));
    }

    public void addSupervisor(Supervisor supervisor) {
        if (supervisors == null)
            supervisors = new HashSet<>();
        supervisors.add(supervisor);
    }

    public void addSupervisors(List<Supervisor> supervisors) {
        for (Supervisor s : supervisors)
            addSupervisor(s);
    }

    public void addChild(Child child) {
        if (childs == null)
            childs = new HashSet<>();
        childs.add(child);
    }

    public void addChildren(List<Child> children) {
        for (Child c : children)
            addChild(c);
    }

    public void addAbsentChild(Child absentchild) {
        if (absentchilds == null)
            absentchilds = new HashSet<>();
        absentchilds.add(absentchild);
    }

    public void addAbsentChildren(List<Child> absentchildren) {
        for (Child c : absentchildren)
            addAbsentChild(c);
    }

    public void removeSupervisor(Supervisor supervisor) {
        supervisors.remove(supervisor);
    }

    public void removeChild(Child child) {
        childs.remove(child);
    }

    public void removeAbsentChild(Child absentchild) {
        absentchilds.remove(absentchild);
    }

    @Override
    public boolean equals(Object obj) {
		if (obj == null)
            return false;

        if (!(obj instanceof Schedule))
            return false;

        final Schedule other = (Schedule) obj;
        if (!Objects.equals(date, other.date))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Schedule from " + date + ":\n- Supervisors: " + supervisors + "\n- Children: " + childs + "\n- Absent children: " + absentchilds + "\n- Max children: " + maxchilds + "\n\n";
    }

}
