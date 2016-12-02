package com.sic.ocms.dao;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sic.ocms.dao.item.ItemDAO;
import com.sic.ocms.persistence.Item;

public class ItemDAOTest {


	@Test
	public void testSave() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");


		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");
		Item itm = new Item();
//		Item itm2 = new Item();
//		Checklist cl = new Checklist();
//		cl.setChecklistId(2);
//		itm2.setItemId(4);
//		itm.setName("課題の識別");
//		itm.setPercentage(0.0);
//		itm.setChecklist(cl);
//		itm.setParent(itm2);
//		itm.setItemId(5);
//		dao.add(itm);

//		dao.delete(1);
		ctx.destroy();

	}

}
