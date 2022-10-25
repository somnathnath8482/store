package com.artix.store.Helper;

import com.hbisoft.pickit.PickiTCallbacks;

import java.util.ArrayList;

public class picItCall implements PickiTCallbacks {
    OnselectFile onselectFile;

    public picItCall(OnselectFile onselectFile) {
        this.onselectFile = onselectFile;
    }

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {

        if (wasSuccessful && path!=null && onselectFile!=null){
            onselectFile.OnSelect(path);
        }
    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }
}
