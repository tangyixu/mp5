
import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

/**
 * Represents the mappings for a single category of items that should
 * be displayed.
 *
 * @author Catie Baker & Tiffany Tang
 *
 */
public class AACCategory implements AACPage {

  /** Properties of the categories. */
  String catName;
  AssociativeArray<String, String> storage;

  /**
   * Creates a new empty category with the given name.
   *
   * @param name the name of the category
   */
  public AACCategory(String name) {
    if (name.isEmpty()) {
      this.catName = "";
    } else {
      this.catName = name;
    } // if-else
    this.storage = new AssociativeArray<>();
  } // AACCategory (String)

  /**
   * Adds the image location, text pairing to the category.
   *
   * @param imageLoc the location of the image
   * @param text     the text that image should speak
   */
  public void addItem(String imageLoc, String text) {
    this.storage.set(imageLoc, text);
  } // addItem (String, String)

  /**
   * Returns an array of all the images in the category.
   *
   * @return the array of image locations; if there are no images,
   *         it should return an empty array
   */
  public String[] getImageLocs() {
    int number = this.storage.size();
    String[] result = new String[number];
    for (int n = 0; n < number; n++) {
      result[n] = this.storage.getPair()[n].key;
    } // for
    return result;
  } // getImageLocs()

  /**
   * Returns the name of the category.
   *
   * @return the name of the category
   */
  public String getCategory() {
    return this.catName;
  } // getCategory()

  /**
   * Returns the text associated with the given image in this category.
   *
   * @param imageLoc the location of the image
   * @return the text associated with the image
   * @throws NoSuchElementException if the image provided is not in the current
   *                                category.
   */
  public String select(String imageLoc) throws NoSuchElementException {
    try {
      return this.storage.get(imageLoc);
    } catch (NullKeyException e) {
      // Do nothing
    } catch (KeyNotFoundException e) {
      throw new NoSuchElementException("There is no such element.");
    } // try-catch
    return null;
  } // select (String)

  /**
   * Determines if the provided images is stored in the category
   *
   * @param imageLoc the location of the category
   * @return true if it is in the category, false otherwise
   */
  public boolean hasImage(String imageLoc) {
    return this.storage.hasKey(imageLoc);
  } // hasImaghe（String)
}
