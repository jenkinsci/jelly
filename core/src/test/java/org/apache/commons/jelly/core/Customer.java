/*
 * Copyright 2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.jelly.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A sample bean that we can construct via Jelly tags
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @version $Revision: 155420 $
 */
public class Customer {

    private String name;
    private String city;
    private String location;
    private List orders = new ArrayList();


    public Customer() {
    }

    public Customer(String name) {
        setName(name);
    }

    public Customer(String name, String city) {
        setName(name);
        setCity(city);
    }

    public Customer(String name, String city, Order anOrder) {
        setName(name);
        setCity(city);
        addOrder(anOrder);
    }

    public Customer(Customer cust) {
        setName(cust.getName());
        setCity(cust.getCity());
        setLocation(cust.getLocation());
        List list = cust.getOrders();
        if(null != list) {
            for(Iterator iter = list.iterator();iter.hasNext();) {
                addOrder((Order)iter.next());
            }
        }
    }

    public String toString() {
        return super.toString() + "[name=" + name + ";city=" + city + "]";
    }

    /**
     * Creates a new Order object
     */
    public Order createOrder() {
        return new Order();
    }

    public List getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Returns the city.
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the location.
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the city.
     * @param city The city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the location.
     * @param location The location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }


}
