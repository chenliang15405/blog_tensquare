package com.tensquare.blog.user.service;

import com.tensquare.blog.user.dao.AdminUserDao;
import com.tensquare.blog.user.entity.AdminUser;
import com.tensquare.blog.user.entity.JwtUser;
import com.tensquare.blog.user.vo.BloggerVo;
import com.tensquare.common.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  amdin 管理服务， 如果使用的是spring security，则需要实现UserDetailsService，然后实现loadUserByUsername类，进行验证即可
 *
 * @auther alan.chen
 * @time 2019/6/7 3:25 PM
 */
@Service
@Transactional
public class AdminUserService implements UserDetailsService {

    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder;


    /**
     * spring security的方法，验证用户登录
     * @param loginname 登录名称
     * @return  需要返回的是UserDetails接口
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginname) throws UsernameNotFoundException {
        AdminUser admin = adminUserDao.findByLoginname(loginname);
        if(admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new JwtUser(admin);
    }


    /**
     * 登录
     * @param adminUser
     * @return
     */
    public AdminUser login(AdminUser adminUser) {
        // 先根据用户名查询用户
        AdminUser admin = adminUserDao.findByLoginname(adminUser.getLoginname());
        if(admin != null) {
            //使用数据库的密码和现在的密码进行对比
            // security 会根据解析算法match，并且每次加密都不相同，可能security 已经对login的输入密码已经加密了，需要使用match进行对比
//            String loginPwd = encoder.encode(adminUser.getPassword());
            if(encoder.matches(adminUser.getPassword(), admin.getPassword())){
                // 登录成功
                return admin;
            }
        }

        return null;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<AdminUser> findAll() {
        return adminUserDao.findAll();
    }

    /**
     * 查询admin信息
     *
     * @return
     */
    public BloggerVo findBloggerInfo() {
        AdminUser adminUser = adminUserDao.findBloggerInfo();
        BloggerVo bloggerVo = new BloggerVo();
        BeanUtils.copyProperties(adminUser, bloggerVo);
        return bloggerVo;
    }

    /**
     * 根据username查询信息
     * @param loginName
     * @return
     */
    public BloggerVo findByLoginname(String loginName) {
        AdminUser adminuser = adminUserDao.findByLoginname(loginName);
        BloggerVo bloggerVo = new BloggerVo();
        BeanUtils.copyProperties(adminuser, bloggerVo);
        return bloggerVo;
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<AdminUser> pageSearch(Map whereMap, int page, int size) {
        Specification<AdminUser> specification = createSpecification(whereMap);
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);
        return adminUserDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<AdminUser> findSearch(Map whereMap) {
        Specification<AdminUser> specification = createSpecification(whereMap);
        return adminUserDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public AdminUser findById(String id) {
        return adminUserDao.findById(id).get();
    }

    /**
     * 增加
     * @param admin
     */
    public void add(AdminUser admin) {
        admin.setId(idWorker.nextId()+ "");
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setState("0");
        admin.setCreateDate(new Date());
        adminUserDao.save(admin);
    }

    /**
     * 修改
     * @param admin
     */
    public void update(AdminUser admin) {
        adminUserDao.save(admin);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        adminUserDao.deleteById(id);
    }




    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<AdminUser> createSpecification(Map searchMap) {

        return new Specification<AdminUser>() {

            @Override
            public Predicate toPredicate(Root<AdminUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.equal(root.get("id").as(String.class), (String) searchMap.get("id")));
                }
                // 昵称
                if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                    predicateList.add(cb.like(root.get("username").as(String.class), "%" + (String) searchMap.get("username") + "%"));
                }
                // 登陆名称
                if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                    predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
                }
                // 状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.equal(root.get("state").as(String.class), (String) searchMap.get("state")));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }


}
