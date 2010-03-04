package krati.mds.impl.array.entry;

import java.io.IOException;
import java.io.RandomAccessFile;

import krati.io.ChannelWriter;
import krati.io.DataWriteChannel;

/**
 * EntryValueInt.
 * 
 * @author jwu
 */
public class EntryValueInt extends EntryValue
{
  public final int val;
  
  public EntryValueInt(int pos, int val, long scn)
  {
    super(pos, scn);
    this.val = val;
  }
  
  @Override
  public String toString()
  {
    return pos + ":" + val + ":" + scn;
  }
  
  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null) return false;
    
    if(o instanceof EntryValueInt)
    {
      EntryValueInt v = (EntryValueInt)o;
      return (pos == v.pos) && (val == v.val) && (scn == v.scn);
    }
    else
    {
      return false;
    }
  }
  
  @Override
  public int hashCode()
  {
    int result;
    result = pos/29 + val/113;
    result = 19 * result + (int) (scn ^ (scn >>> 32));
    return result;
  }
  
  /**
   * Writes this EntryValue to entry log file via a channel writer.
   * 
   * @param writer
   * @throws IOException
   */
  @Override
  public void write(DataWriteChannel writer) throws IOException
  {
    writer.writeInt(pos);  /* array position */
    writer.writeInt(val);  /* data value     */
    writer.writeLong(scn); /* SCN value      */
  }
  
  /**
   * Writes this EntryValue to an random access file at a given position.
   * 
   * @param raf
   * @param position
   * @throws IOException
   */
  @Override
  public void updateArrayFile(RandomAccessFile raf, long position) throws IOException
  {
    raf.seek(position);
    raf.writeInt(val);
  }
  
  /**
   * Writes this EntryValue to a file channel at a given position.
   * 
   * @param writer
   * @param position
   * @throws IOException
   */
  @Override
  public void updateArrayFile(ChannelWriter writer, long position) throws IOException
  {
    writer.writeInt(position, val);
  }
}
