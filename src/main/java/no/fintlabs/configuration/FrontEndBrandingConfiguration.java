package no.fintlabs.configuration;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
//@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "fint.branding")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class FrontEndBrandingConfiguration {
    private String logo;
    private String primaryColor;
    private String primaryColorLight;
    private String secondaryColor;
    private String featureColor1;
    private String featureColor2;
    private String phoneNumber;
    private String mail;
    private String countyName;
    
}
