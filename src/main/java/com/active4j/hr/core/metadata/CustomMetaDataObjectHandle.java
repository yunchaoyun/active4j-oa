package com.active4j.hr.core.metadata;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 实体基础字段的赋值
 * 
 * @author teli_
 *
 */
@Component
public class CustomMetaDataObjectHandle implements MetaObjectHandler {

	/**
	 * 插入赋值
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		/**
		 * 插入时，对乐观锁版本号进行赋值
		 */
		Object versions = getFieldValByName(getVersionsFieldName(), metaObject);
		if (versions == null) {
			setFieldValByName(getVersionsFieldName(), 0, metaObject);
		}

		/**
		 * 插入时，赋值创建时间
		 */
		Object createDate = getFieldValByName(getCreateTimeFieldName(), metaObject);
		if (null == createDate) {
			setFieldValByName(getCreateTimeFieldName(), DateUtils.getNow(), metaObject);
		}

		/**
		 * 插入时，赋值创建人
		 */
		Object createName = getFieldValByName(getCreateNameFieldName(), metaObject);
		if (null == createName) {
			if(ShiroUtils.hasLogin()) {
				setFieldValByName(getCreateNameFieldName(), ShiroUtils.getSessionUserName(), metaObject);
			}else {
				setFieldValByName(getCreateNameFieldName(), "system", metaObject);
			}
			
		}

	}

	/**
	 * 更新赋值
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		Object updateDate = getFieldValByName(getUpdateTimeFieldName(), metaObject);
		if (null == updateDate) {
			setFieldValByName(getUpdateTimeFieldName(), DateUtils.getNow(), metaObject);
		}

		
		Object updateName = getFieldValByName(getUpdateNameFieldName(), metaObject);
		if (null == updateName) {
			if(ShiroUtils.hasLogin()) {
				setFieldValByName(getUpdateNameFieldName(), ShiroUtils.getSessionUserName(), metaObject);
			}else {
				setFieldValByName(getUpdateNameFieldName(), "system", metaObject);
			}
			
		}
	}

	/**
	 * 获取版本号字段
	 * 
	 * @return
	 */
	protected String getVersionsFieldName() {
		return "versions";
	}

	protected String getCreateTimeFieldName() {
		return "createDate";
	}

	protected String getCreateNameFieldName() {
		return "createName";
	}
	
	protected String getUpdateTimeFieldName() {
		return "updateDate";
	}

	protected String getUpdateNameFieldName() {
		return "updateName";
	}
}
