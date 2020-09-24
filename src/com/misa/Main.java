package com.misa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.NoSuchElementException;


public class Main {

    public static void main(String[] args) {
        try (FileOutputStream binFile = new FileOutputStream("data.dat");
            FileChannel binChannel = binFile.getChannel()){
            ByteBuffer buffer = ByteBuffer.allocate(100);

            //with long, there are written start positions of every integer
            byte[] outputBytes = "Hello World".getBytes();
            byte[] outputBytes2 = "Gello".getBytes();
            buffer.put(outputBytes);
            long int1Pos = outputBytes.length;
            //number of bytes in the string will be the start position of 245
            buffer.putInt(245);
            long int2Pos = int1Pos + Integer.BYTES;
            //start position of -98165 is bytes of string + integer
            buffer.putInt(-98165);
            buffer.put(outputBytes2);
            long int3Pos = int2Pos + Integer.BYTES + outputBytes2.length;
            buffer.putInt(1000);
            buffer.flip();
            binChannel.write(buffer);

            //reading in reverse:
            RandomAccessFile raf = new RandomAccessFile("data.dat", "rwd");
            FileChannel fileChannel = raf.getChannel();
            ByteBuffer readBuffer2 = ByteBuffer.allocate(Integer.BYTES);

            fileChannel.position(int3Pos);
            fileChannel.read(readBuffer2);
            readBuffer2.flip();
            System.out.println("int 3 " + readBuffer2.getInt());

            readBuffer2.flip();
            fileChannel.position(int2Pos);
            fileChannel.read(readBuffer2);
            readBuffer2.flip();
            System.out.println("int 2 " + readBuffer2.getInt());

            readBuffer2.flip();
            fileChannel.position(int1Pos);
            fileChannel.read(readBuffer2);
            readBuffer2.flip();
            System.out.println("int 1 " + readBuffer2.getInt());

            RandomAccessFile copyFile = new RandomAccessFile("datacopy.dat", "rw");
            FileChannel copyChannel = copyFile.getChannel();
            fileChannel.position(0);
//            long numTransferred = copyChannel.transferFrom(fileChannel, 0, fileChannel.size());
            long numTransferred = fileChannel.transferTo(0, fileChannel.size(), fileChannel);
            System.out.println("Num transferred = " + numTransferred);
            fileChannel.close();
            raf.close();
            copyChannel.close();



//            //calculating positions
//            byte[] outputString = "Hello World".getBytes();
//            long str1Pos = 0;
//            long newInt1Pos = outputBytes.length;
//            long newInt2Pos = newInt1Pos + Integer.BYTES;
//            byte[] outputSring2 = "Gello".getBytes();
//            long str2Pos = newInt2Pos + Integer.BYTES;
//            long newInt3Pos = str2Pos + outputSring2.length;
//
//            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
//            intBuffer.putInt(245);
//            intBuffer.flip();
//            binChannel.position(newInt1Pos);
//            binChannel.write(intBuffer);
//            intBuffer.flip();
//
//            System.out.println(intBuffer.getInt());

            //Random access file with nio


//            // buffer is backed by the byte[]
//            // position is set to zero
////            ByteBuffer buffer = ByteBuffer.wrap(outputBytes);
//            //code above can be written like:
//            ByteBuffer buffer = ByteBuffer.allocate(outputBytes.length);
//            buffer.put(outputBytes);
//            buffer.flip();
//
//            int numBytes = binChannel.write(buffer);
//            System.out.println("numBytes written " + numBytes);
//
//            // creating a buffer that we can use every time we want to write an int
//            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);      //passing the size we want buffer to be (number of bytes in int)
//            intBuffer.putInt(245);                                          //writing
//            intBuffer.flip();                                               //resetting buffer's positions to zero, to start from beginning
//            numBytes = binChannel.write(intBuffer);
//            System.out.println("numBytes written " + numBytes);
//
//            intBuffer.flip();                                               //two times position is reseted because the same buffer is used
//            intBuffer.putInt(-98765);
//            intBuffer.flip();
//            numBytes = binChannel.write(intBuffer);
//            System.out.println("numBytes written " + numBytes);
//
//            //input with nio
//            RandomAccessFile raf = new RandomAccessFile("data.dat", "rwd");
//            FileChannel channel = raf.getChannel();
//            outputBytes[0] = 'a';
//            buffer.flip();
//            long numBytesRead = channel.read(buffer);
//            if(buffer.hasArray()){
//                System.out.println("byte buffer = " + new String(buffer.array()));
////                System.out.println("byte byffer = " + new String(outputBytes));
//            }
//            //absolute read
//            intBuffer.flip();
//            numBytesRead = channel.read(intBuffer);
//            System.out.println(intBuffer.getInt(0));
//            intBuffer.flip();
//            numBytesRead = channel.read(intBuffer);
//            System.out.println(intBuffer.getInt(0));

            //relative read
//            intBuffer.flip();
//            numBytesRead = channel.read(intBuffer);
//            intBuffer.flip();
//            System.out.println(intBuffer.getInt());
//            intBuffer.flip();
//            numBytesRead = channel.read(intBuffer);
//            intBuffer.flip();
//            System.out.println(intBuffer.getInt());
//            channel.close();
//            raf.close();

            //input with i/o
//            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
//            byte[] b = new byte[outputBytes.length];            //defying number of bytes to read
//            ra.read(b);
//            System.out.println(new String(b));  //converting byte[] to a String
//
//
//            long int1 = ra.readInt();
//            long int2 = ra.readInt();
//            System.out.println(int1 + "\n" + int2);

        } catch (IOException e) {
            System.out.println("IO " + e.getMessage());
        } catch (NoSuchElementException e){
            System.out.println("NSE " + e.getMessage());
        }
    }
}
