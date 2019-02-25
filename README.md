## 项目介绍
 + 该项目为学习springSecurity,仿写springSecurity的模拟项目
## 项目整体分析
 + webSecurity的build过程<br>
 ![webSecurity](https://github.com/yehuali/springSecurityDemo/blob/master/image/webSecurity.jpg)
 + webSecurity的继承关系<br>
 ![webSecurity](https://github.com/yehuali/springSecurityDemo/blob/master/image/webSecurity类图.png)
   + WebSecurity类分析
     + 继承 AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>>
       + 实现AbstractSecurityBuilder的抽象方法doBuild()
          + doBuild()的模板方法：1. init（） 2.configure() 3. performBuild()
             + init() 和 configure()方法都是在遍历配置类并实现其方法(**参考下面的WebSecurityConfigurer接口分析**)
       + 继承 AbstractSecurityBuilder<0>
          + 实现SecurityBuilder<O>的build方法
          + 暴露抽象方法 doBuild(),由子类实现
     + 实现 SecurityBuilder<O>
     + WebSecurity最终构建的是FilterChainProxy过滤器
     
   + WebSecurityConfigurer接口分析
     + 泛型: WebSecurity: T extends SecurityBuilder<Filter> 
       + 通过SecurityBuilder构建的Filter的子类
     + 继承 SecurityConfigurer<O, B extends SecurityBuilder<O>> 
       + O：需构建的对象; B:构建器 
       + 配置SecurityBuilder：1.init(B builder)初始化 2.进行配置configure(B builder) 
         + init()方法 ： 1. 通过getHttp()构建HttpSecurity （参考下面HttpSecurity分析） <br> 
                         2.将HttpSecurity添加到WebSecurity的securityFilterChainBuilders属性中
   
   + HttpSecurity分析(和webSecurity的继承关系类似)
     + 作用： 构建DefaultSecurityFilterChain过滤器链
     + 配置类：1.泛型都继承HttpSecurityBuilder<H> 2. 继承AbstractHttpConfigurer<T extends AbstractHttpConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
       + HttpSecurityBuilder 继承 SecurityBuilder<DefaultSecurityFilterChain>：该构建对象为了构建**DefaultSecurityFilterChain**
         + DefaultSecurityFilterChain实现SecurityFilterChain
     + 在构建的时候通过模板方法configure(B builder)，会将之前添加的configure进行遍历配置configure(H http)--->添加过滤器
     + FilterChainProxy会根据url匹配SecurityFilterChain过滤器链
     
## 参考资料
  + 徐靖峰springSecurity系列：https://www.cnkirito.moe/spring-security-2/
     
      
       
                         
         
          
         
   
 