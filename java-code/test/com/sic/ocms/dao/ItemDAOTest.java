package com.sic.ocms.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sic.ocms.dao.checkitem.CheckitemDAO;
import com.sic.ocms.dao.checkitem.status.CheckitemStatusDAO;
import com.sic.ocms.dao.item.ItemDAO;
import com.sic.ocms.dto.ChecklistDO;
import com.sic.ocms.persistence.Checkitem;
import com.sic.ocms.persistence.CheckitemStatus;
import com.sic.ocms.persistence.Item;

public class ItemDAOTest {

	private static ClassPathXmlApplicationContext ctx;

	@BeforeClass
	public static void beforeClass() {

		 ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@AfterClass
	public static void afterClass() {

		ctx.destroy();
	}
	
	@Test
	public void testSave() throws Exception {

		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");
		Item itm = new Item();
		Item itm1 = new Item();
		Item itm2 = new Item();
		Item itm11 = new Item();
		Item itm12 = new Item();
		Item itm21 = new Item();
		Item itm22 = new Item();
		

		itm.setName("顧客");
		itm1.setName("機会");
		itm11.setName("課題の識別");
		itm12.setName("解決策の方向");
		itm2.setName("ステークホルダー");
		itm21.setName("関係者の特定");
		itm22.setName("代表者の選定");
		
		itm.getChildren().add(itm1);
		itm1.getChildren().add(itm11);
		itm1.getChildren().add(itm12);
		itm.getChildren().add(itm2);
		itm2.getChildren().add(itm21);
		itm2.getChildren().add(itm22);
		
		itm1.setParent(itm);
		itm11.setParent(itm1);
		itm12.setParent(itm1);
		itm2.setParent(itm);
		itm21.setParent(itm2);
		itm22.setParent(itm2);
		
		dao.add(itm);
	}

	public void testDelete() {

		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");
		dao.delete(7);

	}

	public void testLoad() {

		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");
		Item itm = dao.load(15);
		System.out.println(itm);
	}


	public void testPrint1() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");


		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");

		List<Item> itemlist = new ArrayList<Item>();
		itemlist=dao.list("from Item");


		Item root = new Item();

		for(Item item : itemlist){
			if(item.getItemId()==item.getParent().getItemId()){
				for(Item child:item.getChildren()){
					if(item.getItemId()!=child.getItemId())
					System.out.println(item.getItemId()+"	"+item.getName()+"	"+child.getName());
				}
			}
		}

		ctx.destroy();
	}

	public void testPrint2() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");


		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");

		List<Item> itemlist = new ArrayList<Item>();
		itemlist=dao.list("from Item");


		Item root = new Item();

		for(Item item : itemlist){
			for(Item child:item.getChildren()){
				if(item.getItemId()!=child.getItemId())
				System.out.println(item.getItemId()+"	"+item.getName()+"		"+item.getPercentage()+"	"+item.getParent().getItemId()+"	"+child.getName());
			}
		}

		ctx.destroy();
	}

	public void testPrint3() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");


		ItemDAO dao = (ItemDAO) ctx.getBean("itemDAO");
		CheckitemDAO ciDAO = (CheckitemDAO) ctx.getBean("checkitemDAO");

		List<Item> itemlist = new ArrayList<Item>();
		itemlist=dao.list("from Item");

		for(Item item : itemlist){
			for(Item child:item.getChildren()){
				if(item.getItemId()!=child.getItemId())
				System.out.println(item.getItemId()+"	"+item.getName()+"		"+item.getPercentage()+"	"+item.getParent().getItemId()+"	"+child.getName());
			}
		}

		ctx.destroy();
	}

/*	public void testView() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		ItemDAO idao = (ItemDAO) ctx.getBean("itemDAO");
		List<Item> itemlist = idao.list("from Item");

		CheckitemDAO cidao = (CheckitemDAO) ctx.getBean("checkitemDAO");
		List<Checkitem> citemlist = cidao.list("from Checkitem");

		CheckitemStatusDAO csdao =(CheckitemStatusDAO) ctx.getBean("checkitemstatusDAO");
		List<CheckitemStatus> csitemlist = csdao.list("from CheckitemStatus");

		List<ChecklistDO> table = new ArrayList<ChecklistDO>();

		//最小単位であるcheckitemlistから挿入していく
		for(Checkitem citem:citemlist){
			ChecklistDO row = new ChecklistDO();
			row.setCheckitem_content(citem.getContent());
			row.setDescription(citem.getDescrition());
			row.setTypical_deliverables(citem.getTypicalDeliverables());
			row.setCheckitem_content_id(citem.getCheckitemId());
			//次にCheckitemsStatus
			for(CheckitemStatus cs:csitemlist){
				if(citem.getCheckitemId()==cs.getCheckitem().getCheckitemId()){
					row.setStatus(cs.getStatus());
					row.setProblem(cs.getProblem());
					row.setComment(cs.getComment());
					row.setPrjtype(cs.getPrjtype());
					row.setImportance(cs.getImportance());
					Item itm = cs.getItem();
					//最後にItem
					for(Item g3:itemlist){
						if(g3.getItemId()==itm.getItemId()){
							row.setGroup3_name(g3.getName());
							for(Item g2:itemlist){
								if(g2.getItemId()==g3.getParent().getItemId()){
									row.setGroup2_name(g2.getName());
									for(Item g1:itemlist){
										if(g1.getItemId()==g2.getParent().getItemId()){
											row.setGroup1_id(g1.getItemId());
											row.setGroup1_name(g1.getName());
										}
									}
								}
							}
						}
					}
				}
			}

			table.add(row);
		}



		System.out.printf("%-30s\t%-30s\t%-30s\t%-30s\t%-5s\t%-5s\t%-5s\t%-5s\t%-5s\t%-5s\t%-5s\n","グループ1","グループ2","グループ3","チェック項目","補足説明","成果物","ステータス","不遵守事項","コメント","Prjタイプ","重要度");
		for(ChecklistDO dto:table){
			System.out.printf("%-30s\t%-30s\t%-30s\t%-25s\t%-8s\t%-8s\t%-8d\t%-8d\t%-8d\t%-8d\t%-8d\n",dto.getGroup1_name(),dto.getGroup2_name(),dto.getGroup3_name(),dto.getCheckitem_content(),dto.getDescription(),dto.getTypical_deliverables(),dto.getStatus(),dto.getProblem(),dto.getComment(),dto.getPrjtype(),dto.getImportance());
		}



		ctx.destroy();
	}*/

}
