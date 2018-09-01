package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.ConfigurationSystem;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationSystemServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	ConfigurationSystemService configurationSystemService;

	// Tests ------------------------------------------------------------------

	@Test
	public void editCS() throws ForbbidenActionException {
		super.authenticate("admin");
		final ConfigurationSystem cs = this.configurationSystemService.get();
		cs.setBanner("kskjflksdf");
		this.configurationSystemService.save(cs);
	}

	@Test(expected = ForbbidenActionException.class)
	public void editCSWrong() throws ForbbidenActionException {
		super.unauthenticate();
		final ConfigurationSystem cs = this.configurationSystemService.get();
		cs.setBanner("kskjflksdf");
		this.configurationSystemService.save(cs);
	}

}
