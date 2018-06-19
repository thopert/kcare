package snd;

import java.util.Date;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.repositories.*;
import at.qe.sepm.skeleton.ui.beans.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.internal.util.collections.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Tests for Mail-Sending.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class MailTest {

	@Autowired
	MailService mailService;

	@Autowired
	MailScheduleService mailScheduleservice;

	@Test
	public void testSendingAMail() {
		mailService.sendMail("Tolle Mail!", "Hallo Welt,\n\ndas ist eine tolle Mail an hallo@welt.com!\n\nMfg\ndeine Mutter Sonne", "hallo@welt.com");
	}

	@Test
	public void testSendScheduleMailToSupervisors() {
		mailScheduleservice.sendSupervisorsWeeklyReport();
	}

	@Test
	public void testSendScheduledJobMailsToParents() {
		mailScheduleservice.sendParentsWeeklyReport();
	}

}