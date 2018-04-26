package core.nmvc;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanDefinition {
    private ConfigurationBeanScanner cbs;
    private ClassPathBeanScanner cpbs;

    public BeanDefinition(ConfigurationBeanScanner cbs, ClassPathBeanScanner cpbs) {
        this.cbs = cbs;
        this.cpbs = cpbs;
    }

    public Set<Bean> getBeanCandidates() {
        return Stream.concat(this.cbs.doScan().stream(), this.cpbs.doScan().stream()).collect(Collectors.toSet());
    }

    public Object instantiateConfiguration() throws IllegalAccessException, InstantiationException {
        return cbs.getConfigurationFile().newInstance();
    }
}
