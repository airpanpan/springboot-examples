package com.zzq.config;


import com.zzq.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.DigestUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userService;

   /* @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
*/

    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/401");
    }




    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        //校验用户
        auth.userDetailsService( userService ).passwordEncoder( new PasswordEncoder() {
            //对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                System.out.println(charSequence.toString());
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }
            //对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                boolean res = s.equals( encode );
                return res;
            }
        } );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.authorizeRequests()
                //.antMatchers("/login","/login.html").permitAll()
                //.anyRequest().authenticated()
               // .and()
              //  .formLogin()
              //  .loginPage( "/login" )
                //.failureUrl( "/login-error" )
                //.and()
                //.exceptionHandling().accessDeniedPage( "/401" )
                //.and()
                //.csrf().disable();
        //http.logout().logoutSuccessUrl( "/" );
        http.formLogin()
                .loginPage("/hello").loginProcessingUrl("/login").defaultSuccessUrl("/index")
                .and().exceptionHandling().accessDeniedPage( "/401" )
                //.loginProcessingUrl("/demo-login")
                .and()
                .authorizeRequests()
                .antMatchers("/401").permitAll()
                .antMatchers("/401.html").permitAll()
                .antMatchers("/hello").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/hello.html").permitAll()
               /* .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()*/

                .anyRequest().authenticated();
        http.csrf().disable();
        //http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }


}
