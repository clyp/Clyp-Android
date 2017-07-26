package it.clyp.clyp.Ui;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lite20 on 7/23/2017.
 */

public class Visualizer extends GLSurfaceView {
     public Visualizer(Context context) {
         super(context);
         setRenderer(new VisualizerRenderer());
     }
}

class VisualizerRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}