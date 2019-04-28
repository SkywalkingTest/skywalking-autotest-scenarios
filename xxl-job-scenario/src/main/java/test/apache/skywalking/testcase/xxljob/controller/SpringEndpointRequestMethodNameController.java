/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package test.apache.skywalking.testcase.xxljob.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import test.apache.skywalking.testcase.xxljob.handler.DemoJobHandler;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/requestmethod")
@PropertySource("classpath:application.properties")
public class SpringEndpointRequestMethodNameController {

    private Logger logger = LoggerFactory.getLogger(SpringEndpointRequestMethodNameController.class);

    @Autowired
    private DemoJobHandler demoJobHandler;

    @ResponseBody
    @RequestMapping("/xxljob")
    public String xxljob() throws Exception{

        logger.info("xxljob invoked");

        return demoJobHandler.execute("xxljob").toString();
    }
}
