package com.active4j.hr.activiti.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

/**
 * @title ModelEditorJsonRestResource.java
 * @description 流程设计器
 * @time 2020年4月16日 下午1:34:41
 * @author 麻木神
 * @version 1.0
 */
@RestController
@RequestMapping("/activiti")
@Slf4j
public class ModelEditorJsonRestResource implements ModelDataJsonConstants {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WorkflowMngService workflowMngService;

	@RequestMapping(value = "/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ObjectNode getEditorJson(@PathVariable String modelId) {
		ObjectNode modelNode = null;
		Model model = repositoryService.getModel(modelId);
		if (model != null) {
			try {
				if (StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
				} else {
					modelNode = objectMapper.createObjectNode();
					modelNode.put(MODEL_NAME, model.getName());
				}
				modelNode.put(MODEL_ID, model.getId());
				ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
				modelNode.put("model", editorJsonNode);

			} catch (Exception e) {
				log.error("Error creating model JSON", e);
				throw new ActivitiException("Error creating model JSON", e);
			}
		}
		return modelNode;
	}

	@RequestMapping("/process/model/modelList")
	public ModelAndView modelList(HttpServletRequest request) {
		return new ModelAndView("activiti/process/model/modellist");
	}

	/**
	 * 查询数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/process/model/datagrid")
	public void datagrid(ModelEntityImpl modelEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		ModelQuery modelQuery = repositoryService.createModelQuery();
		modelQuery.orderByLastUpdateTime().desc();

		// 条件过滤
		if (StringUtils.isNotEmpty(modelEntity.getKey())) {
			modelQuery.modelKey(modelEntity.getKey());
		}
		if (StringUtils.isNotEmpty(modelEntity.getName())) {
			modelQuery.modelNameLike("%" + modelEntity.getName() + "%");
		}

		Integer pageNum = dataGrid.getPage();
		Integer pageSize = dataGrid.getRows();

		List<Model> lstRecords = modelQuery.listPage((pageNum - 1) * pageSize, pageSize);
		Page<Model> lstResult = new Page<Model>(pageNum, pageSize);
		lstResult.setRecords(lstRecords);
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}

	/**
	 * 跳转到新增编辑页面
	 * 
	 * @param workflowCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/process/model/addorupdate")
	public ModelAndView addorupdate(ModelEntityImpl modelEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/process/model/model");
		if (StringUtils.isNotEmpty(modelEntity.getId())) {

		}

		return view;
	}

	/**
	 * 跳转到新增编辑页面
	 * 
	 * @param workflowCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "model/{modelId}/save", method = RequestMethod.PUT)
	@ResponseBody
	public void save(@PathVariable String modelId, String name, String description, String svg_xml, String json_xml, HttpServletRequest request) {
		try {
			Model model = repositoryService.getModel(modelId);

			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

			modelJson.put(MODEL_NAME, name);
			modelJson.put(MODEL_DESCRIPTION, description);
			model.setMetaInfo(modelJson.toString());
			model.setName(name);
			repositoryService.saveModel(model);

			repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

			InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
			TranscoderInput input = new TranscoderInput(svgStream);

			PNGTranscoder transcoder = new PNGTranscoder();
			// Setup output
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			TranscoderOutput output = new TranscoderOutput(outStream);

			// Do the transformation
			transcoder.transcode(input, output);
			final byte[] result = outStream.toByteArray();
			repositoryService.addModelEditorSourceExtra(model.getId(), result);
			
			outStream.close();

		} catch (Exception e) {
			log.error("保存流程模型失败，错误信息:{}", e);
		}

	}

	@RequestMapping("/process/model/create")
	@ResponseBody
	public AjaxJson create(String name, String key, String description, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);

			Model newModel = repositoryService.newModel();
			newModel.setMetaInfo(modelObjectNode.toString());
			newModel.setName(name);
			newModel.setKey(StringUtils.defaultString(key));

			repositoryService.saveModel(newModel);
			repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));

		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("创建流程模型失败，错误信息:{}", e);
		}

		return j;
	}

	@RequestMapping(value = "/process/model/deploy/{modelId}")
	@ResponseBody
	public AjaxJson deploy(@PathVariable("modelId") String modelId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;
			
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			
			byte[] bytes = repositoryService.getModelEditorSourceExtra(modelData.getId());

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addBytes(modelData.getName() + ".png", bytes)
					.addString(processName, new String(bpmnBytes)).deploy();
			
			/**
			 * 如果新增部署，则查询流程管理表，如果key相同，则设置为过期
			 */
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
			
			QueryWrapper<WorkflowMngEntity> queryWrapper = new QueryWrapper<WorkflowMngEntity>();
			queryWrapper.eq("PROCESS_KEY", pd.getKey());
			List<WorkflowMngEntity> lstResult = workflowMngService.list(queryWrapper);
			if(null != lstResult && lstResult.size() > 0) {
				for(WorkflowMngEntity mng : lstResult) {
					mng.setStatus("2");
					workflowMngService.saveOrUpdate(mng);
				}
			}
			
			j.setMsg("流程模型部署成功");;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("部署流程模型失败，错误信息:{}", e);
		}
		return j;
	}
}
