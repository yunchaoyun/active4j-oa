package com.active4j.hr.common.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Getter;
import lombok.Setter;


/**
 * 公共实体类， 需要自动赋值
 * @author teli_
 *
 */
@Getter
@Setter
public class BaseEntity extends Model<BaseEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3890934764385076499L;

	@TableId(value="ID", type=IdType.UUID)
	private String id;
	
	@Version
	@TableField(fill=FieldFill.INSERT)
	private Integer versions;
	
	@TableField(value="CREATE_NAME", fill=FieldFill.INSERT)
	private String createName;
	
	@TableField(value="CREATE_DATE", fill=FieldFill.INSERT)
	private Date createDate;
	
	@TableField(value="UPDATE_NAME", fill=FieldFill.UPDATE)
	private String updateName;
	
	@TableField(value="UPDATE_DATE", fill=FieldFill.UPDATE)
	private Date updateDate;

}
