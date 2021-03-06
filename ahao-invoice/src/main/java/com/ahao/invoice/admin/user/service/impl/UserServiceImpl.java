package com.ahao.invoice.admin.user.service.impl;

import com.ahao.invoice.admin.user.dao.UserDAO;
import com.ahao.invoice.admin.user.entity.UserDO;
import com.ahao.invoice.admin.user.service.UserService;
import com.ahao.invoice.admin.util.SecurityHelper;
import com.ahao.invoice.base.service.impl.PageServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Avalon on 2017/5/11.
 * <p>
 * User的Service层接口默认实现类
 */
@Service
public class UserServiceImpl extends PageServiceImpl<UserDO> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected Mapper<UserDO> dao() {
        return userDAO;
    }

    @Override
    protected Class<UserDO> clazz() {
        return UserDO.class;
    }

    @Override
    protected Collection<UserDO> getByPage(int start, int pageSize, String sort, String order) {
        return userDAO.selectPage(start, pageSize, sort, order);
    }

    @Override
    public boolean existUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        Example example = new Example(UserDO.class);
        example.createCriteria().andEqualTo("username", username);
        int count = userDAO.selectCountByExample(example);
        return count > 0;
    }

    @Override
    public boolean updateLoginMsg(String username) {
        return userDAO.updateLastLoginMsg(username,
                new Date(), SecurityHelper.getClientIp());
    }
}
