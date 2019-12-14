
package socket;

import java.util.prefs.Preferences;

public class Packet {
    
     private static final Preferences pref = Preferences.userNodeForPackage(Packet.class);
     
         public static void changeNumber(int num){
              pref.putInt("number",num);
          
    }
         
         public static int getNumber(){
             return pref.getInt("number",0);
         }
}
