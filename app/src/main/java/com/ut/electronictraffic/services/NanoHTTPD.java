package com.ut.electronictraffic.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

public abstract class NanoHTTPD
{
  public static final String MIME_HTML = "text/html";
  public static final String MIME_PLAINTEXT = "text/plain";
  private static final String QUERY_STRING_PARAMETER = "NanoHttpd.QUERY_STRING";
  public static final int SOCKET_READ_TIMEOUT = 5000;
  private AsyncRunner asyncRunner;
  private final String hostname;
  private final int myPort;
  private ServerSocket myServerSocket;
  private Thread myThread;
  private Set<Socket> openConnections = new HashSet();
  private TempFileManagerFactory tempFileManagerFactory;

  public NanoHTTPD(int paramInt)
  {
    this(null, paramInt);
  }

  public NanoHTTPD(String paramString, int paramInt)
  {
    this.hostname = paramString;
    this.myPort = paramInt;
    setTempFileManagerFactory(new DefaultTempFileManagerFactory(null));
    setAsyncRunner(new DefaultAsyncRunner());
  }

  private static final void safeClose(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  private static final void safeClose(ServerSocket paramServerSocket)
  {
    if (paramServerSocket != null);
    try
    {
      paramServerSocket.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  private static final void safeClose(Socket paramSocket)
  {
    if (paramSocket != null);
    try
    {
      paramSocket.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public void closeAllConnections()
  {
    monitorenter;
    try
    {
      Iterator localIterator = this.openConnections.iterator();
      while (true)
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
          return;
        safeClose((Socket)localIterator.next());
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  protected Map<String, List<String>> decodeParameters(String paramString)
  {
    HashMap localHashMap = new HashMap();
    StringTokenizer localStringTokenizer;
    if (paramString != null)
      localStringTokenizer = new StringTokenizer(paramString, "&");
    label157: label161: 
    while (true)
    {
      if (!localStringTokenizer.hasMoreTokens())
        return localHashMap;
      String str1 = localStringTokenizer.nextToken();
      int i = str1.indexOf('=');
      String str2;
      if (i >= 0)
      {
        str2 = decodePercent(str1.substring(0, i)).trim();
        label69: if (!localHashMap.containsKey(str2))
          localHashMap.put(str2, new ArrayList());
        if (i < 0)
          break label157;
      }
      for (String str3 = decodePercent(str1.substring(i + 1)); ; str3 = null)
      {
        if (str3 == null)
          break label161;
        ((List)localHashMap.get(str2)).add(str3);
        break;
        str2 = decodePercent(str1).trim();
        break label69;
      }
    }
  }

  protected Map<String, List<String>> decodeParameters(Map<String, String> paramMap)
  {
    return decodeParameters((String)paramMap.get("NanoHttpd.QUERY_STRING"));
  }

  protected String decodePercent(String paramString)
  {
    try
    {
      String str = URLDecoder.decode(paramString, "UTF8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return null;
  }

  public final int getListeningPort()
  {
    if (this.myServerSocket == null)
      return -1;
    return this.myServerSocket.getLocalPort();
  }

  public final boolean isAlive()
  {
    return (wasStarted()) && (!this.myServerSocket.isClosed()) && (this.myThread.isAlive());
  }

  public void registerConnection(Socket paramSocket)
  {
    monitorenter;
    try
    {
      this.openConnections.add(paramSocket);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public Response serve(IHTTPSession paramIHTTPSession)
  {
    HashMap localHashMap = new HashMap();
    Method localMethod = paramIHTTPSession.getMethod();
    if ((Method.PUT.equals(localMethod)) || (Method.POST.equals(localMethod)));
    try
    {
      paramIHTTPSession.parseBody(localHashMap);
      Map localMap = paramIHTTPSession.getParms();
      localMap.put("NanoHttpd.QUERY_STRING", paramIHTTPSession.getQueryParameterString());
      return serve(paramIHTTPSession.getUri(), localMethod, paramIHTTPSession.getHeaders(), localMap, localHashMap);
    }
    catch (IOException localIOException)
    {
      return new Response(Response.Status.INTERNAL_ERROR, "text/plain", "SERVER INTERNAL ERROR: IOException: " + localIOException.getMessage());
    }
    catch (ResponseException localResponseException)
    {
    }
    return new Response(localResponseException.getStatus(), "text/plain", localResponseException.getMessage());
  }

  @Deprecated
  public Response serve(String paramString, Method paramMethod, Map<String, String> paramMap1, Map<String, String> paramMap2, Map<String, String> paramMap3)
  {
    return new Response(Response.Status.NOT_FOUND, "text/plain", "Not Found");
  }

  public void setAsyncRunner(AsyncRunner paramAsyncRunner)
  {
    this.asyncRunner = paramAsyncRunner;
  }

  public void setTempFileManagerFactory(TempFileManagerFactory paramTempFileManagerFactory)
  {
    this.tempFileManagerFactory = paramTempFileManagerFactory;
  }

  public void start()
    throws IOException
  {
    this.myServerSocket = new ServerSocket();
    ServerSocket localServerSocket = this.myServerSocket;
    if (this.hostname != null);
    for (InetSocketAddress localInetSocketAddress = new InetSocketAddress(this.hostname, this.myPort); ; localInetSocketAddress = new InetSocketAddress(this.myPort))
    {
      localServerSocket.bind(localInetSocketAddress);
      this.myThread = new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            label53: 
            do
            {
              Socket localSocket = NanoHTTPD.this.myServerSocket.accept();
              NanoHTTPD.this.registerConnection(localSocket);
              localSocket.setSoTimeout(5000);
              InputStream localInputStream = localSocket.getInputStream();
              NanoHTTPD.this.asyncRunner.exec(new Runnable(localInputStream, localSocket)
              {
                public void run()
                {
                  OutputStream localOutputStream = null;
                  try
                  {
                    localOutputStream = this.val$finalAccept.getOutputStream();
                    TempFileManager localTempFileManager = NanoHTTPD.this.tempFileManagerFactory.create();
                    HTTPSession localHTTPSession = new HTTPSession(NanoHTTPD.this, localTempFileManager, this.val$inputStream, localOutputStream, this.val$finalAccept.getInetAddress());
                    while (true)
                    {
                      boolean bool = this.val$finalAccept.isClosed();
                      if (bool)
                        return;
                      localHTTPSession.execute();
                    }
                  }
                  catch (Exception localException)
                  {
                    if ((!(localException instanceof SocketException)) || (!"NanoHttpd Shutdown".equals(localException.getMessage())))
                      localException.printStackTrace();
                    return;
                  }
                  finally
                  {
                    NanoHTTPD.access$0(localOutputStream);
                    NanoHTTPD.access$0(this.val$inputStream);
                    NanoHTTPD.access$3(this.val$finalAccept);
                    NanoHTTPD.this.unRegisterConnection(this.val$finalAccept);
                  }
                  throw localObject;
                }
              });
            }
            while (!NanoHTTPD.this.myServerSocket.isClosed());
            return;
          }
          catch (IOException localIOException)
          {
            break label53;
          }
        }
      });
      this.myThread.setDaemon(true);
      this.myThread.setName("NanoHttpd Main Listener");
      this.myThread.start();
      return;
    }
  }

  public void stop()
  {
    try
    {
      safeClose(this.myServerSocket);
      closeAllConnections();
      this.myThread.join();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void unRegisterConnection(Socket paramSocket)
  {
    monitorenter;
    try
    {
      this.openConnections.remove(paramSocket);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public final boolean wasStarted()
  {
    return (this.myServerSocket != null) && (this.myThread != null);
  }

  public static abstract interface AsyncRunner
  {
    public abstract void exec(Runnable paramRunnable);
  }

  public static class Cookie
  {
    private String e;
    private String n;
    private String v;

    public Cookie(String paramString1, String paramString2)
    {
      this(paramString1, paramString2, 30);
    }

    public Cookie(String paramString1, String paramString2, int paramInt)
    {
      this.n = paramString1;
      this.v = paramString2;
      this.e = getHTTPTime(paramInt);
    }

    public Cookie(String paramString1, String paramString2, String paramString3)
    {
      this.n = paramString1;
      this.v = paramString2;
      this.e = paramString3;
    }

    public static String getHTTPTime(int paramInt)
    {
      Calendar localCalendar = Calendar.getInstance();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
      localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      localCalendar.add(5, paramInt);
      return localSimpleDateFormat.format(localCalendar.getTime());
    }

    public String getHTTPHeader()
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this.n;
      arrayOfObject[1] = this.v;
      arrayOfObject[2] = this.e;
      return String.format("%s=%s; expires=%s", arrayOfObject);
    }
  }

  public class CookieHandler
    implements Iterable<String>
  {
    private HashMap<String, String> cookies = new HashMap();
    private ArrayList<Cookie> queue = new ArrayList();

    public CookieHandler()
    {
      Object localObject;
      String str = (String)localObject.get("cookie");
      String[] arrayOfString1;
      int i;
      if (str != null)
      {
        arrayOfString1 = str.split(";");
        i = arrayOfString1.length;
      }
      for (int j = 0; ; j++)
      {
        if (j >= i)
          return;
        String[] arrayOfString2 = arrayOfString1[j].trim().split("=");
        if (arrayOfString2.length != 2)
          continue;
        this.cookies.put(arrayOfString2[0], arrayOfString2[1]);
      }
    }

    public void delete(String paramString)
    {
      set(paramString, "-delete-", -30);
    }

    public Iterator<String> iterator()
    {
      return this.cookies.keySet().iterator();
    }

    public String read(String paramString)
    {
      return (String)this.cookies.get(paramString);
    }

    public void set(Cookie paramCookie)
    {
      this.queue.add(paramCookie);
    }

    public void set(String paramString1, String paramString2, int paramInt)
    {
      this.queue.add(new Cookie(paramString1, paramString2, Cookie.getHTTPTime(paramInt)));
    }

    public void unloadQueue(Response paramResponse)
    {
      Iterator localIterator = this.queue.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        paramResponse.addHeader("Set-Cookie", ((Cookie)localIterator.next()).getHTTPHeader());
      }
    }
  }

  public static class DefaultAsyncRunner
    implements AsyncRunner
  {
    private long requestCount;

    public void exec(Runnable paramRunnable)
    {
      this.requestCount = (1L + this.requestCount);
      Thread localThread = new Thread(paramRunnable);
      localThread.setDaemon(true);
      localThread.setName("NanoHttpd Request Processor (#" + this.requestCount + ")");
      localThread.start();
    }
  }

  public static class DefaultTempFile
    implements TempFile
  {
    private File file;
    private OutputStream fstream;

    public DefaultTempFile(String paramString)
      throws IOException
    {
      this.file = File.createTempFile("NanoHTTPD-", "", new File(paramString));
      this.fstream = new FileOutputStream(this.file);
    }

    public void delete()
      throws Exception
    {
      NanoHTTPD.access$0(this.fstream);
      this.file.delete();
    }

    public String getName()
    {
      return this.file.getAbsolutePath();
    }

    public OutputStream open()
      throws Exception
    {
      return this.fstream;
    }
  }

  public static class DefaultTempFileManager
    implements TempFileManager
  {
    private final List<TempFile> tempFiles = new ArrayList();
    private final String tmpdir = System.getProperty("java.io.tmpdir");

    public void clear()
    {
      Iterator localIterator = this.tempFiles.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          this.tempFiles.clear();
          return;
        }
        TempFile localTempFile = (TempFile)localIterator.next();
        try
        {
          localTempFile.delete();
        }
        catch (Exception localException)
        {
        }
      }
    }

    public TempFile createTempFile()
      throws Exception
    {
      DefaultTempFile localDefaultTempFile = new DefaultTempFile(this.tmpdir);
      this.tempFiles.add(localDefaultTempFile);
      return localDefaultTempFile;
    }
  }

  private class DefaultTempFileManagerFactory
    implements TempFileManagerFactory
  {
    private DefaultTempFileManagerFactory()
    {
    }

    public TempFileManager create()
    {
      return new DefaultTempFileManager();
    }
  }

  protected class HTTPSession
    implements IHTTPSession
  {
    public static final int BUFSIZE = 8192;
    private CookieHandler cookies;
    private Map<String, String> headers;
    private PushbackInputStream inputStream;
    private Method method;
    private final OutputStream outputStream;
    private Map<String, String> parms;
    private String queryParameterString;
    private int rlen;
    private int splitbyte;
    private final TempFileManager tempFileManager;
    private String uri;

    public HTTPSession(TempFileManager paramInputStream, InputStream paramOutputStream, OutputStream arg4)
    {
      this.tempFileManager = paramInputStream;
      this.inputStream = new PushbackInputStream(paramOutputStream, 8192);
      Object localObject;
      this.outputStream = localObject;
    }

    public HTTPSession(TempFileManager paramInputStream, InputStream paramOutputStream, OutputStream paramInetAddress, InetAddress arg5)
    {
      this.tempFileManager = paramInputStream;
      this.inputStream = new PushbackInputStream(paramOutputStream, 8192);
      this.outputStream = paramInetAddress;
      Object localObject;
      if ((localObject.isLoopbackAddress()) || (localObject.isAnyLocalAddress()));
      for (String str = "127.0.0.1"; ; str = localObject.getHostAddress().toString())
      {
        this.headers = new HashMap();
        this.headers.put("remote-addr", str);
        this.headers.put("http-client-ip", str);
        return;
      }
    }

    private void decodeHeader(BufferedReader paramBufferedReader, Map<String, String> paramMap1, Map<String, String> paramMap2, Map<String, String> paramMap3)
      throws ResponseException
    {
      StringTokenizer localStringTokenizer;
      try
      {
        String str1 = paramBufferedReader.readLine();
        if (str1 == null)
          return;
        localStringTokenizer = new StringTokenizer(str1);
        if (!localStringTokenizer.hasMoreTokens())
          throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
      }
      catch (IOException localIOException)
      {
        throw new ResponseException(Response.Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + localIOException.getMessage(), localIOException);
      }
      paramMap1.put("method", localStringTokenizer.nextToken());
      if (!localStringTokenizer.hasMoreTokens())
        throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
      String str2 = localStringTokenizer.nextToken();
      int i = str2.indexOf('?');
      String str3;
      if (i >= 0)
      {
        decodeParms(str2.substring(i + 1), paramMap2);
        str3 = NanoHTTPD.this.decodePercent(str2.substring(0, i));
        if (!localStringTokenizer.hasMoreTokens());
      }
      String str4;
      for (Object localObject = paramBufferedReader.readLine(); ; localObject = str4)
      {
        if ((localObject == null) || (((String)localObject).trim().length() <= 0))
        {
          paramMap1.put("uri", str3);
          return;
          str3 = NanoHTTPD.this.decodePercent(str2);
          break;
        }
        int j = ((String)localObject).indexOf(':');
        if (j >= 0)
          paramMap3.put(((String)localObject).substring(0, j).trim().toLowerCase(Locale.US), ((String)localObject).substring(j + 1).trim());
        str4 = paramBufferedReader.readLine();
      }
    }

    private void decodeMultipartData(String paramString, ByteBuffer paramByteBuffer, BufferedReader paramBufferedReader, Map<String, String> paramMap1, Map<String, String> paramMap2)
      throws ResponseException
    {
      String str1;
      label291: label382: 
      do
      {
        int[] arrayOfInt;
        int i;
        try
        {
          arrayOfInt = getBoundaryPositions(paramByteBuffer, paramString.getBytes());
          i = 2;
          str1 = paramBufferedReader.readLine();
          continue;
          if (!str1.contains(paramString))
            throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
        }
        catch (IOException localIOException)
        {
          ResponseException localResponseException = new ResponseException(Response.Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + localIOException.getMessage(), localIOException);
          throw localResponseException;
        }
        i++;
        HashMap localHashMap1 = new HashMap();
        str1 = paramBufferedReader.readLine();
        while (true)
        {
          String str2;
          if ((str1 == null) || (str1.trim().length() <= 0))
          {
            if (str1 == null)
              break;
            str2 = (String)localHashMap1.get("content-disposition");
            if (str2 == null)
              throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
          }
          else
          {
            int n = str1.indexOf(':');
            if (n != -1)
              localHashMap1.put(str1.substring(0, n).trim().toLowerCase(Locale.US), str1.substring(n + 1).trim());
            str1 = paramBufferedReader.readLine();
            continue;
          }
          StringTokenizer localStringTokenizer = new StringTokenizer(str2, "; ");
          HashMap localHashMap2 = new HashMap();
          String str5;
          String str6;
          if (!localStringTokenizer.hasMoreTokens())
          {
            String str4 = (String)localHashMap2.get("name");
            str5 = str4.substring(1, -1 + str4.length());
            str6 = "";
            if (localHashMap1.get("content-type") != null)
              break label465;
            if ((str1 != null) && (!str1.contains(paramString)))
              break label382;
          }
          while (true)
          {
            paramMap1.put(str5, str6);
            break label586;
            String str3 = localStringTokenizer.nextToken();
            int j = str3.indexOf('=');
            if (j == -1)
              break;
            localHashMap2.put(str3.substring(0, j).trim().toLowerCase(Locale.US), str3.substring(j + 1).trim());
            break;
            str1 = paramBufferedReader.readLine();
            if (str1 == null)
              break label291;
            int k = str1.indexOf(paramString);
            if (k == -1)
            {
              str6 = str6 + str1;
              break label291;
            }
            str6 = str6 + str1.substring(0, k - 2);
            break label291;
            if (i > arrayOfInt.length)
              throw new ResponseException(Response.Status.INTERNAL_ERROR, "Error processing request");
            int m = stripMultipartHeaders(paramByteBuffer, arrayOfInt[(i - 2)]);
            paramMap2.put(str5, saveTmpFile(paramByteBuffer, m, -4 + (arrayOfInt[(i - 1)] - m)));
            String str7 = (String)localHashMap2.get("filename");
            str6 = str7.substring(1, -1 + str7.length());
            boolean bool;
            do
            {
              str1 = paramBufferedReader.readLine();
              if (str1 == null)
                break;
              bool = str1.contains(paramString);
            }
            while (!bool);
          }
        }
      }
      while (str1 != null);
      label465:
    }

    private void decodeParms(String paramString, Map<String, String> paramMap)
    {
      if (paramString == null)
        this.queryParameterString = "";
      while (true)
      {
        return;
        this.queryParameterString = paramString;
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "&");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken();
          int i = str.indexOf('=');
          if (i >= 0)
          {
            paramMap.put(NanoHTTPD.this.decodePercent(str.substring(0, i)).trim(), NanoHTTPD.this.decodePercent(str.substring(i + 1)));
            continue;
          }
          paramMap.put(NanoHTTPD.this.decodePercent(str).trim(), "");
        }
      }
    }

    private int findHeaderEnd(byte[] paramArrayOfByte, int paramInt)
    {
      for (int i = 0; ; i++)
      {
        if (i + 3 >= paramInt)
          return 0;
        if ((paramArrayOfByte[i] == 13) && (paramArrayOfByte[(i + 1)] == 10) && (paramArrayOfByte[(i + 2)] == 13) && (paramArrayOfByte[(i + 3)] == 10))
          return i + 4;
      }
    }

    private int[] getBoundaryPositions(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte)
    {
      int i = 0;
      int j = -1;
      ArrayList localArrayList = new ArrayList();
      int k = 0;
      int[] arrayOfInt;
      if (k >= paramByteBuffer.limit())
        arrayOfInt = new int[localArrayList.size()];
      for (int m = 0; ; m++)
      {
        if (m >= arrayOfInt.length)
        {
          return arrayOfInt;
          if (paramByteBuffer.get(k) == paramArrayOfByte[i])
          {
            if (i == 0)
              j = k;
            i++;
            if (i == paramArrayOfByte.length)
            {
              localArrayList.add(Integer.valueOf(j));
              i = 0;
              j = -1;
            }
          }
          while (true)
          {
            k++;
            break;
            k -= i;
            j = -1;
            i = 0;
          }
        }
        arrayOfInt[m] = ((Integer)localArrayList.get(m)).intValue();
      }
    }

    private RandomAccessFile getTmpBucket()
    {
      try
      {
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(this.tempFileManager.createTempFile().getName(), "rw");
        return localRandomAccessFile;
      }
      catch (Exception localException)
      {
        System.err.println("Error: " + localException.getMessage());
      }
      return null;
    }

    // ERROR //
    private String saveTmpFile(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
    {
      // Byte code:
      //   0: ldc 211
      //   2: astore 4
      //   4: iload_3
      //   5: ifle +88 -> 93
      //   8: aconst_null
      //   9: astore 5
      //   11: aload_0
      //   12: getfield 39	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:tempFileManager	Lcom/ut/electronictraffic/services/NanoHTTPD$TempFileManager;
      //   15: invokeinterface 281 1 0
      //   20: astore 8
      //   22: aload_1
      //   23: invokevirtual 307	java/nio/ByteBuffer:duplicate	()Ljava/nio/ByteBuffer;
      //   26: astore 9
      //   28: new 309	java/io/FileOutputStream
      //   31: dup
      //   32: aload 8
      //   34: invokeinterface 286 1 0
      //   39: invokespecial 310	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
      //   42: astore 10
      //   44: aload 10
      //   46: invokevirtual 314	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
      //   49: astore 11
      //   51: aload 9
      //   53: iload_2
      //   54: invokevirtual 318	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
      //   57: iload_2
      //   58: iload_3
      //   59: iadd
      //   60: invokevirtual 322	java/nio/Buffer:limit	(I)Ljava/nio/Buffer;
      //   63: pop
      //   64: aload 11
      //   66: aload 9
      //   68: invokevirtual 325	java/nio/ByteBuffer:slice	()Ljava/nio/ByteBuffer;
      //   71: invokevirtual 331	java/nio/channels/FileChannel:write	(Ljava/nio/ByteBuffer;)I
      //   74: pop
      //   75: aload 8
      //   77: invokeinterface 286 1 0
      //   82: astore 14
      //   84: aload 14
      //   86: astore 4
      //   88: aload 10
      //   90: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   93: aload 4
      //   95: areturn
      //   96: astore 7
      //   98: getstatic 295	java/lang/System:err	Ljava/io/PrintStream;
      //   101: new 119	java/lang/StringBuilder
      //   104: dup
      //   105: ldc_w 297
      //   108: invokespecial 122	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   111: aload 7
      //   113: invokevirtual 298	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   116: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   119: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   122: invokevirtual 303	java/io/PrintStream:println	(Ljava/lang/String;)V
      //   125: aload 5
      //   127: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   130: aload 4
      //   132: areturn
      //   133: astore 6
      //   135: aload 5
      //   137: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   140: aload 6
      //   142: athrow
      //   143: astore 6
      //   145: aload 10
      //   147: astore 5
      //   149: goto -14 -> 135
      //   152: astore 7
      //   154: aload 10
      //   156: astore 5
      //   158: goto -60 -> 98
      //
      // Exception table:
      //   from	to	target	type
      //   11	44	96	java/lang/Exception
      //   11	44	133	finally
      //   98	125	133	finally
      //   44	84	143	finally
      //   44	84	152	java/lang/Exception
    }

    private int stripMultipartHeaders(ByteBuffer paramByteBuffer, int paramInt)
    {
      for (int i = paramInt; ; i++)
      {
        if (i >= paramByteBuffer.limit());
        do
        {
          return i + 1;
          if (paramByteBuffer.get(i) != 13)
            break;
          i++;
          if (paramByteBuffer.get(i) != 10)
            break;
          i++;
          if (paramByteBuffer.get(i) != 13)
            break;
          i++;
        }
        while (paramByteBuffer.get(i) == 10);
      }
    }

    // ERROR //
    public void execute()
      throws IOException
    {
      // Byte code:
      //   0: sipush 8192
      //   3: newarray byte
      //   5: astore 6
      //   7: aload_0
      //   8: iconst_0
      //   9: putfield 342	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:splitbyte	I
      //   12: aload_0
      //   13: iconst_0
      //   14: putfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   17: aload_0
      //   18: getfield 46	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   21: aload 6
      //   23: iconst_0
      //   24: sipush 8192
      //   27: invokevirtual 348	java/io/PushbackInputStream:read	([BII)I
      //   30: istore 8
      //   32: iload 8
      //   34: istore 9
      //   36: iload 9
      //   38: iconst_m1
      //   39: if_icmpne +493 -> 532
      //   42: aload_0
      //   43: getfield 46	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   46: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   49: aload_0
      //   50: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   53: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   56: new 338	java/net/SocketException
      //   59: dup
      //   60: ldc_w 350
      //   63: invokespecial 351	java/net/SocketException:<init>	(Ljava/lang/String;)V
      //   66: athrow
      //   67: astore 5
      //   69: aload 5
      //   71: athrow
      //   72: astore_2
      //   73: aload_0
      //   74: getfield 39	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:tempFileManager	Lcom/ut/electronictraffic/services/NanoHTTPD$TempFileManager;
      //   77: invokeinterface 354 1 0
      //   82: aload_2
      //   83: athrow
      //   84: astore 7
      //   86: aload_0
      //   87: getfield 46	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   90: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   93: aload_0
      //   94: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   97: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   100: new 338	java/net/SocketException
      //   103: dup
      //   104: ldc_w 350
      //   107: invokespecial 351	java/net/SocketException:<init>	(Ljava/lang/String;)V
      //   110: athrow
      //   111: astore 4
      //   113: aload 4
      //   115: athrow
      //   116: aload_0
      //   117: iload 9
      //   119: aload_0
      //   120: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   123: iadd
      //   124: putfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   127: aload_0
      //   128: aload_0
      //   129: aload 6
      //   131: aload_0
      //   132: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   135: invokespecial 356	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:findHeaderEnd	([BI)I
      //   138: putfield 342	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:splitbyte	I
      //   141: aload_0
      //   142: getfield 342	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:splitbyte	I
      //   145: ifle +217 -> 362
      //   148: aload_0
      //   149: getfield 342	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:splitbyte	I
      //   152: aload_0
      //   153: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   156: if_icmpge +25 -> 181
      //   159: aload_0
      //   160: getfield 46	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   163: aload 6
      //   165: aload_0
      //   166: getfield 342	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:splitbyte	I
      //   169: aload_0
      //   170: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   173: aload_0
      //   174: getfield 342	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:splitbyte	I
      //   177: isub
      //   178: invokevirtual 360	java/io/PushbackInputStream:unread	([BII)V
      //   181: aload_0
      //   182: new 62	java/util/HashMap
      //   185: dup
      //   186: invokespecial 63	java/util/HashMap:<init>	()V
      //   189: putfield 362	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:parms	Ljava/util/Map;
      //   192: aload_0
      //   193: getfield 65	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   196: ifnonnull +14 -> 210
      //   199: aload_0
      //   200: new 62	java/util/HashMap
      //   203: dup
      //   204: invokespecial 63	java/util/HashMap:<init>	()V
      //   207: putfield 65	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   210: new 92	java/io/BufferedReader
      //   213: dup
      //   214: new 364	java/io/InputStreamReader
      //   217: dup
      //   218: new 366	java/io/ByteArrayInputStream
      //   221: dup
      //   222: aload 6
      //   224: iconst_0
      //   225: aload_0
      //   226: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   229: invokespecial 368	java/io/ByteArrayInputStream:<init>	([BII)V
      //   232: invokespecial 371	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
      //   235: invokespecial 374	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   238: astore 10
      //   240: new 62	java/util/HashMap
      //   243: dup
      //   244: invokespecial 63	java/util/HashMap:<init>	()V
      //   247: astore 11
      //   249: aload_0
      //   250: aload 10
      //   252: aload 11
      //   254: aload_0
      //   255: getfield 362	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:parms	Ljava/util/Map;
      //   258: aload_0
      //   259: getfield 65	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   262: invokespecial 376	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:decodeHeader	(Ljava/io/BufferedReader;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V
      //   265: aload_0
      //   266: aload 11
      //   268: ldc 134
      //   270: invokeinterface 200 2 0
      //   275: checkcast 81	java/lang/String
      //   278: invokestatic 382	com/ut/electronictraffic/services/NanoHTTPD$Method:lookup	(Ljava/lang/String;)Lcom/ut/electronictraffic/services/NanoHTTPD$Method;
      //   281: putfield 384	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:method	Lcom/ut/electronictraffic/services/NanoHTTPD$Method;
      //   284: aload_0
      //   285: getfield 384	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:method	Lcom/ut/electronictraffic/services/NanoHTTPD$Method;
      //   288: ifnonnull +100 -> 388
      //   291: new 88	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   294: dup
      //   295: getstatic 109	com/ut/electronictraffic/services/NanoHTTPD$Response$Status:BAD_REQUEST	Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;
      //   298: ldc_w 386
      //   301: invokespecial 114	com/ut/electronictraffic/services/NanoHTTPD$ResponseException:<init>	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;Ljava/lang/String;)V
      //   304: athrow
      //   305: astore_3
      //   306: new 388	com/ut/electronictraffic/services/NanoHTTPD$Response
      //   309: dup
      //   310: getstatic 117	com/ut/electronictraffic/services/NanoHTTPD$Response$Status:INTERNAL_ERROR	Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;
      //   313: ldc_w 390
      //   316: new 119	java/lang/StringBuilder
      //   319: dup
      //   320: ldc 121
      //   322: invokespecial 122	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   325: aload_3
      //   326: invokevirtual 125	java/io/IOException:getMessage	()Ljava/lang/String;
      //   329: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   332: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   335: invokespecial 393	com/ut/electronictraffic/services/NanoHTTPD$Response:<init>	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;Ljava/lang/String;Ljava/lang/String;)V
      //   338: aload_0
      //   339: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   342: invokestatic 396	com/ut/electronictraffic/services/NanoHTTPD$Response:access$0	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response;Ljava/io/OutputStream;)V
      //   345: aload_0
      //   346: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   349: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   352: aload_0
      //   353: getfield 39	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:tempFileManager	Lcom/ut/electronictraffic/services/NanoHTTPD$TempFileManager;
      //   356: invokeinterface 354 1 0
      //   361: return
      //   362: aload_0
      //   363: getfield 46	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   366: aload 6
      //   368: aload_0
      //   369: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   372: sipush 8192
      //   375: aload_0
      //   376: getfield 344	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:rlen	I
      //   379: isub
      //   380: invokevirtual 348	java/io/PushbackInputStream:read	([BII)I
      //   383: istore 9
      //   385: goto +147 -> 532
      //   388: aload_0
      //   389: aload 11
      //   391: ldc 168
      //   393: invokeinterface 200 2 0
      //   398: checkcast 81	java/lang/String
      //   401: putfield 398	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:uri	Ljava/lang/String;
      //   404: aload_0
      //   405: new 400	com/ut/electronictraffic/services/NanoHTTPD$CookieHandler
      //   408: dup
      //   409: aload_0
      //   410: getfield 34	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:this$0	Lcom/ut/electronictraffic/services/NanoHTTPD;
      //   413: aload_0
      //   414: getfield 65	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   417: invokespecial 403	com/ut/electronictraffic/services/NanoHTTPD$CookieHandler:<init>	(Lcom/ut/electronictraffic/services/NanoHTTPD;Ljava/util/Map;)V
      //   420: putfield 405	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:cookies	Lcom/ut/electronictraffic/services/NanoHTTPD$CookieHandler;
      //   423: aload_0
      //   424: getfield 34	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:this$0	Lcom/ut/electronictraffic/services/NanoHTTPD;
      //   427: aload_0
      //   428: invokevirtual 409	com/ut/electronictraffic/services/NanoHTTPD:serve	(Lcom/ut/electronictraffic/services/NanoHTTPD$IHTTPSession;)Lcom/ut/electronictraffic/services/NanoHTTPD$Response;
      //   431: astore 12
      //   433: aload 12
      //   435: ifnonnull +60 -> 495
      //   438: new 88	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   441: dup
      //   442: getstatic 117	com/ut/electronictraffic/services/NanoHTTPD$Response$Status:INTERNAL_ERROR	Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;
      //   445: ldc_w 411
      //   448: invokespecial 114	com/ut/electronictraffic/services/NanoHTTPD$ResponseException:<init>	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;Ljava/lang/String;)V
      //   451: athrow
      //   452: astore_1
      //   453: new 388	com/ut/electronictraffic/services/NanoHTTPD$Response
      //   456: dup
      //   457: aload_1
      //   458: invokevirtual 415	com/ut/electronictraffic/services/NanoHTTPD$ResponseException:getStatus	()Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;
      //   461: ldc_w 390
      //   464: aload_1
      //   465: invokevirtual 416	com/ut/electronictraffic/services/NanoHTTPD$ResponseException:getMessage	()Ljava/lang/String;
      //   468: invokespecial 393	com/ut/electronictraffic/services/NanoHTTPD$Response:<init>	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response$Status;Ljava/lang/String;Ljava/lang/String;)V
      //   471: aload_0
      //   472: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   475: invokestatic 396	com/ut/electronictraffic/services/NanoHTTPD$Response:access$0	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response;Ljava/io/OutputStream;)V
      //   478: aload_0
      //   479: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   482: invokestatic 335	com/ut/electronictraffic/services/NanoHTTPD:access$0	(Ljava/io/Closeable;)V
      //   485: aload_0
      //   486: getfield 39	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:tempFileManager	Lcom/ut/electronictraffic/services/NanoHTTPD$TempFileManager;
      //   489: invokeinterface 354 1 0
      //   494: return
      //   495: aload_0
      //   496: getfield 405	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:cookies	Lcom/ut/electronictraffic/services/NanoHTTPD$CookieHandler;
      //   499: aload 12
      //   501: invokevirtual 420	com/ut/electronictraffic/services/NanoHTTPD$CookieHandler:unloadQueue	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response;)V
      //   504: aload 12
      //   506: aload_0
      //   507: getfield 384	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:method	Lcom/ut/electronictraffic/services/NanoHTTPD$Method;
      //   510: invokevirtual 424	com/ut/electronictraffic/services/NanoHTTPD$Response:setRequestMethod	(Lcom/ut/electronictraffic/services/NanoHTTPD$Method;)V
      //   513: aload 12
      //   515: aload_0
      //   516: getfield 48	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   519: invokestatic 396	com/ut/electronictraffic/services/NanoHTTPD$Response:access$0	(Lcom/ut/electronictraffic/services/NanoHTTPD$Response;Ljava/io/OutputStream;)V
      //   522: aload_0
      //   523: getfield 39	com/ut/electronictraffic/services/NanoHTTPD$HTTPSession:tempFileManager	Lcom/ut/electronictraffic/services/NanoHTTPD$TempFileManager;
      //   526: invokeinterface 354 1 0
      //   531: return
      //   532: iload 9
      //   534: ifgt -418 -> 116
      //   537: goto -389 -> 148
      //
      // Exception table:
      //   from	to	target	type
      //   0	17	67	java/net/SocketException
      //   17	32	67	java/net/SocketException
      //   42	67	67	java/net/SocketException
      //   86	111	67	java/net/SocketException
      //   116	148	67	java/net/SocketException
      //   148	181	67	java/net/SocketException
      //   181	210	67	java/net/SocketException
      //   210	305	67	java/net/SocketException
      //   362	385	67	java/net/SocketException
      //   388	433	67	java/net/SocketException
      //   438	452	67	java/net/SocketException
      //   495	522	67	java/net/SocketException
      //   0	17	72	finally
      //   17	32	72	finally
      //   42	67	72	finally
      //   69	72	72	finally
      //   86	111	72	finally
      //   113	116	72	finally
      //   116	148	72	finally
      //   148	181	72	finally
      //   181	210	72	finally
      //   210	305	72	finally
      //   306	352	72	finally
      //   362	385	72	finally
      //   388	433	72	finally
      //   438	452	72	finally
      //   453	485	72	finally
      //   495	522	72	finally
      //   17	32	84	java/lang/Exception
      //   0	17	111	java/net/SocketTimeoutException
      //   17	32	111	java/net/SocketTimeoutException
      //   42	67	111	java/net/SocketTimeoutException
      //   86	111	111	java/net/SocketTimeoutException
      //   116	148	111	java/net/SocketTimeoutException
      //   148	181	111	java/net/SocketTimeoutException
      //   181	210	111	java/net/SocketTimeoutException
      //   210	305	111	java/net/SocketTimeoutException
      //   362	385	111	java/net/SocketTimeoutException
      //   388	433	111	java/net/SocketTimeoutException
      //   438	452	111	java/net/SocketTimeoutException
      //   495	522	111	java/net/SocketTimeoutException
      //   0	17	305	java/io/IOException
      //   17	32	305	java/io/IOException
      //   42	67	305	java/io/IOException
      //   86	111	305	java/io/IOException
      //   116	148	305	java/io/IOException
      //   148	181	305	java/io/IOException
      //   181	210	305	java/io/IOException
      //   210	305	305	java/io/IOException
      //   362	385	305	java/io/IOException
      //   388	433	305	java/io/IOException
      //   438	452	305	java/io/IOException
      //   495	522	305	java/io/IOException
      //   0	17	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   17	32	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   42	67	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   86	111	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   116	148	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   148	181	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   181	210	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   210	305	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   362	385	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   388	433	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   438	452	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
      //   495	522	452	com/ut/electronictraffic/services/NanoHTTPD$ResponseException
    }

    public CookieHandler getCookies()
    {
      return this.cookies;
    }

    public final Map<String, String> getHeaders()
    {
      return this.headers;
    }

    public final InputStream getInputStream()
    {
      return this.inputStream;
    }

    public final Method getMethod()
    {
      return this.method;
    }

    public final Map<String, String> getParms()
    {
      return this.parms;
    }

    public String getQueryParameterString()
    {
      return this.queryParameterString;
    }

    public final String getUri()
    {
      return this.uri;
    }

    public void parseBody(Map<String, String> paramMap)
      throws IOException, ResponseException
    {
      RandomAccessFile localRandomAccessFile = null;
      while (true)
      {
        MappedByteBuffer localMappedByteBuffer;
        BufferedReader localBufferedReader;
        try
        {
          localRandomAccessFile = getTmpBucket();
          if (!this.headers.containsKey("content-length"))
            continue;
          l = Integer.parseInt((String)this.headers.get("content-length"));
          byte[] arrayOfByte = new byte[512];
          if ((this.rlen >= 0) && (l > 0L))
            continue;
          localMappedByteBuffer = localRandomAccessFile.getChannel().map(MapMode.READ_ONLY, 0L, localRandomAccessFile.length());
          localRandomAccessFile.seek(0L);
          localBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(localRandomAccessFile.getFD())));
          try
          {
            if (!Method.POST.equals(this.method))
              break label502;
            String str1 = "";
            str2 = (String)this.headers.get("content-type");
            StringTokenizer localStringTokenizer = null;
            if (str2 == null)
              continue;
            localStringTokenizer = new StringTokenizer(str2, ",; ");
            if (!localStringTokenizer.hasMoreTokens())
              continue;
            str1 = localStringTokenizer.nextToken();
            if (!"multipart/form-data".equalsIgnoreCase(str1))
              break label408;
            if (localStringTokenizer.hasMoreTokens())
              continue;
            throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
          }
          finally
          {
          }
          NanoHTTPD.access$0(localRandomAccessFile);
          NanoHTTPD.access$0(localBufferedReader);
          throw localObject1;
          if (this.splitbyte >= this.rlen)
            break label540;
          l = this.rlen - this.splitbyte;
          continue;
          this.rlen = this.inputStream.read(arrayOfByte, 0, (int)Math.min(l, 512L));
          l -= this.rlen;
          if (this.rlen <= 0)
            continue;
          int i = this.rlen;
          localRandomAccessFile.write(arrayOfByte, 0, i);
          continue;
        }
        finally
        {
          String str2;
          localBufferedReader = null;
          continue;
          String str4 = str2.substring(str2.indexOf("boundary=") + "boundary=".length(), str2.length());
          if ((!str4.startsWith("\"")) || (!str4.endsWith("\"")))
            continue;
          str4 = str4.substring(1, -1 + str4.length());
          decodeMultipartData(str4, localMappedByteBuffer, localBufferedReader, this.parms, paramMap);
          NanoHTTPD.access$0(localRandomAccessFile);
          NanoHTTPD.access$0(localBufferedReader);
          return;
        }
        label408: String str3 = "";
        StringBuilder localStringBuilder = new StringBuilder();
        char[] arrayOfChar = new char[512];
        for (int j = localBufferedReader.read(arrayOfChar); ; j = localBufferedReader.read(arrayOfChar))
        {
          if ((j < 0) || (str3.endsWith("\r\n")))
          {
            decodeParms(localStringBuilder.toString().trim(), this.parms);
            break;
          }
          str3 = String.valueOf(arrayOfChar, 0, j);
          localStringBuilder.append(str3);
        }
        label502: if (!Method.PUT.equals(this.method))
          continue;
        paramMap.put("content", saveTmpFile(localMappedByteBuffer, 0, localMappedByteBuffer.limit()));
        continue;
        label540: long l = 0L;
      }
    }
  }

  public static abstract interface IHTTPSession
  {
    public abstract void execute()
      throws IOException;

    public abstract CookieHandler getCookies();

    public abstract Map<String, String> getHeaders();

    public abstract InputStream getInputStream();

    public abstract Method getMethod();

    public abstract Map<String, String> getParms();

    public abstract String getQueryParameterString();

    public abstract String getUri();

    public abstract void parseBody(Map<String, String> paramMap)
      throws IOException, ResponseException;
  }

  public static enum Method
  {
    static
    {
      POST = new Method("POST", 2);
      DELETE = new Method("DELETE", 3);
      HEAD = new Method("HEAD", 4);
      OPTIONS = new Method("OPTIONS", 5);
      Method[] arrayOfMethod = new Method[6];
      arrayOfMethod[0] = GET;
      arrayOfMethod[1] = PUT;
      arrayOfMethod[2] = POST;
      arrayOfMethod[3] = DELETE;
      arrayOfMethod[4] = HEAD;
      arrayOfMethod[5] = OPTIONS;
      ENUM$VALUES = arrayOfMethod;
    }

    static Method lookup(String paramString)
    {
      Method[] arrayOfMethod = values();
      int i = arrayOfMethod.length;
      for (int j = 0; ; j++)
      {
        Method localMethod;
        if (j >= i)
          localMethod = null;
        do
        {
          return localMethod;
          localMethod = arrayOfMethod[j];
        }
        while (localMethod.toString().equalsIgnoreCase(paramString));
      }
    }
  }

  public static class Response
  {
    private boolean chunkedTransfer;
    private InputStream data;
    private Map<String, String> header = new HashMap();
    private String mimeType;
    private Method requestMethod;
    private Status status;

    public Response(Status paramStatus, String paramString, InputStream paramInputStream)
    {
      this.status = paramStatus;
      this.mimeType = paramString;
      this.data = paramInputStream;
    }

    public Response(Status paramStatus, String paramString1, String paramString2)
    {
      this.status = paramStatus;
      this.mimeType = paramString1;
      if (paramString2 != null);
      try
      {
        for (ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramString2.getBytes("UTF-8")); ; localByteArrayInputStream = null)
        {
          this.data = localByteArrayInputStream;
          return;
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
    }

    public Response(String paramString)
    {
      this(Status.OK, "text/html", paramString);
    }

    private void send(OutputStream paramOutputStream)
    {
      String str1 = this.mimeType;
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      try
      {
        if (this.status == null)
          throw new Error("sendResponse(): Status can't be null.");
        PrintWriter localPrintWriter = new PrintWriter(paramOutputStream);
        localPrintWriter.print("HTTP/1.1 " + this.status.getDescription() + " \r\n");
        if (str1 != null)
          localPrintWriter.print("Content-Type: " + str1 + "\r\n");
        if ((this.header == null) || (this.header.get("Date") == null))
          localPrintWriter.print("Date: " + localSimpleDateFormat.format(new Date()) + "\r\n");
        Iterator localIterator;
        if (this.header != null)
        {
          localIterator = this.header.keySet().iterator();
          if (localIterator.hasNext());
        }
        else
        {
          localPrintWriter.print("Connection: keep-alive\r\n");
          if ((this.requestMethod == Method.HEAD) || (!this.chunkedTransfer))
            break label315;
          sendAsChunked(paramOutputStream, localPrintWriter);
        }
        while (true)
        {
          paramOutputStream.flush();
          NanoHTTPD.access$0(this.data);
          return;
          String str2 = (String)localIterator.next();
          String str3 = (String)this.header.get(str2);
          localPrintWriter.print(str2 + ": " + str3 + "\r\n");
          break;
          label315: sendAsFixedLength(paramOutputStream, localPrintWriter);
        }
      }
      catch (IOException localIOException)
      {
      }
    }

    private void sendAsChunked(OutputStream paramOutputStream, PrintWriter paramPrintWriter)
      throws IOException
    {
      paramPrintWriter.print("Transfer-Encoding: chunked\r\n");
      paramPrintWriter.print("\r\n");
      paramPrintWriter.flush();
      byte[] arrayOfByte1 = "\r\n".getBytes();
      byte[] arrayOfByte2 = new byte[16384];
      while (true)
      {
        int i = this.data.read(arrayOfByte2);
        if (i <= 0)
        {
          paramOutputStream.write(String.format("0\r\n\r\n", new Object[0]).getBytes());
          return;
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        paramOutputStream.write(String.format("%x\r\n", arrayOfObject).getBytes());
        paramOutputStream.write(arrayOfByte2, 0, i);
        paramOutputStream.write(arrayOfByte1);
      }
    }

    private void sendAsFixedLength(OutputStream paramOutputStream, PrintWriter paramPrintWriter)
      throws IOException
    {
      if (this.data != null);
      byte[] arrayOfByte;
      for (int i = this.data.available(); ; i = 0)
      {
        paramPrintWriter.print("Content-Length: " + i + "\r\n");
        paramPrintWriter.print("\r\n");
        paramPrintWriter.flush();
        if ((this.requestMethod != Method.HEAD) && (this.data != null))
        {
          arrayOfByte = new byte[16384];
          if (i > 0)
            break;
        }
        label78: return;
      }
      InputStream localInputStream = this.data;
      if (i > 16384);
      for (int j = 16384; ; j = i)
      {
        int k = localInputStream.read(arrayOfByte, 0, j);
        if (k <= 0)
          break label78;
        paramOutputStream.write(arrayOfByte, 0, k);
        i -= k;
        break;
      }
    }

    public void addHeader(String paramString1, String paramString2)
    {
      this.header.put(paramString1, paramString2);
    }

    public InputStream getData()
    {
      return this.data;
    }

    public String getMimeType()
    {
      return this.mimeType;
    }

    public Method getRequestMethod()
    {
      return this.requestMethod;
    }

    public Status getStatus()
    {
      return this.status;
    }

    public void setChunkedTransfer(boolean paramBoolean)
    {
      this.chunkedTransfer = paramBoolean;
    }

    public void setData(InputStream paramInputStream)
    {
      this.data = paramInputStream;
    }

    public void setMimeType(String paramString)
    {
      this.mimeType = paramString;
    }

    public void setRequestMethod(Method paramMethod)
    {
      this.requestMethod = paramMethod;
    }

    public void setStatus(Status paramStatus)
    {
      this.status = paramStatus;
    }

    public static enum Status
    {
      private final String description;
      private final int requestStatus;

      static
      {
        CREATED = new Status("CREATED", 1, 201, "Created");
        ACCEPTED = new Status("ACCEPTED", 2, 202, "Accepted");
        NO_CONTENT = new Status("NO_CONTENT", 3, 204, "No Content");
        PARTIAL_CONTENT = new Status("PARTIAL_CONTENT", 4, 206, "Partial Content");
        REDIRECT = new Status("REDIRECT", 5, 301, "Moved Permanently");
        NOT_MODIFIED = new Status("NOT_MODIFIED", 6, 304, "Not Modified");
        BAD_REQUEST = new Status("BAD_REQUEST", 7, 400, "Bad Request");
        UNAUTHORIZED = new Status("UNAUTHORIZED", 8, 401, "Unauthorized");
        FORBIDDEN = new Status("FORBIDDEN", 9, 403, "Forbidden");
        NOT_FOUND = new Status("NOT_FOUND", 10, 404, "Not Found");
        METHOD_NOT_ALLOWED = new Status("METHOD_NOT_ALLOWED", 11, 405, "Method Not Allowed");
        RANGE_NOT_SATISFIABLE = new Status("RANGE_NOT_SATISFIABLE", 12, 416, "Requested Range Not Satisfiable");
        INTERNAL_ERROR = new Status("INTERNAL_ERROR", 13, 500, "Internal Server Error");
        Status[] arrayOfStatus = new Status[14];
        arrayOfStatus[0] = OK;
        arrayOfStatus[1] = CREATED;
        arrayOfStatus[2] = ACCEPTED;
        arrayOfStatus[3] = NO_CONTENT;
        arrayOfStatus[4] = PARTIAL_CONTENT;
        arrayOfStatus[5] = REDIRECT;
        arrayOfStatus[6] = NOT_MODIFIED;
        arrayOfStatus[7] = BAD_REQUEST;
        arrayOfStatus[8] = UNAUTHORIZED;
        arrayOfStatus[9] = FORBIDDEN;
        arrayOfStatus[10] = NOT_FOUND;
        arrayOfStatus[11] = METHOD_NOT_ALLOWED;
        arrayOfStatus[12] = RANGE_NOT_SATISFIABLE;
        arrayOfStatus[13] = INTERNAL_ERROR;
        ENUM$VALUES = arrayOfStatus;
      }

      private Status(int arg3, String arg4)
      {
        int i;
        this.requestStatus = i;
        Object localObject;
        this.description = localObject;
      }

      public String getDescription()
      {
        return this.requestStatus + " " + this.description;
      }

      public int getRequestStatus()
      {
        return this.requestStatus;
      }
    }
  }

  public static final class ResponseException extends Exception
  {
    private final Response.Status status;

    public ResponseException(Response.Status paramStatus, String paramString)
    {
      super();
      this.status = paramStatus;
    }

    public ResponseException(Response.Status paramStatus, String paramString, Exception paramException)
    {
      super(paramException);
      this.status = paramStatus;
    }

    public Response.Status getStatus()
    {
      return this.status;
    }
  }

  public static abstract interface TempFile
  {
    public abstract void delete()
      throws Exception;

    public abstract String getName();

    public abstract OutputStream open()
      throws Exception;
  }

  public static abstract interface TempFileManager
  {
    public abstract void clear();

    public abstract TempFile createTempFile()
      throws Exception;
  }

  public static abstract interface TempFileManagerFactory
  {
    public abstract TempFileManager create();
  }
}

/* Location:           C:\Users\chengpx\Desktop\Electronic_traffic_dex2jar.jar
 * Qualified Name:     com.ut.electronictraffic.services.NanoHTTPD
 * JD-Core Version:    0.6.0
 */