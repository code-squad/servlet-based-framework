package core;

import core.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"core.mvc", "next.dao", "next.service"})
public class MyConfiguration {

}
