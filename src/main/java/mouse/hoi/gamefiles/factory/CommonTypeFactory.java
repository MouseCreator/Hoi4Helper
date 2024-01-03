package mouse.hoi.gamefiles.factory;

import mouse.hoi.gamefiles.common.annotation.Factory;
import mouse.hoi.gamefiles.common.annotation.FactoryFor;
import mouse.hoi.gamefiles.tempmodel.common.countrytags.CountryTags;
import org.springframework.stereotype.Component;

@Component
@Factory
public class CommonTypeFactory {
    @FactoryFor
    public CountryTags getCountryTags() {
        return new CountryTags();
    }
}
