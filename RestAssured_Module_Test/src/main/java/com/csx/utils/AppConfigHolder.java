package com.csx.utils;

import org.aeonbits.owner.ConfigCache;

/**
 * This class provides a factory method {@linkgetInstance} to get a Properties File object or
 * instance. ConfigCache create method will load the Properties file and create AppConfig instances
 * from an internal cache and map it to the interface called AppConfig
 *
 * @author Z2287
 *
 */
public class AppConfigHolder {
  private AppConfigHolder() {

  }

  /**
   * The instance of Application Configuration
   *
   */
  private static final AppConfig INSTANCE = ConfigCache.getOrCreate(AppConfig.class);

  /**
   * Gets the instance of Application Configuration.
   *
   * @return the instance of Application Configuration.
   */
  public static AppConfig getInstance() {
    return INSTANCE;
  }
}
