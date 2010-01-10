Cachalot
========

**Cache Java method calls and return values with [Guice][Guice]/[AOP][AOP]. No XML configuration files or boilerplate code.**

Cachalot provides a single [annotation][Annotations] to intercept calls to methods in Guice managed classes and return cached values rather than invoking the annotated method. If a cached return value is not found for the arguments in the method call, then the method is invoked and the return value cached. 

Cachalot uses [Ehcache][Ehcache], which can be configured using the annotation elements. 


Installation
------------

1.	Add the cachalot-*{version}*.jar to your project's classpath. If you use [Maven][Maven] to manage your dependencies use add the following to your POM.xml

		<dependency>
			<groupId>twigkit</groupId>
			<artifactId>cachalot</artifactId>
			<version>{version}</version>
		</dependency>


2.	Add the CachalotModule when you create your Injector:
	
		public static void main(String[] args) {
			Injector injector = Guice.createInjector(new YourGuiceModule(), new CachalotModule());
			...
		}

	Cachalot provides a [META-INF/services][MetaInfServices] descriptor in the .jar so if you're using the [Java 6 ServiceLoader][ServiceLoader] to dynamically add any Guice Modules on your classpath it should work out of the box. This is how we create our Injector in [TwigKit][TwigKit]:
	
		ServiceLoader<Module> modules = ServiceLoader.load(Module.class);
		Guice.createInjector(modules);


Usage
-----

Just add the **@Cache** annotation to the methods you would like to cache access to. That's it!

	@Cache(name=“cache-name”", maxElementsInMemory=10, timeToLiveSeconds=60, timeToIdleSeconds=45)
	public String getValueWithOneArgument( String arg ) {
		…
		return value;
	}


Reference
---------

**@Cache(...) Annotation**

*	*Usage*

		@Cache(name='cache-name', [ maxElementsInMemory=10, timeToLiveSeconds=60, timeToIdleSeconds=45 ... ] )
	
*	*Available Elements*
	
	*	**name** (String, *required*)
		
		Name of the cache. Note that "default" is a reserved name.

	*	**maxElementsInMemory** (int, *default: 3000*)
		
		The maximum number of elements in memory, before they are evicted.

	*	**overflowToDisk** (boolean, *default: false*)
		
		Whether to use the disk store

	*	**eternal** (boolean, *default: false*)
		
		Whether the elements in the cache are eternal, i.e. never expire.

	*	**timeToLiveSeconds** (int, *default: 120*)
		
		Whether the elements in the cache are eternal, i.e. never expire.
	
	*	**timeToIdleSeconds** (int, *default: 100*)
		
		Default amount of time to live for an element from its last accessed or modified date.
	
	*	**diskPersistent** (boolean, *default: false*)
	
		Whether to persist the cache to disk between JVM restarts.

	*	**diskExpiryThreadIntervalSeconds** (long, *default: 120*)
		
		How often to run the disk store expiry thread. A large number of 120 seconds plus is recommended.

[TwigKit]: http://www.twigkit.com/
[Guice]: http://code.google.com/p/google-guice/
[AOP]: http://code.google.com/p/google-guice/wiki/AOP
[Ehcache]: http://ehcache.org/
[Maven]: http://maven.apache.org/
[Annotations]: http://java.sun.com/j2se/1.5.0/docs/guide/language/annotations.html
[ServiceLoader]: http://java.sun.com/javase/6/docs/api/java/util/ServiceLoader.html
[MetaInfServices]: http://java.sun.com/j2se/1.3/docs/guide/jar/jar.html#The%20META-INF%20directory