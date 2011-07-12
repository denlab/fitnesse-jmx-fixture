/*
 * Copyright 2011 fitnesse-jmx-fixture Committers 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adeo.fitnesse;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * 
 * Fixture to call from a FitNesse page a JMX MBean operation using RMI. <br/>
 * <br/>
 * To use this Fixture you must add the jar file of the project containing this
 * class in the lib directory of FitNesse. <br/>
 * <br/>
 * To generate the jar file from the project, you can use this command: mvn
 * clean install. Then you will find the jar file in the target directory. <br/>
 * <br/>
 * Here a sample code to use it in a FitNesse page: <br/>
 * <br/>
 * <code>
 * !contents <br/>
 *  <br/>
 * !path /home/ec2-user/fitnesse/fitnesse.jar <br/>
 * !path /home/ec2-user/fitnesse/lib/* <br/>
 *  <br/>
 * !|ActionFixture| <br/>
 * |start|com.adeo.fitnesse.JMXFixture| <br/>
 * |enter|setHostport|localhost:10102| <br/>
 * |enter|setOperationName|resetLilyState| <br/>
 * |enter|setObjectName|LilyLauncher:name=Launcher| <br/>
 * |check|callJMXOperation|true| <br/>
 * </code>
 * 
 * TODO: code review
 * TODO: manage the returned Object from the JMX Operation call.
 * 
 * @author cyrillakech <cyril.lakech@webadeo.net>
 * 
 */
public class JMXFixture extends fit.Fixture {

	/**
	 * the host and the port of the JVM to reach <br/>
	 * <br/>
	 * sample "localhost:10102"
	 */
	private String hostport = null;

	/**
	 * the mbean object name to call <br/>
	 * <br/>
	 * sample "LilyLauncher:name=Launcher"
	 */
	private String objectName = null;

	/**
	 * the operation name to call <br/>
	 * <br/>
	 * sample "resetLilyState"
	 */
	private String operationName = null;

	public boolean callJMXOperation() {

		if (hostport == null || objectName == null || operationName == null) {
			return false;
		}

		JMXServiceURL url = null;
		try {
			url = new JMXServiceURL("service:jmx:rmi://" + hostport
					+ "/jndi/rmi://" + hostport + "/jmxrmi");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
		JMXConnector connector = null;
		try {
			connector = JMXConnectorFactory.connect(url);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if (connector == null) {
			return false;
		}

		try {
			connector.connect();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		ObjectName lilyLauncher = null;
		try {
			lilyLauncher = new ObjectName(objectName);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
			return false;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			connector.getMBeanServerConnection().invoke(lilyLauncher,
					operationName, new Object[0], new String[0]);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (MBeanException e) {
			e.printStackTrace();
			return false;
		} catch (ReflectionException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			connector.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public String getHostport() {
		return hostport;
	}

	public void setHostport(String hostport) {
		this.hostport = hostport;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
