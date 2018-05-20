/* 
 * Copyright (C) 2018 Santiago Rincon Martinez <rincon.santi@gmail.com>
 * Based on work by 2016 David Pérez Cabrera <dperezcabrera@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package exercises.utils;

import com.jcraft.jsch.JSchException;
import exercises.utils.SSHConnector;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
//import net.jcip.annotations.ThreadSafe;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 * @since 1.0.0
 */
//@ThreadSafe
public enum ImageManager {
    
    INSTANCE;
    
    
    public static final String IMAGES_PATH = "./resources/images/";
    private final transient Map<String, Image> images = new HashMap<>();

    
    private ImageManager() {
    }
    
    public synchronized Image getImage(String imageFile) {
        Image image = images.get(imageFile);
        if (image == null) {
            try {
                String [] aux=imageFile.split("/");
                String auxSt="";
                for (int i=0; i<aux.length-2; i++) {
                    auxSt+=aux[i];
                    auxSt+="/";
                }
                if (aux.length>1) auxSt+=aux[aux.length-2];
                try {
                    SSHConnector.getFile(auxSt, aux[aux.length-1]);
                } catch (JSchException ex) {
                    Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                image = ImageIO.read(new File(imageFile));
                images.put(imageFile, image);
            } catch (IOException ex) {
            }
        }
        return image;
    }
}
