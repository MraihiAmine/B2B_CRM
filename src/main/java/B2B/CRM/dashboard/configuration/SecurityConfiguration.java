package B2B.CRM.dashboard.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private DataSource dataSource;

  @Value("${spring.queries.users-query}")
  private String usersQuery;

  @Value("${spring.queries.roles-query}")
  private String rolesQuery;

  @Override
  //1)Authentication
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .jdbcAuthentication()
      .usersByUsernameQuery(usersQuery)
      .authoritiesByUsernameQuery(rolesQuery)
      .dataSource(dataSource)
      .passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  //2)Autorisation
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/home")
      .permitAll()
      .antMatchers(
        "/resources/**",
        "/static/**",
        "/front/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/plugins/**"
      )
      .permitAll()
      // accès pour tous users
      .antMatchers("/login")
      .permitAll() // access pour tous users
      .antMatchers("/nps/**")
      .permitAll() // accès pour tous users
      .antMatchers("/sales-statistics/**")
      .permitAll() // accès pour tous users
      .antMatchers("/api/customer-retention-rates/**")
      .permitAll() // accès pour tous users
      .antMatchers("/registration")
      .permitAll() // accès pour tous users
      .antMatchers("/article/**")
      .permitAll() // accès pour tous users
      .antMatchers("/year_statistics/**")
      .permitAll() // accès pour tous users
      .antMatchers("/product/**")
      .permitAll() // accès pour tous users
      .antMatchers("/user/add")
      .permitAll()
      .antMatchers("/accounts/profile")
      .hasAnyAuthority("USER", "SUPERADMIN")
      .antMatchers("/role/**")
      .hasAnyAuthority("ADMIN", "SUPERADMIN")
      .antMatchers("/accounts/add")
      .hasAnyAuthority("ADMIN", "SUPERADMIN")
      .antMatchers("/accounts/list")
      .hasAnyAuthority("ADMIN", "SUPERADMIN")
      .anyRequest()
      .authenticated()
      .and()
      .csrf()
      .disable()
      .formLogin() // l'accès de fait via un formulaire
      .loginPage("/login")
      .failureUrl("/login?error=true") // fixer la page login
      .defaultSuccessUrl("/accounts/profile") // page d'accueil après login avec succès
      .usernameParameter("email") // paramètres d'authentifications login et password
      .passwordParameter("password")
      .and()
      .logout()
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // route de deconnexion ici /logut
      .logoutSuccessUrl("/login")
      .and()
      .exceptionHandling() // une fois deconnecté redirection vers login
      .accessDeniedPage("/403");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers(
        "/resources/**",
        "/static/**",
        "/front/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/plugins/**",
        "/error"
      );
  }
}
