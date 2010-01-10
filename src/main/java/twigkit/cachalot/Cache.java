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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use the Cache annotation to intercepts method calls and return value without
 * invoking the method if the cache has a stored return value for the method
 * parameters.
 *
 * If the cache does not have a value for the method parameters then the method
 * is invoked, and the return value cached before returning to the caller.
 * 
 * @author mr.olafsson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

	String name();

	int maxElementsInMemory() default(3000);

	boolean overflowToDisk() default(false);

	boolean eternal() default(false);

	long timeToLiveSeconds() default(120);

	long timeToIdleSeconds() default(100);

	boolean diskPersistent() default(false);

	long diskExpiryThreadIntervalSeconds() default(120);
	
}
