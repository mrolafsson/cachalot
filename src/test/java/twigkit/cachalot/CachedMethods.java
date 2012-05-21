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

import java.util.Date;

/**
 * The CachedMethods class has methods that are annotated with the {@link Cache}
 * annotation to test caching of return values.
 *
 * @author mr.olafsson
 */
public class CachedMethods {

    private int invocationCount = 0;

    /**
     * A method with a single argument to test caching with simple keys.
     *
     * @param arg
     * @return
     */
    @Cache(name = "single-argument", maxElementsInMemory = 10, timeToLiveSeconds = 60, timeToIdleSeconds = 45)
    public String getString(String arg) {
        invocationCount++;

        return "single-argument";
    }

    /**
     * A method with a single argument to test caching with simple keys.
     *
     * @param arg
     * @return
     */
    @Cache(name = "file-configured")
    public String getStringFileConfigured(String arg) {
        invocationCount++;

        return "single-argument";
    }

    @Cache
    public String getStringDefaultNoName(String arg) {
        invocationCount++;

        return "single-argument";
    }

    /**
     * A method that takes multiple arguments including primitives and objects to
     * test caching where the cache key is created from the array of these.
     *
     * @param arg
     * @param arg2
     * @param arg3
     * @return
     */
    @Cache(name = "multiple-arguments", maxElementsInMemory = 10, timeToLiveSeconds = 60, timeToIdleSeconds = 45)
    public String getString(String arg, int arg2, Date arg3) {
        invocationCount++;

        return "multiple-arguments";
    }

    /**
     * Get the number of times these methods have been invoked.
     *
     * @return
     */
    public int getInvocationCount() {
        return invocationCount;
    }
}
