import org.apache.commons.collections.set.TransformedSet;
import org.checkerframework.checker.units.qual.K;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{


    private class BSTNode{
        K key;
        V value;
        BSTNode left, right;
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private BSTNode root;
    private int size;
    private Set<K> set = new HashSet<>();


    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
        set.add(key);
    }
    private BSTNode put(BSTNode p, K key, V value) {
        if (p == null) {
            size++;
            return new BSTNode(key, value);
        }
        if (p.key.compareTo(key) == 0) {
            p.value = value;
        }
        else if (p.key.compareTo(key) > 0) {
            p.left = put(p.left, key, value);
        }
        else {
            p.right = put(p.right, key, value);
        }
        return p;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }
    private V get(BSTNode p, K key) {
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            return p.value;
        }
        else if (p.key.compareTo(key) > 0) {
            return get(p.left, key);
        }
        return get(p.right, key);
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }
    private boolean containsKey(BSTNode p, K key) {
        if (p == null) {
            return false;
        }
        if (p.key.compareTo(key) == 0) {
            return true;
        }
        else if (p.key.compareTo(key) > 0) {
            return containsKey(p.left, key);
        }
        return containsKey(p.right, key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
        set.clear();
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return new TreeSet<>(set);
    }


    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V v = get(root, key);
        size --;
        root = remove(root, key);
        set.remove(key);
        return v;
    }
    private BSTNode remove(BSTNode p, K key) {
        if (p.key.compareTo(key) > 0) {
            p.left = remove(p.left, key);
        }
        else if (p.key.compareTo(key) < 0) {
            p.right = remove(p.right, key);
        } else {
            if (p.left == null && p.right == null) {
                return  null;
            }
            else if (p.left == null) {
                return p.right;
            }
            else if (p.right == null) {
                return p.left;
            } else {
                BSTNode t = min(p.right);
                p.key = t.key;
                p.value = t.value;
                p.right = deleteMin(p.right);
            }
        }
        return p;
    }

    private BSTNode min(BSTNode p) {
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }
    private BSTNode deleteMin(BSTNode p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = deleteMin(p.left);
        return p;
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
