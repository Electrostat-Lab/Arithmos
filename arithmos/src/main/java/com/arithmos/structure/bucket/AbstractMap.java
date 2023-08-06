package com.arithmos.structure.bucket;

import com.arithmos.util.HashingUtils;
import java.util.Map;
import java.util.Objects;

/**
 * Houses the basic algorithms for a BucketCollection.
 *
 * @author pavl_g
 * @param <K>
 * @param <V>
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

   protected Bucket<K, V>[] buckets;
   protected int size = Const.INITIAL_CAPACITY;

   public AbstractMap() {

   }

   @Override
   @SuppressWarnings("unchecked")
   public V put(K key, V value) {
      /* sanity checks */
      if (buckets == null) {
         buckets = new Bucket[size];
         insertNewBucket(key, value);
         return null;
      }

//      if (exists) {
//
//      }


      return null;
   }

   /**
    * Only inserts new buckets replacing old ones if they are different,
    * if they are the same buckets.
    *
    * @param key the key of the bucket to calculate the index
    * @param value the value to save in the bucket
    */
   protected void insertNewBucket(K key, V value) {
      int index = HashingUtils.andCompress(key.hashCode(), size);
      if (buckets[index] != null &&
              buckets[index].hashCode() == Objects.hash(key, value, index)) {
         return;
      }
      buckets[index] = new Bucket<>(key, value, index);
   }

   /**
    * Inserts
    *
    * @param key
    * @param value
    */
   protected void insertBucket(K key, V value) {
      int index = HashingUtils.andCompress(key.hashCode(), size);
      /* Check for a collision and apply a collision criteria */
      insertNewBucket(key, value);
      if (buckets[index].key != key &&
          buckets[index].hashCode() == Objects.hash(key, value, index)) {

      }
   }
}
