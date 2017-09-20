import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ColorBlind{
  public static void main(String args[])throws IOException{
	  
    BufferedImage image = null;
    InputStream pic = null;
    File file = null;

    //read image in from file
    try{
    	pic = ColorBlind.class.getResourceAsStream("/IMG_2647.JPG");
      //file = new File("/Users/samibialozynski/Desktop/Unknown.jpeg");
    	image = ImageIO.read(pic);
    }catch(IOException e){
      System.out.println(e);
    }

    //get width and height of the image
    int width = image.getWidth();
    int height = image.getHeight();

    //convert to red green color blind
    for(int y = 0; y < height; y++)
    {
      for(int x = 0; x < width; x++)
      {
        int pixel = image.getRGB(x,y);
        int red = (pixel >> 16)&0xff;
        int green = (pixel >> 8)&0xff;
        int blue = pixel&0xff;
        
        //calculate the new pixel colors 
        	//found the decimals of red green color blindness or deuteranopia 
        	//https://www.reddit.com/r/gamedev/comments/2i9edg/code_to_create_filters_for_colorblind/ 
        int newRed = (int)(0.625 * red + 0.375 * green + 0.0  * blue);
        int newGreen = (int)(0.7* red + 0.3 * green + 0.0 * blue);
        int newBlue = (int)(0.0 * red + 0.3 * green + 0.7 * blue);

        //Compare the old pixel colors to the new pixel values
        if(newRed > 255){
          red = 255;
        } else {
          red = newRed;
        }

        if(newGreen > 255){
          green = 255;
        } else {
          green = newGreen;
        }

        if(newBlue > 255){
          blue = 255;
        } else {
          blue = newBlue;
        }
     
        //set new RGB pixel value
        pixel = (red << 16) | (green << 8) | blue;
        
        image.setRGB(x, y, pixel);
        }
      }

    //write image to a new picture file
    try{
      file = new File("output.jpg");
      ImageIO.write(image, "jpg", file);
    }catch(IOException e){
      System.out.println(e);
      }
    }
}
