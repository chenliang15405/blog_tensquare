package com.tensquare.blog.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 访问 http://localhost:9001/swagger-ui.html  标准版
 *     http://localhost:9001/docs.html 其他的样式的排版页面
 * swagger 配置类
 * @auther alan.chen
 * @time 2019/6/3 7:55 PM
 */
@Configuration
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.tensquare.blog.user"))
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("The User Of Tensquare_Blog")
                //描述
                .description("接口文档")
                //创建人
                //.contact(new Contact("LinXiuNan", "", ""))
                //版本号
                .version("1.0")
                .build();
    }


    /**
     * 如果直接是通过@requestBody 接收的实体类，则不用写@ApiImplicitParam
     *
     *  @Api value    字符串	可用在class头上,class描述
     *        description	字符串
     * @Api(value = "xxx", description = "xxx")
     * @ApiOperation value    字符串	可用在方法头上.参数的描述容器
     *              notes	字符串	说明
     *              httpMethod	字符串	请求方法
     * @ApiOperation(value = "xxx", notes = "xxx", method = "GET")
     * @ApiImplicitParams    {}	@ApiImplicitParam数组	可用在方法头上.参数的描述容器
     * @ApiImplicitParams({@ApiImplicitParam1,@ApiImplicitParam2,...})
     * @ApiImplicitParam name    字符串 与参数命名对应	可用在@ApiImplicitParams里
     *                   value	字符串	参数中文描述
     *                   required	布尔值	true/false
     *                   dataType	字符串	参数类型
     *                   paramType	字符串	参数请求方式:query/path 如果通过json接收的实体类，则不用写
     *                                      query:对应@RequestParam传递
     *                                      path: 对应@PathVariable{}path传递
     *                   dataType	字符串	参数类型,  如果是接收的是@requestParam中的参数，则需要使用DataType 和DataTypeClass来标示
     *                   dataTypeClass	类	参数对应的类
     *                   defaultValue	字符串	在api测试中默认值
     * @ApiImplicitParam(name = "newProduct", value = "商品信息对象", required = true, dataType = "Product", dataTypeClass = Product.class)
     * @ApiResponses    {}	@ApiResponse数组	可用在方法头上.参数的描述容器
     * @ApiResponses({@ApiResponse1,@ApiResponse2,...})
     * @ApiResponse code    整形	可用在@ApiResponses里
     * message	字符串	错误描述
     * response	类	返回结果对应的类
     * @ApiResponse(code = 200, message = "Successful", response = CommonResponse.class)
     * @ApiModelProperty name    字符串	实体类参数名称
     * value	字符串	实体类参数值
     * notes	字符串	说明
     * @ApiModelProperty(name = "name", value = "name", notes = "名称")
     *
     *
     *
     *
     *
     */



}
