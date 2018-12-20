package com.elliot.security.core.validate.sms;

import com.elliot.security.core.validate.ValidateCodeProcessor;
import com.elliot.security.core.validate.token.MobileAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

public class MobileAuthenticationProvider implements AuthenticationProvider {

    private final static Logger logger = LoggerFactory.getLogger(MobileAuthenticationProvider.class);
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsService userDetailsService;
    private boolean forcePrincipalAsString = false;

    public MobileAuthenticationProvider(UserDetailsService userDetailsService, ValidateCodeProcessor validateCodeProcessor) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
        UserDetails user = this.retrieveUser(username, (UsernamePasswordAuthenticationToken)authentication);
        this.preAuthenticationChecks.check(user);
        this.postAuthenticationChecks.check(user);
        Object principalToReturn = user;
        if (this.forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }
        return this.createSuccessAuthentication(principalToReturn, authentication, user);
    }

    private UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
        UserDetails loadedUser;
        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException userNotFoundException) {
            throw userNotFoundException;
        } catch (Exception otherException) {
            throw new InternalAuthenticationServiceException(otherException.getMessage(), otherException);
        }
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        MobileAuthenticationToken result = new MobileAuthenticationToken(principal, authentication, user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                logger.debug("User account credentials have expired");
                throw new CredentialsExpiredException(messages.getMessage("MobileAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                logger.debug("User account is locked");
                throw new LockedException(messages.getMessage("MobileAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                logger.debug("User account is disabled");
                throw new DisabledException(messages.getMessage("MobileAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                logger.debug("User account is expired");
                throw new AccountExpiredException(messages.getMessage("MobileAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }

}
