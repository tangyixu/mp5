
package edu.grinnell.csc207.util;

import java.util.Arrays;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Tiffany Tang
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) new KVPair[DEFAULT_CAPACITY];
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> copy = new AssociativeArray<>();
    copy.size = this.size;
    copy.pairs = Arrays.copyOf(this.pairs, this.pairs.length);
    K key = null;
    V value = null;
    for (int n = 0; n < this.size; n++) {
      key = this.pairs[n].key;
      value = this.pairs[n].val;
      copy.pairs[n] = new KVPair<>(key, value);
    } // for loop
    return copy;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String result = "";
    for (int n = 0; n < this.size; n++) {
      result += this.pairs[n].toString() + "\n ";
    } // for loop
    return result;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @param keys
   *              The key whose value we are seeting.
   * @param value
   *              The value of that key.
   */
  public void set(K keys, V value) {
    try {
      this.pairs[find(keys)].val = value;
    } catch (Exception e) {
      if (this.size == this.pairs.length) {
        this.expand();
      } // if
      this.pairs[size] = new KVPair<>(keys, value);
      size++;
    } // try-catch
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key A key
   *
   * @return The corresponding value
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not appear in the
   *                              associative array.
   */
  public V get(K key) throws NullKeyException, KeyNotFoundException {
    if (key.equals(null)) {
      throw new NullKeyException("A null key is provided.");
    } // if
    return this.pairs[find(key)].val;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key, since it cannot appear.
   *
   * @param key
   *            The key we're looking for.
   *
   * @return true if the key appears and false otherwise.
   */
  public boolean hasKey(K key) {
    if (key == null || this.size == 0) {
      return false;
    } else {
      for (KVPair<K, V> x : this.pairs) {
        if (x != null && x.key.equals(key)) {
          return true;
        } // if
      } // for
    } // if-else
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   *
   * @param key The key to remove.
   */
  public void remove(K key) {
    try {
      KVPair<K, V> hold = new KVPair<K, V>(this.pairs[this.size - 1].key,
          this.pairs[this.size - 1].val);
      this.pairs[find(key)] = hold;
      this.pairs[this.size - 1] = null;
      this.size--;
    } catch (Exception e) {
    } // try-catch
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   *
   * @return The number of key/value pairs in the array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   *
   * @param keyVal
   *               The key of the entry.
   *
   * @return
   *         The index of the key, if found.
   *
   * @throws KeyNotFoundException
   *                              If the key does not appear in the associative
   *                              array.
   */
  int find(K keyVal) throws KeyNotFoundException {
    for (int n = 0; n < this.size; n++) {
      if (this.pairs[n] != null && this.pairs[n].key.equals(keyVal)) {
        return n;
      } // if
    } // for loop
    throw new KeyNotFoundException("The key is not found.");
  } // find(K)

  /**
   * Return the pairs array for the associative array.
   *
   * @return the pairs as an KVPair array
   */
  public KVPair<K, V>[] getPair() {
    return this.pairs;
  } // getPair()
} // class AssociativeArray