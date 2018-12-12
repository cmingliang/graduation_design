package com.warmnut.service;

import com.warmnut.bean.S_Dict;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.vo.DictParam;

public interface DictService {
	public DataResponse getDict(String dictName);
	public DataResponse getDictList(DictParam param);
	public SimpleResponse addDict(S_Dict dict); 

}
