package snd;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
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
 * Tests for {@link Picture}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class PictureTest {

    @Test
    public void testEquality() {
        Picture p1 = new Picture();
        Picture p2 = new Picture();
        p1.setName("abc");
        p1.setPath("test");
        p2.setName("test");
        p2.setPath("falsepath");
        Assert.assertFalse("Wrong equality on different pictures: " + p1.getPath() + " and " + p2.getPath(), p1.equals(p2));
        p2.setPath("test");
        Assert.assertTrue("Wrong equality on same pictures: " + p1.getPath() + " and " + p2.getPath(), p1.equals(p2));
    }

}
