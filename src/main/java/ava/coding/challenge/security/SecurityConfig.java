package ava.coding.challenge.security;

import ava.coding.challenge.main.employee.EmployeeService;
import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.MasterOrganizationService;
import ava.coding.challenge.main.organization.OrganizationService;
import ava.coding.challenge.main.organization.entities.MasterOrganization;
import ava.coding.challenge.main.organization.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MasterOrganizationService masterOrganizationService;

    // Authentication : User --> Roles
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<Employee> allEmployees = (ArrayList<Employee>) employeeService.getAllEmployees().getBody();
        List<Organization> allOrganization = organizationService.getAllOrganizations();
        List<MasterOrganization> allMasterOrganizations = masterOrganizationService.getAllMasterOrganizations();
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authBuilder =
                auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance());

        for (Iterator<Employee> i = allEmployees.iterator(); i.hasNext();) {
            Employee item = i.next();
            authBuilder.withUser(item.getId()).password(item.getId()).roles("EMPLOYEE");
        }
        for (Iterator<Organization> i = allOrganization.iterator(); i.hasNext();) {
            Organization item = i.next();
            authBuilder.withUser(item.getId()).password(item.getId()).roles("ORGANIZATION");
        }
        for (Iterator<MasterOrganization> i = allMasterOrganizations.iterator(); i.hasNext();) {
            MasterOrganization item = i.next();
            authBuilder.withUser("mo_" + item.getId()).password("mo_" + item.getId()).roles("MASTER_ORGANIZATION");
        }
    }

    // Authorization : Role -> Access
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .antMatchers(HttpMethod.GET, "/organizations").hasAnyRole("EMPLOYEE", "ORGANIZATION", "MASTER_ORGANIZATIONS")
            .antMatchers(HttpMethod.POST, "/organizations").hasRole("MASTER_ORGANIZATION")
            .antMatchers(HttpMethod.PUT, "/organizations").hasRole("MASTER_ORGANIZATION")
            .antMatchers(HttpMethod.DELETE, "/organizations").hasRole("MASTER_ORGANIZATION")
            .antMatchers("/organizations/**").hasAnyRole("ORGANIZATION", "EMPLOYEE")
            .antMatchers("/master-organizations/**").hasRole("MASTER_ORGANIZATION")
            .and().formLogin()
            .and().httpBasic()
            .and().csrf().disable()
            .headers().frameOptions().disable();

       /* http.httpBasic()
            .and()
            .anonymous()
            .and()
            .antMatcher("/");*/

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
