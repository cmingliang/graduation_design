package com.warmnut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warmnut.bean.S_Dict;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.service.DictService;
import com.warmnut.vo.DictParam;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 数据字典相关
 * @author qy
 *
 */
@ApiIgnore
@RestController
@RequestMapping("/dict")
public class DictController {
	
	@Autowired
	private DictService dictService;
	
	/**
	 * 获取单个数据字典
	 * @param dictName
	 * @return
	 */
	@GetMapping
	public DataResponse getDict(String dictName) {
		return dictService.getDict(dictName);
	}
	
	/**
	 * 获取数据字典列表
	 * @param param
	 * @return
	 */
	@GetMapping("/list")
	public DataResponse getDictList(DictParam param) {
		return dictService.getDictList(param);
	}
	
	/**
	 * 添加数据字典
	 * @param dict
	 * @return
	 */
	@PostMapping()
	public SimpleResponse addDict(S_Dict dict) {
		return dictService.addDict(dict);
	}
	
	/**
	 * 修改数据字典
	 * @param dict
	 * @return
	 */
	@PutMapping()
	public SimpleResponse updateDict(S_Dict dict) {
		return dictService.addDict(dict);
	}
	

}
