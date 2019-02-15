package com.springboot.template.userthread;

import com.springboot.template.common.util.BeanUtil;
import com.springboot.template.entity.User;
import com.springboot.template.mapper.source1.UserMapperOne;
import tk.mybatis.mapper.entity.Example;

public class UserThread implements Runnable {

    private int count = 0;

    public UserThread(int count) {
        this.count = count;
    }

    private UserMapperOne userMapper = BeanUtil.getBean(UserMapperOne.class);

    @Override
    public void run() {
        System.out.println("进入线程 run 方法................................");
        User user = new User();
        user.setId(1L);
        User user1 = userMapper.queryById(user.getId());
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", user1.getId())
                .andEqualTo("restMoney", user1.getRestMoney());
        System.out.println("----------start--------------");
        if (user1.getRestMoney() > 0) {
            user1.setRestMoney(user1.getRestMoney() - 1);
            int i = userMapper.updateByExampleSelective(user1, example);
            if (i == 1) {
                System.out.println("更新成功.........................");
            } else {
                System.err.println("更新失败.........................");
            }
        }
        System.out.println("----------end--------------");
    }
}
