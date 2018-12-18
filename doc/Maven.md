1. 启动前 mvn clean install

2. Maven多模块，子模块依赖父模块，且子模块作为Web容器入口(SpringBootApplication)，
如果不指定ComponentScan的路径，则需要把启动类放在公有包路径下，如依赖com.elliot.security.app
则放在他的父节点包下如con.elliot.security。