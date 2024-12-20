
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KVPair;

/**
 * Creates a set of mappings of an AAC that has two levels,
 * one for categories and then within each category, it has
 * images that have associated text to be spoken. This class
 * provides the methods for interacting with the categories
 * and updating the set of images that would be shown and handling
 * an interactions.
 *
 * @author Catie Baker & Tiffany Tang
 *
 */
public class AACMappings implements AACPage {

  /**
   * The category that it is currently in, and the associative array that combine
   * all the categories.
   */
  public final static AACCategory DEFAULT_CATEGORY = new AACCategory("Home");
  AACCategory currentCat;
  AssociativeArray<String, AACCategory> allCat;

  /**
   * Creates a set of mappings for the AAC based on the provided file.
   *
   * @param filename the name of the file that stores the mapping information
   */
  public AACMappings(String filename) {
    if (filename.equals(null)) {
      this.currentCat = DEFAULT_CATEGORY;
    } else {
      this.currentCat = new AACCategory(filename);
    } // if-else
    this.allCat = new AssociativeArray<>();
  } // AACMappings

  /**
   * Given the image location selected, it determines the action to be taken.
   * This can be updating the information that should be displayed or returning
   * text to be spoken. If the image provided is a category, it updates the
   * AAC's current category to be the category associated with that image and
   * returns the empty string. If the AAC is currently in a category and the
   * image provided is in that category, it returns the text to be spoken.
   *
   * @param imageLoc the location where the image is stored
   * @return if there is text to be spoken, it returns that information, otherwise
   *         it returns the empty string
   * @throws NoSuchElementException if the image provided is not in the current
   *                                category
   */
  public String select(String imageLoc) throws NoSuchElementException {
    if (this.allCat.hasKey(imageLoc)) {
      this.currentCat = new AACCategory(imageLoc);
      return "";
    } else if (this.currentCat.hasImage(imageLoc)) {
      return currentCat.select(imageLoc);
    } // if-else
    throw new NoSuchElementException("Not in the current category.");
  } // select (String)

  /**
   * Provides an array of all the images in the current category.
   *
   * @return the array of images in the current category; if there are no images,
   *         it should return an empty array
   */
  public String[] getImageLocs() {
    return this.currentCat.getImageLocs();
  } // getImageLocs()

  /**
   * Resets the current category of the AAC back to the default
   * category
   */
  public void reset() {
    this.currentCat = DEFAULT_CATEGORY;
  } // rest()

  /**
   * Writes the ACC mappings stored to a file. The file is formatted as
   * the text location of the category followed by the text name of the
   * category and then one line per item in the category that starts with
   * > and then has the file name and text of that image
   *
   * for instance:
   * img/food/plate.png food
   * >img/food/icons8-french-fries-96.png french fries
   * >img/food/icons8-watermelon-96.png watermelon
   * img/clothing/hanger.png clothing
   * >img/clothing/collaredshirt.png collared shirt
   *
   * represents the file with two categories, food and clothing
   * and food has french fries and watermelon and clothing has a
   * collared shirt
   *
   * @param filename the name of the file to write the
   *                 AAC mapping to
   */
  public void writeToFile(String filename) {
    try {
      PrintWriter pen = new PrintWriter(filename);
      KVPair<String, AACCategory>[] mappings = allCat.getPair();
      for (int n = 0; n < this.allCat.size(); n++) {
        pen.println(mappings[n].toString());
        pen.println(">" + mappings[n].val.storage.toString());
      } // for
      pen.close();
    } catch (FileNotFoundException e) {
      // Do nothing
    } // try-catch
  } // writeToFile(String)

  /**
   * Adds the mapping to the current category (or the default category if
   * that is the current category)
   *
   * @param imageLoc the location of the image
   * @param text     the text associated with the image
   */
  public void addItem(String imageLoc, String text) {
    this.currentCat.addItem(imageLoc, text);
  } // addItem(String, String)

  /**
   * Gets the name of the current category
   *
   * @return returns the current category or the empty string if
   *         on the default category
   */
  public String getCategory() {
    if (this.currentCat.equals(DEFAULT_CATEGORY)) {
      return "";
    } else {
      return this.currentCat.getCategory();
    } // if-else
  } // getCategory()

  /**
   * Determines if the provided image is in the set of images that
   * can be displayed and false otherwise
   *
   * @param imageLoc the location of the category
   * @return true if it is in the set of images that
   *         can be displayed, false otherwise
   */
  public boolean hasImage(String imageLoc) {
    for (KVPair<String, AACCategory> x : allCat.getPair()) {
      x.val.hasImage(imageLoc);
    } // for
    return false;
  } // hasImage (String)
} // AACMappings Class