package com.active4j.hr.func.upload.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.func.upload.dao.UploadAttachmentDao;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title UploadAttachmentServiceImpl.java
 * @description 
		文件上传下载管理类
 * @time  2020年2月8日 下午1:22:22
 * @author guyp
 * @version 1.0
 */
@Service("uploadAttachmentService")
@Transactional
public class UploadAttachmentServiceImpl extends ServiceImpl<UploadAttachmentDao, UploadAttachmentEntity> implements UploadAttachmentService {
	
}
