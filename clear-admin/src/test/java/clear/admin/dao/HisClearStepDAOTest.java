//package clear.admin.dao;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sge.clear.admin.dao.ClearStepDOMapper;
//import com.sge.clear.admin.dao.HisClearStepDAOMapper;
//import com.sge.clear.admin.model.ClearStepDO;
//import com.sge.clear.admin.model.HisClearStep;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/spring.xml",
//		"classpath:spring/spring-mybatis.xml" })
//public class HisClearStepDAOTest {
//
//	// private static final Logger logger =
//	// Logger.getLogger(HisClearStepDAOTest.class);
//
//	@Resource(name = "clearStepDOMapper")
//	private ClearStepDOMapper clearDao;
//
//	@Resource(name = "hisClearStepDAOMapper")
//	private HisClearStepDAOMapper hisClearStepDao;
//
//	@Test
//	public void test_insert() {
//		Assert.assertNotNull(hisClearStepDao);
//		Assert.assertNotNull(clearDao);
//		
//		ClearStepDO clearStepDO = clearDao.getAll().get(0);
//		HisClearStep hisStep = createHisStepByStep(clearStepDO);
//		
//		hisStep.setCln_date("20140616");
//		hisStep.setId(3);
//		hisClearStepDao.insert(hisStep);
//
//		HisClearStep hisClearStep = hisClearStepDao.selectByPrimaryKey(2);
//		Assert.assertNotNull(hisClearStep);
//		Assert.assertEquals("20140616", hisClearStep.getCln_date());
//		hisClearStepDao.deleteByPrimaryKey(3);
//	}
//
//	@Test
//	public void test_selectByStepName() {
//		ClearStepDO clearStepDO = clearDao.getAll().get(0);
//		HisClearStep hisStep = createHisStepByStep(clearStepDO);
//		//hisStep.setCln_date(new Date());
//		hisStep.setName("testSelect");
//		hisStep.setId(3);
//		hisStep.setCln_status("01");
//		hisClearStepDao.insert(hisStep);
//		
//		List<HisClearStep> hisSteps = hisClearStepDao.selectByStepName("testSelect");
//		Assert.assertEquals(1, hisSteps.size());
//		Assert.assertEquals("testSelect", hisSteps.get(0).getName());
//		hisClearStepDao.deleteByPrimaryKey(hisSteps.get(0).getId());
//	}
//
//	private HisClearStep createHisStepByStep(ClearStepDO step) {
//		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		HisClearStep hisStep = new HisClearStep();
//		
//		hisStep.setCreated_at(step.getGmtCreated());
//		
//		hisStep.setIp(step.getIp());
//		
//		hisStep.setName(step.getName());
//
//		hisStep.setUser_modified(step.getUserModified());
//		
//		hisStep.setStep(step.getStep());
//		hisStep.setUpdated_at(new Date());
//		hisStep.setUrl(step.getUrl());
//		return hisStep;
//	}
//}
