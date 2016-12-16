package com.sic.ocms.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.sic.ocms.dto.ChecklistDO;
import com.sic.ocms.service.ChecklistService;
import com.sic.ocms.util.easyui.DataGrid;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class ChecklistAction extends ActionSupport {
	
	public String execute() {
		return "show";
	}
	
	public String dataGrid() {
		
		DataGrid<ChecklistDO> dg = checklistService.getDataGrid();
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().write(JSONObject.fromObject(dg).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
		
	}
	
	public String update() {
		checklistService.update(rows);
		return NONE;
	}
	
	
	/*
	 * 
	 * 以下宣言
	 * 
	 * */
	
	private ChecklistService checklistService;
	private ChecklistDO checklistDO;
	private String rows;
	
	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setChecklistDO(ChecklistDO checklistDO) {
		this.checklistDO = checklistDO;
	}

	@Resource
	public void setChecklistService(ChecklistService checklistService) {
		this.checklistService = checklistService;
	}
	
	

}
