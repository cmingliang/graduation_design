package com.warmnut.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warmnut.bean.S_User;
import com.warmnut.bean.V_VisitorRecord;
import com.warmnut.cache.UserCache;

/**
 * 访客信息的包装类
 * @author qy
 *
 */
@Component
public class V_VisitorRecordWrapper {
	@Autowired
	private UserCache userCache;
	public void wrap(V_VisitorRecord bean) {
		S_User user = userCache.findById(bean.getInterviewed());
		if(user != null) {
			bean.setInterviewedName(user.getName());
		}
		
	}

}
