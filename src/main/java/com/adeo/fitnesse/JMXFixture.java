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

public class JMXFixture extends fit.Fixture{

	private String hostport = "localhost:10102";
	private String objectName = "LilyLauncher:name=Launcher";
	private String operationName = "resetLilyState";
	
	public Boolean callJMXOperation() {
		
		if(hostport == null || objectName == null || operationName == null) {
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
		
		if(connector == null){
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
