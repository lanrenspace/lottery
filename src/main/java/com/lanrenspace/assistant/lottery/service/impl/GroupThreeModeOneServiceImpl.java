package com.lanrenspace.assistant.lottery.service.impl;

import com.lanrenspace.assistant.lottery.dao.GroupThreeModeOneDao;
import com.lanrenspace.assistant.lottery.entity.GroupThreeModeOne;
import com.lanrenspace.assistant.lottery.service.GroupThreeModeOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: GroupThreeModeOneServiceImpl
 * @Author: helongjun
 * @Date: 2022/12/14 18:14
 * @Description: TODO
 **/
@Service
public class GroupThreeModeOneServiceImpl implements GroupThreeModeOneService {

    private GroupThreeModeOneDao groupThreeModeOneDao;

    @Autowired
    public void setGroupThreeModeOneDao(GroupThreeModeOneDao groupThreeModeOneDao) {
        this.groupThreeModeOneDao = groupThreeModeOneDao;
    }

    @Override
    public void save(GroupThreeModeOne groupThreeModeOne) {
        groupThreeModeOneDao.save(groupThreeModeOne);
    }
}
