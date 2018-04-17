package com.ut.electronictraffic.interfaces;

public class RECT
  implements Comparable
{
  public int angle;
  public int direct;
  public int h;
  public int w;
  public int x;
  public int y;

  public int compareTo(Object paramObject)
  {
    return 0;
  }

  public int compareTo(Object paramObject1, Object paramObject2)
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof RECT)) && (((RECT)paramObject).x == this.x) && (((RECT)paramObject).y == this.y) && (((RECT)paramObject).direct == this.direct) && (((RECT)paramObject).angle == this.angle);
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.interfaces.RECT
 * JD-Core Version:    0.6.0
 */