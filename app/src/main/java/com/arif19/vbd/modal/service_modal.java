package com.arif19.vbd.modal;

public class service_modal {

    // string txt for storing txt
    // and imgid for storing image id.
    private String txt;
    private int imgid;

    public service_modal(String txt, int imgid) {
        this.txt = txt;
        this.imgid = imgid;
    }

    public String getTitle() {
        return txt;
    }

    public void setTitle(String txt) {
        this.txt = txt;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
