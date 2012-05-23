/*
 * Copyright 2010 Hjortur Stefan Olafsson
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
 *
 */
package twigkit.cachalot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import java.util.Date;

import net.sf.ehcache.CacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Cachalot method caching.
 *
 * @author mr.olafsson
 */
public class CachalotTest {

    private Injector injector;
    private CacheManager cacheManager;

    @Before
    public void setUpClass() throws Exception {
        Module testModule = Modules.override(new CachalotModule()).with(new CachalotTestModule());
        injector = Guice.createInjector(testModule);

        cacheManager = injector.getInstance(CachalotModule.class).getCacheManager();
    }

    @After
    public void tearDownClass() throws Exception {
        cacheManager.clearAll();
        cacheManager.shutdown();
    }

    @Test
    public void singleArgumentTest() {
        CachedMethods mct = injector.getInstance(CachedMethods.class);

        String arg = "arg";
        assertEquals("Return value for single argument is incorrect (invocation)", mct.getString(arg), "single-argument");
        assertEquals("Invocation count not incremented even though method should have been invoked", 1, mct.getInvocationCount());

        assertEquals("Return value for single argument is incorrect (cached)", mct.getString(arg), "single-argument");
        assertEquals("Invocation count changed even though method should not have been invoked", 1, mct.getInvocationCount());
    }

    @Test
    public void singleArgumentFileConfiguredTest() {
        CachedMethods mct = injector.getInstance(CachedMethods.class);

        String arg = "arg";
        assertEquals("Return value for single argument configured via ehcache.xml (invocation)", mct.getStringFileConfigured(arg), "single-argument");
        assertEquals("Invocation count not incremented even though method should have been invoked", 1, mct.getInvocationCount());

        assertEquals("Return value for single argument configured via ehcache.xml is incorrect (cached)", mct.getStringFileConfigured(arg), "single-argument");
        assertEquals("Invocation count changed even though method should not have been invoked", 1, mct.getInvocationCount());
    }

    @Test
    public void singleArgumentNoNameTest() {
        CachedMethods mct = injector.getInstance(CachedMethods.class);

        String arg = "arg";
        assertEquals("Return value for single argument is incorrect, no name configured (invocation)", mct.getStringDefaultNoName(arg), "single-argument");
        assertEquals("Invocation count not incremented even though method should have been invoked", 1, mct.getInvocationCount());

        assertEquals("Return value for single argument is incorrect, no name configured (cached)", mct.getStringDefaultNoName(arg), "single-argument");
        assertEquals("Invocation count changed even though method should not have been invoked", 1, mct.getInvocationCount());
    }

    @Test
    public void singleArgumentNonSerializableTest() {
        CachedMethods mct = injector.getInstance(CachedMethods.class);

        String arg = "arg";
        assertEquals("Non-serializable return value for single argument is incorrect (invocation)", mct.getNonSerializable(1), new CachedMethods.NonSerializable(1));
        assertEquals("Invocation count not incremented even though method should have been invoked", 1, mct.getInvocationCount());

        assertEquals("Non-serializable return value for single argument is incorrect (cached)", mct.getNonSerializable(1), new CachedMethods.NonSerializable(1));
        assertEquals("Invocation count changed even though method should not have been invoked", 1, mct.getInvocationCount());
    }

    @Test
    public void multipleArgumentTest() {
        CachedMethods mct = injector.getInstance(CachedMethods.class);

        String arg0 = "arg0";
        int arg1 = 100;
        Date arg2 = new Date();

        assertEquals("Return value for multiple arguments is incorrect (invocation)", mct.getString(arg0, arg1, arg2), "multiple-arguments");
        assertEquals("Invocation count not incremented even though method should have been invoked", 1, mct.getInvocationCount());

        assertEquals("Return value for multiple arguments is incorrect (cached)", mct.getString(arg0, arg1, arg2), "multiple-arguments");
        assertEquals("Invocation count changed even though method should not have been invoked", 1, mct.getInvocationCount());
    }
}