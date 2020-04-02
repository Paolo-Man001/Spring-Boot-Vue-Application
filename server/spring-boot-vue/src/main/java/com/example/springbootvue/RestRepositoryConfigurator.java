package com.example.springbootvue;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;


public class RestRepositoryConfigurator implements RepositoryRestConfigurer {
   @Override
   public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

      /* by Default, RestRepository DO NOT return IDs.
       *  This exposes IDs for the client to easily find
       *  the ID of created and listed resources.
       * */
      config.exposeIdsFor(Todo.class);
   }
}
