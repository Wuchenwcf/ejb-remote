/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.ejb.remote.client;

import org.jboss.as.quickstarts.ejb.remote.stateful.RemoteCounter;
import org.jboss.as.quickstarts.ejb.remote.stateless.RemoteCalculator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * A sample program which acts a remote client for a EJB deployed on AS7 server. This program shows how to lookup stateful and
 * stateless beans via JNDI and then invoke on them
 *
 * @author Jaikiran Pai
 */
public class RemoteEJBClient {

	 RemoteCalculator statelessRemoteCalculator;
	 RemoteCounter statefulRemoteCounter;
	
	 public RemoteEJBClient() throws NamingException
	 {
		   invokeStatelessBean();
	        // Invoke a stateful bean
	        invokeStatefulBean();
	 }
    public static void main(String[] args) throws Exception {
        // Invoke a stateless bean
        RemoteEJBClient re=new RemoteEJBClient();
        
        re.select();
    }

    /**
     * Looks up a stateless bean and invokes on it
     *
     * @throws NamingException
     */
    private  void invokeStatelessBean() throws NamingException {
        // Let's lookup the remote stateless calculator
        statelessRemoteCalculator = lookupRemoteStatelessCalculator();
       
    }

    /**
     * Looks up a stateful bean and invokes on it
     *
     * @throws NamingException
     */
    private  void invokeStatefulBean() throws NamingException {
        // Let's lookup the remote stateful counter
      statefulRemoteCounter = lookupRemoteStatefulCounter();
     
    }

    /**
     * Looks up and returns the proxy to remote stateless calculator bean
     *
     * @return
     * @throws NamingException
     */
    private static RemoteCalculator lookupRemoteStatelessCalculator() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateless session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : WildFly allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.

        // let's do the lookup
        return (RemoteCalculator) context.lookup("ejb:/wildfly-ejb-remote-server-side/CalculatorBean!"
            + RemoteCalculator.class.getName());
    }

    /**
     * Looks up and returns the proxy to remote stateful counter bean
     *
     * @return
     * @throws NamingException
     */
    private static RemoteCounter lookupRemoteStatefulCounter() throws NamingException {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateful session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>?stateful
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : WildFly allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.

        // let's do the lookup
        return (RemoteCounter) context.lookup("ejb:/wildfly-ejb-remote-server-side/CounterBean!"
            + RemoteCounter.class.getName() + "?stateful");
    }

    public void select()
    {
    	System.out.println("请选择:1.无状态的JAVABean 2.有状态的JAVABean");
    	Scanner sc=new Scanner(System.in);
    	int n=sc.nextInt();
    	switch(n)
    	{
    	case 1:this.StatelessSelect();break;
    	case 2:this.StatefulSelect();break;
    	default:break;
    	}
    	
    }
    public void StatelessSelect()
    {
    	System.out.println("请选择:1。登录    2。注册  0。退出(当前为无状态)");
    	Scanner sc=new Scanner(System.in);
    	int n=sc.nextInt();
    	switch(n)
    	{
    	case 1:this.StatelessLoad();break;
    	case 2:this.StatelessRegist();break;
    	default:break;
    	}
    	this.select();
    }
    public void StatefulSelect()
    {
    
    	System.out.println("请选择:1。登录 2.注册  0.退出(当前为有状态)");
    	Scanner sc=new Scanner(System.in);
    	int n=sc.nextInt();
    	switch(n)
    	{
    	case 1:this.StatefulLoad();;break;
    	case 2:this.StatefulRegist();;break;
    	default:break;
    	}
    this.select();
    }
    public void StatelessLoad()
    {
    	System.out.println("请输入用户名和密码");
    	Scanner sc=new Scanner(System.in);
    	String name=sc.next();
    	String password=sc.next();
    	try
    	{
    	System.out.println(this.statelessRemoteCalculator.statelessLoad(name, password));
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    	this.StatelessSelect();
    }
    public void StatelessRegist()
    {
    	System.out.println("请输入用户名和密码");
    	Scanner sc=new Scanner(System.in);
    	String name=sc.next();
    	String password=sc.next();
    	try
    	{
    	System.out.println(this.statelessRemoteCalculator.statelessRgist(name, password));
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    	this.StatelessSelect();
    }
    public void StatefulLoad()
    {
    	System.out.println("请输入用户名和密码");
    	Scanner sc=new Scanner(System.in);
    	String name=sc.next();
    	String password=sc.next();
    	long time1=System.currentTimeMillis();   
    	try
    	{
    	System.out.println(this.statefulRemoteCounter.statefulLoad(name, password));
    	}
    	catch(Exception e)
    	{
    		
    	}
    	String s="no";
    	while(s.equals("no"))
    	{
    		System.out.println("退出吗？yes/no");
    		s=sc.next();
    	}
    	long time2=System.currentTimeMillis();
    	System.out.println("本次登录共"+time2+"毫秒！");
    	this.StatefulSelect();
    }
    public void StatefulRegist()
    {
    	this.StatefulSelect();
    }
}

