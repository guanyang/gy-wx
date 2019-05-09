###install到本地仓库：
`mvn clean install -Dmaven.test.skip=true -Dfile.encoding=UTF-8 -Dmaven.javadoc.skip=false -U -T 1C -Pprod`
###deploy到私服：
`mvn clean deploy -Dmaven.test.skip=true -Dfile.encoding=UTF-8 -Dmaven.javadoc.skip=false -U -T 1C -Pprod`

###mybatis-generator:generate

1. 选择cpx-pom工程，运行maven - Run As Maven build...  
Goals参数：`clean install -Dmaven.test.skip=true -Dfile.encoding=UTF-8 -Dmaven.javadoc.skip=false -U -T 1C`  

2. 修改cpx-business\src\main\resources\conf\generator\generator.properties中配置  
1）jdbc信息  
2）将`generator.tableName`设置成要生成的表名，每次生成的时候修改  
3) 针对自增主键，将`generator.tableName.keyProperty`设置驼峰之后的属性名称  

3. 修改cpx-business\src\main\resources\conf\generator\mysqlGeneratorConfig.xml中配置    
1）针对非自增主键，将`<generatedKey column="${generator.tableName.keyProperty}" sqlStatement="JDBC" />`(49行)注释掉，每次生成的时候修改  

4. 选择cpx-business工程，运行maven - Run As Maven build...  
Goals参数：`mybatis-generator:generate`

5. 刷新cpx-business工程即可