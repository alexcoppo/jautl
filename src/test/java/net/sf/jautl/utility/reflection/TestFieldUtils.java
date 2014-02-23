/*
    Copyright (c) 2000-2014 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.utility.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class TestFieldUtils {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FooBar { }
    
    public class DummyPOJO {
        public String strField;
        @FooBar
        public double dblField;
    }

    @Test
    public void testSetField() throws Exception {
        DummyPOJO obj = new DummyPOJO();
        
        final double v = 3.14;
        
        FieldUtils.setValue(obj, "dblField", new Double(v));
        Assert.assertTrue(obj.dblField == v);
    }
    
    @Test
    public void testGetField() throws Exception {
        DummyPOJO obj = new DummyPOJO();
        
        final double v = 3.14;
        obj.dblField = v;
        
        Double dbl = (Double)FieldUtils.getValue(obj, "dblField");
        Assert.assertTrue(dbl.doubleValue() == v);
    }
    
    @Test
    public void testGetFieldNames() {
        DummyPOJO obj = new DummyPOJO();
        
        String[] names = FieldUtils.getFieldNames(obj);
        Assert.assertTrue(names.length == 2);

        Arrays.sort(names);
        Assert.assertTrue(Arrays.binarySearch(names, "dblField") >= 0);
        Assert.assertTrue(Arrays.binarySearch(names, "strField") >= 0);
    }

    @Test
    public void testGetFieldNamesByAttribute() {
        DummyPOJO obj = new DummyPOJO();
        
        String[] names = FieldUtils.getFieldNamesByAttribute(obj, FooBar.class);
                
        Assert.assertTrue(names.length == 1);
        Assert.assertTrue(names[0].equals("dblField"));
    }

    @Test
    public void testGetFieldsByAttribute() {
        DummyPOJO obj = new DummyPOJO();
        
        Field[] fields = FieldUtils.getFieldsByAttribute(obj, FooBar.class);
                
        Assert.assertTrue(fields.length == 1);
        Assert.assertTrue(fields[0].getName().equals("dblField"));
    }
}
