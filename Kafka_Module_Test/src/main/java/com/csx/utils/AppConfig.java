package com.csx.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

/**
 * This interface associates Keys in the AppConfig.properties file with the abstract methods
 * declared inside this Interface and since the name of this interface is same as the properties
 * file hence AEON OWNERs API will be able to associate them. Key annotation is used to map the
 * property name often containing dot in the Key names to the associated method.
 *
 * @author Z2287
 *
 */
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:AppConfig.properties"})
public interface AppConfig extends Config, Mutable {

  @Key("environment")
  String environment();

  @Key("application.qa.url")
  String applicationMELQAURL();

  @Key("application.stage.url")
  String applicationMELStageURL();

  @Key("application.dev.url")
  String applicationMELDevURL();

  @Key("browser")
  String browser();

  @Key("manager_Username")
  String userName();

  @Key("manager_Password")
  String password();

  @Key("maintainer_Username")
  String mantainer_userName();

  @Key("maintainer_Password")
  String maintainer_password();

  @Key("googleurl")
  String googleurl();
  @Key("remoteExecution")
  String remoteExecution();
  @Key("headlessRun")
  String headlessRun();


}
