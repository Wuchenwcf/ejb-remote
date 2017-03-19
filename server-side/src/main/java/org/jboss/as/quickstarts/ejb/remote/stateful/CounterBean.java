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
package org.jboss.as.quickstarts.ejb.remote.stateful;

import java.sql.SQLException;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.jboss.as.quickstarts.ejb.remote.database.*;

/**
 * @author Jaikiran Pai
 */
@Stateful
@Remote(RemoteCounter.class)
public class CounterBean implements RemoteCounter {

    private int count = 0;
    private DatabaseContrler Dc=new DatabaseContrler();

    
	@Override
	public String test() {
		return Dc.test();
	}


	@Override
	public String statefulLoad(String name, String password) {
		// TODO Auto-generated method stub
		String s="";
		try {
			s= Dc.statefullLoad(name, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	
			e.printStackTrace();
			s=e.toString();
		}
		return s;
	}

   
}
