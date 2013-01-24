package com.example.map;

import com.example.viewtut.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.WindowManager;

public class NormalView extends View {
	private Context ctx;
	Bitmap mapUW;
	int displayWidth;
	int displayHeight;
	Rect displayRect;
	Rect scrollRect;
	
	int scrollRectX=0;
	int scrollRectY=0;
	float scrollByX=0;
	float scrollByY=0;
	float startX=0;
	float startY=0;
	
	//stores the change in x and y
	int changeX;
	int changeY;
	
	ScaleGestureDetector mScaleDetector;
	float mScaleFactor=1f;
	
	Paint linePaint=new Paint();
	
	Line line1= new Line(0,0,352,235);
	Line line2= new Line(352,235,316,146);
	

	
	public NormalView(Context context) {
		super(context);
		ctx=context;
			
		//get display width and height
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	displayWidth = display.getWidth();
    	displayHeight = display.getHeight();
    	
    	
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(8);
        linePaint.setAlpha(180);
		
    	//create display and scroll rectangles
		displayRect = new Rect(0, 0, displayWidth, displayHeight);
		scrollRect = new Rect(0, 0, displayWidth, displayHeight);
		
		mapUW = BitmapFactory.decodeResource(getResources(), R.drawable.uw);//get bitmap from image called "uw"
		
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}
	
	/** ----- Begin ScaleListener Class ----- */
	public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();
	        
	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

	        invalidate();
	        return true;
	    }
	}
	
	/** ----- End ScaleListener Class ----- */
	
	@Override
    public void onDraw(Canvas canvas) {
		
		
		
	    canvas.save();
	    canvas.scale(mScaleFactor, mScaleFactor);
		
		
		// Our move updates are calculated in ACTION_MOVE in the opposite direction
        // from how we want to move the scroll rect. Think of this as dragging to
        // the left being the same as sliding the scroll rect to the right.
        int newScrollRectX = scrollRectX - (int)scrollByX;
        int newScrollRectY = scrollRectY - (int)scrollByY;

        // Don't scroll off the left or right edges of the bitmap.
        if (newScrollRectX < 0)
                newScrollRectX = 0;
        else if (newScrollRectX > (mapUW.getWidth() - displayWidth))
                newScrollRectX = (mapUW.getWidth() - displayWidth);

        // Don't scroll off the top or bottom edges of the bitmap.
        if (newScrollRectY < 0)
                newScrollRectY = 0;
        else if (newScrollRectY > (mapUW.getHeight() - displayHeight))
                newScrollRectY = (mapUW.getHeight() - displayHeight);

        // We have our updated scroll rect coordinates, set them and draw.
        scrollRect.set(newScrollRectX, newScrollRectY,
                newScrollRectX + displayWidth, newScrollRectY + displayHeight);
        Paint paint = new Paint();
        canvas.drawBitmap(mapUW, scrollRect, displayRect, paint);

        //save the difference, so the lines can be redrawn correctly
        int cx=newScrollRectX-scrollRectX;
        int cy=newScrollRectY-scrollRectY;
        changeX += -cx;
        changeY += -cy;
        // Reset current scroll coordinates to reflect the latest updates,
        // so we can repeat this update process.
        scrollRectX = newScrollRectX;
        scrollRectY = newScrollRectY;
        
        //line1.drawLine(canvas, -cx, -cy, linePaint);
        //line2.drawLine(canvas, -cx, -cy, linePaint);
    	drawAllLines(canvas,linePaint, changeX, changeY);


        
        
        canvas.restore();
    }
	
	public class Line {
		float x1;
		float y1;
		float x2;
		float y2;
		
		public Line(float x1,float y1, float x2, float y2){
			this.x1=x1;
			this.y1=y1;
			this.x2=x2;
			this.y2=y2;
		}
		
		void drawLine(Canvas canvas,float xChange,float yChange,Paint paint){
			x1+=xChange;
			y1+=yChange;
			x2+=xChange;
			y2+=yChange;
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		mScaleDetector.onTouchEvent(event); //scale detector: inspect all events
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getRawX();
	            startY = event.getRawY();
	            break;
	 
	        case MotionEvent.ACTION_MOVE:
	            float x = event.getRawX();
	            float y = event.getRawY();
	            scrollByX = x - startX;
	            scrollByY = y - startY;
	            startX = x;
	            startY = y;
	            invalidate();
	            break;
		}
	    return true;
	}
	
	public void drawAllLines(Canvas canvas,Paint paint, int changeX, int changeY) {
		Graph myGraph = new Graph();
		int i;
		for(i=0;i<myGraph.solution.size()-1;i++){
			canvas.drawLine(myGraph.points.elementAt(myGraph.solution.elementAt(i)).x +changeX,myGraph.points.elementAt(myGraph.solution.elementAt(i)).y+changeY,myGraph.points.elementAt(myGraph.solution.elementAt(i+1)).x+changeX,myGraph.points.elementAt(myGraph.solution.elementAt(i+1)).y+changeY,paint);
		}
	}
}



