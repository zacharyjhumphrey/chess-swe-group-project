package backend;

import java.io.Serializable;

public class MoveData implements Serializable{
 private int x;
 private int y;
 
 public int getX() {
	 return x;
 }
 
 public int getY() {
	 return y;
 }
 
 public void setX(int xcord) {
	 x = xcord;
 }
 
 public void setY(int ycord) {
	 y = ycord;
 }
 
 public MoveData(int xcord, int ycord) {
	 x = xcord;
	 y = ycord;
 }
}
