package com.active4j.hr.system.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;

/**
 * 系统工具类
 * 
 * @author teli_
 *
 */
public class SystemUtils {

	public static SysDeptService sysDeptService = ApplicationContextUtil.getContext().getBean(SysDeptService.class);
	public static SysUserService sysUserService = ApplicationContextUtil.getContext().getBean(SysUserService.class);
	public static SysRoleService sysRoleService = ApplicationContextUtil.getContext().getBean(SysRoleService.class);

	/**
	 * 数据字典map
	 */
	public static Map<String, List<SysDicValueEntity>> Sys_dictionary = new HashMap<String, List<SysDicValueEntity>>();

	/**
	 * 存入数据字典
	 * 
	 * @param key
	 * @param map
	 */
	public static void putDictionary(String key, List<SysDicValueEntity> lst) {
		Sys_dictionary.put(key, lst);
	}

	/**
	 * 获取字典对应的值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getDictionaryValue(String key, String value) {
		List<SysDicValueEntity> lstValue = Sys_dictionary.get(key);
		if (null != lstValue && lstValue.size() > 0) {
			for (SysDicValueEntity dic : lstValue) {
				if (StringUtils.equals(value, dic.getValue())) {
					return dic.getLabel();
				}
			}
		}
		return "";
	}

	/**
	 * 根据字典代码取得字典
	 * 
	 * @param key
	 * @return
	 */
	public static List<SysDicValueEntity> getDictionaryLst(String key) {
		return Sys_dictionary.get(key);
	}

	/**
	 * 部门Id 名称
	 */
	public static Map<String, String> mapDept = new HashMap<String, String>();

	/**
	 * 存放部门信息
	 * 
	 * @param id
	 * @param name
	 */
	public static void putDept(String id, String name) {
		mapDept.put(id, name);
	}

	/**
	 * 根据Id获取部门名称
	 * 
	 * @param id
	 * @return
	 */
	public static String getDeptNameById(String id) {
		return StringUtils.isEmpty(mapDept.get(id)) ? "" : mapDept.get(id);
	}

	/**
	 * 表单类别Id 名称
	 */
	public static Map<String, String> mapCategory = new HashMap<String, String>();

	/**
	 * 存放表单类别信息
	 * 
	 * @param id
	 * @param name
	 */
	public static void putCategory(String id, String name) {
		mapCategory.put(id, name);
	}

	/**
	 * 根据Id获取表单类别名称
	 * 
	 * @param id
	 * @return
	 */
	public static String getCategoryNameById(String id) {
		return StringUtils.isEmpty(mapCategory.get(id)) ? "" : mapCategory.get(id);
	}

	/**
	 * 
	 * @description 获取角色的树形结构
	 * @return String
	 * @author 麻木神
	 * @time 2020年4月20日 下午2:39:11
	 */
	public static String getRoleTree(Set<String> lstRoleIds) {
		// 顶级角色
		List<SysRoleEntity> lstParents = sysRoleService.getParentRoles();

		// 拼接字符串
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		roleContact(lstParents, sb, lstRoleIds);

		sb = sb.append("]");

		return sb.toString();
	}

	/**
	 * 
	 * @description
	 *  	角色树形结构的拼接
	 * @return void
	 * @author 麻木神
	 * @time 2020年4月20日 下午2:51:00
	 */
	private static void roleContact(List<SysRoleEntity> lstParents, StringBuffer sb, Set<String> lstRoleIds) {
		if(null != lstParents && lstParents.size() > 0) {
			for(int i = 0; i < lstParents.size(); i++) {
				SysRoleEntity role = lstParents.get(i);
				if(null != lstRoleIds && lstRoleIds.size() > 0 && lstRoleIds.contains(role.getId())) {
					//查询所有组织架构
					sb = sb.append("{").append("text:").append("\"").append(role.getRoleName()).append("\",").append("type:").append("\"R\"").append(",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(role.getId()).append("\"");
				}else {
					//查询所有组织架构
					sb = sb.append("{").append("text:").append("\"").append(role.getRoleName()).append("\",").append("type:").append("\"R\"").append(",").append("id:").append("\"").append(role.getId()).append("\"");
				}
				
				//查询子部门
				List<SysRoleEntity> lstChild = sysRoleService.getChildRolesByParentId(role.getId());
				
				if(null != lstChild && lstChild.size() > 0) {
					//根据type过滤数据
					List<SysRoleEntity> lstTmp = new ArrayList<SysRoleEntity>();
					for(SysRoleEntity d : lstChild) {
						lstTmp.add(d);
					}
					if(lstTmp.size() > 0) {
						sb = sb.append(", nodes: [");
						roleContact(lstTmp, sb, lstRoleIds);
						sb.append("]");
					}
					
				}
				
				if(i == lstParents.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
		
	}

	public static String getCompanyOfUser(Set<String> lstUserIds) {
		// 先查询顶级部门
		List<SysDeptEntity> lstDeparts = sysDeptService.getParentDepts();

		// 拼接字符串
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		departUserContact(lstDeparts, sb, lstUserIds);

		sb = sb.append("]");

		return sb.toString();
	}

	/**
	 * 递归调用，正式公司类型的组织机构下
	 * 
	 * @param lstDeparts
	 * @param sb
	 */
	private static void departUserContact(List<SysDeptEntity> lstDeparts, StringBuffer sb, Set<String> lstUserIds) {
		if (null != lstDeparts && lstDeparts.size() > 0) {
			for (int i = 0; i < lstDeparts.size(); i++) {
				SysDeptEntity depart = lstDeparts.get(i);
				// 查询所有组织架构
				sb = sb.append("{").append("icon:").append("\"fa fa-sitemap\"").append(",").append("type:").append("\"C\"").append(",").append("text:").append("\"").append(depart.getName()).append("\",").append("id:").append("\"").append(depart.getId()).append("\"");
				List<SysDeptEntity> lstTmp = sysDeptService.getChildDeptsByDeptId(depart.getId());
				if (null != lstTmp && lstTmp.size() > 0) {
					// 根据type过滤数据
					if (lstTmp.size() > 0) {
						sb = sb.append(", nodes: [");
						departUserContact(lstTmp, sb, lstUserIds);
						List<SysUserEntity> lstUsers = sysUserService.findUsersByDept(depart);
						if (null != lstUsers && lstUsers.size() > 0) {
							sb = sb.append(",");
							for (int m = 0; m < lstUsers.size(); m++) {
								SysUserEntity user = lstUsers.get(m);
								if (m == lstUsers.size() - 1) {
									if (null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									} else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}

								} else {
									if (null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									} else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}

								}
							}
						}
						sb.append("]");
					} else {
						List<SysUserEntity> lstUsers = sysUserService.findUsersByDept(depart);
						if (null != lstUsers && lstUsers.size() > 0) {
							sb = sb.append(", nodes:[");
							for (int m = 0; m < lstUsers.size(); m++) {
								SysUserEntity user = lstUsers.get(m);
								if (m == lstUsers.size() - 1) {
									if (null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									} else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}

								} else {
									if (null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									} else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}

								}
							}
							sb.append("]");
						}
					}
				} else {
					List<SysUserEntity> lstUsers = sysUserService.findUsersByDept(depart);
					if (null != lstUsers && lstUsers.size() > 0) {
						sb = sb.append(", nodes:[");
						for (int m = 0; m < lstUsers.size(); m++) {
							SysUserEntity user = lstUsers.get(m);
							if (m == lstUsers.size() - 1) {
								if (null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
								} else {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("}");
								}

							} else {
								if (null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
								} else {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("},");
								}

							}
						}
						sb.append("]");
					}

				}

				if (i == lstDeparts.size() - 1) {
					sb = sb.append("}");
				} else {
					sb = sb.append("},");
				}
			}
		}
	}

}
