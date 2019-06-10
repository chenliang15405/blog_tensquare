package com.tensquare.blog.user.service;

import com.google.common.collect.Lists;
import com.tensquare.blog.user.dao.UserDao;
import com.tensquare.blog.user.entity.User;
import com.tensquare.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther alan.chen
 * @time 2019/6/3 8:10 PM
 */
@Service
@Transactional
public class UserService {

    private final String IS_FOLLOW_ME = "Y";
    private final String NOT_FOLLOW_ME = "N";

    @Autowired
    private UserDao userDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder;


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Object findById(String id) {
        return userDao.findById(id);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * 条件分页查询
     *
     * @param searchMap
     * @param page
     * @param pageSize
     * @return
     */
    public Page<User> pageSearch(Map searchMap, int page, int pageSize) {
        // 排序
        Sort sort = new Sort(Sort.Direction.DESC, "lastdate");
        //构建pageAble对象
        PageRequest pageRequest = PageRequest.of(page -1 ,pageSize, sort);
        // 动态条件构建
        Specification<User> specification = createSpecification(searchMap);
        return userDao.findAll(specification, pageRequest);
    }

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    public List<User> findSearch(Map searchMap) {
        Specification<User> specification = createSpecification(searchMap);
        return userDao.findAll(specification);
    }

    /**
     * 增加
     * @param user
     */
    public void add(User user) {
        user.setFanscount(0);
        user.setFollowcount(0);
        user.setLastdate(new Date());
        user.setOnline(0L);
        user.setRegdate(new Date());
        user.setFollowme(NOT_FOLLOW_ME);
        user.setId(idWorker.nextId() + "");
//        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }

    /**
     * 修改
     * @param user
     */
    public void updateById(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        // TODO 判断是否有admin角色，否则不能删除
        // throw new RuntimeException("权限不足")
        userDao.deleteById(id);
    }




    /**
     * 动态查询条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {
        return new Specification<User>() {
            /**
             *
             * @param root  根对象，也就是要把条件封装到哪个对象中，where 类名=label.getId
             * @param query    封装的都是查询关键字，比如group by,order by等
             * @param cb   用来封装条件对象的，如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = Lists.newArrayList();

                //id
                if(searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.equal(root.get("id").as(String.class), searchMap.get("id").toString()));
                }
                // 是否关注我的动态
                if (searchMap.get("followme")!=null && !"".equals(searchMap.get("followme"))) {
                    predicateList.add(cb.equal(root.get("followme").as(String.class), searchMap.get("followme").toString()));
                }
                // 注册日期
               /* if (searchMap.get("regdate")!=null && !"".equals(searchMap.get("regdate"))) {
                    predicateList.add(cb.equal(root.get("regdate").as(Date.class), searchMap.get("regdate")));
                }*/
                // 在线时长（分钟）
                /*if (searchMap.get("online")!=null && !"".equals(searchMap.get("online"))) {
                    predicateList.add(cb.equal(root.get("online").as(Long.class), searchMap.get("online")));
                }*/
                //最后登陆日期
                /*if (searchMap.get("lastdate")!=null && !"".equals(searchMap.get("lastdate"))) {
                    predicateList.add(cb.equal(root.get("lastdate").as(Date.class), searchMap.get("lastdate")));
                }*/
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }


}
