package cn.tycoding.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangqing
 * @date 2019-10-21
 */
@Configuration
public class ShiroConfig {
    /**
     * 凭证匹配器
     *
     * 哈希密码比较器。在UserRealm中作用参数使用
     * 登陆时会比较用户输入的密码，跟数据库密码配合盐值salt解密后是否一致。
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        //所需加密的参数,即密码
        String password = "123";
        //[盐]一般为用户名或随机数
        String salt = "2";
        //加密次数
        int hashIterations = 1;
        String md5 = new Md5Hash(password, salt).toString();//得到散列值
        String md5More = new Md5Hash(password, salt, hashIterations).toString(); //散列次数为1
        System.out.println(md5);
        System.out.println(md5More);

        //md5加密1次
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); //散列算法：MD5算法
        hashedCredentialsMatcher.setHashIterations(1); //散列的次数，比如散列2次，相当于md5(md5(""));
        return hashedCredentialsMatcher;

    }


    /**
     * 自定义realm
     * 身份认证Realm，此处的注入不可以缺少。否则会在ShiroRealm中注入对象会报空指针。
     * (这个需要自己写，账号密码校验；权限等)
     * @return
     */
    @Bean
    public ShiroRealm userRealm() {
        ShiroRealm userRealm = new ShiroRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    //解决RequirePermissions的问题
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);

        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 安全管理器
     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManger会报错，直接引用shiro-spring则不报错
     *
     * @return
     */
    /*@Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
*/
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;

    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题
     * 注意：单独一个ShiroFilterFactoryBean配置是会报错的，因为在初始化ShiroFilterFactoryBean时需要注入：SecurityManager
     * Filter Chain定义说明：
     * 1.一个URL可以配置多个Filter，使用逗号分隔；
     * 2.当设置多个过滤器时，全部验证通过，才视为通过；
     * 3.部分过滤器可指定参数，如perms，roles。
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //默认跳转到登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //自定义拦截器链 权限控制map
        //注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，已匹配的就不再继续验证；
        //所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
        //开发的静态资源
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //配置不会被拦截的链接 顺序判断
        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //配置登录方法不被拦截
        filterChainDefinitionMap.put("login", "anon");
        //配置静态页面不被拦截,静态资源，对应`/resources/static`文件夹下的资源
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");

        //其他请求一律拦截，一般放在拦截器链的最后
        //区分`user`和`authc`拦截器区别：`user`拦截器允许登录用户和RememberMe的用户访问
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;

    }


}
