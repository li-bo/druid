/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.server.log;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.metamx.emitter.service.ServiceEmitter;
import io.druid.java.util.common.logger.Logger;

import javax.validation.constraints.NotNull;

/**
 */
@JsonTypeName("emitter")
public class EmittingRequestLoggerProvider implements RequestLoggerProvider
{
  private static final Logger log = new Logger(EmittingRequestLoggerProvider.class);

  @JsonProperty
  @NotNull
  private String feed = null;

  @JacksonInject
  @NotNull
  private ServiceEmitter emitter = null;

  @Inject
  public void injectMe(Injector injector)
  {
  }

  @Override
  public RequestLogger get()
  {
    EmittingRequestLogger logger = new EmittingRequestLogger(emitter, feed);
    log.info(new Exception("Stack trace"), "Creating %s at", logger);
    return logger;
  }
}
