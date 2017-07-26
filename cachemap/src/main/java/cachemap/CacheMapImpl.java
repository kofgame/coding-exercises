package cachemap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 25.05.2017.
 */
public class CacheMapImpl extends HashMap implements CacheMap  {
    private long timeToLive;
    private Map<Object, Long> expirations;

    public CacheMapImpl(long timeToLive) {
        super();
        expirations = new HashMap<Object, Long>();
        this.timeToLive = timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
    public long getTimeToLive() {
        return timeToLive;
    }

    public Object put(Object key, Object value) {
        this.expirations.put(key, now() + getTimeToLive());
        return super.put(key, value);
    }

    public void clearExpired() {
        for (Entry<Object, Long> entry : this.expirations.entrySet()) {
            if (isExpired(entry.getValue())) {
                this.remove(entry.getKey());
            }
        }
    }

    public void clear() {
        super.clear();
        this.expirations.clear();
    }

    public boolean containsKey(Object key) {
        this.removeIfExpired(key);
        return super.containsKey(key);
    }
    public boolean containsValue(Object value) {
        this.clearExpired();
        return super.containsValue(value);
    }

    public Object get(Object key) {
        this.removeIfExpired(key);
        return super.get(key);
    }

    public boolean isEmpty() {
        this.clearExpired();
        return super.isEmpty();
    }

    public Object remove(Object key) {
        expirations.remove(key);
        return super.remove(key);
    }

    public int size() {
        this.clearExpired();
        return super.size();
    }

    private boolean isExpired(Long expirationTime) {
        if (expirationTime == null) {
            return false;
        }
        return expirationTime >= 0L && now() >= expirationTime;
    }

    private long now() {
//        return System.currentTimeMillis();
        return Clock.getTime();
    }

    private void removeIfExpired(Object key) {
        Long expirationTime = this.expirations.get(key);
        if(this.isExpired(expirationTime)) {
            this.remove(key);
        }
    }
}