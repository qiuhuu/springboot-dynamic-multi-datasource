# springboot-dynamic-multi-datasource
springboot-dynamic-multi-datasource，springboot动态多数据源，可以连接不同的数据库，使用不同的数据层框架，随心所欲。只需要把数据库连接配置到相应的地方即可。

## 步骤：

1. `application-dev.yaml`中配置数据库连接

	例：

	```yaml
	spring:
	  datasource:
	    master:
	      type: com.zaxxer.hikari.HikariDataSource
	      url: jdbc:mysql://127.0.0.1:3306/uac-test?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
	      username: root
	      password: 123456
	      driver-class-name: com.mysql.cj.jdbc.Driver
	      hikari:
	        connection-test-query: SELECT 1 FROM DUAL
	        connection-timeout: 30000
	        maximum-pool-size: 20
	        max-lifetime: 1800000
	        minimum-idle: 5
	```

	

2. `DataSourceConfig.class`中配置对应的数据源

	例：

	```java
		@Bean(name = "masterDataSourceProperties")
	    @Primary
	    @ConfigurationProperties(prefix = "spring.datasource.master")
	    public DataSourceProperties masterDataSourceProperties() {
	        return new DataSourceProperties();
	    }
	
	    @Bean(name = "masterDataSource")
	    @Qualifier("masterDataSource")
	    @Primary
	    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
	    public DataSource masterDataSource() {
	        DataSourceProperties properties = masterDataSourceProperties();
	        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	        if (StringUtils.hasText(properties.getName())) {
	            dataSource.setPoolName(properties.getName());
	        }
	        return dataSource;
	    }
	```

	

3. 需要使用哪个数据源，请在Dao层方法加上 @DataSource("Node")。

	注：Node为使用的数据源
	
	例：JPA中
	
	```java
	public interface SysUserDao extends JpaRepository<SysUser,Integer> {
		@DataSource("node")
	    @Query(nativeQuery = true,value = "select username from sys_user ")
	    List<String> findUsername();
	}
	```
	
	Mybatis中
	
	```java
	public interface UserMapper {
	    @DataSource("Slave")
	    User selectByPrimaryKey(Integer id);
	}
	```
	
	



### 注意：

​	该项目使用MyBatis、Oracle数据库。请安装对应数据库或注释、删除相关配置。