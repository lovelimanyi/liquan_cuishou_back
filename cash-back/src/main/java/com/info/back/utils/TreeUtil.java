package com.info.back.utils;

import java.util.List;

import com.info.web.pojo.BackTree;
import com.info.web.pojo.BackUserCompanyPermissions;
import com.info.web.pojo.MmanLoanCollectionCompany;

/**
 * 
 * 类描述：树形结构工具类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-29 下午03:00:36 <br>
 * 
 * @version
 * 
 */
public class TreeUtil {
	/**
	 * 将传入的集合转换为树形结构字符串
	 * 
	 * @param url
	 * @param pId
	 * @param id
	 * @param tg
	 * @return
	 */
	public static String getTreeModelStrings(String url, String target,
			int pId, int id, List<BackTree> tg) {
		String tree = "<ul>";
		for (BackTree tp : tg) {
			if (tp.getPid() == pId) {
				tree += "<li><a href=" + url + tp.getId()
						+ " target=\"ajax\" rel=" + target + " >"
						+ tp.getTitle() + "</a>";
				tree += getTreeModelStrings(url, target, tp.getId(), id, tg);
				tree += "</li>";
			}
		}
		return tree + "</ul>";
	}

	/**
	 * 用户角色授权的树
	 * 
	 * @param treeList
	 * @param havaeNode
	 * @param checkboxName
	 * @return
	 */
	public static String getTreeStringWidthCheckBoxOne(List<BackTree> treeList,
			List<BackTree> havaeNode, String checkboxName) {
		String tree = "<ul class='tree treeFolder treeCheck expand'>";
		for (BackTree tp : treeList) {
			if (isExit(tp.getId(), havaeNode)) {
				tree += "<li><a  tname=" + checkboxName + " tvalue="
						+ tp.getId() + " checked>" + tp.getTitle() + "</a>";
			} else {
				tree += "<li><a  tname=" + checkboxName + " tvalue="
						+ tp.getId() + " >" + tp.getTitle() + "</a>";
			}
			tree += "<ul ></ul>";
			tree += "</li>";
		}
		return tree + "</ul>";
	}

	/**
	 * 获得弹窗单选菜单树
	 * 
	 * @param pId
	 * @param id
	 * @param tg
	 * @return
	 */
	public static String getCallBackTreeString(int pId, int id,
			List<BackTree> tg) {
		String tree = "";
		if (pId == id) {
			tree = "<ul class='tree treeFolder collapse'>";
		} else {
			tree = "<ul>";
		}
		for (BackTree tp : tg) {
			if (tp.getPid() == pId) {
				tree += "<li  curId="
						+ tp.getId()
						+ "><a href=\"javascript:void(0)\" onclick=\"$.bringBack({id:'"
						+ tp.getId() + "', districtName:'" + tp.getTitle()
						+ "'})\">" + tp.getTitle() + "</a>";
				tree += getCallBackTreeString(tp.getId(), id, tg);
				tree += "</li>";
			}
		}
		return tree + "</ul>";
	}

	/**
	 * 以treeList为基数，与havaeNode的差集设置为选中状态
	 * 
	 * @param pId
	 * @param id
	 * @param treeList
	 * @param havaeNode
	 * @param checkboxName
	 * @return
	 */
	public static String getTreeStringWidthCheckBox(int pId, int id,
			List<BackTree> treeList, List<BackTree> havaeNode,
			String checkboxName) {
		String tree = "";
		if (pId == id) {
			tree = "<ul class='tree treeFolder treeCheck expand'>";
		} else {
			tree = "<ul >";
		}
		for (BackTree tp : treeList) {
			if (tp.getPid() == pId) {
				if (isExit(tp.getId(), havaeNode)) {
					tree += "<li><a  tname=" + checkboxName + " tvalue="
							+ tp.getId() + " checked>" + tp.getTitle() + "</a>";
				} else {
					tree += "<li><a  tname=" + checkboxName + " tvalue="
							+ tp.getId() + " >" + tp.getTitle() + "</a>";
				}
				tree += getTreeStringWidthCheckBox(tp.getId(), id, treeList,
						havaeNode, checkboxName);
				tree += "</li>";
			}
		}
		return tree + "</ul>";
	}

	public static boolean isExit(int id, List<BackTree> treeList) {
		boolean flag = false;
		if (treeList != null && treeList.size() > 0) {
			for (int i = 0; i < treeList.size(); i++) {
				if (id == treeList.get(i).getId()) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static String getCompanyStringWidthCheckBoxOne(List<MmanLoanCollectionCompany> companyList,
			List<BackUserCompanyPermissions> companyPermissionsList,
			String checkboxName) {
		String tree = "<ul class='tree treeCheck expand'>";
		for (MmanLoanCollectionCompany tp : companyList) {
			if (isCompany(tp.getId(), companyPermissionsList)) {
				tree += "<li><a  tname=" + checkboxName + " tvalue="
						+ tp.getId() + " checked>" + tp.getTitle() + "</a>";
			} else {
				tree += "<li><a  tname=" + checkboxName + " tvalue="
						+ tp.getId() + " >" + tp.getTitle() + "</a>";
			}
			tree += "<ul ></ul>";
			tree += "</li>";
		}
		return tree + "</ul>";
	}
	
	public static boolean isCompany(String id, List<BackUserCompanyPermissions> treeList) {
		boolean flag = false;
		if (treeList != null && treeList.size() > 0) {
			for (int i = 0; i < treeList.size(); i++) {
				if (id.equals(treeList.get(i).getCompanyId())) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
