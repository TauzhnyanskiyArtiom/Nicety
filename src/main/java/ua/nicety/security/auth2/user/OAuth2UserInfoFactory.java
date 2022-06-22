package ua.nicety.security.auth2.user;



import ua.nicety.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

import static ua.nicety.database.entity.AuthProvider.google;


public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}