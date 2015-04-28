package com.glass.cuxtomcam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CameraOverlay extends View {

    // level
    private Paint mPaint = new Paint();
    private float mAngle = 0.f;
    private Paint mPaint2 = new Paint();
    private float mAngle2 = 0.f;
	public enum Mode {PLAIN, RECORDING, FOCUS, DISABLED}
	
	private Mode mode = Mode.PLAIN;
	public CameraOverlay(Context context) {
		super(context);
        mPaint.setColor(Color.BLUE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mPaint2.setColor(Color.RED);
        mPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStrokeWidth(5);
	}

    // overlay
    /**
     * Set the angle of the level line.
     *
     * @param angle Angle of the level line.
     */
    public void setAngle(float angle) {
        float oldAngle = mAngle;

        // Redraw the line.
        if (Math.abs(angle - oldAngle) > 0.05) {
            mAngle = angle;
            invalidate();

        }
    }
    public float getAngle() {
        return mAngle;
    }

    public void setAngle2(float angle) {
        float oldAngle = mAngle2;

        if (Math.abs(angle - oldAngle) > 0.05) {
            mAngle2 = angle;
            invalidate();
        }

    }

    public void setBitmap(Bitmap newBitmap) {
        mBitmap = newBitmap;
        invalidate();

    }
	
	public CameraOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
        mPaint.setColor(Color.BLUE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mPaint2.setColor(Color.RED);
        mPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStrokeWidth(5);
        mBitmap = null;
	}
	Bitmap mBitmap;
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		// nothing gets drawn :(
        int width = canvas.getWidth();
        int height = canvas.getHeight() / 2;
        /*if (mBitmap != null) {
            Bitmap scaledBitmap = mBitmap.createScaledBitmap(mBitmap, width,height,false);
            Paint myPaint = new Paint();
            myPaint.setColor(Color.WHITE);
            myPaint.setStrokeWidth(5);
            myPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            canvas.drawBitmap(scaledBitmap,0,0,myPaint);
        }*/
        // Compute the coordinates.
        float y = (float) Math.tan(mAngle) * width / 2;

        // Draw the level line.
        canvas.drawLine(0, y + height, width, -y + height, mPaint);

        float y2 = (float) Math.tan(mAngle2) * width / 2;
        canvas.drawLine(0, y2 + height, width, -y2 + height, mPaint2);
		/*float midX = canvas.getWidth()/2;
		float midY = canvas.getHeight()/2;
		float reticleSizeX = canvas.getWidth()/6;
		float reticleSizeY = canvas.getHeight()/6;
		
		float leftX = midX - reticleSizeX;
		float rightX = midX + reticleSizeX;
		float topY = midY - reticleSizeY;
		float bottomY = midY + reticleSizeY;
		
		if (this.mode == Mode.RECORDING) {
			p.setColor(Color.RED);
		    p.setStyle(Paint.Style.FILL);
		    float radius = 20;
		    float offset = 40;
		    canvas.drawCircle(offset + radius, offset+radius, radius, p);
		} else if (this.mode == Mode.DISABLED){
			p.setColor(Color.RED);
			p.setStrokeWidth(3);
			
			//Rectangle with an X through it
			canvas.drawRect(new RectF(leftX,topY,rightX,bottomY), p);
			canvas.drawLines(new float[]{leftX, topY, rightX, bottomY,
					rightX, topY, leftX, bottomY}, p);
		} else {
			p.setColor(this.mode == Mode.FOCUS ? Color.GREEN : Color.WHITE);
			p.setStrokeWidth(3);
			
			//two angle brackets, one in the top left corner, one in the bottom right
			canvas.drawLines(new float[]{leftX, topY, midX, topY,
					leftX, topY, leftX, midY,
					rightX, bottomY, midX, bottomY,
					rightX, bottomY, rightX, midY}, p);
		}*/
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
		this.invalidate();
	}
}
