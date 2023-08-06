package com.arithmos.structure.bucket;

import java.util.Map;
import java.util.Objects;

class Bucket<K, V> implements Map.Entry<K, V> {

    protected K key;
    protected V value;
    protected int index;

    public Bucket(K key, V value, int index) {
        this.key = key;
        this.value = value;
        this.index = index;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        return this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket<?, ?> bucket = (Bucket<?, ?>) o;
        return index == bucket.index && Objects.equals(key, bucket.key) && Objects.equals(value, bucket.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, index);
    }
}
