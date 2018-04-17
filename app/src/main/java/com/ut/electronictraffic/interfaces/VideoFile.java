package com.ut.electronictraffic.interfaces;

import java.io.Serializable;

public class VideoFile
  implements Comparable<VideoFile>, Serializable
{
  private int mIcon;
  private boolean mSelectable = true;
  private String mSummay = "";
  private String mText = "";

  public VideoFile(String paramString1, String paramString2, int paramInt)
  {
    this.mIcon = paramInt;
    this.mText = paramString1;
    this.mSummay = paramString2;
  }

  public int compareTo(VideoFile paramVideoFile)
  {
    if (this.mText != null)
      return this.mText.compareTo(paramVideoFile.getText());
    throw new IllegalArgumentException();
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    do
    {
      return false;
      if (paramObject == this)
        return true;
    }
    while ((paramObject.getClass() != getClass()) || (!((VideoFile)paramObject).mText.equals(this.mText)));
    return true;
  }

  public int getIcon()
  {
    return this.mIcon;
  }

  public String getSummayText()
  {
    return this.mSummay;
  }

  public String getText()
  {
    return this.mText;
  }

  public boolean isSelectable()
  {
    return this.mSelectable;
  }

  public void setIcon(int paramInt)
  {
    this.mIcon = paramInt;
  }

  public void setSelectable(boolean paramBoolean)
  {
    this.mSelectable = paramBoolean;
  }

  public void setSummayText(String paramString)
  {
    this.mSummay = paramString;
  }

  public void setText(String paramString)
  {
    this.mText = paramString;
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.interfaces.VideoFile
 * JD-Core Version:    0.6.0
 */