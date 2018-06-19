package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.services.JobService;
import at.qe.sepm.skeleton.services.LunchService;
import at.qe.sepm.skeleton.utils.DateUtils;
import at.qe.sepm.skeleton.utils.LunchReportEntry;

@Component
@Scope("view")
public class LunchReportController implements Serializable {
	private static final long serialVersionUID = 2660073609438873097L;

	@Autowired
	private LunchService lunchService;
	@Autowired
	private JobService jobService;

	private Date start;
	private Date end;
	private Double price;
	private List<LunchReportEntry> lunchReport;
	private List<LunchReportEntry> filteredLunchReport;
	
	private Date deadline;
	private Integer daysBefore;

	@PostConstruct
	public void init() {
		this.start = DateUtils.getWeekStart(new Date()).getTime();
		this.end = DateUtils.getWeekEnd(new Date()).getTime();
		this.price = 5.0d;
		this.lunchReport = this.lunchService.getLunchReport(this.start, this.end, this.price);
	}

	public List<LunchReportEntry> getLunchReport() {
		return this.lunchReport;
	}
	
	public void update() {
		this.lunchReport = this.lunchService.getLunchReport(this.start, this.end, this.price);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Lunch-Report aktualisiert!"));
	}
	
	public void generateJob() {
		if (this.filteredLunchReport != null) {
			if (this.deadline != null && this.daysBefore != null && !this.filteredLunchReport.isEmpty()) {
				this.jobService.createlunchPayments(this.filteredLunchReport,
						this.jobService.getLunchPaymentJob(this.deadline, this.daysBefore), this.start, this.end);
				FacesContext.getCurrentInstance().addMessage("success",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Jobs wurden erstellt!"));
			}
		}
	}

	public void clearDialog(){
		this.deadline = null;
		this.daysBefore = null;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public Double getPrice() {
		return price;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Integer getDaysBefore() {
		return daysBefore;
	}

	public void setDaysBefore(Integer daysBefore) {
		this.daysBefore = daysBefore;
	}

	public List<LunchReportEntry> getFilteredLunchReport() {
		return filteredLunchReport;
	}

	public void setFilteredLunchReport(List<LunchReportEntry> filteredLunchReport) {
		this.filteredLunchReport = filteredLunchReport;
	}
	
}
