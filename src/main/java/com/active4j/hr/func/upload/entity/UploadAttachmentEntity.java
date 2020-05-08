package com.active4j.hr.func.upload.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title FuncAttachmentEntity.java
 * @description 
		文件上传表
 * @time  2020年2月8日 下午1:14:33
 * @author guyp
 * @version 1.0
 */
@TableName("func_upload_attachment")
@Getter
@Setter
public class UploadAttachmentEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7279745573210936382L;

	/**
	 * 附件名称
	 */
	@TableField("NAME")
	private String name;
	
	/**
	 * 扩展名
	 */
	@TableField("EXTEND_NAME")
	private String extendName;
	
	/**
	 * 保存名称
	 */
	@TableField("SAVE_NAME")
	private String saveName;
	
	/**
	 * 附件内容
	 */
	@TableField("CONTENT")
	private byte[] content;
	
	/**
	 * 路径
	 */
	@TableField("PATH")
	private String path;
	
	/**
	 * 类型
	 */
	@TableField("TYPE")
	private String type;
	
	/**
	 * 上传日期
	 */
	@TableField("UPLOADER_DATE")
	private Date uploaderDate;
	
	/**
	 * 上传人名称
	 */
	@TableField("UPLOADER_NAME")
	private String uploaderName;
	
}
