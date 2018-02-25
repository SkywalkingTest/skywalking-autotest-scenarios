/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.skywalking.testcase.hystrix.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class SuccessCommand extends HystrixCommand<String> {
    private String name;

    protected SuccessCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SuccessCommand"))
            .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                .withExecutionTimeoutEnabled(true)
                    .withExecutionTimeoutInMilliseconds(100)
            )
            .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
            )
        );
        this.name = name;
    }

    protected String run() throws Exception {

        try {
            System.out.println("start run: " + +Thread.currentThread().getId());
            return "Hello " + name + "!";
        } finally {
            System.out.println("start end");
        }
    }

    @Override protected String getFallback() {
        try {
            System.out.println("getFallback run: " + Thread.currentThread().getId());
            return "failed";
        } finally {
            System.out.println("getFallback end");
        }
    }
}
