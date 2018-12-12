package com.warmnut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warmnut.bean.S_Dict;
import com.warmnut.common.util.PageUtil;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.repository.S_DictRepository;
import com.warmnut.service.DictService;
import com.warmnut.vo.DictParam;

@Service("dictService")
public class DictServiceImpl implements DictService {
	
	@Autowired
	private S_DictRepository dictRepository;

	@Override
	public DataResponse getDict(String dictName) {
		List<S_Dict> findByDictName = dictRepository.findByDictName(dictName);
		return new DataResponse(YgngError.SUCCESS,findByDictName);
	}

	@Override
	public DataResponse getDictList(DictParam param) {
		param.setSort("dictName");
		param.setOrder("asc");
		Pageable page = PageUtil.getPage(param);
		Page<S_Dict> findAll = dictRepository.findAll(page);
		PageData<S_Dict> pageData = new PageData<S_Dict>();
		pageData.setContent(findAll.getContent());
		pageData.setTotalElements((int) findAll.getTotalElements());
		pageData.setNumberOfElements(findAll.getNumberOfElements());
		pageData.setTotalPages(findAll.getTotalPages());
		pageData.setSize(findAll.getSize());
		pageData.setFirst(findAll.isFirst());
		pageData.setLast(findAll.isLast());
		return new DataResponse<>(YgngError.SUCCESS, pageData);
	}

	@Override
	public SimpleResponse addDict(S_Dict dict) {
//		dict.setId(null);
		try {
			dictRepository.save(dict);
		}catch(DataIntegrityViolationException e) {
			return new SimpleResponse(YgngError.DUPLICATE_ENTRY);
		}
		
		return new SimpleResponse(YgngError.SUCCESS);
	}

}
