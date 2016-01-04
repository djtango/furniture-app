package com.example.deon.furnituar;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class StrokedRectangle {

    private float[] _projectionMatrix = new float[16];
    private float[] _viewMatrix = new float[16];

    private boolean _faceDetected = false;

    public enum Type {
        FACE, STANDARD, EXTENDED
    }

    private final static String TAG = "StrokedRectangle";

    String _fragmentShaderCode =
            "precision mediump float;" +
            "void main()" +
            "{" +
            " gl_FragColor = vec4(1.0, 0.58, 0.16, 1.0);" +
            "}";

    String _vertexShaderCode =
            "attribute vec4 v_position;" +
            "uniform mat4 Projection;" +
            "uniform mat4 ModelView;" +
            "void main()" +
            "{" +
            "gl_Position = Projection * ModelView * v_position;" +
            "}";

    private int _augmentationProgram = -1;
    private int _positionSlot;
    private int _projectionUniform;
    private int _modelViewUniform;

    static float rectVertsFace[] = {
            -0.5f, -0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f, -0.5f, 0.0f };

    static float rectVerts[] = {
            -160, -160, 0.0f,
            -160,  160, 0.0f,
            160,  160, 0.0f,
            160, -160, 0.0f };

    static float rectVertsExtended[] = {
            -230, -230, 0.0f,
            -230,  230, 0.0f,
            230,  230, 0.0f,
            230, -230, 0.0f };

    private final ShortBuffer _lindicesBuffer;
    private final FloatBuffer rectBuffer;

    private final short _lindices[] = { 0, 1, 2, 3 };

    public StrokedRectangle() {
        this(Type.STANDARD);
    }

    public StrokedRectangle(Type type) {
        ByteBuffer dlb = ByteBuffer.allocateDirect(_lindices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        _lindicesBuffer = dlb.asShortBuffer();
        _lindicesBuffer.put(_lindices);
        _lindicesBuffer.position(0);

        ByteBuffer bb = ByteBuffer.allocateDirect(rectVerts.length * 4);
        bb.order(ByteOrder.nativeOrder());
        rectBuffer = bb.asFloatBuffer();
        if (type == Type.EXTENDED) {
            rectBuffer.put(rectVertsExtended);
        } else if (type == Type.FACE) {
            rectBuffer.put(rectVertsFace);
        } else {
            rectBuffer.put(rectVertsFace);
        }
        rectBuffer.position(0);
    }

    public void setProjectionMatrix(float[] _projectionMatrix) {
        this._projectionMatrix = _projectionMatrix;
    }

    public void setViewMatrix(float[] _viewMatrix) {
        this._viewMatrix = _viewMatrix;
        this._faceDetected = true;
    }

    public void onFaceLost() {
        this._faceDetected = false;
    }

    public void onDrawFrame() {
        if (_faceDetected) {
            if (_augmentationProgram == -1) {
                compileShaders();
                _positionSlot = GLES20.glGetAttribLocation(_augmentationProgram, "v_position");
                _modelViewUniform = GLES20.glGetUniformLocation(_augmentationProgram, "ModelView");
                _projectionUniform = GLES20.glGetUniformLocation(_augmentationProgram, "Projection");
                GLES20.glDisable(GLES20.GL_DEPTH_TEST);
                GLES20.glLineWidth(10.0f);
            }

            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            GLES20.glUseProgram(_augmentationProgram);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

            GLES20.glVertexAttribPointer(_positionSlot, 3, GLES20.GL_FLOAT, false, 0, rectBuffer);
            GLES20.glEnableVertexAttribArray(_positionSlot);

            GLES20.glUniformMatrix4fv(_projectionUniform, 1, false, _projectionMatrix, 0);
            GLES20.glUniformMatrix4fv(_modelViewUniform, 1, false, _viewMatrix, 0);

            GLES20.glDrawElements(GLES20.GL_LINE_LOOP, _lindices.length, GLES20.GL_UNSIGNED_SHORT, _lindicesBuffer);
        }
    }

    private void compileShaders() {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, _vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, _fragmentShaderCode);
        _augmentationProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(_augmentationProgram, vertexShader);
        GLES20.glAttachShader(_augmentationProgram, fragmentShader);
        GLES20.glLinkProgram(_augmentationProgram);
    }

    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
