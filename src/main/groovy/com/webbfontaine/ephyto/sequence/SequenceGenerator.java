package com.webbfontaine.ephyto.sequence;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;



@Component
public class SequenceGenerator implements DisposableBean {

    private KeyRepository keyRepository;

    private final LoadingCache<Integer, AtomicLong> sequenceCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, AtomicLong>() {
        @Override
        public AtomicLong load(Integer year) {
            return new AtomicLong(keyRepository.findLatestTrNumber(year));
        }
    });

    public long nextRequestNumber(int year) {
        return sequenceCache.getUnchecked(year).incrementAndGet();
    }

    @Override
    public void destroy() throws Exception {
        sequenceCache.cleanUp();
    }

    @Autowired
    public void setKeyRepository(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }
}
