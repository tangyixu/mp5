import java.io.PrintWriter;
import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KVPair;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Tiffany Tang
 *
 */
public class AACCategory implements AACPage {

	
	String catName;
  AssociativeArray<String,String> arr = new AssociativeArray<>();

	/**
	 * Creates a new empty category with the given name
	 * @param name the name of the category
	 */
	public AACCategory(String name) {
    
		catName = name;

	}//AACCategory
	
	/**
	 * Adds the image location, text pairing to the category
	 * @param imageLoc the location of the image
	 * @param text the text that image should speak
	 */
	public void addItem(String imageLoc, String text) {
    
		PrintWriter pen = new PrintWriter(System.out);
			try {
				this.arr.set(imageLoc, text);
			} catch (NullKeyException e) {
				pen.println("Expections are caught\n");;
			}
		
	}//addItem

	/**
	 * Returns an array of all the images in the category
	 * @return the array of image locations; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		
		if(this.arr.getpairs().length == 0)
		{
			return null;
		}//if
		else
		{ 
			String[] result = new String[this.arr.getpairs().length];
			int n = 0;
			for (KVPair<String,String> x : this.arr.getpairs())
			{ 	
				if(!x.equals(null))
				{
					result[n] = this.arr.getpairs().toString();
					n++;
				}
			}
			return result;
		}//else
	}//getImageLocs

	/**
	 * Returns the name of the category
	 * @return the name of the category
	 */
	public String getCategory() {
		return catName;
	}//getCategory

	/**
	 * Returns the text associated with the given image in this category
	 * @param imageLoc the location of the image
	 * @return the text associated with the image
	 * @throws NoSuchElementException if the image provided is not in the current
	 * 		   category
	 */
	public String select(String imageLoc) {
		if(this.hasImage(imageLoc))
		{
      try {
				return this.arr.get(imageLoc);
			} catch (KeyNotFoundException e)
			{;}
		}
		else
		{
			throw new NoSuchElementException("Such image is not in the current category.");
		}
		return null;
	}//select

	/**
	 * Determines if the provided images is stored in the category
	 * @param imageLoc the location of the category
	 * @return true if it is in the category, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		if(this.arr.hasKey(imageLoc))
		 return true;
		else
		 return false;
	}
}
