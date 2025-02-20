package com.otitan.xnbhq.supertreeview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aspsine on 15/10/11.
 *
 * lsitview item 自定义实体类
 */
public class Line implements Parcelable {
    private int num;
    private String text;
    private String tview;
	private boolean focus;

    public Line() {
    }

    protected Line(Parcel in) {
        num = in.readInt();
        text = in.readString();
        tview = in.readString();
        focus = in.readByte() != 0;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
    
    public String getTview() {
		return tview;
	}

	public void setTview(String tview) {
		this.tview = tview;
	}


    public static final Creator<Line> CREATOR = new Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel in) {
            return new Line(in);
        }

        @Override
        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(num);
        dest.writeString(text);
        dest.writeString(tview);
        dest.writeByte((byte) (focus ? 1 : 0));
    }
}
