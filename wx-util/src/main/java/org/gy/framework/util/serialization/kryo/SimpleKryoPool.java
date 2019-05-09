package org.gy.framework.util.serialization.kryo;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class SimpleKryoPool implements KryoPool {

    private KryoPool    delegate;
    private Queue<Kryo> queue;

    public SimpleKryoPool(int size) {
        queue = new ArrayBlockingQueue<Kryo>(size);
        delegate = new KryoPool.Builder(new SimpleKryoFactory()).queue(queue).softReferences().build();
    }

    public int size() {
        return queue.size();
    }

    public void clear() {
        queue.clear();
    }

    @Override
    public Kryo borrow() {
        return delegate.borrow();
    }

    @Override
    public void release(Kryo kryo) {
        delegate.release(kryo);
    }

    @Override
    public <T> T run(KryoCallback<T> callback) {
        return delegate.run(callback);
    }

    public class SimpleKryoFactory implements KryoFactory {
        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            return kryo;
        }

    }

}
