package twigkit.cachalot;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.sf.ehcache.CacheManager;

import java.net.URL;

/**
 * @author mr.olafsson
 */
@Singleton
public class CacheManagerProvider implements Provider<CacheManager> {

    private final CacheManager cacheManager;

    public CacheManagerProvider() {
        cacheManager = CacheManager.newInstance();
    }

    @Override
    public CacheManager get() {
        return cacheManager;
    }
}
