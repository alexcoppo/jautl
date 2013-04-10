/*
    Copyright (c) 2000-2013 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 */
public class LookAndFeelManager {
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR_CLASS_NOT_FOUND = 1;
    public static final int RESULT_ERROR_INSTANTIATION = 2;
    public static final int RESULT_ERROR_ILLEGAL_ACCESS = 3;
    public static final int RESULT_ERROR_UNSUPPORTED_LOOK_AND_FEEL = 4;
    public static final int RESULT_UNKNOWN_LOOK_AND_FEEL = 5;
    
    public static List<String> enumInstalledLookAndFeels() {
        List<String> names = new ArrayList<String>();
        
        for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
            names.add(lafi.getName());
        }
        
        return names;
    }
    
    public static List<String> enumInstalledLookAndFeelClassNames() {
        List<String> classNames = new ArrayList<String>();
        
        for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
            classNames.add(lafi.getClassName());
        }
        
        return classNames;
    }
    
    public static List<LookAndFeelInfo> enumInstalledLookAndFeelInfo() {
        List<LookAndFeelInfo> lafis = new ArrayList<LookAndFeelInfo>();
        
        for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
            lafis.add(lafi);
        }
        
        return lafis;
    }

    public static int setLookAndFeelByClassName(String className) {
        try {
            UIManager.setLookAndFeel(className);
            return RESULT_OK;
        } catch (ClassNotFoundException ex) {
            return RESULT_ERROR_CLASS_NOT_FOUND;
        } catch (InstantiationException ex) {
            return RESULT_ERROR_INSTANTIATION;
        } catch (IllegalAccessException ex) {
            return RESULT_ERROR_ILLEGAL_ACCESS;
        } catch (UnsupportedLookAndFeelException ex) {
            return RESULT_ERROR_UNSUPPORTED_LOOK_AND_FEEL;
        }
    }
    
    public static int setLookAndFeelByName(String lfName) {
        for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
            if (lfName.equals(lafi.getName()))
                 return setLookAndFeelByClassName(lafi.getClassName());
        }
        
        return RESULT_UNKNOWN_LOOK_AND_FEEL;
    }
    
    public static int setNimbusLF() {
        return setLookAndFeelByName("Nimbus");
    }
    
    public static int setSystemLF() {
        return setLookAndFeelByClassName(UIManager.getSystemLookAndFeelClassName());
    }
    
    public static int setCrossPlatformLF() {
        return setLookAndFeelByClassName(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    
    public static void setLF(String name) {
        int result = LookAndFeelManager.setLookAndFeelByName(name);
        
        if (result != LookAndFeelManager.RESULT_OK)
            result = LookAndFeelManager.setSystemLF();
        
        if (result != LookAndFeelManager.RESULT_OK)
            LookAndFeelManager.setCrossPlatformLF();
    }
}
