

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImgFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
        {
            return true;
        }
        
        if (getExtension(f) != null)
        {
            if (getExtension(f).equals("bmp") ||
                getExtension(f).equals("tiff")||
                getExtension(f).equals("tif") ||
                getExtension(f).equals("gif") ||
                getExtension(f).equals("jpg") ||
                getExtension(f).equals("jpeg")||
                getExtension(f).equals("png"))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "*.bmp, *.jpg, *.jpeg, *.png, *.gif, *.tif, *.tiff";
    }
    
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
