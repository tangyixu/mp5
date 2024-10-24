package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import edu.grinnell.csc207.util.KVPair;

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

  private static final Writer FILE = null;

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
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
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
    AssociativeArray<K,V> copy = new AssociativeArray<>();
    copy.size = this.size;
    K key = null;
    V value = null;
    for(int n = 0; n < this.size; n++)
    { 
      key = this.pairs[n].key;
      value = this.pairs[n].val;
      copy.pairs[n] = new KVPair<>(key,value);

    }//for loop
    return copy;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String result = "{";
    for(int n = 0; n < this.size - 1; n++)
    {
      result += this.pairs[n].toString()+", ";
    }//for loop

    result += this.pairs[this.size - 1].toString() + "}";
    System.out.println(result);
    return result;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @param key
   *   The key whose value we are seeting.
   * @param value
   *   The value of that key.
   *
   * @throws NullKeyException
   *   If the client provides a null key.
   */
  public void set(K key, V value) throws NullKeyException {
    PrintWriter pen = new PrintWriter(System.out,true);
    if(value.equals(null))
     {
      throw new NullKeyException("A null key is.");
     }//if
    if(hasKey(key))
    { 
      try {
        this.pairs[find(key)].val = value;
      } catch (KeyNotFoundException e) {
        pen.println("Key is not found.");
      }//try-catch
    }//if
    else{
      if (this.size == 0){
        this.size = 1;
        this.expand();
        this.pairs[0] = new KVPair<K,V>();
        this.pairs[0].key = key;
        this.pairs[0].val = value;
      }//if there is no pairs
      else
      {
       for(int n = 0; n < this.size-1; n++){
        if(this.pairs[n].key.equals(null))
        {
          this.pairs[n] = new KVPair<K,V>();
          this.pairs[n].key = key;
          this.pairs[n].val = value;
          return;
        }//if
       }//for loop
      }//else
    }//else
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key
   *   A key
   *
   * @return
   *   The corresponding value
   *
   * @throws KeyNotFoundException
   *   when the key is null or does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    if(key.equals(null))
     {
      throw new KeyNotFoundException("A null key is provided.");
     }//if
    if(!this.hasKey(key))
     {
      throw new KeyNotFoundException("Key not Found.");
     }//if
      return this.pairs[find(key)].val; 
    } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key, since it cannot appear.
   *
   * @param key
   *   The key we're looking for.
   *
   * @return true if the key appears and false otherwise.
   */
  public boolean hasKey(K key) {
    if(key.equals(null)||this.size == 0)
    {
      return false;
    }//if
    else
    {
      for(KVPair<K,V> x : this.pairs){
        while(x!=null)
        {
          if(x.key.equals(key))
        {
          return true;
        }//if
        }//while  
      }//for loop
      return false;
    }//else
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   *
   * @param key
   *   The key to remove.
   */
  public void remove(K key) {
    PrintWriter pen = new PrintWriter(System.out,true);
    if(this.hasKey(key))
    {
      try {
        this.pairs[find(key)] = this.pairs[this.size-1];
        this.pairs[this.size-1] = null;
      } catch (KeyNotFoundException e) {
        pen.println("The Key is not Found:" + e);
      }//try_catch
    }//if
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
   * @param key
   *   The key of the entry.
   *
   * @return
   *   The index of the key, if found.
   *
   * @throws KeyNotFoundException
   *   If the key does not appear in the associative array.
   */
  int find(K key) throws KeyNotFoundException {
    for(int n = 0; n < this.size; n++){
      if(this.pairs[n].key.equals(key))
      {
       return n;
      }//if
    }//for loop
    throw new KeyNotFoundException("The key is not found.");
  } // find(K)

  /**
   * Return the pairs of the associative array
   * 
   * @return pairs
   */
  public KVPair<K,V>[] getpairs(){
    
    return this.pairs;

  }//getpairs

} // class AssociativeArray
