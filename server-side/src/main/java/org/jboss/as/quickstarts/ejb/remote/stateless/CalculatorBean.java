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
package org.jboss.as.quickstarts.ejb.remote.stateless;

import java.sql.SQLException;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.as.quickstarts.ejb.remote.database.DatabaseContrler;

/**
 * @author Jaikiran Pai
 */
@Stateless
@Remote(RemoteCalculator.class)
public class CalculatorBean implements RemoteCalculator {

	DatabaseContrler Dc=new DatabaseContrler();
	@Override
	public String statelessRgist(String name, String password) {
		// TODO Auto-generated method stub
		String s="";
		try
		{
			s=Dc.statelessRegist(name, password);
		}
		catch(Exception e)
		{
			s=e.toString();
		}
		return s;
	}
	@Override
	public String test() {
		// TODO Auto-generated method stub
		return Dc.test();
	}
	@Override
	public String statelessLoad(String name, String password) {
		// TODO Auto-generated method stub
		String s="";
		try {
			s= Dc.statelessLoad(name, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			s=e.toString();
			e.printStackTrace();
		}
		return s;
	}

}
