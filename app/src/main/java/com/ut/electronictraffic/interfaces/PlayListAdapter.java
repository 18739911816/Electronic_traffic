package com.ut.electronictraffic.interfaces;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class PlayListAdapter extends BaseAdapter
{
  private static boolean Allvisflag;
  private static boolean visflag = false;
  private Context mContext = null;
  private LayoutInflater mInflater = null;
  private List<VideoFile> mItems = new ArrayList();
  private int selectedItem = -1;

  static
  {
    Allvisflag = false;
  }

  public PlayListAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }

  public void addItem(VideoFile paramVideoFile)
  {
    this.mItems.add(paramVideoFile);
  }

  public boolean areAllItemsSelectable()
  {
    return false;
  }

  public int getCount()
  {
    return this.mItems.size();
  }

  public Object getItem(int paramInt)
  {
    return this.mItems.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    if (paramView == null)
    {
      localViewHolder = new ViewHolder();
      paramView = this.mInflater.inflate(2130903041, null);
      localViewHolder.title = ((TextView)paramView.findViewById(2131296278));
      localViewHolder.img = ((ImageView)paramView.findViewById(2131296277));
      localViewHolder.summay = ((TextView)paramView.findViewById(2131296280));
      paramView.setTag(localViewHolder);
    }
    while (true)
    {
      VideoFile localVideoFile = (VideoFile)this.mItems.get(paramInt);
      localViewHolder.title.setText(localVideoFile.getText());
      localViewHolder.img.setImageDrawable(this.mContext.getResources().getDrawable(localVideoFile.getIcon()));
      localViewHolder.summay.setText(localVideoFile.getSummayText());
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
    }
  }

  public boolean isSelectable(int paramInt)
  {
    return ((VideoFile)this.mItems.get(paramInt)).isSelectable();
  }

  public void setAllvisflag(boolean paramBoolean)
  {
    Allvisflag = paramBoolean;
  }

  public void setListItems(List<VideoFile> paramList)
  {
    this.mItems = paramList;
  }

  public void setSelectedItem(int paramInt)
  {
    this.selectedItem = paramInt;
  }

  public void setvisflag(boolean paramBoolean)
  {
    visflag = paramBoolean;
  }

  static class ViewHolder
  {
    public ImageView img;
    public TextView summay;
    public TextView title;
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.interfaces.PlayListAdapter
 * JD-Core Version:    0.6.0
 */