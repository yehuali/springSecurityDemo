package com.example.core.config;

import com.example.core.authentication.AuthenticationManager;
import com.example.core.authentication.AuthenticationManagerBuilder;
import com.example.core.authentication.DefaultAuthenticationEventPublisher;
import com.example.core.authentication.config.AuthenticationConfiguration;
import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.web.access.intercept.FilterSecurityInterceptor;
import com.example.core.web.builders.HttpSecurity;
import com.example.core.web.builders.WebSecurity;
import com.example.core.web.config.WebSecurityConfigurer;
import com.example.core.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;


public abstract class WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {

    private ApplicationContext context;
    private HttpSecurity http;

    private AuthenticationConfiguration authenticationConfiguration;
    private AuthenticationManagerBuilder authenticationBuilder;
    private AuthenticationManagerBuilder localConfigureAuthenticationBldr;
    private boolean authenticationManagerInitialized;
    private boolean disableLocalConfigureAuthenticationBldr;
    private AuthenticationManager authenticationManager;
    private boolean disableDefaults;

    private ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
        public <T> T postProcess(T object) {
            throw new IllegalStateException(
                    ObjectPostProcessor.class.getName()
                            + " is a required bean. Ensure you have used @EnableWebSecurity and @Configuration");
        }
    };

    @Override
    public void init(final WebSecurity web) throws Exception {
        //获取http实例
        final HttpSecurity http = getHttp();
        //添加过滤器链
        web.addSecurityFilterChainBuilder(http).postBuildAction(new Runnable() {
            public void run() {
                FilterSecurityInterceptor securityInterceptor = http
                        .getSharedObject(FilterSecurityInterceptor.class);
                web.securityInterceptor(securityInterceptor);
            }
        });
    }


    protected final HttpSecurity getHttp() throws Exception{
        if (http != null) {
            return http;
        }

        DefaultAuthenticationEventPublisher eventPublisher = objectPostProcessor.postProcess( new DefaultAuthenticationEventPublisher());
        localConfigureAuthenticationBldr.authenticationEventPublisher(eventPublisher);

        AuthenticationManager authenticationManager = authenticationManager();
        authenticationBuilder.parentAuthenticationManager(authenticationManager);
        Map<Class<? extends Object>, Object> sharedObjects = createSharedObjects();

        http = new HttpSecurity(objectPostProcessor,authenticationBuilder,sharedObjects);
        if (!disableDefaults) {
            http
                .addFilter(new WebAsyncManagerIntegrationFilter())
                .sessionManagement().and()
                .securityContext().and()
                .anonymous()
                ;
        }
        configure(http);
        return http;
    }



    @Autowired
    public void setObjectPostProcessor(ObjectPostProcessor<Object> objectPostProcessor) {
        this.objectPostProcessor = objectPostProcessor;

        authenticationBuilder = new AuthenticationManagerBuilder(objectPostProcessor);

        localConfigureAuthenticationBldr = new AuthenticationManagerBuilder(
                objectPostProcessor) {
            @Override
            public AuthenticationManagerBuilder eraseCredentials(boolean eraseCredentials) {
                authenticationBuilder.eraseCredentials(eraseCredentials);
                return super.eraseCredentials(eraseCredentials);
            }

        };
    }

    protected final ApplicationContext getApplicationContext() {
        return this.context;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }


    private Map<Class<? extends Object>, Object> createSharedObjects() {
        Map<Class<? extends Object>, Object> sharedObjects = new HashMap<Class<? extends Object>, Object>();
//        sharedObjects.putAll(localConfigureAuthenticationBldr.getSharedObjects());
//        sharedObjects.put(UserDetailsService.class, userDetailsService());
        sharedObjects.put(ApplicationContext.class, context);
//        sharedObjects.put(ContentNegotiationStrategy.class, contentNegotiationStrategy);
//        sharedObjects.put(AuthenticationTrustResolver.class, trustResolver);
        return sharedObjects;
    }



    /**
     * 获取要使用的AuthenticationManager
     * 默认策略：如果{@link #configure(AuthenticationManagerBuilder)}方法被重写 ，否则通过类型自动装配
     * @return
     * @throws Exception
     */
    protected AuthenticationManager authenticationManager() throws Exception {
        if (!authenticationManagerInitialized) {
            //这个方法由用户重写 -->待看Oauth2将重写此方法为空方法：false
//            configure(localConfigureAuthenticationBldr);
            /**
             *  如果用户对configure(AuthenticationManagerBuilder auth)方法进行了重写，
             * 那么，disableLocalConfigureAuthenticationBldr将为false,即:将使用用户的
             * 配置来构建“授权管理器”
             */
            if (disableLocalConfigureAuthenticationBldr) {
                authenticationManager = authenticationConfiguration
                        .getAuthenticationManager();
            }
            else {//此时为null
                authenticationManager = localConfigureAuthenticationBldr.build();
            }
            authenticationManagerInitialized = true;
        }
        return authenticationManager;
    }

    /**
     * 1.使用{@link #authenticationManager()}的默认实现进行尝试获取{@link AuthenticationManager}
     *    如果覆盖,{@link AuthenticationManagerBuilder}应用于指定 {@link AuthenticationManager}
     * 2.{@link #authenticationManagerBean()}方法可用于公开结果 {@link AuthenticationManager}作为Bean
     * 3.{@link #userDetailsServiceBean()}可以用于公开创建的最后一个填充的由{@link AuthenticationManagerBuilder}创建的{@link UserDetailsService}bean
     * 4.{@link UserDetailsService}也将在{@link HttpSecurity#getSharedObject(Class)}用于其他 {@link SecurityContextConfigurer}(即memorbermeconfigurer)时 自动填充
     *
     * 基于内存验证
     *  1.重写
     *   protected void configure(AuthenticationManagerBuilder auth) {
     *     auth.inMemoryAuthentication().withUser(&quot;user&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;).and()
     *     .withUser(&quot;admin&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;, &quot;ADMIN&quot;);
     *   }
     * 2.将UserDetailsService作为bean公开
     *   -->重写
     *   public UserDetailsService userDetailsServiceBean() throws Exception {
     *       return super.userDetailsServiceBean();
     *   }
     *
     * @param auth
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        this.disableLocalConfigureAuthenticationBldr = true;
    }


    protected void configure(HttpSecurity http) throws Exception{

    }

    public void configure(WebSecurity web) throws Exception {
    }
}
