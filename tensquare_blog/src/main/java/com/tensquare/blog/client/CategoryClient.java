package com.tensquare.blog.client;

import com.tensquare.blog.client.impl.CategoryClientImpl;
import com.tensquare.common.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign 客户端，调用文章分类为服务
 *  article中保存的是分类的id，在提交博客之后，会先保存分类,调用分类服务保存，然后返回id，保存到文章表中。使用mq直接传递消息，处理快！
 *  tag也可以这样。不过有点麻烦。好处是可以进行每个tag的颜色好处理
 *  图片的话，直接在blog中处理即可
 *
 *  查询博客的时候，也是根据blog中的分类id查询出对应的分类名称
 *
 * @auther alan.chen
 * @time 2019/6/11 8:44 PM
 */
//feignClient注解中用于从那个服务中调用方法，和application.name保持一致
@FeignClient(value = "tensquare-category", fallback = CategoryClientImpl.class) // 使用hystrix 实现服务熔断
public interface CategoryClient {

    //requestMapping对调用的地址保持一致，@PathVariable必须与path中的保持一致，需要指定名称
    @RequestMapping(value = "/category/{id}",method = RequestMethod.GET)
    public Response findById(@PathVariable("id") String id);


    @RequestMapping(value = "/category",method = RequestMethod.POST)
    public Response save(String json);

    @RequestMapping(value = "/category/feign/{categoryname}",method = RequestMethod.GET)
    public Response findByCategoryname(@PathVariable("categoryname") String categoryname);
}
