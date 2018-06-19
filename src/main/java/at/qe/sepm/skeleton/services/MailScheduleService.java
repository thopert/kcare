package at.qe.sepm.skeleton.services;

import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.repositories.*;

import java.sql.Timestamp;
import java.util.*;

// Scheduled Mail-Handling
@Component
public class MailScheduleService {

    @Autowired
    ChildRepository childRepository;

    @Autowired
    ContactPickupRepository contactPickupRepository;

    @Autowired
    SupervisorRepository supervisorRepository;

    @Autowired
    LunchRepository lunchRepository;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    MailService mailService;

    @Autowired
    JobRepository jobRepository;

    // send a weekly mail to all supervisors about the upcoming schedule for next week
    @Scheduled(cron = "1 0 0 * * 0") // run each sunday at 00:00
    public void sendSupervisorsWeeklyReport() {
        Date d = new Date();
// for testing:
// d = new Date(2000 - 1900, 0, 1);
        Date startDate = new Date();
        Date endDate = new Date();
        List<Supervisor> supervisors = supervisorRepository.findAll();
        List<Schedule> schedules = new ArrayList<>();
        List<String> days = new ArrayList<>();
        days.add("Montag");
        days.add("Dienstag");
        days.add("Mittwoch");
        days.add("Donnerstag");
        days.add("Freitag");
        List<String> months = new ArrayList<>();
        months.add("Jänner");
        months.add("Februar");
        months.add("März");
        months.add("April");
        months.add("Mai");
        months.add("Juni");
        months.add("Juli");
        months.add("August");
        months.add("September");
        months.add("Oktober");
        months.add("November");
        months.add("Dezember");

        // get schedules for next week
        List<Date> dates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            d = new Date(d.getYear(), d.getMonth(), d.getDate() + 1);
            dates.add(d);
            schedules.add(scheduleService.loadSchedule(d));
            if (i == 0)
                startDate = d;
            else if (i == 4)
                endDate = d;
        }

        // send each supervisor a mail for next week
        for (Supervisor supervisor : supervisors) {
            String sname = supervisor.getPerson().getFirstName();
            String smail = supervisor.getPerson().getMail();
            StringBuilder text = new StringBuilder("Hallo " + sname + ",\n");
            text.append("für die nächste Woche ist der folgende Ablaufplan festgelegt:\n\n");

            text.append("Angemeldete Betreuer:\n");
            for (int i = 0; i < 5; i++) {
                text.append(days.get(i) + ":\n");
                Schedule sch = schedules.get(i);
                for (Supervisor sup : sch.getSupervisors()) {
                    text.append("- " + sup.getPerson().getFirstName() + " " + sup.getPerson().getLastName() + "\n");
                }
                if (sch.getSupervisors().size() == 0)
                    text.append("keine\n");
                text.append("\n");
            }
            text.append("\n");

            text.append("Angemeldete Kinder:\n");
            for (int i = 0; i < 5; i++) {
                text.append(days.get(i) + ":\n");
                Schedule sch = schedules.get(i);
                for (Child ch : sch.getChilds()) {
                    String pickupString = ch.getParents().getMother().getFirstName() + " " + ch.getParents().getMother().getLastName() + " oder " + ch.getParents().getFather().getFirstName() + " " + ch.getParents().getFather().getLastName();
                    ContactPickup cp = contactPickupRepository.getByDateChild(dates.get(i), ch.getId());
                    if (cp != null)
                        pickupString = cp.getContact().getPerson().getFirstName() + " " + cp.getContact().getPerson().getLastName();
                    text.append("- " + ch.getPerson().getFirstName() + " " + ch.getPerson().getLastName() + " (Bringzeit: " + ch.getPutTimeBegin() + " - " + ch.getPutTimeEnd() + ", Abholzeit: " + ch.getPickTimeBegin() + " - " + ch.getPickTimeEnd() + ", von: " + pickupString + ")\n");
                }
                if (sch.getChilds().size() == 0)
                    text.append("keine\n");
                text.append("\n");
            }
            text.append("\n");

            text.append("Maximale Belegungszahl:\n");
            for (int i = 0; i < 5; i++) {
                text.append(days.get(i) + ":\n");
                Long mc = schedules.get(i).getMaxChilds();
                text.append((mc == null ? 0 : mc) + "\n\n");
            }
            text.append("\n");

            text.append("Anmeldungen zum Mittagessen:\n");
            for (int i = 0; i < 5; i++) {
                text.append(days.get(i) + ":\n");
                List<Lunch> lunchs = lunchRepository.getByDate(dates.get(i));
                for (Lunch lu : lunchs) {
                    text.append("- " + lu.getChild().getPerson().getFirstName() + " " + lu.getChild().getPerson().getLastName() + "\n");
                }
                if (lunchs.size() == 0)
                    text.append("keine\n");
                text.append("\n");
            }
            text.append("\n");

            text.append("Bevorstehende Kindergeburtstage:\n");
            List<Child> birthdaychildren = childRepository.getBirthdayChildrenWithinDates(startDate, endDate);
            for (Child c : birthdaychildren) {
                text.append("- " + c.getPerson().getFirstName() + " " + c.getPerson().getLastName() + " (" + c.getPerson().getBirthDate().getDate() + ". " + months.get(c.getPerson().getBirthDate().getMonth()) + "\n");
            }
            if (birthdaychildren.size() == 0)
                text.append("keine\n");
            text.append("\n");

            text.append("Überfällige Kinder:\nFolgende Kinder haben das Abmeldedatum überschritten:\n");
            List<Child> children = childRepository.getChildrenWithLowerCancellationDate(startDate);
            for (Child c : children) {
                text.append("- " + c.getPerson().getFirstName() + " " + c.getPerson().getLastName() + " (" + c.getCancellationDate().getDate() + ". " + months.get(c.getCancellationDate().getMonth()) + " " + (c.getCancellationDate().getYear() + 1900) + ")\n");
            }
            if (children.size() == 0)
                text.append("keine\n");
            text.append("\n");

            text.append("Einen erfolgreichen Start in die Woche wünscht,\nDas kcare-Service-Team\n");

            // send mail to supervisor
            mailService.sendMail("kCrare: Tagesplan für nächste Woche!", text.toString(), smail);

        }
    }

    // sends an info-mail of all jobs to their specified parents which have their due-date - daysbefore set to the current date
    // second, minute, hour, day, month, weekday
    @Scheduled(cron = "1 0 0 * * *")    // run each day at 00:00
    public void sendParentsWeeklyReport() {
        List<String> months = new ArrayList<>();
        months.add("Jänner");
        months.add("Februar");
        months.add("März");
        months.add("April");
        months.add("Mai");
        months.add("Juni");
        months.add("Juli");
        months.add("August");
        months.add("September");
        months.add("Oktober");
        months.add("November");
        months.add("Dezember");
        Date d = new Date();
        d = new Date(d.getYear(), d.getMonth(), d.getDate());
        List<Job> jobs = jobRepository.getJobsToSendInfoMail(d);
        for (Job j : jobs) {
            Parent p = j.getParents();
            String pmmail = p.getMother().getMail();
            String pfmail = p.getFather().getMail();
            StringBuilder text = new StringBuilder("Hallo " + p.getMother().getFirstName() + " und " + p.getFather().getFirstName() + ",\n");
            text.append("wir möchten euch an folgenden Job erinnern:\n");
            text.append(j.getTitle() + ", zu erledigen am " + j.getEndDate().getDate() + ". " + months.get(j.getEndDate().getMonth() + 1) + " " + (j.getEndDate().getYear() + 1900) + "\n");
            text.append("\nEs bedankt sich höflichst,\n");
            text.append("Das kCare - Service - Team\n");
            mailService.sendMail("kCare - Job-Erinnerung: " + j.getTitle(), text.toString(), pmmail);
            mailService.sendMail("kCare - Job-Erinnerung: " + j.getTitle(), text.toString(), pfmail);
        }
    }

}