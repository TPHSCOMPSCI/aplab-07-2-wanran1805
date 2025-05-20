import java.awt.Color;
import java.util.ArrayList;
import javax.xml.stream.events.ProcessingInstruction;

public class Steganography {
    
/** * Clear the lower (rightmost) two bits in a pixel. */ 
public static void clearLow( Pixel p ) 
{ 
    Color np = new Color((int) p.getRed()/4 *4,(int) p.getGreen()/4 *4,(int) p.getBlue()/4*4);

} 
public static Picture testClearLow(Picture a) {
    Picture copy = new Picture(a);
    Pixel[][] array = copy.getPixels2D();  
    for (int r = 0; r < array.length; r++)
{ 
        for (int c = 0; c < array[0].length; c++)
        { 
            clearLow(array[r][c]);
        } 
} 
    return copy;
 }
 /** o * Sets the highest two bits of each pixel’s colors * to the lowest two bits of each pixel’s color s */ 
public static Picture revealPicture(Picture hidden) 
{ 
    Picture copy = new Picture(hidden);
    Pixel[][] pixels = copy.getPixels2D(); 
    Pixel[][] source = hidden.getPixels2D(); 
    for (int r = 0; r < pixels.length; r++)
{ 
        for (int c = 0; c < pixels[0].length; c++)
        { 
            Color col = source[r][c].getColor(); 
            int rgb = col.getRGB();
            copy.setBasicPixel(c,r,rgb);
        } 
} 
    return copy;
}
public static void setLow(Pixel p, Color c) 
{
    Color np = new Color((int) p.getRed()/4 *4,(int) p.getGreen()/4 *4,(int) p.getBlue()/4*4);
    p.setColor(np); 
}
// returns a new Picture object with the lowest two bits of each 
// pixel  set to the highest two bits of the provided color. 
public static Picture testSetLow(Picture p, Color c) 
{
    Picture copy = new Picture(p);
    Pixel[][] array = copy.getPixels2D();  
    for (int r = 0; r < array.length; r++)
{ 
        for (int k = 0; k < array[0].length; k++)
        { 
            setLow(array[r][k],c);
        } 
} 
    return copy;
}

/** * Determines whether secret can be hidden in source, 
 * which is * true if source and secret are the same dimension ns. 
 * * @param source is not null * 
 * @param secret is not nul ll * 
 * @return true if secret can be hidden in source, false otherwise. 
 * */ 
public static boolean canHide(Picture source, Picture secret) {
    return source.getHeight() == secret.getHeight() && source.getWidth() == secret.getWidth();
}

/** * Creates a new Picture with data from secret hidden in
 *  data from source * @param source is not null * 
 * @param se ecret is not null * @return combined Picture with secret 
 * hidden in source * precondition: source is same width and 
 * height as secret */ 
public static Picture hidePicture(Picture source, Picture secret, int a, int b) {
    Picture p = new Picture(source);
    Pixel[][] secret1 = secret.getPixels2D(); 
    Pixel[][] source1 = p.getPixels2D(); 
    for (int r = 0; r < secret1.length; r++)
{ 
        for (int c = 0; c < secret1[0].length; c++)
        { 
            setLow(source1[r + a][c + b], secret1[r][c].getColor());
        } 
} 
    return p;
}


public static boolean isSame(Picture a, Picture b) {
    if(canHide(a,b)){
        Pixel[][] source1 = a.getPixels2D(); 
        Pixel[][] source2 = b.getPixels2D(); 
        for (int i = 0; i < a.getHeight(); i++)
        { 
            for (int j = 0; j < a.getWidth(); j++)
            { 
                if(source1[i][j].getColor().getRGB() != source2[i][j].getColor().getRGB())
                {
                    return false;
                }
            } 
        } 
    return true;
    }
    
return false;
}

public static ArrayList<Point> findDifferences(Picture a, Picture b) 
{
    ArrayList<Point> points = new ArrayList<Point>();
    if(canHide(a,b))
    {
        Pixel[][] source1 = a.getPixels2D(); 
        Pixel[][] source2 = b.getPixels2D(); 
        for (int i = 0; i < a.getHeight(); i++)
        { 
            for (int j = 0; j < a.getWidth(); j++)
            { 
                if(source1[i][j].getColor().getRGB() != source2[i][j].getColor().getRGB())
                {
                    points.add(new Point(i,j));
                }
            } 
        } 
    }
return points;
}

public static Picture showDifferentArea(Picture a, ArrayList<Point> p) 
{
    int maxX = p.get(0).getX();
    int minX = p.get(0).getX();
    int maxY = p.get(0).getY();
    int minY = p.get(0).getY();
    for(int i = 1; i < p.size(); i++)
    {
        if(p.get(i).getX() > maxX) 
        {
            maxX = p.get(i).getX();
        }
        else if (p.get(i).getX() < maxX) 
        {
            minX = p.get(i).getX();
        }
        if(p.get(i).getY() > maxY) 
        {
            maxY = p.get(i).getY();
        }
        else if (p.get(i).getY() < maxY) 
        {
            minY = p.get(i).getY();
        }
    }
    Picture copy = new Picture(a);
    Pixel[][] array = copy.getPixels2D();

    for (int i = minX; i < maxX; i++)
    { 
        array[i][minY].setColor(new Color(255,0,0));
        array[i][maxY].setColor(new Color(255,0,0));
    } 
    for (int i = minY; i < maxY; i++)
    { 
        array[i][minX].setColor(new Color(255,0,0));
        array[i][maxX].setColor(new Color(255,0,0));
    } 
    return copy;
}
/** * Takes a string consisting of letters and spaces and  
 * * encodes the string into an arraylist of integers. 
 * * The integers are 1-26 for A-Z, 27 for space, and 0 for end of 
 * * string. The arraylist of integers is returned. 
 * * @p param s string consisting of letters and spaces version of s 
 * * @return ArrayList containing integer encoding of uppercase 
 * *  version of s
 * */ 


public static ArrayList<Integer> encodeString(String s) 
{ 
    s = s.toUpperCase(); 
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    ArrayList<Integer> result = new ArrayList<Integer>(); 
    for (int i = 0; i < s.length(); i++) 
    { 
        if (s.substring(i,i+1).equals(" ")) 
        { 
            result.add(27); 
        } 
        else 
        { 
            result.add(alpha.indexOf(s.substring(i,i+1))+1); 
        }
    } 
    result.add(0); 
    return result; 
}
/** * Returns the string represented by the codes arraylist t. 
 * * 1-26 = A-Z, 27 = space 
 * * @param codes encoded string 
 * * @return decoded string 
 * 
 * */ 
public static String decodeString(ArrayList<Integer> codes) 
{
     String result= ""; 
     String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
     for (int i = 0; i < codes.size(); i++) 
     { 
        if (codes.get(i) == 27) 
        { 
            result = result + " "; 
        } 
        else 
        { 
            result = result + alpha.substring(codes.get(i)-1, codes.get(i));
        } 
    } 
    return result; 
} 


/** * Given a number from 0 to 63, creates and returns a 3--element 
 * * int array consisting of the integers repre esenting the 
 * * pairs of bits in the number from right to left. 
 * * @param num number to be broken up 
 * * @return bit p pairs in number 
 * */ 
private static int[] getBitPairs(int num) 
{ 
    int[] bits = new int[3];   
    int code = num;   
    for (int i = 0; i < 3; i++)
    { 
        bits[i] = code % 4; 
        code = code / 4; 
    } 
    
    return bits; 
} 

/** * Hide a string (must be only capital letters and spaces) in a picture. 
 * * The string always starts in the upper left corner. 
 * * @param source picture to hide string in 
 * * @param s string to hide 
 * * @return picture with hid dden string 
 * */ 
public static void hideText(Picture source, String s) 
{
    Pixel[][] p = source.getPixels2D();
    ArrayList<Integer> index = encodeString(s);
    for (int i = 0; i < index.size(); i++)
    { 
        int red = p[0][i].getRed();
        int green = p[0][i].getGreen();
        int blue = p[0][i].getBlue();
        int[] bits = getBitPairs(index.get(i)); 
        red = red/ 4 * 4 + bits[2];
        blue = blue/ 4 * 4 + bits[2];
        green = green/ 4 * 4 + bits[2];
        p[0][i].setColor(new Color(red,green,blue));
    } 
}
/** * Returns a string hidden in the picture 
 * * @param source picture with hidden string 
 * * @return revealed string 
 * */ 
public static String revealText(Picture source) {
    Pixel[][] p = source.getPixels2D();
    ArrayList<Integer> text = new ArrayList<Integer>();
    loop:
    for (int i = 0; i < p.length; i++)
        { 
            for (int j = 0; j < p[0].length; j++)
            { 
                int code = p[i][j].getRed() % 4 * 16 + p[i][j].getGreen() % 4 * 16 + p[i][j].getBlue() % 4 * 16;
                if(code == 0)
                {
                    break loop;
                }
                else
                {
                }
                text.add(code);
            } 
        } 
    String a = decodeString(text);
    return a;
}




public static void main(String[] args) 
{
    Picture beach = new Picture ("beach.jpg"); 
    beach.explore(); 
    Picture beach2 = new Picture ("beach.jpg"); 
    beach2.explore(); 
    Picture copy = testClearLow(beach); 
    copy.explore(); 
    Picture copy2 = testSetLow(beach2, Color.PINK); 
    copy2.explore(); 
    Picture copy3 = revealPicture(copy2); 
    copy3.explore(); 
    Picture robot = new Picture("robot.jpg"); 
    Picture flower1 = new Picture("flower1.jpg");
    beach.explore();
    // these lines hide 2 pictures  
    Picture hidden1 = hidePicture(beach, robot, 65, 208); 
    Picture hidden2 = hidePicture(hidden1, flower1, 280, 110); 
    hidden2.explore(); Picture unhidden = revealPicture(hidden2); 
    unhidden.explore(); 


    Picture swan = new Picture("swan.jpg"); 
    Picture swan2 = new Picture("swan.jpg"); 
    System.out.println("Swan and swan2 ar re the same: " + isSame(swan, swan2)); 
    swan = testClearLow(swan); 
    System.out.println("Swan and swan2 are the same (af fter clearLow run on swan): " + isSame(swan, swan2)); 
    Picture arch = new Picture("arch.jpg"); 
    Picture arch2 = new Picture("arch.jpg"); 
    Picture koala = new Picture("koala.jpg"); 
    Picture robot1 = new Picture("robot.jpg"); 
    ArrayList<Point> pointList = findDifferences(arch, arch2);
    System.out.println("PointList after comparing two identical pictures " +
     "has a size of " + pointList.size()); 
    pointList = findDifferences(arch, koala); 
    System.out.println("PointList after comparing two different sized pictures "  + 
    "has a size of " + pointList.size()); 
    arch2 = hidePicture(arch, robot1, 65, 102); 
    pointList = findDifferences(arch, arch2);
    System.out.println("Pointlist after hiding a picture has a size of" + 
    pointList.size());
    arch.show();
    arch2.show(); 

    Picture hall = new Picture("femaleLionAndHall.jpg"); 
    Picture robot2 = new Picture("robot.jpg"); 
    Picture flower2 = new Picture("flower1.jpg"); 
    // hide pictures 
    Picture hall2 = hidePicture(hall, robot2, 50, 300); 
    Picture hall3 = hidePicture(hall2, flower2, 115, 275); 
    hall3.explore(); 
    if(!isSame(hall, hall3)) 
    { 
        Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
        hall4.show(); 
        Picture unhiddenHall3 = revealPicture(hall3); 
        unhiddenHall3.show(); 
    }
    Picture hideT = new Picture("femaleLionAndHall.jpg"); 
    hideText(hideT,"HIDDEN TEXT");
    hideT.explore();
    System.out.println(revealText(hideT)); 




 }
}
