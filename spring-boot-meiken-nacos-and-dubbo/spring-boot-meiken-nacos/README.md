
### 数据库配置放在Nacos中

#### 依赖

```$xslt
<!--nacos-->
<dependency>
   <groupId>com.alibaba.boot</groupId>
   <artifactId>nacos-config-spring-boot-starter</artifactId>
   <version>0.2.1</version>
</dependency>

<!--数据库-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

```
#### spring boot 项目配置Nacos地址

```text
项目 application.properities 文件配置

nacos.config.server-addr=IP:8848
```



#### Nacos服务端创建配置

dataId 为 spring-boot-meiken-nacos

```text
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/developer_use?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true&pinGlobalTxToPhysicalConnection=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.filters=config
#
spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

nacosTestField=glftest

```


#### 启动加载配置
```java
@SpringBootApplication 
@NacosPropertySource(dataId = "spring-boot-meiken-nacos", autoRefreshed = true) 
public class NacosConfigApplication { 
    public static void main(String[] args) { 
        SpringApplication.run(NacosConfigApplication.class, args);
    }
 }
```


#### 测试接口

```java
@RequestMapping("/nacos")
@RestController
public class TestController {

    @NacosValue(value = "${nacosTestField:isNull}",autoRefreshed = true)
    private String nacosTestField;


    @Autowired
    private DataSource dataSource;

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return nacosTestField;
    }


    @GetMapping("/datasourceConfig")
    @ResponseBody
    public String dataSourceConfig(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int rowCount = jdbcTemplate.queryForObject("select count(*) from Teacher", Integer.class);

        return rowCount+"";
    }
}

```


